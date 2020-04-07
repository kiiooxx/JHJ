package admin.action;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import action.Action;
import admin.svc.MailModifyService;
import vo.ActionForward;
import vo.MailOption;

public class MailModifyAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		ActionForward forward = null;
		
		String col_title = request.getParameter("col_title");
		String col_content = request.getParameter("col_content");
		
		String title = request.getParameter("title");
		String content = request.getParameter("content");
		
		System.out.println("col_title:"+col_title);
		System.out.println("col_content:"+col_content);
		System.out.println("title:"+title);
		
		
		MailModifyService mailModifyService = new MailModifyService();
		boolean isSuccess = mailModifyService.modifyMailForm(col_title, title, col_content, content);
		
		if(!isSuccess) {
			response.setContentType("text/html;charset=UTF-8");
			PrintWriter out = response.getWriter();
			out.println("<script>");
			out.println("alert('수정 실패')");
			out.println("</script>");
		}else {
			
			response.setContentType("text/html;charset=UTF-8");
			PrintWriter out = response.getWriter();
			out.println("<script>");
			out.println("alert('수정사항이 저장되었습니다.')");
			out.println("window.close()");
			out.println("</script>");
		}
		
		return forward;
	}

}
