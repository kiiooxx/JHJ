package admin.action;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import action.Action;
import admin.svc.MemberGradeManagementSvc;
import vo.ActionForward;
import vo.Member;

public class MemberGradeManagementAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// TODO Auto-generated method stub
		ActionForward forward = null;
		
		String user_id[] = request.getParameter("user_id").split(",");
		System.out.println("아이디 : " + request.getParameter("user_id"));
		String grade = request.getParameter("membergrade");
		
		System.out.println("등급 : " + grade);
		
		MemberGradeManagementSvc membergrademanagementsvc = new MemberGradeManagementSvc();
		boolean isUpdateMember = false;
		isUpdateMember = membergrademanagementsvc.updateGrade(grade, user_id);
		
		if(!isUpdateMember) {
			response.setContentType("text/html;charset=UTF-8");
			PrintWriter out = response.getWriter();
			out.println("<script>");
			out.println("alert('수정 실패')");
			out.println("history.back()");
			out.println("</script>");
		}else {
			response.setContentType("text/html;charset=UTF-8");
			PrintWriter out = response.getWriter();
			out.println("<script>");
			out.println("alert('등급변경이 완료되었습니다.')");
			out.println("location.href='memberList.ad'");
			out.println("</script>");
		}
		
		return forward;
	}
	
}

	
	