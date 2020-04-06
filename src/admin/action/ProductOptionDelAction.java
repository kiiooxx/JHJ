package admin.action;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import action.Action;
import admin.svc.ProductDelService;
import vo.ActionForward;

public class ProductOptionDelAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// TODO Auto-generated method stub
		String color = "";
		if(!(request.getParameter("color") == null || request.getParameter("color").equals(""))) {
			color = request.getParameter("color");
		}
		String size = "";
		if(!(request.getParameter("size") == null || request.getParameter("size").equals(""))) {
			size = request.getParameter("size");
		}
		String pro_num = "";
		if(!(request.getParameter("pro_num") == null || request.getParameter("pro_num").equals(""))) {
			pro_num = request.getParameter("pro_num");
		}
		String pro_det_num = "";
		if(!(request.getParameter("pro_det_num") == null || request.getParameter("pro_det_num").equals(""))) {
			pro_det_num = request.getParameter("pro_det_num");
		}
		System.out.println(color);
		System.out.println(size);
		System.out.println(pro_num);
		System.out.println(pro_det_num);
		ProductDelService productDelService = new ProductDelService();
		boolean isDeleteSuccess = productDelService.deleteProductOption(color, size, pro_num, pro_det_num);
		ActionForward forward = null;
		
		if(!isDeleteSuccess) {
			response.setContentType("text/html;charset=UTF-8");
			PrintWriter out = response.getWriter();
			out.println("<script>");
			out.println("alert('상품옵션 삭제 실패!')");
			out.println("history.back()");
			out.println("</script>");
			out.close();
		}else {
			 forward = new ActionForward("productModifyForm.ad?pro_num="+pro_num, true);
		}
		return forward;
	}

}
