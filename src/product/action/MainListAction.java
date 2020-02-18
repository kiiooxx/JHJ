package product.action;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import action.Action;
import product.svc.ProductListService;
import vo.ActionForward;
import vo.PageInfo;
import vo.ProductBean;

public class MainListAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// TODO Auto-generated method stub
		ArrayList<ProductBean> prdList = new ArrayList<>();
		
		
		ProductListService prdListService = new ProductListService();
		prdList = prdListService.getPrdList();
		
		request.setAttribute("prdList", prdList);
		
		request.setAttribute("pagefile", "/main.jsp");
		ActionForward forward = new ActionForward("/template.jsp", false);
		return forward;
	}

}
