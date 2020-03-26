package member.action;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import action.Action;
import admin.svc.OrderManageListService;
import member.svc.OrderService;
import vo.ActionForward;
import vo.Order;
import vo.PageInfo;


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
		
		int page = 1;
		int limit = 5;
		int limitPage = 5;
		
		if(request.getParameter("page") != null) {
			page = Integer.parseInt(request.getParameter("page"));
		}
		
		int listCount = orderService.getOrderListCount(id);
		orderList = orderService.selectOrderList(id, page, limit);

		
		int maxPage = (int)((double)listCount/limit+0.95);
		int startPage = (((int)((double)page/limitPage+0.9))-1)*limitPage+1;
		int endPage = startPage+ limitPage - 1;
		if(endPage>maxPage) endPage=maxPage;
		
		PageInfo pageInfo = new PageInfo();
		pageInfo.setEndPage(endPage);
		pageInfo.setListCount(listCount);
		pageInfo.setMaxPage(maxPage);
		pageInfo.setPage(page);
		pageInfo.setStartPage(startPage);
		
		request.setAttribute("pageInfo", pageInfo);
		request.setAttribute("orderList", orderList);
		request.setAttribute("pagefile", "/member/order.jsp");
		forward = new ActionForward("/template.jsp", false);
		
		
		return forward;
	}

}
