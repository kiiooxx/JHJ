package log.action;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import action.Action;
import vo.ActionForward;

public class LogoutAction implements Action{

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// TODO Auto-generated method stub
		
		HttpSession session = request.getSession();
		session.removeAttribute("id");	//아이디
		session.removeAttribute("grade");	//등급
		session.removeAttribute("cartList");	//카트
		
		request.setAttribute("pagefile", "/member/loginForm.jsp");
		ActionForward forward = new ActionForward("/template.jsp", false);
		
		return forward;
		
		
	}


}
