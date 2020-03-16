package admin.action;

import java.io.PrintWriter;
import java.util.Properties;

import authTest.GoogleAuthentication;
import javax.mail.Address;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;



public class SendMailAction {

	public void mailling(HttpServletRequest request, HttpServletResponse response) throws Exception {
	
		String id = request.getParameter("id");
		String sender = "camillayin@gmail.com";
		String receiver = request.getParameter("email");
		String subject = "[JHJ]의 회원이 되신 것을 축하합니다!";
		String content = "[JHJ]의 회원이 되신 것을 축하합니다!<br>가입하신 정보를 안내해 드립니다.<br>회원아이디:"+id;
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out = response.getWriter();
		
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
