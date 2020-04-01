package point.action;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import action.Action;

import point.svc.MyPointService;
import point.svc.PointService;
import vo.ActionForward;
import vo.PageInfo;
import vo.Point;

public class MyPointAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// TODO Auto-generated method stub
		ActionForward forward = null;
		HttpSession session = request.getSession();
		String user_id = (String) session.getAttribute("id");
		MyPointService myPointService = new MyPointService();
		PointService pointService = new PointService();
		
			int page =1;
			int limit = 10;
			int limitPage = 5;
			
			if(request.getParameter("page") !=null) {
				page = Integer.parseInt(request.getParameter("page"));
			}
			
			int listCount = myPointService.getMyPointListCount(user_id);
			ArrayList<Point> myPointList = myPointService.selectMyPoint(user_id,page,limit);
			Point memberPoint= pointService.memberPoint(user_id);
	
			
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
			
			request.setAttribute("memberPoint", memberPoint);
			request.setAttribute("pageInfo", pageInfo);
			request.setAttribute("myPointList", myPointList);		
			request.setAttribute("pagefile", "member/point.jsp");
			forward = new ActionForward("/template.jsp",false);
			
		return forward;
	}

}
