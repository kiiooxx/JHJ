package admin.action;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import action.Action;
import admin.svc.CategoryDelService;
import vo.ActionForward;

public class CategoryDelAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// TODO Auto-generated method stub
		int cate_num = Integer.parseInt(request.getParameter("cate_num"));
		CategoryDelService categoryDelService = new CategoryDelService();
		boolean isDeleteSuccess = categoryDelService.deleteCategory(cate_num);
		ActionForward forward = null;
		
		if(!isDeleteSuccess) {
			response.setContentType("text/html;charset=UTF-8");
			PrintWriter out = response.getWriter();
			out.println("<script>");
			out.println("alert('카테고리 삭제 실패!')");
			out.println("history.back()");
			out.println("</script>");
			out.close();
		}else {
			 forward = new ActionForward("categoryManagement.ad", true);
		}
		return forward;
	}

}
