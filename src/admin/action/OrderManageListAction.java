package admin.action;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import action.Action;
import admin.svc.OrderManageListService;
import vo.ActionForward;
import vo.Order;
import vo.PageInfo;

public class OrderManageListAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		HttpSession session = request.getSession();
		ActionForward forward = null;
		
		String searchType = "user_id";
		String searchText = "";
		String orderDate = "";
		String[] deliStatus = null;
		
		if(!(request.getParameter("searchType")==null || request.getParameter("searchType").trim().equals(""))) {
			searchType = request.getParameter("searchType");
		}
		if(request.getParameter("searchText") != null) {
			searchText = request.getParameter("searchText");
		}
		if(request.getParameter("orderDate") != null) {
			orderDate = request.getParameter("orderDate");
		}
		if(request.getParameterValues("deliStatus") != null) {
			deliStatus = request.getParameterValues("deliStatus");
			
		}
		
		
		
		int page = 1;
		int limit = 10;
		int limitPage = 5;
		
		if(request.getParameter("page") != null) {
			page = Integer.parseInt(request.getParameter("page"));
		}
		
		ArrayList<Order> orderList = new ArrayList<Order>();
		OrderManageListService orderListService = new OrderManageListService();
		int listCount = orderListService.getOrderListCount(searchType, searchText, orderDate, deliStatus);
		orderList = orderListService.getOrderList(searchType, searchText, orderDate, deliStatus, page, limit);
		
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
		request.setAttribute("searchType", searchType);
		request.setAttribute("searchText", searchText);
		request.setAttribute("orderDate", orderDate);
		request.setAttribute("deliStatus", deliStatus);
		
		request.setAttribute("pagefile", "/admin/orderManageList.jsp");
		forward = new ActionForward("/admin_template.jsp", false);
		
		return forward;
	}

}
