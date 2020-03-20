package controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import action.Action;
import admin.action.AcceptCancelAction;
import admin.action.BoardViewAction;
import admin.action.BoardManagementFormAction;
import admin.action.CategoryAddAction;
import admin.action.CategoryDelAction;
import admin.action.CategoryListAction;
import admin.action.CategoryManagementForm;
import admin.action.CategorySubListAction;
import admin.action.CategoryUpdateAction;
import admin.action.ChangeStatusAction;
import admin.action.MailManageFormAction;
import admin.action.MailManagementAction;
import admin.action.MemberInfoAction;
import admin.action.MemberListAction;
import admin.action.OrderManageDetailAction;
import admin.action.OrderManageListAction;
import admin.action.ProducRegistAction;
import admin.action.ProductDelAction;
import admin.action.ProductListManagementAction;
import admin.action.ProductListUpdateAction;
import admin.action.ProductModifyAction;
import admin.action.ProductModifyFormAction;
import admin.action.ProductOptionDelAction;
import admin.action.StockListManagementAction;
import admin.action.StockListModifyAction;
import admin.action.PointManagementAction;
import admin.action.PointManagementFormAction;
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
		
		//=================관리자 페이지===================
		if(command.equals("/adminPage.ad")) {
			request.setAttribute("pagefile", "/admin/category_management.jsp");
	    	forward = new ActionForward("/admin_template.jsp", false);
    	}
		
		//===================카테고리====================
		//카테고리 관리 페이지
		else if(command.equals("/categoryManagement.ad")) {
    		action = new CategoryManagementForm();
			try {
				forward = action.execute(request, response);
			}catch(Exception e) {
				e.printStackTrace();
			}
		}
		//카테고리 추가 액션
		else if(command.equals("/categoryAddAction.ad")) {
			action = new CategoryAddAction();
			try {
				forward = action.execute(request, response);
			}catch(Exception e) {
				e.printStackTrace();
			}
		}
		//카테고리 리스트 세션에 저장하는 액션
		else if(command.equals("/categoryList.ad")) {
			action = new CategoryListAction();
			//프로젝트명+기능+형태(?)
			try {
				forward = action.execute(request, response);
			}catch(Exception e) {
				e.printStackTrace();
			}
		}
		//카테고리 삭제 액션
    	else if(command.equals("/categoryDelAction.ad")) {
			action = new CategoryDelAction();
			try {
				forward = action.execute(request, response);
			}catch(Exception e) {
				e.printStackTrace();
			}
    	}
		//카테고리 수정 액션
    	else if(command.equals("/categoryUpdateAction.ad")) {
			action = new CategoryUpdateAction();
			try {
				forward = action.execute(request, response);
			}catch(Exception e) {
				e.printStackTrace();
			}
    	}
		
		//====================상품 등록=====================
		//상품관리 - 상품 등록 페이지
		else if(command.equals("/productManagement.ad")) {
			request.setAttribute("pagefile", "/admin/product_management.jsp");
	    	forward = new ActionForward("/admin_template.jsp", false);
    	}
		//상품 등록 액션
		else if(command.equals("/productRegistAction.ad")) {
			action = new ProducRegistAction();
			try {
				forward = action.execute(request, response);
			}catch(Exception e) {
				e.printStackTrace();
			}
    	}
		
		//====================회원 관리======================
		//회원 목록
    	else if(command.equals("/memberList.ad")) {
			action = new MemberListAction();
			try {
				forward = action.execute(request, response);
			}catch(Exception e) {
				e.printStackTrace();
				System.out.println("controller error : memberList.ad");
			}
		}
    	//회원 정보 보기
    	else if(command.equals("/memberInfo.ad")) {
			action = new MemberInfoAction();
			try {
				forward = action.execute(request, response);
			}catch(Exception e) {
				e.printStackTrace();
				System.out.println("controller error : memberInfo.ad");
			}
		}
		
    	
    	

    	else if(command.equals("/categorySubList.ad")) {
			action = new CategorySubListAction();
			try {
				forward = action.execute(request, response);
			}catch(Exception e) {
				e.printStackTrace();
			}
    	}
		
		//=====================상품 목록=======================
		//상품 리스트 보여주기
    	else if(command.equals("/productListManagement.ad")) {
			action = new ProductListManagementAction();
			try {
				forward = action.execute(request, response);
			}catch(Exception e) {
				e.printStackTrace();
			}
    	}
		//상품 수정 페이지
    	else if(command.equals("/productModifyForm.ad")) {
			action = new ProductModifyFormAction();
			try {
				forward = action.execute(request, response);
			}catch(Exception e) {
				e.printStackTrace();
			}
    	}
    	//상품 수정 액션
    	else if(command.equals("/productModifyAction.ad")) {
			action = new ProductModifyAction();
			try {
				forward = action.execute(request, response);
			}catch(Exception e) {
				e.printStackTrace();
			}
    	}
    	//상품 삭제 액션
    	else if(command.equals("/productDelAction.ad")) {
			action = new ProductDelAction();
			try {
				forward = action.execute(request, response);
			}catch(Exception e) {
				e.printStackTrace();
			}
    	}
    	
		//상품 리스트에서 수정하는 액션 
    	else if(command.equals("/productListUpdate.ad")) {
			action = new ProductListUpdateAction();

			try {
				forward = action.execute(request, response);
			}catch(Exception e) {
				e.printStackTrace();
			}
    	}
    	//상품 옵션 삭제 액션
    	else if(command.equals("/productOptionDel.ad")) {
			action = new ProductOptionDelAction();
			try {
				forward = action.execute(request, response);
			}catch(Exception e) {
				e.printStackTrace();
			}
    	}
		
		//====================재고 관리=====================
    	//재고 리스트 보여주는 액션
    	else if(command.equals("/stockListManagement.ad")) {
			action = new StockListManagementAction();
			try {
				forward = action.execute(request, response);
			}catch(Exception e) {
				e.printStackTrace();
			}
    	}
		//재고 리스트에서 재고 수정하는 액션
    	else if(command.equals("/stockListModify.ad")) {
			action = new StockListModifyAction();
			try {
				forward = action.execute(request, response);
			}catch(Exception e) {
				e.printStackTrace();
			}
    	}
		
		//==================주문 관리======================
		//주문검색페이지
    	else if(command.equals("/orderManageList.ad")) {
			action = new OrderManageListAction();
			try {
				forward = action.execute(request, response);
			}catch(Exception e) {
				e.printStackTrace();
			}
    	}
		//주문내역상세
    	else if(command.equals("/orderManageDetail.ad")) {
			action = new OrderManageDetailAction();
			try {
				forward = action.execute(request, response);
			}catch(Exception e) {
				e.printStackTrace();
			}
    	}
		//배송상태변경
    	else if(command.equals("/changeStatus.ad")) {
			action = new ChangeStatusAction();
			try {
				forward = action.execute(request, response);
			}catch(Exception e) {
				e.printStackTrace();
			}
    	}
		//주문취소승인
		
		else if(command.equals("/acceptCancelForm.ad")) {
			forward = new ActionForward("/admin/popup_cancel_req.jsp", false);
    	}
		
    	else if(command.equals("/acceptCancel.ad")) {
			action = new AcceptCancelAction();
			try {
				forward = action.execute(request, response);
			}catch(Exception e) {
				e.printStackTrace();
			}
    	}
		//====================자동메일옵션=======================
    	else if(command.equals("/mailManageForm.ad")) {
			action = new MailManageFormAction();
			try {
				forward = action.execute(request, response);
			}catch(Exception e) {
				e.printStackTrace();
			}
    	}
    	else if(command.equals("/mailManagement.ad")) {
			action = new MailManagementAction();
			try {
				forward = action.execute(request, response);
			}catch(Exception e) {
				e.printStackTrace();
			}
    	}
		//================적립금 관리=======================
    	else if(command.equals("/pointManagementForm.ad")) {
			action = new PointManagementFormAction();
			try {
				forward = action.execute(request, response);
			}catch(Exception e) {
				e.printStackTrace();
			}
    	}
    	else if(command.equals("/pointManagement.ad")) {
			action = new PointManagementAction();
			try {
				forward = action.execute(request, response);
			}catch(Exception e) {
				e.printStackTrace();
			}
    	}
		//===================게시판 관리=======================
    	else if(command.equals("/boardManagementForm.ad")) {
			action = new BoardManagementFormAction();
			try {
				forward = action.execute(request, response);
			}catch(Exception e) {
				e.printStackTrace();
			}
    	}
    	else if(command.equals("/boardView.ad")) {
	    	action = new BoardViewAction();
			try {
				forward = action.execute(request, response);
			}catch(Exception e) {
				e.printStackTrace();
			}
    	}
		
		
		System.out.println(command);
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
