package member.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import action.Action;
import member.svc.IdCheckSVC;
import vo.ActionForward;
import vo.Member;

public class IdCheckAction implements Action {

	//아이디 중복확인 
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		ActionForward forward = null;
		
		String chk_id = request.getParameter("id");
		IdCheckSVC idCheckSvc = new IdCheckSVC();
		Member member = new Member();
		member = idCheckSvc.memberId(chk_id);
		
		if(member == null) {//멤버 객체가 존재하지 않을 때 --id 사용가능
			request.setAttribute("useableId", true);
		}else {//멤버 객체가 존재할 때 --id 사용불가
			request.setAttribute("useableId", false);
		}
		request.setAttribute("id", chk_id);
	
		forward = new ActionForward("/member/idCheck.jsp", false);
		
		return forward;
	}
}
