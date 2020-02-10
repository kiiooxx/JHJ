package admin.action;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import action.Action;
import admin.svc.CategoryUpdateService;
import vo.ActionForward;

public class CategoryUpdateAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// TODO Auto-generated method stub
		ActionForward forward = null;
		int ca_ref = Integer.parseInt(request.getParameter("cate_large"));
		String cate_name = request.getParameter("cate_name");
		int cate_num = Integer.parseInt(request.getParameter("cate_num"));
		
		CategoryUpdateService categoryUpdateService = new CategoryUpdateService();
		boolean isUpdateSuccess = categoryUpdateService.updateCategory(ca_ref, cate_name, cate_num);
		
		if(!isUpdateSuccess) {
			response.setContentType("text/html;charset=UTF-8");
			PrintWriter out = response.getWriter();
			out.println("<script>");
			out.println("alert('카테고리 수정 실패!')");
			out.println("history.back()");
			out.println("</script>");
		}else {
			forward = new ActionForward("categoryManagement.ad", true);
		}
		return forward;
	}

}
