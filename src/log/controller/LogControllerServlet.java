package log.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import action.Action;
import log.action.LoginAction;
import log.action.LoginFormAction;
import log.action.LogoutAction;
import log.svc.LoginSvc;
import vo.ActionForward;
import vo.Member;

/**
 * Servlet implementation class LogControllerServlet
 */
@WebServlet("*.log")
public class LogControllerServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LogControllerServlet() {
        super();
        // TODO Auto-generated constructor stub
    }
   
    protected void doProcess(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
    	String RequestURI = request.getRequestURI();
    	String contextPath = request.getContextPath();
    	String command = RequestURI.substring(contextPath.length());
    	ActionForward forward = null;
    	Action action = null;
    	System.out.println(command);
    	
    	
		if(command.equals("/loginForm.log")) {
    		action = new LoginFormAction();
    		try {
    			forward = action.execute(request, response);
    		}catch(Exception e) {
    			e.printStackTrace();
    		}	
    	}else if(command.equals("/login.log")) {
    		action = new LoginAction();
    		try {
    			forward = action.execute(request, response);
    		}catch(Exception e) {
    			e.printStackTrace();
    		}	
    	}else if(command.equals("/logout.log")) {
    		action = new LogoutAction();
    		try {
    			forward = action.execute(request, response);
    		}catch(Exception e) {
    			e.printStackTrace();
    		}	
    	}
    	
    	if(forward != null) {
    		if(forward.isRedirect()) {
    			response.sendRedirect(forward.getPath());
    		}else {
    			RequestDispatcher dispatcher = request.getRequestDispatcher(forward.getPath());
    			dispatcher.forward(request, response);
    		}
    	
		
		
			Cookie[] cookieArray = request.getCookies();
	    	String id = "";
	    	String passwd = "";
	    	
	    	if(cookieArray != null ) {
	    		
	    		for (int i = 0; i<cookieArray.length; i++) {
	    			if(cookieArray[i].getName().equals("passwd")) {
	    				passwd = cookieArray[i].getValue();
	    			}
	    			else if(cookieArray[i].getName().equals("id")) {
	    				id = cookieArray[i].getValue();
	    			}
	    			
	    		}
	    	
	    		LoginSvc loginService = new LoginSvc();
	    		Member loginMember = loginService. memberLogin(id );
	    		
	    		
	    		
	    		if(loginMember !=null) {
	    			RequestDispatcher dispatcher = request.getRequestDispatcher("loginSuccess.jsp");
	    			request.setAttribute("loginMember", loginMember);
	    			dispatcher.forward(request, response);
	    		}
	    		else {
	    			RequestDispatcher dispatcher =
	    			request.getRequestDispatcher("loginForm.jsp");
	    			dispatcher.forward(request, response);
	    		
	    	
	    		}
	    	}
    	}
    }
    
   
    
    
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		doProcess(request,response);

	}
		                                                                                     
		
	

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doProcess(request,response);
		 String id = request.getParameter("id");
		    String passwd = request.getParameter("passwd");
		    String useCookie = request.getParameter("useCookie");
		    LoginSvc loginService = new LoginSvc();
		    Member loginMember = loginService.memberLogin(id);
		    
		    
		    
		    if(useCookie != null) {
		    	
		    	Cookie idCookie = new Cookie("id", id);
		    	idCookie.setMaxAge(60*60*24);
		    	Cookie passwdCookie = new Cookie("passwd",passwd);
		    	passwdCookie.setMaxAge(60*60*24);
		    	response.addCookie(idCookie);
		    	response.addCookie(passwdCookie);
		    	
		    }
		    
		    	if(loginMember != null) {
		    	
		    		RequestDispatcher dispatcher = request.getRequestDispatcher("template.jsp");
		    		request.setAttribute("loginMember",loginMember);
		    		dispatcher.forward(request, response);
		    	
		    	
		    	}
		    	else {
		    		
		    		RequestDispatcher dispatcher = request.getRequestDispatcher("template.jsp");
		    		request.setAttribute("loginMember",loginMember);
		    		dispatcher.forward(request, response);
		    	}
	
		    
	}
}
