package board.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import action.Action;
import product.svc.ProductDetailService;
import vo.ActionForward;
import vo.ProductBean;

public class QnAWriteFormAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// TODO Auto-generated method stub
		int pro_num = Integer.parseInt(request.getParameter("pro_num"));	//상품 번호
		System.out.println("번호머" + pro_num);
		ProductDetailService prdDetailService = new ProductDetailService();
		ProductBean prd = prdDetailService.getProduct(pro_num);	//상품 정보 불러오기
		request.setAttribute("prd", prd);
		request.setAttribute("pagefile", "/board/qna_write_form.jsp");
		ActionForward forward = new ActionForward("/template.jsp", false);
		return forward;
	}

}