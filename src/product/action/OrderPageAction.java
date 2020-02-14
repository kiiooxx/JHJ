package product.action; 
 
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import action.Action;
import admin.svc.OrderManageListService;
import product.svc.CartListService;
import vo.ActionForward;
import vo.Cart;

public class OrderPageAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		CartListService cartListService = new CartListService();
		ArrayList<Cart> cartList = null;
		System.out.println("zzz" + request.getParameter("pro_det_num"));
		String pro_det_num[] = null;
		
		
		if(!(request.getParameter("pro_det_num") == null || request.getParameter("pro_det_num").equals(""))) {
			
			pro_det_num = request.getParameter("pro_det_num").split(",");
			cartList = cartListService.getCheckedCartList(pro_det_num,request);
		}else {
			cartList = cartListService.getCartList(request);
		}
		
		for(int i = 0; i<cartList.size(); i++) {
			System.out.println("ㅇㅇㅇ" + cartList.get(i).getPro_det_num());
		}
		
		
		
		int totalMoney = 0;
		int money = 0;
		
		for(int i = 0; i < cartList.size(); i++) {
			money = cartList.get(i).getPro_price()*cartList.get(i).getBas_pro_qnt();
			totalMoney += money;
		}
		
		request.setAttribute("totalMoney", totalMoney);
		request.setAttribute("cartList", cartList);
		request.setAttribute("pagefile", "/product/orderPage.jsp");
		ActionForward forward = new ActionForward("/template.jsp", false);
		
		return forward;
	}

}
