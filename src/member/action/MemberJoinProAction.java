package member.action;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import action.Action;
import admin.action.SendMailAction;
import member.svc.MemberJoinService;
import point.action.PointAction;
import point.svc.PointService;
import vo.ActionForward;
import vo.Member;

public class MemberJoinProAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ActionForward forward = null;
		
		Member member = new Member();
		member.setUser_id(request.getParameter("id"));
		member.setUser_pw(request.getParameter("pass"));
		member.setUser_name(request.getParameter("name"));
		member.setTel(request.getParameter("tel"));
		member.setPostcode(request.getParameter("postcode"));
		member.setAddr1(request.getParameter("addr1"));
		member.setAddr2(request.getParameter("addr2"));
		member.setEmail(request.getParameter("email"));
		member.setSex(request.getParameter("sex"));		
		
		String birthY = request.getParameter("birthY");
		String birthM = request.getParameter("birthM");
		String birthD = request.getParameter("birthD");
		member.setBirth(birthY+birthM+birthD);
		
		String user_id = request.getParameter("id");
		
		
		//service 를 이용해서 DAO로 보낸다.
		MemberJoinService memberJoinsvc = new MemberJoinService();
		boolean isJoinSuccess = memberJoinsvc.joinMember(member);
		
		
		if(!isJoinSuccess) {
			response.setContentType("text/html;charset=UTF-8");
			PrintWriter out = response.getWriter();
			out.println("<script>");
			out.println("alert('회원가입 실패');");
			out.println("history.back();");
			out.println("</script>");
			
		}else {
			SendMailAction sendMailAction = new SendMailAction();
			sendMailAction.mailling(request, response);
			PointService pointService = new PointService();
			pointService.pointForNewmem(user_id);
			//PointAction pointAction = new PointAction();
			//pointAction.newMemPoint(request, response);
			request.setAttribute("pagefile", "/main.jsp");
			forward = new ActionForward("/main.pro", false);
			
		}
		
		return forward;
	}

}
