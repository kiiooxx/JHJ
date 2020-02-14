package admin.action;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import action.Action;
import admin.svc.ProductDelService;
import vo.ActionForward;

public class ProductDelAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// TODO Auto-generated method stub
		String pro_num[] = request.getParameter("pro_num").split(",");
		ProductDelService productDelService = new ProductDelService();
		boolean isDeleteSuccess = productDelService.deleteProduct(pro_num);
		ActionForward forward = null;
		
		if(!isDeleteSuccess) {
			response.setContentType("text/html;charset=UTF-8");
			PrintWriter out = response.getWriter();
			out.println("<script>");
			out.println("alert('상품 삭제 실패!')");
			out.println("history.back()");
			out.println("</script>");
			out.close();
		}else {
			 forward = new ActionForward("productListManagement.ad", true);
		}
		return forward;
		
	}

}
