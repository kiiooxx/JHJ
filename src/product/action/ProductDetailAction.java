package product.action;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import action.Action;
import board.svc.BoardListService;
import product.svc.ProductDetailService;
import vo.ActionForward;
import vo.BoardBean;
import vo.PageInfo;
import vo.ProDetBean;
import vo.ProductBean;

public class ProductDetailAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// TODO Auto-generated method stub
		int pro_num = Integer.parseInt(request.getParameter("pro_num"));
		ProductDetailService prdDetailService = new ProductDetailService();
		ProductBean prd = prdDetailService.getProduct(pro_num);
		ArrayList<ProDetBean> prdDetList = prdDetailService.getProductDetail(pro_num);
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
		review_listCount = boardListService.getBoardListCount("review", "", pro_num);
		reviewList = boardListService.getBoardList("review", "", pro_num, page, limit);
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
		qna_listCount = boardListService.getBoardListCount("qna", "", pro_num);
		qnaList = boardListService.getBoardList("qna", "", pro_num, page, limit);
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
		
				
		request.setAttribute("reviewPageInfo", review_pageInfo);
		request.setAttribute("reviewList", reviewList);
		request.setAttribute("qnaPageInfo", qna_pageInfo);
		request.setAttribute("qnaList", qnaList);
		request.setAttribute("prd", prd);
		request.setAttribute("prdDetList", prdDetList);
		request.setAttribute("pagefile", "/product/product_detail.jsp");
		ActionForward forward = new ActionForward("/template.jsp", false);
		return forward;
	}

}
