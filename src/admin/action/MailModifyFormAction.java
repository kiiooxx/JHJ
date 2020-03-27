package admin.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import action.Action;
import admin.svc.MailModifyFormService;
import vo.ActionForward;
import vo.MailOption;

public class MailModifyFormAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		String col_title = request.getParameter("col_title");
		String col_content = request.getParameter("col_content");
		ActionForward forward = null;
		MailModifyFormService mailModifyFormService = new MailModifyFormService();
		MailOption mailOption = mailModifyFormService.getMailForm(col_title, col_content);

		request.setAttribute("mailOption", mailOption);
		forward = new ActionForward("/mail_form/mail_form_modify.jsp", false);
		
		return forward;
	}

}
