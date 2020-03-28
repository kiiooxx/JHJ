package member.action;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import action.Action;
import admin.svc.OrderManageDetailService;
import admin.svc.OrderManageListService;
import vo.ActionForward;
import vo.Order;
import vo.OrderProView;
import vo.PageInfo;


public class OrderAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// TODO Auto-generated method stub
		ActionForward forward = null;
		String user_id ="";
		HttpSession session = request.getSession();
		user_id = (String) session.getAttribute("id");
		
		int page = 1;
		int limit = 5;
		int limitPage = 5;
		
		if(request.getParameter("page") != null) {
			page = Integer.parseInt(request.getParameter("page"));
		}
		
		//회원주문내역
		OrderManageListService orderListService = new OrderManageListService();
		int listCount = orderListService.getOrderListCount(user_id);
		ArrayList<Order> order = orderListService.getOrderList(user_id,page, limit);
		
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
		
		request.setAttribute("order", order);
		request.setAttribute("user_id", user_id);
		request.setAttribute("pageInfo", pageInfo);
		
		request.setAttribute("pagefile", "/member/order.jsp");
		forward = new ActionForward("/template.jsp", false);
		
		return forward;
	}

}
