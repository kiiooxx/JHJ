package board.action;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import action.Action;
import board.svc.ReivewDeleteService;
import vo.ActionForward;

public class ReviewDeleteAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// TODO Auto-generated method stub
		int rev_num = Integer.parseInt(request.getParameter("rev_num"));
		ReivewDeleteService reviewDeleteService = new ReivewDeleteService();
		boolean isDeleteSuccess = reviewDeleteService.deleteReview(rev_num);
		
		if(!isDeleteSuccess) {
			response.setContentType("text/html;charset=UTF-8");
			PrintWriter out = response.getWriter();
			out.println("<script>");
			out.println("alert('Fail');");
			out.println("history.back();");
			out.println("</script>");
			out.close();
		}
		
		ActionForward forward = new ActionForward("myboard.mem", true);
		return forward;
	}

}
