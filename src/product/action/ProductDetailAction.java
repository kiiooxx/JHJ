package product.action;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import action.Action;
import board.svc.ReviewListService;
import product.svc.ProductDetailService;
import vo.ActionForward;
import vo.PageInfo;
import vo.ProDetBean;
import vo.ProductBean;
import vo.ReviewBean;

public class ProductDetailAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// TODO Auto-generated method stub
		int pro_num = Integer.parseInt(request.getParameter("pro_num"));
		ProductDetailService prdDetailService = new ProductDetailService();
		ProductBean prd = prdDetailService.getProduct(pro_num);
		ArrayList<ProDetBean> prdDetList = prdDetailService.getProductDetail(pro_num);
		ArrayList<ReviewBean> reviewList = new ArrayList<>();	//리뷰 리스트

		int page = 1;
		int limit = 10;	//페이지에 보여줄 목록 수
		int limitPage = 5;	//페이지 수
		int listCount = 0;
		
		if(request.getParameter("page") != null) {
			page = Integer.parseInt(request.getParameter("page"));
		}
		
		//전체 리뷰 목록
		ReviewListService reviewListService = new ReviewListService();
		listCount = reviewListService.getListCount(pro_num);
		//총 리스트 수를 받아옴
		reviewList = reviewListService.getReviewList(pro_num, page, limit);
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
		request.setAttribute("reviewPageInfo", pageInfo);
		request.setAttribute("reviewList", reviewList);
		request.setAttribute("prd", prd);
		request.setAttribute("prdDetList", prdDetList);
		request.setAttribute("pagefile", "/product/product_detail.jsp");
		ActionForward forward = new ActionForward("/template.jsp", false);
		return forward;
	}

}
