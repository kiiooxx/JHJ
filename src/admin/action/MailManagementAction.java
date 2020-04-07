package admin.action;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import action.Action;
import admin.svc.MailManagementService;
import vo.ActionForward;
import vo.MailOption;

public class MailManagementAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		ActionForward forward = null;
		MailOption mailOption = new MailOption();
		int newMem = 0;
		int quitMem = 0;
		int newOrder = 0;
		int checkPaid = 0;
		int sendPro = 0;
		int deliIng = 0;
		int deliFin = 0;
		int confirmOrder = 0; 
		int accCancel = 0;
		int qnaRe = 0;
			
		if(request.getParameter("A")!=null) {
			newMem = 1;
		}
		if(request.getParameter("B")!=null) {
			quitMem = 1;
		}
		if(request.getParameter("C")!=null) {
			newOrder = 1;
		}
		if(request.getParameter("D")!=null) {
			checkPaid = 1;
		}
		if(request.getParameter("E")!=null) {
			sendPro = 1;
		}
		if(request.getParameter("F")!=null) {
			deliIng = 1;
		}
		if(request.getParameter("G")!=null) {
			deliFin = 1;
		}
		if(request.getParameter("H")!=null) {
			confirmOrder = 1;
		}
		if(request.getParameter("I")!=null) {
			accCancel = 1;
		}
		if(request.getParameter("J")!=null) {
			qnaRe = 1;
		}
		
		mailOption.setNew_mem(newMem);
		mailOption.setQuit_mem(quitMem);
		mailOption.setOrder_info(newOrder);
		mailOption.setCheck_paid(checkPaid);
		mailOption.setSend_pro(sendPro);
		mailOption.setDeli_ing(deliIng);
		mailOption.setDeli_fin(deliFin);
		mailOption.setConfirm_order(confirmOrder);
		mailOption.setAcc_cancel(accCancel);
		mailOption.setQna_re(qnaRe);			
		
		MailManagementService mailManagementService = new MailManagementService();
		boolean isSettingSuccess = mailManagementService.updateMailOption(newMem, quitMem, newOrder, 
				checkPaid, sendPro, deliIng, deliFin, confirmOrder, accCancel, qnaRe);
		if(!isSettingSuccess) {
			response.setContentType("text/html;charset=UTF-8");
			PrintWriter out = response.getWriter();
			out.println("<script>");
			out.println("alert('저장실패')");
			out.println("history.back()");
			out.println("</script>");
		}else {
			request.setAttribute("mailOption", mailOption);
			response.setContentType("text/html;charset=UTF-8");
			PrintWriter out = response.getWriter();
			out.println("<script>");
			out.println("alert('설정이 저장되었습니다.')");
			out.println("window.close();");
			out.println("</script>");
		}
		
		return forward;
	}
}
