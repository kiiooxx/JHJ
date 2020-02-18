package product.action; 
 
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import action.Action;
import admin.svc.OrderManageListService;
import product.svc.CartListService;
import product.svc.OrderPageService;
import vo.ActionForward;
import vo.Cart;
import vo.Member;

public class OrderPageAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		CartListService cartListService = new CartListService();
		ArrayList<Cart> cartList = null;
		String pro_det_num[] = null;
		Member member = null;
		
		HttpSession session = request.getSession();
		String user_id = (String) session.getAttribute("id");
		
		
		if(!(request.getParameter("pro_det_num") == null || request.getParameter("pro_det_num").equals(""))) {
			//장바구니에서 선택한 상품만 주문할 경우 
			pro_det_num = request.getParameter("pro_det_num").split(",");
			cartList = cartListService.getCheckedCartList(pro_det_num,request);
		}else {
			//장바구니에서 전체 상품을 주문할 경우
			cartList = cartListService.getCartList(request);
			
		}
		
		
		int totalMoney = 0;
		int money = 0;
		
		for(int i = 0; i < cartList.size(); i++) {
			money = cartList.get(i).getPro_price()*cartList.get(i).getBas_pro_qnt();
			totalMoney += money;
		}
		
		
		OrderPageService orderPageService = new OrderPageService();
		member = orderPageService.getOrderUserInfo(user_id);
		
		request.setAttribute("totalMoney", totalMoney);
		request.setAttribute("cartList", cartList);
		request.setAttribute("member", member);
		request.setAttribute("pagefile", "/product/orderPage.jsp");
		ActionForward forward = new ActionForward("/template.jsp", false);
		
		return forward;
	}

}
