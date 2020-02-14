package admin.action;

import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import action.Action;
import admin.svc.MemberInfoService;
import admin.svc.OrderManageListService;
import vo.ActionForward;
import vo.Member;
import vo.Order;
import vo.PageInfo;

public class MemberInfoAction implements Action{

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

		ActionForward forward = null;
		HttpSession session = request.getSession();
		
		if((session.getAttribute("id")==null) || (!((String)session.getAttribute("id")).equals("admin"))) {
			response.setContentType("text/html;charset=utf-8");
			PrintWriter out = response.getWriter();
			out.println("<script>");
			out.println("alert('관리자 로그인이 필요합니다.');");
			out.println("history.back();");
			out.println("</script>");
		}else {
			
			
			int page = 1;
			int limit = 10;
			int limitPage = 5;
			
			//회원개인정보
			String user_id = request.getParameter("user_id");
			MemberInfoService memberInfoService = new MemberInfoService();
			Member member = memberInfoService.getMemberInfo(user_id);
			request.setAttribute("member", member);
			
			
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
			System.out.println("오더리스트액션");
			
			
			request.setAttribute("pagefile", "admin/member_info.jsp");
			forward = new ActionForward("/template.jsp", false);
			
		}

		return forward;
	}

}
