package log.action;

import java.io.PrintWriter;
import java.sql.ResultSet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import action.Action;
import log.svc.IdfindService;

import vo.ActionForward;
import vo.Member;

public class IdfindAction implements Action {

	@SuppressWarnings("unused")
	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// TODO Auto-generated method stub
		Member member = null;
		ActionForward forward = null;

		// 1. form에서 전송한 값 getParameter로 받아온다.(이름, 휴대번호...)
		String name = request.getParameter("name");
		String phone = request.getParameter("phone") + request.getParameter("phone1") + request.getParameter("phone2");
		String name2 = request.getParameter("name2");
		String email = request.getParameter("email")  + "@" + request.getParameter("e_domain");
			// 2. 서비스 생성
		IdfindService idfindService = new IdfindService();

		if(request.getParameter("phone") == null) {
			member = idfindService.memberSelect1(name2,email);
			System.out.println("ㅇㅇ" + member.getUser_id());
		}else {
			member = idfindService.memberSelect(name, phone);
		}
		
		
	
		// 3.
		if (member == null) {
			response.setContentType("text/html;charset=UTF-8");
			PrintWriter out = response.getWriter();
			out.println("<script>");
			out.println("alert('아이디가 존재하지 않습니다.')");
			out.println("history.back()");
			out.println("</script>");
		} else {
			response.setContentType("text/html;charset=UTF-8");
			PrintWriter out = response.getWriter();
			out.println("<script>");
			out.println("alert('아이디는 " + member.getUser_id() + " 입니다.')");
			out.println("history.back()");
			out.println("</script>");
			
		}
		return forward;

	}

}
