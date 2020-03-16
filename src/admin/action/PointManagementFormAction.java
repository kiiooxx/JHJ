package admin.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import action.Action;
import admin.svc.PointManageFormService;
import vo.ActionForward;
import vo.PointMan;

public class PointManagementFormAction implements Action{

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		ActionForward forward = null;
		int seq = Integer.parseInt(request.getParameter("seq"));
		PointManageFormService pointManageFormService = new PointManageFormService();
		PointMan pointMan = pointManageFormService.getPointOption(seq);
		request.setAttribute("pointMan", pointMan);
		request.setAttribute("pagefile", "/admin/point_management.jsp");
		forward = new ActionForward("/admin_template.jsp", false);
		
		return forward;
	}

}
