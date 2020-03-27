package log.action;

import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import action.Action;
import log.svc.LoginService;
import product.svc.CartListService;
import product.svc.CartQtyService;
import vo.ActionForward;
import vo.Cart;
import vo.Member;

public class LoginAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// TODO Auto-generated method stub
		ActionForward forward = null;
		String id = request.getParameter("id");
		Member member = null;
		LoginService loginService = new LoginService();
		member = loginService.memberLogin(id);
		
		
		
		
		if(member == null ) {
			response.setContentType("text/html;charset=UTF-8");
			PrintWriter out = response.getWriter();
			out.println("<script>");
			out.println("alert('아이디가 존재하지 않습니다.')");
			out.println("history.back()");
			out.println("</script>");
		}else if(!member.getUser_pw().equals(request.getParameter("pass"))) {
				response.setContentType("text/html;charset=UTF-8");
				PrintWriter out = response.getWriter();
				out.println("<script>");
				out.println("alert('아이디나 비밀번호가 잘못되었습니다.')");
				out.println("history.back()");
				out.println("</script>");
		}else {
			if(member.getUser_del()=='Y') {
				response.setContentType("text/html;charset=UTF-8");
				PrintWriter out = response.getWriter();
				out.println("<script>");
				out.println("alert('탈퇴한 회원입니다.')");
				out.println("history.back()");
				out.println("</script>");
				
			}else {
				HttpSession session = request.getSession();
				session.setAttribute("id", id);
				session.setAttribute("grade", member.getGrade());
				
				CartListService cartListService = new CartListService();
				CartQtyService cartQtyService = new CartQtyService();
				
				ArrayList<Cart> cartList = cartListService.getCartList(request);
				ArrayList<Cart> cartList2 = cartListService.getCartList(id);	//로그인한 아이디의 장바구니 불러오기
				
				if(cartList != null) {
					cartList2 = cartQtyService.addCart(cartList2, cartList, id);	//세션에 있는 장바구니를 DB에 넣기
				}
				
				
				
				forward = new ActionForward("main.pro", true);
			}
		
		}
		return forward;
	}

}
