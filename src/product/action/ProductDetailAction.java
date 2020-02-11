package product.action;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import action.Action;
import product.svc.ProductDetailService;
import vo.ActionForward;
import vo.ProDetBean;
import vo.ProductBean;

public class ProductDetailAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// TODO Auto-generated method stub
		int pro_num = Integer.parseInt(request.getParameter("pro_num"));
		String page = request.getParameter("page");
		ProductDetailService prdDetailService = new ProductDetailService();
		ProductBean prd = prdDetailService.getProduct(pro_num);
		ArrayList<ProDetBean> prdDetList = prdDetailService.getProductDetail(pro_num);
		
		request.setAttribute("page", page);
		request.setAttribute("prd", prd);
		request.setAttribute("prdDetList", prdDetList);
		request.setAttribute("pagefile", "/product/product_detail.jsp");
		ActionForward forward = new ActionForward("/template.jsp", false);
		return forward;
	}

}
