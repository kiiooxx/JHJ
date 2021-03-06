package member.action;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import action.Action;
import admin.svc.OrderManageDetailService;
import member.svc.OrderCancelService;
import member.svc.OrderDetailService;
import vo.ActionForward;
import vo.DeliInfo;
import vo.Order;
import vo.OrderCancel;
import vo.OrderProView;
import vo.PayInfo;

public class OrderCancelFormAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// TODO Auto-generated method stub
		ActionForward forward = null;
		
		HttpSession session = request.getSession();
		String id = (String) session.getAttribute("id");
		
			String sel_num = request.getParameter("sel_num");
			
			
			OrderManageDetailService orderManageDetailService = new OrderManageDetailService();
			OrderDetailService orderDetailService = new OrderDetailService();
			OrderCancelService orderCancelService = new OrderCancelService();
			
			ArrayList<OrderProView> orderProList = orderManageDetailService.getOrderDetail(sel_num);
			DeliInfo deliInfo = orderManageDetailService.getDeliInfo(id);
			PayInfo payInfo = orderManageDetailService.getPayInfo(sel_num);
			Order orderInfo = orderDetailService.getOrderDetailInfo(sel_num);
			
			
			
		
			request.setAttribute("orderProList", orderProList);
			request.setAttribute("deliInfo", deliInfo);
			request.setAttribute("payInfo", payInfo);
			request.setAttribute("orderInfo", orderInfo);
		
			
			orderInfo.setCancel_reason(request.getParameter("cancel_reason"));
			
			
			request.setAttribute("pagefile", "member/orderCancel.jsp");
			forward = new ActionForward("/template.jsp",false);
			
		return forward;
	}

}
