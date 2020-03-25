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
		ActionForward forward = null;
		String board_num[] = request.getParameter("board_num").split(",");
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
		}else {
			response.setContentType("text/html;charset=UTF-8");
			PrintWriter out = response.getWriter();
			out.println("<script>");
			out.println("alert('삭제되었습니다!');");
			out.println("location.href = document.referrer;");	
			out.println("</script>");
			out.close();
		}
		
		//forward = new ActionForward("myboard.mem", true);

		return forward;
	}

}
