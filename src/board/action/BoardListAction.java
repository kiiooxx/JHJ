package board.action;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import action.Action;
import board.svc.BoardListService;
import product.svc.ProductDetailService;
import vo.ActionForward;
import vo.BoardBean;
import vo.PageInfo;
import vo.ProductBean;


public class BoardListAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// TODO Auto-generated method stub
		request.setCharacterEncoding("UTF-8");

		ArrayList<BoardBean> boardList = new ArrayList<>();

		int page = 1;
		int limit = 10;	//페이지에 보여줄 목록 수
		int limitPage = 5;	//페이지 수
		int listCount = 0;
		
		if(request.getParameter("page") != null) {
			page = Integer.parseInt(request.getParameter("page"));
		}
		
		String board_type = request.getParameter("board_type");	//게시판 타입
		String id = "";
		int pro_num = 0;	
		
		//전체 리뷰 목록
		BoardListService boardListService = new BoardListService();
		listCount = boardListService.getBoardListCount(board_type, id, pro_num);
		//총 리스트 수를 받아옴
		boardList = boardListService.getBoardList(board_type, id, pro_num, page, limit);
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
		
		
		//리뷰게시판이면 상품 사진 추가
		ArrayList<ProductBean> prdList = null;
		ProductBean prd = new ProductBean();
		ProductDetailService prdDetailService = new ProductDetailService();
		if(board_type.equals("review")) {
			prdList = new ArrayList<>();
			for(int i=0; i<boardList.size(); i++) {
				prd = prdDetailService.getProduct(boardList.get(i).getPro_num());	//상품 정보 불러오기
				prdList.add(prd);
			}
		}
		
		ArrayList<BoardBean> noticeList = null;
		noticeList = boardListService.getNoticeListCount(board_type);
		
		request.setAttribute("prdList", prdList);
		request.setAttribute("pageInfo", pageInfo);
		request.setAttribute("boardList", boardList);
		request.setAttribute("board_type", board_type);
		request.setAttribute("noticeList", noticeList);
		request.setAttribute("listCount", listCount);

		request.setAttribute("pagefile", "/board/board_list.jsp");
		ActionForward forward = new ActionForward("/template.jsp", false);
		return forward;
	}

}
