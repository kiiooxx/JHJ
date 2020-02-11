//package mail;
//
//import java.io.IOException;
//import java.io.PrintWriter;
//import java.util.Properties;
//import javax.mail.Address;
//import javax.mail.Authenticator;
//import javax.mail.Message;
//import javax.mail.Session;
//import javax.mail.Transport;
//import javax.mail.internet.InternetAddress;
//import javax.mail.internet.MimeMessage;
//import javax.servlet.ServletException;
//import javax.servlet.annotation.WebServlet;
//import javax.servlet.http.HttpServlet;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//
//import action.Action;
//import log.action.pwfindAction;
//import vo.ActionForward;
//
///**
// * Servlet implementation class MailSendServlet
// */
//@WebServlet("/mailSend")
//public class MailSendServlet extends HttpServlet {
//	private static final long serialVersionUID = 1L;
//	
//       
//    /**
//     * @see HttpServlet#HttpServlet()
//     */
//    public MailSendServlet() {
//        super();
//        // TODO Auto-generated constructor stub
//    }
//
//	/**
//	 * @param content 
//	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
//	 */
//	protected void doPost(HttpServletRequest request, HttpServletResponse response, String content) throws ServletException, IOException {
//		request.setCharacterEncoding("UTF-8");
//		String RequestURI = request.getRequestURI();
//    	String contextPath = request.getContextPath();
//    	String command = RequestURI.substring(contextPath.length());
//    	ActionForward forward = null;
//    	Action action = null;
//		
//		String sender = request.getParameter("sender");
//		String receiver = request.getPar ameter("receiver");
//	
//	}if(command.equals("/pwfind.log")) {
//		request.setAttribute("pagefile", "/member/pwfind.jsp");
//		forward = new ActionForward("/template.jsp", false);
//	}else if(command.equals("/pwfindAction.log")) {
//		action = new pwfindAction();
//	
//		try {
//			forward = action.execute(request, response);
//		}catch(Exception e) {
//			e.printStackTrace();
//		}	
//		
//		
//		System.out.println(sender);
//		System.out.println(receiver);
//	
//
//		
//
//	}
//}
