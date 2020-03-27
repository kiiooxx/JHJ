package admin.action;


import java.util.Properties;

import authTest.GoogleAuthentication;
import vo.MailOption;

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
			String sender = "camillayin@gmail.com";
			String receiver = request.getParameter("email");
			String subject = mailForm.getTitle();
			String content = mailForm.getContent();
			content = content.replace("{member_id}", id);
			
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
