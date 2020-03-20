package controller;

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
import log.action.LogoutAction;
import log.action.PwFindAction;
import log.action.IdFindAction;

import log.svc.LoginService;
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
    	

    	//===================로그인===================
    	//1. 로그인 페이지
		if(command.equals("/loginForm.log")) {
    		request.setAttribute("pagefile", "/member/loginForm.jsp");
    		forward = new ActionForward("/template.jsp", false);
    	}
		//2. 로그인 액션
		else if(command.equals("/login.log")) {
    		action = new LoginAction();
    		try {
    			forward = action.execute(request, response);
    		}catch(Exception e) {
    			e.printStackTrace();
    		}	
    	}
		//3. 로그아웃 액션
		else if(command.equals("/logout.log")) {
    		action = new LogoutAction();
    		try {
    			forward = action.execute(request, response);
    		}catch(Exception e) {
    			e.printStackTrace();
    		}	
    	
    	}
		//4. 아이디 찾기 페이지
		else if(command.equals("/idfind.log")) {
    		request.setAttribute("pagefile", "/member/idfind.jsp");
    		forward = new ActionForward("/template.jsp", false);
    	}
		//5. 아이디 찾기 액션
		else if(command.equals("/idfindAction.log")) {
    		action = new IdFindAction();
    	
    		try {
    			forward = action.execute(request, response);
    		}catch(Exception e) {
    			e.printStackTrace();
    		}		
    	}
		//6. 비밀번호 찾기 페이지
		else if(command.equals("/pwfind.log")) {
    		request.setAttribute("pagefile", "/member/pwfind.jsp");
    		forward = new ActionForward("/template.jsp", false);
    	}
		//7. 비밀번호 찾기 액션
		else if(command.equals("/pwfindAction.log")) {
    		action = new PwFindAction();
    	
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
	}
}

