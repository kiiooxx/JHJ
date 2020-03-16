package board.action;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import action.Action;
import board.svc.Notice_BoardListService;
import vo.ActionForward;
import vo.Notice_BoardBean;
import vo.PageInfo;

public class Notice_BoardListAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// TODO Auto-generated method stub
				ArrayList<Notice_BoardBean> articleList = new ArrayList<Notice_BoardBean>();
				int page = 1;
				int limit = 5;	//페이지에 보여줄 목록 수
				int limitPage = 3;	//페이지 수
				
				System.out.println("BoardListAction 페이지" + request.getParameter("page"));
				if(request.getParameter("page") != null) {
					page = Integer.parseInt(request.getParameter("page"));
				}
				
				Notice_BoardListService notice_boardListService = new Notice_BoardListService();
				int listCount = notice_boardListService.getListCount();
				//총 리스트 수를 받아옴
				articleList = notice_boardListService.getArticleList(page,limit);
				//리스트를 받아옴
				//총 페이지 수
				int maxPage = (int)((double)listCount/limit+0.95);
				//0.95를 더해서 올림 처리
				//현재 페이지에 보여줄 시작 페이지 수(1, 11, 21 등...)
				int startPage = (((int)((double)page/limitPage+0.9)) -1) * limitPage + 1;
			
				//현재 페이지에 보여줄 마지막 페이지 수.(10,20,30 등..)
				int endPage = startPage + limitPage-1;
				
				if(endPage > maxPage) endPage = maxPage;
				
				
				PageInfo pageInfo = new PageInfo();
				pageInfo.setEndPage(endPage);
				pageInfo.setListCount(listCount);
				pageInfo.setMaxPage(maxPage);
				pageInfo.setPage(page);
				pageInfo.setStartPage(startPage);
				request.setAttribute("pageInfo", pageInfo);
				request.setAttribute("articleList", articleList);
				
				request.setAttribute("pagefile", "/board/notice_board_list.jsp");
				ActionForward forward = new ActionForward("/template.jsp",false);
				
				return forward;
			}
}