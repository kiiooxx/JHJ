package product.action;
 
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import action.Action;
import product.svc.CartListService;
import vo.ActionForward;
import vo.Cart;

public class CartListAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// TODO Auto-generated method stub
		CartListService cartListService = new CartListService();
		ArrayList<Cart> cartList = cartListService.getCartList(request);
		
		HttpSession session =request.getSession();
		String id = (String)session.getAttribute("id");	//로그인한 아이디 확인
		
		if(id != null) {
			
		}
		
		//총 금액 계산
		int totalPrice = 0;
		int price = 0;
		
		if(cartList != null) {
			for(int i=0; i<cartList.size(); i++) {
				price = cartList.get(i).getPro_price()*cartList.get(i).getBas_pro_qnt();
				totalPrice += price;
			}
		}
		
		
		request.setAttribute("totalPrice", totalPrice);
		request.setAttribute("cartList", cartList);
		request.setAttribute("pagefile", "/product/cartList.jsp");
		ActionForward forward = new ActionForward("/template.jsp", false);
		
		return forward;
	}

}
