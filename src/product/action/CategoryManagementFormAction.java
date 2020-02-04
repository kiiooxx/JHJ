package product.action;

import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import action.Action;
import product.svc.CategoryManagementFormService;
import vo.ActionForward;
import vo.CategoryBean;
import vo.ProductBean;

public class CategoryManagementFormAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// TODO Auto-generated method stub
		ArrayList<CategoryBean> categoryList = null;
		CategoryManagementFormService categoryManagementFormService = new CategoryManagementFormService();
		categoryList = categoryManagementFormService.selectCategoryList();

		request.setAttribute("categoryList", categoryList);
		request.setAttribute("pagefile", "/admin/category_management.jsp");
		ActionForward forward = new ActionForward("/template.jsp", false);
		return forward;
	}

}
