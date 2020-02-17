package product.action;

import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import action.Action;
import product.svc.CartListService;
import product.svc.CartQtyService;
import product.svc.OrderProcessService;
import vo.ActionForward;
import vo.Cart;
import vo.DeliInfo;
import vo.Order;
import vo.OrderDet;
import vo.PayInfo;

public class OrderProcessAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		ActionForward forward = null;
		
		//현재 로그인되어있는 아이디 = 주문자 아이디
		HttpSession session = request.getSession();
		String user_id = (String)session.getAttribute("id");
		
		//주문번호 주문일 format 설정
		SimpleDateFormat ForSelNum = new SimpleDateFormat("yyMMddHHmmss");
		SimpleDateFormat ForSelDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		
		//입금기한 (주문일로부터 3일 이내)
		SimpleDateFormat ForExpDate = new SimpleDateFormat("yyyy-MM-dd");
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		cal.add(Calendar.DATE, 3);

		Date now = new Date();
		String sel_num = ForSelNum.format(now);
		String sel_date = ForSelDate.format(now);
		String pay_exp = ForExpDate.format(cal.getTime());
		
		//orderpage에 있는 상품정보, 배송정보, 결제정보 가져와서 객체에 저장
		
		
		
		CartListService cartListService = new CartListService();
		ArrayList<Cart> cartList = cartListService.getCartList(request);
		String pro_det_num[] = null;
//		CartQtyService cartQtyService = new CartQtyService();
		
		if(!(request.getParameter("pro_det_num") == null || request.getParameter("pro_det_num").equals(""))) {
			//장바구니에서 선택한 상품만 주문할 경우 
			pro_det_num = request.getParameter("pro_det_num").split(",");
			cartList = cartListService.getCheckedCartList(pro_det_num,request);
			
//			cartQtyService.delCartQty(pro_det_num, request);
		}else {
			//장바구니에서 전체 상품을 주문할 경우
			cartList = cartListService.getCartList(request);
//			cartQtyService.delAllCartQty(request);
		}
		
		int totalMoney = 0;//총 결제금액
		int money = 0;//낱개금액
		int usePoint = Integer.parseInt(request.getParameter("usePoint"));//사용포인트
		int deliPrice = 0;
		
		for(int i = 0; i < cartList.size(); i++) {
			money = cartList.get(i).getPro_price()*cartList.get(i).getBas_pro_qnt();
			System.out.println(money);
			totalMoney += money;
			System.out.println("cartList:"+cartList);
		}
		
		if(totalMoney < 50000) {
			deliPrice = 2500;
			totalMoney = totalMoney + deliPrice - usePoint;
			
		}else if(totalMoney >= 50000) {
			totalMoney = totalMoney - usePoint;
		}
		
		//배송정보 db에 넣을거
		DeliInfo deliInfo = new DeliInfo();
		deliInfo.setDeli_num("D"+sel_num);
		deliInfo.setUser_id(user_id);
		deliInfo.setRec_name(request.getParameter("recName"));
		deliInfo.setDeli_postcode(request.getParameter("recPostcode"));
		deliInfo.setDeli_addr1(request.getParameter("recAddr1"));
		deliInfo.setDeli_addr2(request.getParameter("recAddr2"));
		deliInfo.setRec_tel(request.getParameter("recTel"));
		deliInfo.setDeli_content(request.getParameter("deli_content"));
		
		//주문서 db에 넣을거
		Order order = new Order();
		order.setSel_num(sel_num);
		order.setSel_date(sel_date);
		order.setUser_id(user_id);
		order.setDeli_num("D"+sel_num); 
		order.setSel_status("order_done");
		order.setDeli_price(deliPrice);
		order.setPoint_use(Integer.parseInt(request.getParameter("usePoint")));
		order.setFinal_price(totalMoney);
		
		//주문 상세 db에 넣을거 - 
		ArrayList<OrderDet> orderDet2 = new ArrayList<OrderDet>();
		
		if(cartList != null) {
			for(int i=0; i<cartList.size(); i++) {
				OrderDet orderDet = new OrderDet();
				orderDet.setSel_num(sel_num);
				orderDet.setPro_det_num(cartList.get(i).getPro_det_num());
				orderDet.setPro_qnt(cartList.get(i).getBas_pro_qnt());
				orderDet2.add(orderDet);
			}
		}
		
		
		
		//결제정보 db에 넣을거
		PayInfo payInfo = new PayInfo();
		payInfo.setSel_num(sel_num);
		//payInfo.setPay_date("");//결제일은 주문일이랑 다르니까 이건 비워놔야할듯? 지금은 무통장만 있으니까,,카드는 거의 동시에 될듯
		payInfo.setPay_type(request.getParameter("payment"));
		payInfo.setAcc_name(request.getParameter("account_name"));
		payInfo.setAcc_bank(request.getParameter("bank"));
		payInfo.setPay_exp(pay_exp);
		
		
		OrderProcessService orderProcessService = new OrderProcessService();
		
		boolean addOrderSuccess = orderProcessService.addOrder(deliInfo, order, orderDet2, payInfo);

		
		if(!addOrderSuccess) {
			response.setContentType("text/html;charset=UTF-8");
			PrintWriter out = response.getWriter();
			out.println("<script>");
			out.println("alert('주문 진행에 오류가 발생했습니다.');");
			out.println("history.back();");
			out.println("</script>");
			
		}else {
			//다 입력 성공하면 아래에 있는거 가져가서 orderResult.jsp에 보여주기??
			request.setAttribute("totalMoney", totalMoney);
			request.setAttribute("deliInfo", deliInfo);
			request.setAttribute("order", order);
			request.setAttribute("orderDet", orderDet2);
			request.setAttribute("payInfo", payInfo);
			request.setAttribute("cartList", cartList);
			
			request.setAttribute("pagefile", "/product/orderResult.jsp");
			forward = new ActionForward("/template.jsp", false);
			
		}
		
		return forward;
	}

}
