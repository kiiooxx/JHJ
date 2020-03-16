package admin.action;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.StringTokenizer;

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
		String deliChecked = "";//주문상태 체크된 값을 String으로 받고
		String[] deliStatus = null;//StringTokenizer로 나눈 문자들을 넣을 배열
		
		if(!(request.getParameter("searchType")==null || request.getParameter("searchType").trim().equals(""))) {
			searchType = request.getParameter("searchType");
		}
		if(request.getParameter("searchText") != null) {
			searchText = request.getParameter("searchText");
		}
		if(request.getParameter("orderDate") != null) {
			orderDate = request.getParameter("orderDate");
		}
		if(request.getParameter("deliStatus") != null) {
			
			deliChecked = Arrays.toString(request.getParameterValues("deliStatus"));
			//값을 그냥 받으면 배열 메모리 주소값([Ljava.lang.String@숫자)만 나와서 Arrays.toString()을 사용했습니다.
			
			//System.out.println("deliChecked:" + deliChecked);
			
			StringTokenizer st = new StringTokenizer(deliChecked,"[, ]");
			//Arrays.toString()으로 얻은 문자열 형태가 [값, 값, 값] 이라서 StringTokenizer로 잘랐습니다.
			
			deliStatus = new String[st.countTokens()];
			int i = 0;
			
			while(st.hasMoreTokens()) {
				deliStatus[i++] = st.nextToken();	
			}
//			for(i=0; i < deliStatus.length; i++) {
//				System.out.println("deliStatus["+i+"]:"+deliStatus[i]);
//			}			
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
