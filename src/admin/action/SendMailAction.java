package admin.action;


import java.util.ArrayList;
import java.util.Properties;

import authTest.GoogleAuthentication;
import vo.BoardBean;
import vo.Cart;
import vo.DeliInfo;
import vo.MailOption;
import vo.Order;
import vo.OrderProView;
import vo.PayInfo;

import javax.mail.Address;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import admin.svc.SendMailService;


public class SendMailAction {

	public void mailling(HttpServletRequest request, HttpServletResponse response) throws Exception {
	
		String col = request.getParameter("col");
		String col_title = request.getParameter("col_title");
		String col_content = request.getParameter("col_content");
		
		SendMailService sendMailService = new SendMailService();
		
		//메일 전송 옵션 사용함 상태인지 아닌지 확인
		int option = sendMailService.oneOptionValue(col);
		//해당 메일 폼 가져오기
		MailOption mailForm = sendMailService.getMailForm(col_title, col_content);
		
		if(option == 1) {
			
			//메일 내용 가져오기
			String id = request.getParameter("id");
			//BoardBean boardBean = (BoardBean) request.getAttribute("boardBean");
			//BoardBean boardBean_ref = (BoardBean) request.getAttribute("boardBean_ref");
			String sender = "camillayin@gmail.com";
			String receiver = request.getParameter("email");
			String subject = mailForm.getTitle();
			String content = mailForm.getContent();
			if(id != null) {
				content = content.replace("{member_id}", id);
			}
			
			if(col.equals("order_info")) {
				//주문안내메일
				Order order = new Order();
				order = (Order) request.getAttribute("order");
				DeliInfo deliInfo = new DeliInfo();
				deliInfo = (DeliInfo) request.getAttribute("deliInfo");
				PayInfo payInfo = new PayInfo();
				payInfo = (PayInfo) request.getAttribute("payInfo");
				ArrayList<Cart> cart = (ArrayList<Cart>) request.getAttribute("cartList");
				
				
				String user_name = request.getParameter("user_name");
				content = content.replace("{고객성명}", user_name);
							
				String sel_num = order.getSel_num();
				content = content.replace("{주문번호}", sel_num);
				
				//String pro_name = cart.get();
				//content = content.replace("{주문상품}", pro_name);
				
				String rec_name = deliInfo.getRec_name();
				content = content.replace("{수령인}", rec_name);
				
				String rec_tel = deliInfo.getRec_tel();
				content = content.replace("{연락처}", rec_tel);
				
				String rec_addr = deliInfo.getDeli_addr1() + deliInfo.getDeli_addr2();
				content = content.replace("{주소}", rec_addr);
				
				String pay_type = payInfo.getPay_type();
				content = content.replace("{결제수단}", pay_type);
				
				String price = Integer.toString(order.getFinal_price()+order.getPoint_use()-order.getDeli_price());
				content = content.replace("{주문금액합계}", price);
				
				String deli_price = Integer.toString(order.getDeli_price());
				content = content.replace("{배송비합계}", deli_price);
				
				String point_use = Integer.toString(order.getPoint_use());
				content = content.replace("{적립금사용}", point_use);
				
				String final_price = Integer.toString(order.getFinal_price());
				content = content.replace("{최종결제금액}", final_price);
			}
			
			
			try {
				Properties properties = System.getProperties();
				properties.put("mail.smtp.starttls.enable", "true");
				properties.put("mail.smtp.host", "smtp.gmail.com");
				properties.put("mail.smtp.auth", "true");
				properties.put("mail.smtp.port", "587");
				Authenticator auth = new GoogleAuthentication();
				Session s = Session.getDefaultInstance(properties, auth);
				Message message = new MimeMessage(s);
				
				Address sender_addr = new InternetAddress(sender);
				Address receiver_addr = new InternetAddress(receiver);
				message.setHeader("content-type", "text/html;charset=UTF-8");
				message.setFrom(sender_addr);
				message.addRecipient(Message.RecipientType.TO, receiver_addr);
				message.setSubject(subject);
				message.setContent(content, "text/html;charset=UTF-8");
				message.setSentDate(new java.util.Date());
				Transport.send(message);
				System.out.println("send success");
			}catch(Exception e) {
				e.printStackTrace();
				System.out.println("send failed");
			}

		}
		
	}

}
