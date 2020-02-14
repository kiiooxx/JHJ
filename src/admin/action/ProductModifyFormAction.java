package admin.action;

import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import action.Action;
import admin.svc.ProductModifyService;
import vo.ActionForward;
import vo.ProDetBean;
import vo.ProductBean;

public class ProductModifyFormAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// TODO Auto-generated method stub
		int pro_num = Integer.parseInt(request.getParameter("pro_num"));
		ProductModifyService productModifyService = new ProductModifyService();
		ProductBean productBean = null;
		ArrayList<ProDetBean> proDetList = null;
		productBean = productModifyService.selectProInfo(pro_num);
		proDetList = productModifyService.selectProDet(pro_num);
		
		if(productBean == null) {
			response.setContentType("text/html;charset=UTF-8");
			PrintWriter out = response.getWriter();
			out.println("<script>");
			out.println("alert('상품 정보 불러오기 실패!')");
			out.println("history.back()");
			out.println("</script>");
		}
		
		request.setAttribute("pro_num", pro_num);
		request.setAttribute("prd", productBean);
		request.setAttribute("proDetList", proDetList);
		ActionForward forward = null;
		forward = new ActionForward("/admin/product_modify.jsp", false);
		return forward;
	}

}
