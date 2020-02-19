package board.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import action.Action;
import board.svc.ReviewDetailService;
import product.svc.ProductDetailService;
import vo.ActionForward;
import vo.ProductBean;
import vo.ReviewBean;

public class ReviewDetailAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// TODO Auto-generated method stub
		
		int rev_num = Integer.parseInt(request.getParameter("rev_num"));	//리뷰 번호
		int pro_num = Integer.parseInt(request.getParameter("pro_num"));	//상품 번호
		ReviewDetailService reviewDetailService = new ReviewDetailService();
		ReviewBean reviewBean = reviewDetailService.getReview(rev_num);
		ProductDetailService prdDetailService = new ProductDetailService();
		ProductBean prd = prdDetailService.getProduct(pro_num);	//상품 정보 불러오기
		request.setAttribute("review", reviewBean);
		request.setAttribute("prd", prd);
		request.setAttribute("pagefile", "/board/review_detail.jsp");
		ActionForward forward = new ActionForward("/template.jsp", false);
		return forward;
	}

}
