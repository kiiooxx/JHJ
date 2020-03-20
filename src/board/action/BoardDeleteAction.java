package board.action;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import action.Action;
import board.svc.BoardDeleteService;
import vo.ActionForward;

public class BoardDeleteAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// TODO Auto-generated method stub
		int board_num = Integer.parseInt(request.getParameter("board_num"));
		BoardDeleteService boardDeleteService = new BoardDeleteService();
		boolean isDeleteSuccess = boardDeleteService.deleteBoard(board_num);
		
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
