 package member.action;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import action.Action;
import member.svc.MemberModProSvc;
import vo.ActionForward;
import vo.Member;

public class MyInfoModAction implements Action {

	@SuppressWarnings("unused")//이게머지?
	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// TODO Auto-generated method stub
		ActionForward forward = null;
		boolean isModifySuccess = false;
		Member member = new Member();
		MemberModProSvc memberModProSvc = new MemberModProSvc();
		
		
		String birth = request.getParameter("birthY") +"-"+ request.getParameter("birthM") +"-"+ request.getParameter("birthD");
		member.setUser_id(request.getParameter("id"));
		member.setUser_pw(request.getParameter("passChk"));
		member.setUser_pw(request.getParameter("pass"));
		member.setUser_name(request.getParameter("name"));
		member.setTel(request.getParameter("tel"));
		member.setSex(request.getParameter("sex"));
		member.setEmail(request.getParameter("email"));
		member.setPostcode(request.getParameter("postcode"));
		member.setAddr1(request.getParameter("addr1"));
		member.setAddr2(request.getParameter("addr2"));
		member.setSex(request.getParameter("sex"));
		member.setBirth(birth);
//		System.out.println("생일 : "+birth);
			
		isModifySuccess = memberModProSvc.modifyMember(member);
		
		if(!isModifySuccess) {
			response.setContentType("text/html;charset=UTF-8");
			PrintWriter out = response.getWriter();
			out.println("<script>");
			out.println("alert('수정 실패')");
			out.println("history.back()");
			out.println("</script>");
		}else {
			forward = new ActionForward("myinfo.mem",true);
		}
		return forward;
	}
}