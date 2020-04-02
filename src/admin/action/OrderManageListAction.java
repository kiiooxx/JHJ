package admin.action;

import java.io.PrintWriter;
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
		
		if((session.getAttribute("id")==null) || (!((String)session.getAttribute("id")).equals("admin"))) {
			response.setContentType("text/html;charset=utf-8");
			PrintWriter out = response.getWriter();
			out.println("<script>");
			out.println("alert('관리자 로그인이 필요합니다.');");
			out.println("location.href='loginForm.log'");
			out.println("</script>");
		}else {
		
		String searchType = "user_id";
		String searchText = "";
		String startDate = "";
		String endDate = "";
		int startPrice = 0;
		int endPrice = 0;
		String deliChecked = "";//주문상태 체크된 값을 String으로 받고
		String[] deliStatus = null;//StringTokenizer로 나눈 문자들을 넣을 배열
		String cancelChecked = "";
		String[] cancelReq = null;
		
		if(!(request.getParameter("searchType")==null || request.getParameter("searchType").trim().equals(""))) {
			searchType = request.getParameter("searchType");
		}
		if(request.getParameter("searchText") != null) {
			searchText = request.getParameter("searchText");
		}
		if(!(request.getParameter("startDate") == null && request.getParameter("endDate") == null)) {
			startDate = request.getParameter("startDate");
			endDate = request.getParameter("endDate");
		}
		//시작가격만 입력한 경우(~이상 전부)
		if(!(request.getParameter("startPrice") == null || request.getParameter("startPrice").trim().equals(""))) {
			startPrice = Integer.parseInt(request.getParameter("startPrice"));
			
		}
		//종료가격만 입력한 경우(~이하 전부)
		if(!(request.getParameter("endPrice") == null || request.getParameter("endPrice").trim().equals(""))) {
			endPrice = Integer.parseInt(request.getParameter("endPrice"));
		}
		
		if(request.getParameter("deliStatus") != null) {
			deliChecked = Arrays.toString(request.getParameterValues("deliStatus"));
			//값을 그냥 받으면 배열 메모리 주소값([Ljava.lang.String@숫자)만 나와서 Arrays.toString() 사용
			StringTokenizer st = new StringTokenizer(deliChecked,"[, ]");
			//Arrays.toString()으로 얻은 문자열 형태가 [값, 값, 값] 이라서 StringTokenizer로 자르기
			deliStatus = new String[st.countTokens()];
			int i = 0;
			while(st.hasMoreTokens()) {
				deliStatus[i++] = st.nextToken();	
			}			
		}
		if(request.getParameter("cancel_req") != null) {
			cancelChecked = Arrays.toString(request.getParameterValues("cancel_req"));
			StringTokenizer st = new StringTokenizer(cancelChecked, "[, ]");
			cancelReq = new String[st.countTokens()];
			int i = 0;
			while(st.hasMoreTokens()) {
				cancelReq[i++] = st.nextToken();
			}
		}
		
		ArrayList<Order> orderList = new ArrayList<Order>();
		
		int page = 1;
		int limit = 10;
		int limitPage = 5;
		
		if(request.getParameter("page") != null) {
			page = Integer.parseInt(request.getParameter("page"));
		}
		
		
		OrderManageListService orderListService = new OrderManageListService();
		int listCount = orderListService.getOrderListCount(searchType, searchText, startDate, endDate, startPrice, endPrice, deliStatus, cancelReq);
		orderList = orderListService.getOrderList(searchType, searchText, startDate, endDate, startPrice, endPrice, deliStatus, cancelReq, page, limit);
		
		
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
		request.setAttribute("startDate", startDate);
		request.setAttribute("endDate", endDate);
		request.setAttribute("startPrice", startPrice);
		request.setAttribute("endPrice", endPrice);
		request.setAttribute("deliStatus", deliStatus);
		request.setAttribute("cancelReq", cancelReq);
		
		request.setAttribute("pagefile", "/admin/orderManageList.jsp");
		forward = new ActionForward("/admin_template.jsp", false);
		}
		return forward;
	}

}
