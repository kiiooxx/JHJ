package controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import action.Action;
import admin.action.CategoryAddAction;
import admin.action.CategoryDelAction;
import admin.action.CategoryListAction;
import admin.action.CategoryManagementForm;
import admin.action.CategorySubListAction;
import admin.action.CategoryUpdateAction;
import admin.action.MemberInfoAction;
import admin.action.MemberListAction;
import admin.action.ProducRegistAction;
import admin.action.ProductDelAction;
import admin.action.ProductListManagementAction;
import admin.action.ProductListUpdateAction;
import admin.action.ProductModifyAction;
import admin.action.ProductModifyFormAction;
import vo.ActionForward;

/**
 * Servlet implementation class productController
 */
@WebServlet("*.ad")
public class AdminController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AdminController() {
        super();
        // TODO Auto-generated constructor stub
    }

    protected void doProcess(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		request.setCharacterEncoding("UTF-8");
		//1. 요청파악
		String requestURI = request.getRequestURI();
		//요청 URL : http://localhost:8088/boardProject/boardWriteForm.bo
		//requestURI : /boardProject/boardWriteForm.bo 반환
		
		String contextPath = request.getContextPath();
		//	/boardProject 반환
		
		String command = requestURI.substring(contextPath.length());
		Action action = null;
		ActionForward forward = null;
		
		//2. 각 요청별로 비즈니스 로직 호출
		if(command.equals("/adminPage.ad")) {
			request.setAttribute("pagefile", "/admin/category_management.jsp");
	    	forward = new ActionForward("/admin_template.jsp", false);
    	}else if(command.equals("/categoryManagement.ad")) {
    		action = new CategoryManagementForm();
			//프로젝트명+기능+형태(?)
			try {
				forward = action.execute(request, response);
			}catch(Exception e) {
				e.printStackTrace();
			}
		}else if(command.equals("/categoryAddAction.ad")) {
			action = new CategoryAddAction();
			//프로젝트명+기능+형태(?)
			try {
				forward = action.execute(request, response);
			}catch(Exception e) {
				e.printStackTrace();
			}
		}else if(command.equals("/productManagement.ad")) {
			request.setAttribute("pagefile", "/admin/product_management.jsp");
	    	forward = new ActionForward("/admin_template.jsp", false);
    	}else if(command.equals("/productRegistAction.ad")) {
			action = new ProducRegistAction();
			//프로젝트명+기능+형태(?)
			try {
				forward = action.execute(request, response);
			}catch(Exception e) {
				e.printStackTrace();
			}
    	}else if(command.equals("/categoryList.ad")) {
			action = new CategoryListAction();
			//프로젝트명+기능+형태(?)
			try {
				forward = action.execute(request, response);
			}catch(Exception e) {
				e.printStackTrace();
			}
    	}else if(command.equals("/categoryDelAction.ad")) {
			action = new CategoryDelAction();
			//프로젝트명+기능+형태(?)
			try {
				forward = action.execute(request, response);
			}catch(Exception e) {
				e.printStackTrace();
			}
    	}else if(command.equals("/categoryUpdateAction.ad")) {
			action = new CategoryUpdateAction();
			//프로젝트명+기능+형태(?)
			try {
				forward = action.execute(request, response);
			}catch(Exception e) {
				e.printStackTrace();
			}
    	}else if(command.equals("/memberList.ad")) {
			action = new MemberListAction();
			try {
				forward = action.execute(request, response);
			}catch(Exception e) {
				e.printStackTrace();
				System.out.println("controller error : memberList.ad");
			}
		}else if(command.equals("/memberInfo.ad")) {
			action = new MemberInfoAction();
			try {
				forward = action.execute(request, response);
			}catch(Exception e) {
				e.printStackTrace();
				System.out.println("controller error : memberInfo.ad");
			}
		}else if(command.equals("/productListManagement.ad")) {
			action = new ProductListManagementAction();
			try {
				forward = action.execute(request, response);
			}catch(Exception e) {
				e.printStackTrace();
			}
    	}else if(command.equals("/categorySubList.ad")) {
			action = new CategorySubListAction();
			try {
				forward = action.execute(request, response);
			}catch(Exception e) {
				e.printStackTrace();
			}
    	}else if(command.equals("/categorySubList.ad")) {
			action = new CategorySubListAction();
			try {
				forward = action.execute(request, response);
			}catch(Exception e) {
				e.printStackTrace();
			}
    	}else if(command.equals("/productModifyForm.ad")) {
			action = new ProductModifyFormAction();
			try {
				forward = action.execute(request, response);
			}catch(Exception e) {
				e.printStackTrace();
			}
    	}else if(command.equals("/productModifyAction.ad")) {
			action = new ProductModifyAction();
			try {
				forward = action.execute(request, response);
			}catch(Exception e) {
				e.printStackTrace();
			}
    	}else if(command.equals("/productDelAction.ad")) {
			action = new ProductDelAction();
			try {
				forward = action.execute(request, response);
			}catch(Exception e) {
				e.printStackTrace();
			}
    	}else if(command.equals("/productListUpdate.ad")) {
			action = new ProductListUpdateAction();
			try {
				forward = action.execute(request, response);
			}catch(Exception e) {
				e.printStackTrace();
			}
    	}
		
		//3.포워딩
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
		doProcess(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doProcess(request, response);
	}

}
