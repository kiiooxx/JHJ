package member.action;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import action.Action;
import member.svc.MemberQuitService;
import vo.ActionForward;

public class MyInfoQuitAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// TODO Auto-generated method stub
		
		ActionForward forward = null;
		HttpSession session = request.getSession();
		
		String delete_id = (String) session.getAttribute("id"); //세션은 스트링따로 붙여줘야한다.(형변환) int면 인트로하고
		MemberQuitService memberQuitSvc = new MemberQuitService();
		boolean isMemberDel = memberQuitSvc.memberDelUpdate(delete_id);
		System.out.println("del여부" + isMemberDel);
		if(!isMemberDel) {
			response.setContentType("text/html;charset=UTF-8");
			PrintWriter out = response.getWriter();
			out.println("<script>");
			out.println("alert('삭제 실패');");
			out.println("history.back()");
			out.println("</script>");
			out.close();
		}else {
			response.setContentType("text/html;charset=UTF-8");
			PrintWriter out = response.getWriter();
			out.println("<script>");
			out.println("alert('탈퇴가 완료되었습니다.');");
			out.println("location.href='logout.log';");
			out.println("</script>");
			out.close();
		}
		
		
		return forward;
	}

}
