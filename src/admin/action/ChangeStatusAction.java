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
//			//결제확인
//			if(deliStatus.equals("check_paid")) {
//				SendMailAction sendMailAction = new SendMailAction();
//				sendMailAction.mailling(request, response);
//			}
//			//상품발송
//			if(deliStatus.equals("send_pro")) {
//				SendMailAction sendMailAction = new SendMailAction();
//				sendMailAction.mailling(request, response);
//			}
//			//배송중
//			if(deliStatus.equals("deli_ing")) {
//				SendMailAction sendMailAction = new SendMailAction();
//				sendMailAction.mailling(request, response);				
//			}
//			//배송완료
//			if(deliStatus.equals("deli_fin")) {
//				SendMailAction sendMailAction = new SendMailAction();
//				sendMailAction.mailling(request, response);
//			}
			//구매확정 시 적립금 지급
			if(deliStatus.equals("order_confirm")) {
				PointService pointService = new PointService();
				pointService.orderPoint(sel_num, user_id);
//				SendMailAction sendMailAction = new SendMailAction();
//				sendMailAction.mailling(request, response);	
			}
			
			response.setContentType("text/html;charset=UTF-8");
			PrintWriter out = response.getWriter();
			out.println("<script>");
			out.println("alert('배송상태가 변경되었습니다.');");
			out.println("location.href='orderManageDetail.ad?sel_num=" + sel_num + "&user_id=" + user_id +"';");
			out.println("</script>");
			out.close();
		}
		
		return forward;
	}

}
