package product.action;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import action.Action;
import product.svc.CategoryManagementFormService;
import vo.ActionForward;

public class CategoryAddAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// TODO Auto-generated method stub
		CategoryManagementFormService categoryManagementFormService = new CategoryManagementFormService();
		String cate_large = null;
		String cate_name = null;
		boolean addSuccess = false;
		
		
		if(request.getParameter("cate_name") != null) {
			cate_large = request.getParameter("cate_large");
			System.out.println(cate_large);
			cate_name = request.getParameter("cate_name");

			//소분류 등록일때
			if(request.getParameter("cate_large") != null) {
				addSuccess = categoryManagementFormService.addCategory(cate_large, cate_name, 1, 1);
			//대분류 등록일때
			}else {
				addSuccess = categoryManagementFormService.addCategory(cate_large, cate_name, 0, 0);
			}
			
			if(!addSuccess) {
				response.setContentType("text/html;charset=UTF-8");
				PrintWriter out = response.getWriter();
				out.println("<script>");
				out.println("alert('카테고리 추가 실패');");
				out.println("history.back()");
				out.println("</script>");
			}
		}
		
		ActionForward forward = new ActionForward("categoryManagement.pro", true);
		return forward;
	}

}
