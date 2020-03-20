package member.action;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import action.Action;
import member.svc.OrderService;
import vo.ActionForward;
import vo.Order;


public class OrderAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// TODO Auto-generated method stub
		ActionForward forward = null;
		ArrayList<Order> orderList = null;
		String id ="";
		HttpSession session = request.getSession();
		id = (String) session.getAttribute("id");
		OrderService orderService = new OrderService();
		
		orderList = orderService.selectorder(id);
		
		request.setAttribute("orderList", orderList);
		request.setAttribute("pagefile", "/member/order.jsp");
		forward = new ActionForward("/template.jsp", false);
		
		
		return forward;
	}

}
