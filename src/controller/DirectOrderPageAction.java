package controller;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import action.Action;
import admin.svc.PointManageFormService;
import point.svc.PointService;
import product.svc.CartListService;
import product.svc.OrderPageService;
import vo.ActionForward;
import vo.Cart;
import vo.Member;
import vo.Point;
import vo.PointMan;

public class DirectOrderPageAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		String pro_det_num[] = request.getParameter("pro_det_num").split(",");
		String qnt[] = request.getParameter("qnt").split(",");
		String color[] = request.getParameter("color").split(",");
		String size[] = request.getParameter("pro_size").split(",");
		int pro_num = Integer.parseInt(request.getParameter("pro_num"));
		String pro_name = request.getParameter("pro_name");
		String pro_photo = request.getParameter("pro_photo");
		int pro_price = Integer.parseInt(request.getParameter("pro_price"));
		
		
		ArrayList<Cart> cartList2 = new ArrayList<>();	//바로 주문하기 누른 상품 리스트
		int bas_num = 0;
		for(int i=0; i<pro_det_num.length; i++) {
			Cart cart = new Cart();
			bas_num += 1;
			cart.setBas_num(bas_num);
			cart.setPro_num(pro_num);
			cart.setPro_name(pro_name);
			cart.setPro_price(pro_price);
			cart.setPro_photo(pro_photo);
			cart.setColor(color[i]);
			cart.setPro_size(size[i]);
			cart.setPro_det_num(pro_det_num[i]);
			cart.setBas_pro_qnt(Integer.parseInt(qnt[i]));
			System.out.println(pro_det_num[i]);
			System.out.println(qnt[i]);
			cartList2.add(cart);

		}
		
		Member member = null;
		HttpSession session = request.getSession();
		String user_id = (String)session.getAttribute("id");
		
		
		
		int totalMoney = 0;
		int money = 0;
		if(cartList2 != null) {
			for(int i = 0; i < cartList2.size(); i++) {
				money = cartList2.get(i).getPro_price()*cartList2.get(i).getBas_pro_qnt();
				totalMoney += money;
			}
		}
		
		OrderPageService orderPageService = new OrderPageService();
		member = orderPageService.getOrderUserInfo(user_id);
		PointManageFormService pointManageFormService = new PointManageFormService();
		PointMan pointMan = pointManageFormService.getPointOption(1);
		PointService pointService = new PointService();
		Point memberPoint = pointService.memberPoint(user_id);
		request.setAttribute("memberPoint", memberPoint);
		request.setAttribute("pointMan", pointMan);
		request.setAttribute("totalMoney", totalMoney);
		request.setAttribute("cartList2", cartList2);
		request.setAttribute("member", member);
		request.setAttribute("pagefile", "/product/orderPage.jsp");
		ActionForward forward = new ActionForward("/template.jsp", false);
		
		return forward;
		
	}

}
