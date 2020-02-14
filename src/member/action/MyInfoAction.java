package member.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import action.Action;
import log.svc.IdFindService;
import log.svc.LoginService;
import member.svc.MyInfoService;
import vo.ActionForward;
import vo.Member;

public class MyInfoAction implements Action {

	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// TODO Auto-generated method stub
		ActionForward forward = null;
		Member member = null;
		HttpSession session = request.getSession();
		String id = (String) session.getAttribute("id");
		MyInfoService MyInfoService = new MyInfoService();
		System.out.println("myinfoaction" + id);
		member = MyInfoService.selectmemberinfo(id);

		request.setAttribute("member", member);
		request.setAttribute("pagefile", "/member/myinfo.jsp");
		forward = new ActionForward("/template.jsp", false);
		return forward;

	}
}
