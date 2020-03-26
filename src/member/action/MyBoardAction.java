package member.action;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import action.Action;
import board.svc.BoardListService;
import product.svc.ProductDetailService;
import vo.ActionForward;
import vo.BoardBean;
import vo.PageInfo;
import vo.ProDetBean;
import vo.ProductBean;

public class MyBoardAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// TODO Auto-generated method stub
		HttpSession session = request.getSession();
		String id = (String) session.getAttribute("id");
		
		ArrayList<BoardBean> reviewList = null;	//리뷰 리스트
		ArrayList<BoardBean> qnaList = null;	//QnA리스트
		
		int page = 1;
		int limit = 10;	//페이지에 보여줄 목록 수
		int limitPage = 5;	//페이지 수
		int review_listCount = 0;
		int qna_listCount = 0;
		
		if(request.getParameter("page") != null) {
			page = Integer.parseInt(request.getParameter("page"));
		}
		
		//리뷰 목록
		BoardListService boardListService = new BoardListService();
		review_listCount = boardListService.getBoardListCount("review", id, 0);
		reviewList = boardListService.getBoardList("review", id, 0, page, limit);
		int review_maxPage = (int)((double)review_listCount/limit+0.95);
		int review_startPage = (((int)((double)page/limitPage+0.9)) -1) * limitPage + 1;
		int review_endPage = review_startPage + limitPage-1;
		if(review_endPage > review_maxPage) review_endPage = review_maxPage;
		
		PageInfo review_pageInfo = new PageInfo();
		review_pageInfo.setEndPage(review_endPage);
		review_pageInfo.setListCount(review_listCount);
		review_pageInfo.setMaxPage(review_maxPage);
		review_pageInfo.setPage(page);
		review_pageInfo.setStartPage(review_startPage);
		
		//문의 목록
		qna_listCount = boardListService.getBoardListCount("qna", id, 0);
		qnaList = boardListService.getBoardList("qna", id, 0, page, limit);
		int qna_maxPage = (int)((double)qna_listCount/limit+0.95);
		int qna_startPage = (((int)((double)page/limitPage+0.9)) -1) * limitPage + 1;
		int qna_endPage = qna_startPage + limitPage-1;
		if(qna_endPage > qna_maxPage) qna_endPage = qna_maxPage;
		
		PageInfo qna_pageInfo = new PageInfo();
		qna_pageInfo.setEndPage(qna_endPage);
		qna_pageInfo.setListCount(qna_listCount);
		qna_pageInfo.setMaxPage(qna_maxPage);
		qna_pageInfo.setPage(page);
		qna_pageInfo.setStartPage(qna_startPage);
		
				
		//리뷰게시판이면 상품 사진 추가
		ArrayList<ProductBean> prdList = null;
		ProductBean prd = new ProductBean();
		ProductDetailService prdDetailService = new ProductDetailService();
		if(reviewList != null) {
			prdList = new ArrayList<>();
			for(int i=0; i<reviewList.size(); i++) {
				prd = prdDetailService.getProduct(reviewList.get(i).getPro_num());	//상품 정보 불러오기
				prdList.add(prd);
			}
		}
		
		request.setAttribute("prdList", prdList);
		request.setAttribute("reviewPageInfo", review_pageInfo);
		request.setAttribute("reviewList", reviewList);
		request.setAttribute("qnaPageInfo", qna_pageInfo);
		request.setAttribute("qnaList", qnaList);
		request.setAttribute("pagefile", "/member/myboard.jsp");
		ActionForward forward = new ActionForward("/template.jsp", false);
		return forward;
	}

}
