package member.action;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import action.Action;
import member.svc.MyInfoService;
import vo.ActionForward;
import vo.Member;

public class MyInfoAction implements Action {

	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// TODO Auto-generated method stub
		ActionForward forward = null;
		Member member = null;
		String id = "";
		HttpSession session = request.getSession();
		id = (String) session.getAttribute("id");
		MyInfoService MyInfoService = new MyInfoService();
	
		member = MyInfoService.selectmemberinfo(id);

		request.setAttribute("member", member);
		request.setAttribute("pagefile", "/member/myinfo.jsp");
		forward = new ActionForward("/template.jsp", false);
		return forward;

	}
}

//		ActionForward forward = null;
//		Member member = null;
//		String id = "";
//		
//		HttpSession session = request.getSession();
//		id = (String) session.getAttribute("id");
//		MyInfoService MyInfoService = new MyInfoService();
//	
//		member = MyInfoService.selectmemberinfo(id);
//
//		request.setAttribute("member", member);
//		request.setAttribute("pagefile", "/member/myinfo.jsp");
//		forward = new ActionForward("/template.jsp", false);
//
//		
//		
//		if(session.getAttribute("id")==null) {
//			response.setContentType("text/html;charset=UTF-8");
//			PrintWriter out = response.getWriter();
//			out.println("<script>");
//			out.println("alert('로그인을 하세요!!')");
//			out.println("location.href='loginForm.log'");
//			out.println("</script>");
//		}else {
//	
//			id = (String) session.getAttribute("id");
//			
//			member = MyInfoService.selectmemberinfo(id);
//	
//			request.setAttribute("member", member);
//			request.setAttribute("pagefile", "/member/myinfo.jsp");
//			forward = new ActionForward("/template.jsp", false);
//		
//			return forward;
//		}		
//				
//	}
//}
