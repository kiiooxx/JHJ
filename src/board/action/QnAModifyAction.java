package board.action;

import java.io.PrintWriter;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;

import action.Action;
import board.svc.QnAModifyService;
import vo.ActionForward;
import vo.QnABean;


public class QnAModifyAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// TODO Auto-generated method stub
		ActionForward forward = null;
		QnABean qnaBean = null;

		String realFolder = "";
		String saveFolder = "/upload";
		int fileSize = 5*1024*1024;
		ServletContext context = request.getServletContext();
		realFolder = context.getRealPath(saveFolder);
		MultipartRequest multi = new MultipartRequest(request, realFolder, fileSize, "UTF-8", new DefaultFileRenamePolicy());
		int qna_num = Integer.parseInt(multi.getParameter("qna_num"));	//리뷰 번호 불러오기
		
		qnaBean = new QnABean();
		qnaBean.setQna_title(multi.getParameter("subject"));	//제목
		qnaBean.setQna_content(multi.getParameter("content"));	//내용
		qnaBean.setQna_open(multi.getParameter("qna_open").charAt(0));	//공개여부
		qnaBean.setQna_step(multi.getParameter("qna_step").charAt(0));	//답변여부
		qnaBean.setQna_email(multi.getParameter("email"));	//이메일
		qnaBean.setQna_type(multi.getParameter("qna_type"));	//문의 타입
		qnaBean.setUser_id(multi.getParameter("user_id"));	//작성자 아이디
		
		int pro_num = 0;
		System.out.println("넘버 : " + multi.getParameter("pro_num"));
		if(!(multi.getParameter("pro_num") == null || multi.getParameter("pro_num").equals(""))) {
			pro_num = Integer.parseInt(multi.getParameter("pro_num"));
		}
		qnaBean.setPro_num(pro_num);
		
		String sel_num = "";
		if(multi.getParameter("sel_num") != null) {
			sel_num = multi.getParameter("sel_num");
		}
		qnaBean.setSel_num(sel_num);	//주문 번호
		
		String qna_file = multi.getParameter("qna_file2");
		if(!(multi.getFilesystemName("qna_file") == null || multi.getFilesystemName("qna_file").equals(""))) {
			qna_file = multi.getFilesystemName("qna_file");	//파일
		}
		qnaBean.setQna_file(qna_file);
		
		
		QnAModifyService qnaModifyService = new QnAModifyService();
		boolean isUpdateSuccess = qnaModifyService.modifyQnA(qnaBean, qna_num);
		
		if(isUpdateSuccess) {
			forward = new ActionForward("qnaDetail.bo?qna_num="+qna_num+"&pro_num="+qnaBean.getPro_num(), true);
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
