package board.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import action.Action;
import product.svc.ProductDetailService;
import vo.ActionForward;
import vo.ProductBean;

public class BoardWriteFormAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// TODO Auto-generated method stub
		int pro_num = 0;
		ProductBean prd = null;
		
		if(request.getParameter("pro_num") != null) {
			pro_num = Integer.parseInt(request.getParameter("pro_num"));
			ProductDetailService prdDetailService = new ProductDetailService();
			prd = prdDetailService.getProduct(pro_num);	//상품 정보 불러오기
		}
		String board_type = request.getParameter("board_type");
		
		request.setAttribute("board_type", board_type);
		request.setAttribute("prd", prd);
		request.setAttribute("pagefile", "/board/board_write_form.jsp");
		ActionForward forward = new ActionForward("/template.jsp", false);
		return forward;
	}

}
