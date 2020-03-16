package admin.action;

import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import action.Action;
import admin.svc.MailOptionService;
import vo.ActionForward;
import vo.MailOption;

public class MailManagementAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		ActionForward forward = null;
		HttpSession session = request.getSession();
		
		if((session.getAttribute("id")==null) || !((String)session.getAttribute("id")).equals("admin")) {
			response.setContentType("text/html;charset=utf-8");
			PrintWriter out = response.getWriter();
			out.println("<script>");
			out.println("alert('관리자 로그인이 필요합니다.');");
			out.println("location.href='loginForm.log'");
			out.println("</script>");
			
		}else {
			
			ArrayList<MailOption> mailList = new ArrayList<>();
			
			char newMem = request.getParameter("A").charAt(0);
			char quitMem = request.getParameter("B").charAt(0);
			char newOrder = request.getParameter("C").charAt(0);
			char checkPaid = request.getParameter("D").charAt(0);
			char sendPro = request.getParameter("E").charAt(0);
			char deliIng = request.getParameter("F").charAt(0);
			char deliFin = request.getParameter("G").charAt(0);
			char confirmOrder = request.getParameter("H").charAt(0);
			char accCancel = request.getParameter("I").charAt(0);
			MailOptionService mailOptionService = new MailOptionService();
			mailList = mailOptionService.updateMailOption(newMem, quitMem, newOrder, 
					checkPaid, sendPro, deliIng, deliFin, confirmOrder, accCancel);
			
			request.setAttribute("mailList", mailList);
			request.setAttribute("pagefile", "/admin/mailManagement.jsp");
			forward = new ActionForward("/admin_template.jsp", false);
			
			
		}
		
		return forward;
	}

}
