package admin.action;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import action.Action;
import admin.svc.ProductListUpdateService;
import vo.ActionForward;

public class ProductListUpdateAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// TODO Auto-generated method stub
		String pro_num[] = request.getParameter("pro_num").split(",");
		String active = null;
		String main_nb = null;
		ProductListUpdateService productListUpdateService = new ProductListUpdateService();
		boolean isUpdateSuccess = false;
		
		//진열 상태를 변경했을 시
		if(request.getParameter("active") != null) {
			active = request.getParameter("active");
			isUpdateSuccess = productListUpdateService.updateActive(pro_num, active);
		}
		
		//메인 상품 진열을 변경 했을 시에
		if(request.getParameter("main_nb") != null) {
			main_nb = request.getParameter("main_nb");
			isUpdateSuccess = productListUpdateService.updateMain_nb(pro_num, main_nb);
		}
		
		ActionForward forward = null;
		
		if(!isUpdateSuccess) {
			response.setContentType("text/html;charset=UTF-8");
			PrintWriter out = response.getWriter();
			out.println("<script>");
			out.println("alert('수정 실패!')");
			out.println("history.back()");
			out.println("</script>");
			out.close();
		}else {
			 forward = new ActionForward("productListManagement.ad", true);
		}
		return forward;
	}

}
