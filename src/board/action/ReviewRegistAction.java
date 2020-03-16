package board.action;

import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;

import action.Action;
import admin.svc.ProductRegistService;
import board.svc.ReviewRegistService;
import vo.ActionForward;
import vo.ReviewBean;

public class ReviewRegistAction implements Action {

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
		reviewBean = new ReviewBean();
		reviewBean.setRev_subject(multi.getParameter("subject"));	//제목
		reviewBean.setRev_content(multi.getParameter("content"));	//내용
		reviewBean.setScore(Integer.parseInt(multi.getParameter("score")));	//별점
		reviewBean.setUser_id(multi.getParameter("user_id"));	//작성자 아이디
		reviewBean.setPro_num(Integer.parseInt(multi.getParameter("pro_num")));	//상품 번호
		String photo = "";
		if(multi.getFilesystemName("rev_photo") != null) {
			photo = multi.getFilesystemName("rev_photo");
		}
		reviewBean.setRev_photo(photo);	//리뷰 이미지
		
		ReviewRegistService reviewRegistService = new ReviewRegistService();
		boolean isRegistSuccess = reviewRegistService.registReview(reviewBean);
		
		if(isRegistSuccess) {
			forward = new ActionForward("reviewList.bo", true);
		}else {
			response.setContentType("text/html;charset=UTF-8");
			PrintWriter out = response.getWriter();
			out.println("<script>");
			out.println("alert('등록실패');");
			out.println("history.back()");
			out.println("</script>");
		}
		
		return forward;
	}

}
