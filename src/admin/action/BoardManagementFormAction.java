package admin.action;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import action.Action;
import admin.svc.ProductListManagementService;
import board.svc.BoardListService;
import vo.ActionForward;
import vo.BoardBean;
import vo.PageInfo;


public class BoardManagementFormAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// TODO Auto-generated method stub
		request.setCharacterEncoding("UTF-8");
		
		//게시글 시간
		String board_date = "all";
		if(request.getParameter("board_date") != null) {
			board_date = request.getParameter("board_date");
		}
		
		//게시판 종류(리뷰게시판, QnA게시판...)
		String board_type = "all";
		if(request.getParameter("board_type") != null) {
			board_type = request.getParameter("board_type");
		}
		
		//검색 종류(제목, 내용...)
		String search_type = "";
		if(request.getParameter("search_type") != null) {
			search_type = request.getParameter("search_type");
		}
		
		//검색 텍스트
		String search_text = "";
		if(request.getParameter("search_text") != null) {
			search_text = request.getParameter("search_text");
		}
		
		//답변 여부
		String board_step = "all";
		if(request.getParameter("board_step") != null) {
			board_step = request.getParameter("board_step");
		}
		
		//첨부파일 여부
		String board_photo = "all";
		if(request.getParameter("board_photo") != null) {
			board_photo = request.getParameter("board_photo");
		}
		
		ArrayList<BoardBean> boardList = new ArrayList<>();
		
		int page = 1;
		int limit = 5;	//페이지에 보여줄 목록 수
		int limitPage = 10;	//페이지 수
		int listCount = 0;
		
		if(request.getParameter("page") != null) {
			page = Integer.parseInt(request.getParameter("page"));
		}
		
		BoardListService boardListService = new BoardListService();
		listCount = boardListService.getBoardListCount(board_date, board_type, search_type, search_text, board_step, board_photo);
		boardList = boardListService.getBoardList(board_date, board_type, search_type, search_text, board_step, board_photo, page, limit);
		
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
		request.setAttribute("boardList", boardList);
		
		//검색 조건 넘기기
		request.setAttribute("board_date", board_date);
		request.setAttribute("board_type", board_type);
		request.setAttribute("search_type", search_type);
		request.setAttribute("search_text", search_text);
		request.setAttribute("board_step", board_step);
		request.setAttribute("board_photo", board_photo);
		
		request.setAttribute("pagefile", "/admin/board_management.jsp");
		ActionForward forward = new ActionForward("/admin_template.jsp", false);
		return forward;
	}

}
