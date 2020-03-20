package controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import action.Action;
import admin.action.MemberListAction;
import member.action.MyInfoAction;
import member.action.MyInfoModAction;
import member.action.MyInfoQuitAction;
import member.action.OrderAction;
import member.action.OrderDetailAction;
import member.action.IdCheckAction;
import member.action.MemberJoinProAction;
import member.action.MyInfoAction;
import vo.ActionForward;

@WebServlet("*.mem")
public class MemberController extends HttpServlet {

	protected void doProcess(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		request.setCharacterEncoding("UTF-8");
		String RequestURI = request.getRequestURI();
		String contextPath = request.getContextPath();
		String command = RequestURI.substring(contextPath.length());
		ActionForward forward = null;
		Action action = null;

		if (command.equals("/account.mem")) {

			request.setAttribute("pagefile", "/member/accountForm.jsp");
			forward = new ActionForward("/template.jsp", false);
		
		}

		System.out.println(command);

		if (command.equals("/joinForm.mem")) {
			request.setAttribute("pagefile", "/member/joinForm.jsp");
			forward = new ActionForward("/template.jsp", false);

		} else if (command.equals("/memberJoinProcess.mem")) {
			action = new MemberJoinProAction();
			try {
				forward = action.execute(request, response);
			} catch (Exception e) {
				e.printStackTrace();
				System.out.println("Controller error");
			}

		} else if (command.equals("/idCheck.mem")) {
			action = new IdCheckAction();
			try {
				forward = action.execute(request, response);
			} catch (Exception e) {

			}
		} else if (command.equals("/idCheckForm.mem")) {

			forward = new ActionForward("/member/idCheck.jsp", false);

		} else if (command.equals("/order.mem")){
			action = new OrderAction();
			try {
				forward = action.execute(request, response);
			} catch (Exception e) {
				e.printStackTrace();
				System.out.println("Controller error");
			}
		
		} else if (command.equals("/orderdetail.mem")) {
			action = new OrderDetailAction();
			try {
				forward = action.execute(request, response);
			} catch (Exception e){
				e.printStackTrace();
				System.out.println("Controller error");
			}
	
		} else if (command.equals("/myinfo.mem")) {

			action = new MyInfoAction();
			try {
				forward = action.execute(request, response);
			} catch (Exception e) {
				e.printStackTrace();
				System.out.println("Controller error");
			}
		}

		else if(command.equals("/myinfomodify.mem")){
    		action = new MyInfoModAction();
    		try {
    			forward = action.execute(request, response);
    		}catch(Exception e) {
    			e.printStackTrace();
    		}	
    		
    	}
		
		else if(command.equals("/myinfoquit.mem")){
    		action = new MyInfoQuitAction();
    		try {
    			forward = action.execute(request, response);
    		}catch(Exception e) {
    			e.printStackTrace();
    		}	
    		
    	}
		if (forward != null) {

			if (forward.isRedirect()) {
				response.sendRedirect(forward.getPath());
			} else {
				RequestDispatcher dispatcher = request.getRequestDispatcher(forward.getPath());
				dispatcher.forward(request, response);
			}

		}
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		doProcess(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		doProcess(request, response);
	}

}

