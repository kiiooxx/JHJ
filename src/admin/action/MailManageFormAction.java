package admin.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import action.Action;
import admin.svc.MailManageFormService;
import vo.ActionForward;
import vo.MailOption;

public class MailManageFormAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

		ActionForward forward = null;
		int seq = Integer.parseInt(request.getParameter("seq"));
		MailManageFormService mailManageFormService = new MailManageFormService();
		MailOption mailOption = mailManageFormService.getMailOption(seq);
		request.setAttribute("mailOption", mailOption);
		request.setAttribute("pagefile", "/admin/mailManagement.jsp");
		forward = new ActionForward("/admin_template.jsp", false);
		
		return forward;
	}

}
