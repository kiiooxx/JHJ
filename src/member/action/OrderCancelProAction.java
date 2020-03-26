package member.action;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import action.Action;
import member.svc.OrderCancelService;
import vo.ActionForward;

public class OrderCancelProAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// TODO Auto-generated method stub
		ActionForward forward = null;
		
		String sel_num = request.getParameter("sel_num");
		
		String cancel_reason =  request.getParameter("reqselect");
		
		if(request.getParameter("reqselect").equals("etc")) {
			cancel_reason = request.getParameter("cancel_reason");
		}else if(cancel_reason.equals("orderReqCancel")) {
			cancel_reason = "구매의사없음";
		}else if(cancel_reason.equals("orderReqCancel")) { 
			cancel_reason = "색상 및 사이즈 변경";
		}else if(cancel_reason.equals("orderReqCancel")){
			cancel_reason = "다른 상품 잘못 주문";
		}else if(cancel_reason.equals("orderReqCancel")){
			cancel_reason = "서비스 정보 불만족";
		}else if(cancel_reason.equals("orderReqCancel")) {
			cancel_reason = "상품 정보 상이";
		}

		System.out.println("취소사유확인"+cancel_reason);
		
		OrderCancelService orderCancelService = new OrderCancelService();
		
		boolean isUpdateCancel = false;
		
		isUpdateCancel = orderCancelService.cancelReq(cancel_reason,sel_num);
		
		if(!isUpdateCancel) {
			response.setContentType("text/html;charset=UTF-8");
			PrintWriter out = response.getWriter();
			out.println("<script>");
			out.println("alert('수정 실패')");
			out.println("history.back()");
			out.println("</script>");
		}else {
			response.setContentType("text/html;charset=UTF-8");
			PrintWriter out = response.getWriter();
			out.println("<script>");
			out.println("alert('취소요청이 완료되었습니다.')");
			out.println("location.href='order.mem'");
			out.println("</script>");
		}
		return forward;
	}

}
