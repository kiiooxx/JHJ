package admin.action;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import action.Action;
import admin.svc.PointManagementService;
import vo.ActionForward;
import vo.PointMan;

public class PointManagementAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		ActionForward forward = null;
		
		int pointDate = Integer.parseInt(request.getParameter("pointDate"));
		String markOption = request.getParameter("markOption");
		float rate = Float.parseFloat((request.getParameter("rate")));
		String usePointOpt = request.getParameter("usePointOption");
		int newMem = Integer.parseInt(request.getParameter("newMem"));
		int priceLimit = Integer.parseInt(request.getParameter("priceLimit"));
		int pointLimit = Integer.parseInt(request.getParameter("pointLimit"));
		
		int reviewP = 0;
		if(request.getParameter("reviewP")!=null) {
			reviewP = Integer.parseInt(request.getParameter("reviewP"));
			
		}
		System.out.println("reviewP"+reviewP);
		
		PointMan pointMan = new PointMan();
		pointMan.setP_date(pointDate);
		pointMan.setP_mark(markOption);
		pointMan.setP_rate(rate);
		pointMan.setP_stand(usePointOpt);
		pointMan.setP_newmem(newMem);
		pointMan.setP_pricelimit(priceLimit);
		pointMan.setP_pointlimit(pointLimit);
		pointMan.setP_review(reviewP);
		
		PointManagementService pointManagementService = new PointManagementService();
		boolean isSettingSuccess = pointManagementService.setPointOption(pointDate, markOption, rate, 
				usePointOpt, newMem, priceLimit, pointLimit, reviewP);
		
		if(!isSettingSuccess) {
			response.setContentType("text/html;charset=UTF-8");
			PrintWriter out = response.getWriter();
			out.println("<script>");
			out.println("alert('저장실패')");
			out.println("history.back()");
			out.println("</script>");
		}else {
			//request.setAttribute("pointMan", pointMan);
			
			response.setContentType("text/html;charset=UTF-8");
			PrintWriter out = response.getWriter();
			out.println("<script>");
			out.println("alert('설정이 저장되었습니다.')");
			out.println("location.href='pointManagementForm.ad?seq=1';");
			out.println("</script>");
			
			//request.setAttribute("pagefile", "/admin/point_management.jsp");
			//forward = new ActionForward("/admin_template.jsp", false);
		}
		
		
		return forward;
	}

}
