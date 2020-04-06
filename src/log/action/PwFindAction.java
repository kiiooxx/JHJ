package log.action;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import action.Action;
import log.svc.IdFindService;
import log.svc.PwFindService;
import mail.GoogleAuthentication;
import vo.ActionForward;
import vo.Member;

import java.util.Properties;
import java.util.UUID;
import java.sql.*;
import javax.sql.*;
import javax.mail.Address;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.naming.*;

public class PwFindAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// TODO Auto-generated method stub
		Member member = null;
		ActionForward forward = null;

		// 1. form에서 전송한 값 getParameter로 받아온다.(이름, 휴대번호...)
		String id = request.getParameter("id");

		String receiver = request.getParameter("email") + "@" + request.getParameter("e_domain");
		System.out.println(receiver);
		// 2. 서비스 생성
		PwFindService pwFindService = new PwFindService();
		member = pwFindService.selectMember3(id, receiver);

		if (member != null) {

			String uuid = "";
			// uuid : 임시비밀번호

			uuid = UUID.randomUUID().toString().replaceAll("-", ""); // -를 제거해 주었다.
			uuid = uuid.substring(0, 10); // uuid를 앞에서부터 10자리 잘라줌.

			boolean isRight = false;//
			
			isRight = pwFindService.updateTempPW(id, uuid);
			
			//불린값이 펄스면 실행 안되는거 아닌가?
			if(isRight) {// content : 임시비밀번호는 uuid 입니다.
				System.out.println(" 임시" + uuid);
				System.out.println(" 아이디 :" + member.getUser_id());
				System.out.println(" 아이디2:" + request.getParameter("id"));

				// content = uuid;
				response.setContentType("text/html;charset=UTF-8");
				PrintWriter out = response.getWriter();
				try {
					Properties properties = System.getProperties();
					properties.put("mail.smtp.starttls.enable", "true"); // gmail은 무조건 true 고정
					properties.put("mail.smtp.host", "smtp.gmail.com"); // smtp 서버 주소
					properties.put("mail.smtp.auth", "true"); // gmail은 무조건 true 고정
					properties.put("mail.smtp.port", "587"); // gmail 포트
					Authenticator auth = new GoogleAuthentication();
					Session s = Session.getDefaultInstance(properties, auth);
					//Session s = Session.getdefultInstance(properties, auth);
					Message message = new MimeMessage(s);
					Address sender_address = new InternetAddress("lhj1836@hanmail.net");
					Address receiver_address = new InternetAddress(receiver);
					message.setHeader("content-type", "text/html;charset=UTF-8");
					message.setFrom(sender_address);
					message.addRecipient(Message.RecipientType.TO, receiver_address);
					message.setContent(member.getUser_id()+"님의 임시비밀번호는    "+uuid+"     입니다", "text/html;charset=UTF-8");
					message.setSentDate(new java.util.Date());
					Transport.send(message);
					out.println("<script>");
					out.println("alert('임시비밀번호가 발급되었습니다.')");
					out.println("location.href='loginForm.log'");
					out.println("</script>");

					
				} catch (Exception e) {
					out.println("<script>");
					out.println("alert('SMTP 서버가 잘못 설정되었거나, 서비스에 문제가 있습니다.')");
					out.println("history.back()");
					out.println("</script>"); 
					e.printStackTrace();
			
			// 숫자만 == 사용 가능
				}


			}else {
				response.setContentType("text/html;charset=UTF-8");
				PrintWriter out = response.getWriter();
				out.println("<script>");
				out.println("alert('임시비밀번호 발급 실패.')");
				out.println("history.back()");
				out.println("</script>");
			
			
		}


	}	else {
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out = response.getWriter();
		out.println("<script>");
		out.println("alert('이메일이 일치하지 않습니다.')");
		out.println("history.back()");
		out.println("</script>");
	}
		
	
		return forward;
	}
}
