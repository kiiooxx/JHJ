package log.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import action.Action;
import log.svc.IdfindService;
import log.svc.LoginSvc;
import log.svc.accountSvc;
import vo.ActionForward;
import vo.Member;

public class myinfoAction implements Action {

	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// TODO Auto-generated method stub
		ActionForward forward = null;
		Member member = null;
		HttpSession session = request.getSession();
		String id = (String) session.getAttribute("id");
		accountSvc accountSvc = new accountSvc();
		System.out.println("myinfoaction" + id);
		member = accountSvc.selectmemberinfo(id);

		request.setAttribute("member", member);
		request.setAttribute("pagefile", "/member/myinfo.jsp");
		forward = new ActionForward("/template.jsp", false);
		return forward;

	}
}
