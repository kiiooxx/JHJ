package admin.action;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import action.Action;
import admin.svc.CategoryListService;
import vo.ActionForward;
import vo.CategoryBean;

public class CategoryManagementForm implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// TODO Auto-generated method stub
		ArrayList<CategoryBean> categoryList = null;
		CategoryListService categoryListService = new CategoryListService();
		categoryList = categoryListService.selectCategoryList();
		
		//세션으로 저장...
		HttpSession session = request.getSession();
		session.setAttribute("categoryList", categoryList);
		request.setAttribute("pagefile", "/admin/category_management.jsp");
		ActionForward forward = new ActionForward("/admin_template.jsp", false);
		return forward;
	}

}
