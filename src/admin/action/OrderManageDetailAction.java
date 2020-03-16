package admin.action;

import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import action.Action;
import admin.svc.OrderManageDetailService;
import vo.ActionForward;
import vo.DeliInfo;
import vo.Order;
import vo.OrderProView;
import vo.PayInfo;


public class OrderManageDetailAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		ActionForward forward = null;
		HttpSession session = request.getSession();
		
		if((session.getAttribute("id")==null) || (!((String)session.getAttribute("id")).equals("admin"))) {
			response.setContentType("text/html;charset=utf-8");
			PrintWriter out = response.getWriter();
			out.println("<script>");
			out.println("alert('관리자 로그인이 필요합니다.');");
			out.println("location.href='loginForm.log'");
			out.println("</script>");
		}else {
			
			
			String sel_num = request.getParameter("sel_num");
			String user_id = request.getParameter("user_id");
			OrderManageDetailService orderManageDetailService = new OrderManageDetailService();
			
			ArrayList<OrderProView> orderProList = orderManageDetailService.getOrderDetail(sel_num);
			DeliInfo deliInfo = orderManageDetailService.getDeliInfo(user_id);
			PayInfo payInfo = orderManageDetailService.getPayInfo(sel_num);
			Order orderInfo = orderManageDetailService.getOrderInfo(sel_num);
			
			
			request.setAttribute("orderProList", orderProList);
			request.setAttribute("deliInfo", deliInfo);
			request.setAttribute("payInfo", payInfo);
			request.setAttribute("orderInfo", orderInfo);
			request.setAttribute("pagefile", "admin/orderManageDetail.jsp");
			forward = new ActionForward("/admin_template.jsp", false);
		}
		
		return forward;
	}

}
