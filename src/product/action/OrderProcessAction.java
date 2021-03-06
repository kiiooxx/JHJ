package product.action;

import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.StringTokenizer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import action.Action;
import admin.action.SendMailAction;
import admin.svc.PointManageFormService;
import point.svc.PointService;
import product.svc.CartListService;
import product.svc.CartQtyService;
import product.svc.OrderProcessService;
import vo.ActionForward;
import vo.Cart;
import vo.DeliInfo;
import vo.Order;
import vo.OrderDet;
import vo.PayInfo;
import vo.PointMan;

public class OrderProcessAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		ActionForward forward = null;
		
		//현재 로그인되어있는 아이디 = 주문자 아이디
		HttpSession session = request.getSession();
		String user_id = (String)session.getAttribute("id");
		
		//예정 적립금
		String confrimPoint = request.getParameter("confrimPoint");
		String reviewPoint = request.getParameter("reviewPoint");
		String total1 = request.getParameter("total1"); //상품총금액
		String result2 = request.getParameter("result2"); //최종결제금액
		
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

		
		CartListService cartListService = new CartListService();
		ArrayList<Cart> cartList = new ArrayList<Cart>();
		String pro_det_num[] = request.getParameterValues("pro_det_num");	//상품 상세 코드
		CartQtyService cartQtyService = new CartQtyService();
		
		//바로 주문 했을 시
		if(request.getParameter("directOrder").equals("true")) {
			String bas_pro_qnt[] = request.getParameterValues("bas_pro_qnt");
			String color[] = request.getParameterValues("color");
			String size[] = request.getParameterValues("pro_size");
			String pro_num[] = request.getParameterValues("pro_num");
			String pro_name[] = request.getParameterValues("pro_name");
			String pro_photo[] = request.getParameterValues("pro_photo");
			String pro_price[] = request.getParameterValues("pro_price");

			for(int i = 0; i < pro_det_num.length; i++) {
				Cart cart = new Cart();
				cart.setBas_num(i+1);
				cart.setPro_det_num(pro_det_num[i]);
				cart.setBas_pro_qnt(Integer.parseInt(bas_pro_qnt[i]));
				cart.setPro_num(Integer.parseInt(pro_num[i]));
				cart.setPro_name(pro_name[i]);
				cart.setPro_price(Integer.parseInt(pro_price[i]));
				cart.setPro_photo(pro_photo[i]);
				cart.setColor(color[i]);
				cart.setPro_size(size[i]);
				cart.setPro_det_num(pro_det_num[i]);

				cartList.add(cart);
			}
		}
		//장바구니에서 주문했을 경우
		else if(request.getParameter("directOrder").equals("false")){
			cartList = cartListService.getCheckedCartList(pro_det_num, request);
			cartQtyService.delCartQty(pro_det_num, request);	//주문한 상품 삭제
		}
		
		int totalMoney = 0;//총 결제금액
		int money = 0;//낱개금액
		String str = request.getParameter("point");
		str = str.replaceAll(",", "");
		int usePoint = Integer.parseInt(str);//사용포인트
		int deliPrice = 0;
		
		for(int i = 0; i < cartList.size(); i++) {
			money = cartList.get(i).getPro_price()*cartList.get(i).getBas_pro_qnt();
			System.out.println(money);
			totalMoney += money;
		}
		
		if(totalMoney < 50000) {
			deliPrice = 2500;
			totalMoney = totalMoney + deliPrice - usePoint;
			
		}else if(totalMoney >= 50000) {
			totalMoney = totalMoney - usePoint;
		}
		
		//배송정보 db에 넣을 내용
		DeliInfo deliInfo = new DeliInfo();
		deliInfo.setDeli_num("D"+sel_num);
		deliInfo.setUser_id(user_id);
		deliInfo.setRec_name(request.getParameter("recName"));
		deliInfo.setDeli_postcode(request.getParameter("recPostcode"));
		deliInfo.setDeli_addr1(request.getParameter("recAddr1"));
		deliInfo.setDeli_addr2(request.getParameter("recAddr2"));
		deliInfo.setRec_tel(request.getParameter("recTel"));
		deliInfo.setDeli_content(request.getParameter("deli_content"));
		
		//주문서 db에 넣을 내용
		Order order = new Order();
		order.setSel_num(sel_num);
		order.setSel_date(sel_date);
		order.setUser_id(user_id);
		order.setDeli_num("D"+sel_num); 
		order.setSel_status("order_done");
		order.setDeli_price(deliPrice);
		order.setPoint_use(usePoint);
		order.setFinal_price(totalMoney);
		
		//주문 상세 db에 넣을 내용 
		ArrayList<OrderDet> orderDet2 = new ArrayList<OrderDet>();
		
		if(!cartList.isEmpty()) {
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
		//payInfo.setPay_date("");//카드,실시간 이체 등 즉시 결제할 경우 사용
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
			PointManageFormService pointManageFormService = new PointManageFormService();
			PointMan pointMan = pointManageFormService.getPointOption(1);
			
			if(usePoint > 0) {
				PointService pointService = new PointService();
				boolean isUse = pointService.usePoint(sel_num);
				if(!isUse) {
					System.out.println("적립금 마이너스 실패");
				}else {
					System.out.println("적립금 마이너스 성공");
				}
			}
			
			
			
			request.setAttribute("totalMoney", totalMoney);
			request.setAttribute("deliInfo", deliInfo);
			request.setAttribute("order", order);
			request.setAttribute("orderDet", orderDet2);
			request.setAttribute("payInfo", payInfo);
			request.setAttribute("cartList", cartList);
			request.setAttribute("confrimPoint", confrimPoint);
			request.setAttribute("reviewPoint", reviewPoint);
			request.setAttribute("pointMan", pointMan);
			request.setAttribute("total1", total1);
			request.setAttribute("result2", result2);
			
			SendMailAction sendMailAction = new SendMailAction();
			sendMailAction.mailling(request, response);
			
			request.setAttribute("pagefile", "/product/orderResult.jsp");
			forward = new ActionForward("/template.jsp", false);
			
		}
		
		return forward;
	}

}
