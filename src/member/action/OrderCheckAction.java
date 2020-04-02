package member.action;

import java.io.PrintWriter;
import static db.JdbcUtil.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import action.Action;
import member.svc.OrderCancelService;
import member.svc.OrderCheckService;
import point.svc.PointService;
import vo.ActionForward;

public class OrderCheckAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// TODO Auto-generated method stub
		ActionForward forward = null;
		
		String sel_num = request.getParameter("sel_num");
		String sel_status = request.getParameter("sel_status");
		HttpSession session = request.getSession();
		String user_id = (String) session.getAttribute("id");
		
		OrderCheckService orderCheckService = new OrderCheckService();
		
		boolean isUpdateorderChk = false;
		
		isUpdateorderChk = orderCheckService.orderChk(sel_status, sel_num);
		
		if(!isUpdateorderChk) {
			response.setContentType("text/html;charset=UTF-8");
			PrintWriter out = response.getWriter();
			out.println("<script>");
			out.println("alert('구매 확정 실패')");
			out.println("history.back()");
			out.println("</script>");
		}else {
			PointService pointService = new PointService();
			boolean isOrderPoint = pointService.orderPoint(sel_num, user_id);
			
			if(isOrderPoint) {
				response.setContentType("text/html;charset=UTF-8");
				PrintWriter out = response.getWriter();
				out.println("<script>");
				out.println("alert('구매확정이 완료되었습니다.')");
				out.println("location.href='order.mem'");
				out.println("</script>");
			}else {
				response.setContentType("text/html;charset=UTF-8");
				PrintWriter out = response.getWriter();
				out.println("<script>");
				out.println("alert('적립금 지급 실패')");
				out.println("history.back()");
				out.println("</script>");
			}
		}
		return forward;
		
		
	}

}
