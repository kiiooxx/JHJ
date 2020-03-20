package board.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import action.Action;
import board.svc.QnADetailService;
import board.svc.ReviewDetailService;
import product.svc.ProductDetailService;
import vo.ActionForward;
import vo.ProductBean;
import vo.QnABean;

public class QnAModifyFormAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// TODO Auto-generated method stub
		int qna_num = Integer.parseInt(request.getParameter("qna_num"));	//리뷰 번호
		
		//상품 번호
		int pro_num = 0;
		System.out.println("넘버 : " + request.getParameter("pro_num"));
		if(!(request.getParameter("pro_num") == null || request.getParameter("pro_num").equals(""))) {
			pro_num = Integer.parseInt(request.getParameter("pro_num"));
		}
		
		QnADetailService qnaDetailService = new QnADetailService();
		QnABean qnaBean = qnaDetailService.getQnA(qna_num);
		ProductDetailService prdDetailService = new ProductDetailService();
		ProductBean prd = prdDetailService.getProduct(pro_num);	//상품 정보 불러오기
		request.setAttribute("qna", qnaBean);
		request.setAttribute("prd", prd);
		request.setAttribute("pagefile", "/board/qna_modify_form.jsp");
		ActionForward forward = new ActionForward("/template.jsp", false);
		return forward;
	}

}
