package controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import action.Action;
import admin.action.CategoryListAction;
import product.action.CartAddAction;
import product.action.CartDeleteAction;
import product.action.CartListAction;
import product.action.CartQtyDownAction;
import product.action.CartQtyUpAction;
import product.action.DirectOrderPageAction;
import product.action.MainListAction;
import product.action.ProductDetailAction;
import product.action.ProductListAction;
import product.action.ProductListBestNewAction;
import product.action.OrderPageAction;
import product.action.OrderProcessAction;
import vo.ActionForward;

/**
 * Servlet implementation class productController
 */
@WebServlet("*.pro")
public class ProductController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ProductController() {
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
		
	
		//===================상품==================
		//1. 메인 상품
		if(command.equals("/main.pro")) {
			action = new MainListAction();
			//프로젝트명+기능+형태(?)
			try {
				forward = action.execute(request, response);
			}catch(Exception e) {
				e.printStackTrace();
			}
    	}
		//2. 상품 리스트 보기 액션
		else if(command.equals("/productList.pro")) {
			action = new ProductListAction();
			try {
				forward = action.execute(request, response);
			}catch(Exception e) {
				e.printStackTrace();
			}
    	}
		//3. 상품 상세 정보 보기 액션
		else if(command.equals("/productDetail.pro")) {
			action = new ProductDetailAction();
			try {
				forward = action.execute(request, response);
			}catch(Exception e) {
				e.printStackTrace();
			}
    	}
		//4. 상품 리스트 - 베스트 신상품 보기 액션
		else if(command.equals("/productListBestNew.pro")) {
			action = new ProductListBestNewAction();
			//프로젝트명+기능+형태(?)
			try {
				forward = action.execute(request, response);
			}catch(Exception e) {
				e.printStackTrace();
			}
    	}
		
		//===================장바구니==================
		//1. 장바구니 리스트 보기 액션
		else if(command.equals("/cartList.pro")) {
			action = new CartListAction();
			//프로젝트명+기능+형태(?)
			try {
				forward = action.execute(request, response);
			}catch(Exception e) {
				e.printStackTrace();
			}
    	}
		//2. 장바구니 수량 추가
		else if(command.equals("/cartQtyUp.pro")) {
			action = new CartQtyUpAction();
			//프로젝트명+기능+형태(?)
			try {
				forward = action.execute(request, response);
			}catch(Exception e) {
				e.printStackTrace();
			}
    	}
		//3. 장바구니 수량 감소
		else if(command.equals("/cartQtyDown.pro")) {
			action = new CartQtyDownAction();
			//프로젝트명+기능+형태(?)
			try {
				forward = action.execute(request, response);
			}catch(Exception e) {
				e.printStackTrace();
			}
    	}
		//4. 장바구니 삭제
		else if(command.equals("/cartDelete.pro")) {
			action = new CartDeleteAction();
			//프로젝트명+기능+형태(?)
			try {
				forward = action.execute(request, response);
			}catch(Exception e) {
				e.printStackTrace();
			}
    	}
		//5. 장바구니 추가
		else if(command.equals("/cartAdd.pro")) {
			action = new CartAddAction();
			//프로젝트명+기능+형태(?)
			try {
				forward = action.execute(request, response);
			}catch(Exception e) {
				e.printStackTrace();
			}
    	}
		
		//===================주문==================
		//1. 주문 페이지
		else if(command.equals("/directOrderPage.pro")) {
			action = new DirectOrderPageAction();
			try {
				forward = action.execute(request, response);
			}catch(Exception e) {
				e.printStackTrace();
			}
		}
		else if(command.equals("/orderPage.pro")) {
			action = new OrderPageAction();
			try {
				forward = action.execute(request, response);
			}catch(Exception e) {
				e.printStackTrace();
			}
    	}
		//2. 결제
		else if(command.equals("/payProcess.pro")) {
			action = new OrderProcessAction();
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
