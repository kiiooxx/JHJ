package log.action;

import java.io.PrintWriter;
import java.sql.ResultSet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import action.Action;
import log.svc.IdFindService;

import vo.ActionForward;
import vo.Member;

public class IdFindAction implements Action {

	@SuppressWarnings("unused")
	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// TODO Auto-generated method stub
		Member member = null;
		ActionForward forward = null;
      
		// 1. form에서 전송한 값 getParameter로 받아온다.(이름, 휴대번호...)
		String name = "";
		if(!(request.getParameter("name") == null || request.getParameter("name").equals(""))) {
			name = request.getParameter("name");
		}
		
		String phone = "";
		if(!(request.getParameter("phone") == null || request.getParameter("phone1") ==null || request.getParameter("phone2") == null || request.getParameter("phone").equals("") ||  request.getParameter("phone1").equals("") || request.getParameter("phone2").equals(""))) {
			phone = request.getParameter("phone")+ request.getParameter("phone1")+ request.getParameter("phone2");
		}
				
		String name2 = "";
		if(!(request.getParameter("name2") == null || request.getParameter("name2").equals(""))) {
			name2 = request.getParameter("name2");
		}
		request.getParameter("name2");
		
		
		String email = "";
		if(!(request.getParameter("email") == null || request.getParameter("e_domain") == null || request.getParameter("email").equals("") || request.getParameter("e_domain").equals(""))) {
			email = request.getParameter("email")+ "@" + request.getParameter("e_domain");
			
		}
			// 2. 서비스 생성
      
		IdFindService idFindService = new IdFindService();

		if(phone.equals("")) {
			member = idFindService.memberSelect1(name2,email);

		}else {
			member = idFindService.memberSelect(name, phone);
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
