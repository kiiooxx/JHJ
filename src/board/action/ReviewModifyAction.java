package board.action;

import java.io.PrintWriter;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;

import action.Action;
import board.svc.ReviewModifyService;
import board.svc.ReviewRegistService;
import vo.ActionForward;
import vo.ReviewBean;

public class ReviewModifyAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// TODO Auto-generated method stub
		ActionForward forward = null;
		ReviewBean reviewBean = null;

		String realFolder = "";
		String saveFolder = "/upload";
		int fileSize = 5*1024*1024;
		ServletContext context = request.getServletContext();
		realFolder = context.getRealPath(saveFolder);
		MultipartRequest multi = new MultipartRequest(request, realFolder, fileSize, "UTF-8", new DefaultFileRenamePolicy());
		int rev_num = Integer.parseInt(multi.getParameter("rev_num"));	//리뷰 번호 불러오기
		
		reviewBean = new ReviewBean();
		reviewBean.setRev_subject(multi.getParameter("subject"));	//제목
		reviewBean.setRev_content(multi.getParameter("content"));	//내용
		reviewBean.setScore(Integer.parseInt(multi.getParameter("score")));	//별점
		reviewBean.setUser_id(multi.getParameter("user_id"));	//작성자 아이디
		reviewBean.setPro_num(Integer.parseInt(multi.getParameter("pro_num")));	//상품 번호
		
		String photo = multi.getParameter("rev_photo2");	//원래 저장되어있는 리뷰이미지를 photo 변수에 저장한다.
		
		//만약 첨부한 파일을 수정했으면..?
		if(!(multi.getFilesystemName("rev_photo") == null || multi.getFilesystemName("rev_photo").equals(""))) {
			//수정된 파일로 저장
			photo = multi.getFilesystemName("rev_photo");
		}
		reviewBean.setRev_photo(photo);	//리뷰 이미지
		
		ReviewModifyService reviewModifyService = new ReviewModifyService();
		boolean isUpdateSuccess = reviewModifyService.modifyReview(reviewBean, rev_num);
		
		if(isUpdateSuccess) {
			forward = new ActionForward("reviewDetail.bo?rev_num="+rev_num+"&pro_num="+reviewBean.getPro_num(), true);
		}else {
			response.setContentType("text/html;charset=UTF-8");
			PrintWriter out = response.getWriter();
			out.println("<script>");
			out.println("alert('수정실패');");
			out.println("history.back()");
			out.println("</script>");
		}
		
		return forward;
	}

}
