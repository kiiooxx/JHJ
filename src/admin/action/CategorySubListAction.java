package admin.action;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import action.Action;
import admin.svc.CategoryListService;
import vo.ActionForward;
import vo.CategoryBean;

public class CategorySubListAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// TODO Auto-generated method stub
		int ca_ref = Integer.parseInt(request.getParameter("ca_ref"));
		
		ArrayList<CategoryBean> categorySubList = null;
		CategoryListService categoryListService = new CategoryListService();
		categorySubList = categoryListService.selectCategoryList(ca_ref);
		
		//세션으로 저장...
		HttpSession session = request.getSession();
		
		ActionForward forward = new ActionForward("productListManagement.ad", true);
		return forward;
	}

}
