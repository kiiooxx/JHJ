package admin.action;


import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import action.Action;
import admin.svc.AcceptCancelService;
import vo.ActionForward;

public class AcceptCancelAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		ActionForward forward = null;
		String sel_num = request.getParameter("sel_num");
		String user_id = request.getParameter("user_id");
		
		AcceptCancelService acceptCancelService = new AcceptCancelService();
		boolean isSuccess = acceptCancelService.acceptCancel(sel_num);
		
		if(!isSuccess) {
			response.setContentType("text/html;charset=UTF-8");
			PrintWriter out = response.getWriter();
			out.println("<script>");
			out.println("alert('취소승인 실패')");
			out.println("history.back()");
			out.println("</script>");
		}else {
			
			response.setContentType("text/html;charset=UTF-8");
			PrintWriter out = response.getWriter();
			out.println("<script>");
			out.println("alert('주문이 취소되었습니다.')");
			out.println("location.href='orderManageDetail.ad?sel_num=" +sel_num+ "&user_id=" +user_id+"';");
			out.println("</script>");
		}
		
		return forward;
	}
	
	

}
