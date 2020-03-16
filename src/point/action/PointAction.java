package point.action;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import action.Action;
import point.svc.PointService;
import vo.ActionForward;

public class PointAction{

	public void newMemPoint(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		
		String user_id = request.getParameter("id");
		
		PointService pointService = new PointService();
		boolean isPoint = pointService.pointForNewmem(user_id);
		
		if(!isPoint) {
			response.setContentType("text/html;charset=UTF-8");
			PrintWriter out = response.getWriter();
			out.println("<script>");
			out.println("alert('회원가입이 완료되었습니다.')");
			out.println("history.back()");
			out.println("</script>");
		}else {
			response.setContentType("text/html;charset=UTF-8");
			PrintWriter out = response.getWriter();
			out.println("<script>");
			out.println("alert('회원가입이 완료되었습니다.<br>회원가입기념 적립금${}이 지급되었습니다.')");
			out.println("history.back()");
			out.println("</script>");
		}
		
		
	}

	
}
