package member.action;

import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import action.Action;
import admin.svc.OrderManageDetailService;
import board.svc.BoardListService;
import member.svc.OrderDetailService;
import vo.ActionForward;
import vo.BoardBean;
import vo.DeliInfo;
import vo.Order;
import vo.OrderProView;
import vo.PayInfo;

public class OrderDetailAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// TODO Auto-generated method stub
		ActionForward forward = null;
		
		HttpSession session = request.getSession();
		String id = (String) session.getAttribute("id");
		
		String sel_num = request.getParameter("sel_num");
		
		OrderManageDetailService orderManageDetailService = new OrderManageDetailService();
		OrderDetailService orderDetailService = new OrderDetailService();
		
		ArrayList<OrderProView> orderProList = orderManageDetailService.getOrderDetail(sel_num);
		DeliInfo deliInfo = orderManageDetailService.getDeliInfo(id);
		PayInfo payInfo = orderManageDetailService.getPayInfo(sel_num);
		Order orderInfo = orderDetailService.getOrderDetailInfo(sel_num);
	
		//리뷰글 목록을 들고온다.
		BoardListService boardListService = new BoardListService();
		ArrayList<BoardBean> reviewList = boardListService.getBoardList("review", id, 0, 1, 0);
				
		request.setAttribute("reviewList", reviewList);
		request.setAttribute("orderProList", orderProList);
		request.setAttribute("deliInfo", deliInfo);
		request.setAttribute("payInfo", payInfo);
		request.setAttribute("orderInfo", orderInfo);
		request.setAttribute("pagefile", "/member/orderDetail.jsp");
		forward = new ActionForward("/template.jsp", false);
		
		
		return forward;
	}

}
