package admin.action;

import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import action.Action;
import admin.svc.MemberListService;
import vo.ActionForward;
import vo.Member;
import vo.PageInfo;

public class MemberListAction implements Action{

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
			String searchGrade = "";
			int startPrice = 0;
			int endPrice = 0;
			String startDate = "";
			String endDate = "";
			
			
			if(!(request.getParameter("searchType") == null || request.getParameter("searchType").trim().equals("")) ) {
				searchType = request.getParameter("searchType");
			}
			if(request.getParameter("searchText") != null) {
				searchText = request.getParameter("searchText");
			}
			if(request.getParameter("searchGrade") != null) {
				searchGrade = request.getParameter("searchGrade");
			}
			
			//시작가격만 입력한 경우(~이상 전부)
			if(!(request.getParameter("startPrice") == null || request.getParameter("startPrice").trim().equals(""))) {
				startPrice = Integer.parseInt(request.getParameter("startPrice"));
				
			}
			//종료가격만 입력한 경우(~이하 전부)
			if(!(request.getParameter("endPrice") == null || request.getParameter("endPrice").trim().equals(""))) {
				endPrice = Integer.parseInt(request.getParameter("endPrice"));
			}
		
			if(!(request.getParameter("startDate") == null && request.getParameter("endDate") == null)) {
				startDate = request.getParameter("startDate");
				endDate = request.getParameter("endDate");
			}
			
			
//			System.out.println("searchType:"+searchType);
//			System.out.println("searchText:"+searchText);
//			System.out.println("searchGrade:"+searchGrade);
			System.out.println("startPrice : " + startPrice);
			System.out.println("endPrice : " + endPrice);
			System.out.println("startDate : " + startDate);
			System.out.println("endDate : " + endDate);
			
			
			ArrayList<Member> memberList = new ArrayList<Member>();
			
			int page = 1;
			int limit = 10;
			int limitPage = 5;
			
			if(request.getParameter("page") != null) {
				page = Integer.parseInt(request.getParameter("page"));
			}
			
			MemberListService memberListService = new MemberListService();
			int listCount = memberListService.getListCount(searchType, searchText, searchGrade, startPrice, endPrice, startDate, endDate);
			System.out.println("listcount: " + listCount); 
			memberList = memberListService.getMemberList(searchType, searchText, searchGrade, startPrice, endPrice, startDate, endDate, page, limit);
			
			
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
			
			
			request.setAttribute("memberList", memberList);
			
			request.setAttribute("searchType", searchType);
			request.setAttribute("searchText", searchText);
			request.setAttribute("searchGrade", searchGrade);
			request.setAttribute("startPrice", String.valueOf(startPrice));
			request.setAttribute("endPrice", String.valueOf(endPrice));
			request.setAttribute("startDate", startDate);
			request.setAttribute("endDate", endDate);
			
			request.setAttribute("pagefile", "/admin/member_list.jsp");
			forward = new ActionForward("/admin_template.jsp", false);
		}
		
		

		return forward;
	}

}
