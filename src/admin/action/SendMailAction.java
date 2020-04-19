package admin.action;


import java.util.ArrayList;
import java.util.Properties;

import authTest.GoogleAuthentication;
import vo.BoardBean;
import vo.Cart;
import vo.DeliInfo;
import vo.MailOption;
import vo.Order;
import vo.OrderDet;
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
import javax.servlet.http.HttpSession;

import admin.svc.SendMailService;


public class SendMailAction {

	public void mailling(HttpServletRequest request, HttpServletResponse response) throws Exception {
	
		HttpSession session = request.getSession();
		String col = request.getParameter("col");
		
		if(col != null) {
			col = request.getParameter("col");
		}else {
			col = request.getParameter("deliStatus");
		}
		
		String col_title = col + "_title" ;
		String col_content = col + "_content" ;
		
		System.out.println("col : " +  col);
		System.out.println("col_title : " +  col_title);
		System.out.println("col_content : " +  col_content);
		SendMailService sendMailService = new SendMailService();
		
		MailOption mailForm = sendMailService.getMailForm(col, col_title, col_content);
		
		if(mailForm != null) {
			
			//메일 내용 가져오기
			String id = "";
			String sender = "camillayin@gmail.com";
			String receiver = request.getParameter("email");
			String subject = mailForm.getTitle();
			String content = mailForm.getContent();
			if(col.equals("new_mem")) {
				id = request.getParameter("id");	
				content = content.replace("{고객아이디}", id);
			}
			if(col.equals("quit_mem")) {
				id = (String) session.getAttribute("id");
				System.out.println("id : "+id);
				content = content.replace("{고객아이디}", id);
			}
			
//			if(id != null) {
//				content = content.replace("{고객아이디}", id);
//			}
//			if(col.equals("check_paid")) {
//				Order order = new Order();
//				order = (Order) request.getAttribute("orderInfo");
//				PayInfo payInfo = new PayInfo();
//				payInfo = (PayInfo) request.getAttribute("payInfo");
//				
//				String sel_num = order.getSel_num();
//				content = content.replace("{주문번호}", sel_num);
//				
//				String pay_type = payInfo.getPay_type();
//				if(pay_type.equals("mutong")) {
//					content = content.replace("{결제수단}", "무통장입금");
//				}else if(pay_type.equals("silsi")) {
//					content = content.replace("{결제수단}", "실시간 계좌이체");
//				}else {
//					content = content.replace("{결제수단}", "카드결제");
//				}
//			}
			
			
			if(col.equals("order_info")) {
				//주문안내메일
				Order order = new Order();
				order = (Order) request.getAttribute("order");
				DeliInfo deliInfo = new DeliInfo();
				deliInfo = (DeliInfo) request.getAttribute("deliInfo");
				PayInfo payInfo = new PayInfo();
				payInfo = (PayInfo) request.getAttribute("payInfo");
				
				String[] proPhoto = request.getParameterValues("photo");
				String[] proName = request.getParameterValues("pro_name");
				String[] proSize = request.getParameterValues("pro_size");
				String[] proColor = request.getParameterValues("color");
				String[] proQnt = request.getParameterValues("bas_pro_qnt");
				String str = "";
				
				for(int i = 0; i < proName.length; i++) {
					str += proName[i] + "[옵션 : " +proSize[i] + " / " + proColor[i] + "]  수량: " + proQnt[i] + "<br>";	
				}
				content = content.replace("{주문상품}", str);
				
				String user_name = request.getParameter("user_name");
				content = content.replace("{고객성명}", user_name);
							
				String sel_num = order.getSel_num();
				content = content.replace("{주문번호}", sel_num);
				
				String rec_name = deliInfo.getRec_name();
				content = content.replace("{수령인}", rec_name);
				
				String rec_tel = deliInfo.getRec_tel();
				content = content.replace("{연락처}", rec_tel);
				
				String rec_addr = deliInfo.getDeli_addr1() + deliInfo.getDeli_addr2();
				content = content.replace("{주소}", rec_addr);
				
				String pay_type = payInfo.getPay_type();
				if(pay_type.equals("mutong")) {
					content = content.replace("{결제수단}", "무통장입금");
				}else if(pay_type.equals("silsi")) {
					content = content.replace("{결제수단}", "실시간 계좌이체");
				}else {
					content = content.replace("{결제수단}", "카드결제");
				}
				
				
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


