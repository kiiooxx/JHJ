package admin.action;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import action.Action;
import admin.svc.ChangeStatusService;
import point.svc.PointService;
import vo.ActionForward;

public class ChangeStatusAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ActionForward forward = null;
			
		
		String sel_num = request.getParameter("sel_num");
		String deliStatus = request.getParameter("deliStatus");
		String user_id = request.getParameter("user_id");
		System.out.println("selNum:"+sel_num);
		System.out.println("deliStatus:"+deliStatus);
		ChangeStatusService changeStatusService = new ChangeStatusService(); 
		boolean isSuccess = changeStatusService.changeDeliStatus(sel_num, deliStatus);
		if(!isSuccess) {
			response.setContentType("text/html;charset=UTF-8");
			PrintWriter out = response.getWriter();
			out.println("<script>");
			out.println("alert('Fail');");
			out.println("history.back();");
			out.println("</script>");
			out.close();
		}else {
			
			if(deliStatus.equals("order_confirm")) {
				PointService pointService = new PointService();
				pointService.orderPoint(sel_num, user_id);
			}
			
			response.setContentType("text/html;charset=UTF-8");
			PrintWriter out = response.getWriter();
			out.println("<script>");
			out.println("alert('배송상태가 변경되었습니다.');");
			out.println("location.href='orderManageDetail.ad?sel_num=" + sel_num +"';");
			out.println("</script>");
			out.close();
			//forward = new ActionForward("orderManageDetail.ad?sel_num="+selNum, true);
		}
		
		
	
		
		return forward;
	}

}
