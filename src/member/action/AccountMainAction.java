package member.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import action.Action;
import point.svc.PointService;
import vo.ActionForward;
import vo.Point;

public class AccountMainAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// TODO Auto-generated method stub
		ActionForward forward = null;
		HttpSession session = request.getSession();
		String user_id = (String) session.getAttribute("id");
		
		PointService pointService = new PointService();
		Point memberPoint= pointService.memberPoint(user_id);
		request.setAttribute("memberPoint", memberPoint);
		
		request.setAttribute("pagefile", "/member/accountForm.jsp");
		forward = new ActionForward("/template.jsp", false);
		return forward;
	}

}
