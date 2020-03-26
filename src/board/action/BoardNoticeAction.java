package board.action;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import action.Action;
import board.svc.BoardNoticeService;
import vo.ActionForward;

public class BoardNoticeAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// TODO Auto-generated method stub
		String board_num[] = request.getParameter("board_num").split(",");
		String board_notice = request.getParameter("board_notice");	//공지 등록 여부
		
		BoardNoticeService boardNoticeService = new BoardNoticeService();
		boolean isDeleteSuccess = boardNoticeService.updateNoticeBoard(board_num, board_notice);
		
		if(!isDeleteSuccess) {
			response.setContentType("text/html;charset=UTF-8");
			PrintWriter out = response.getWriter();
			out.println("<script>");
			out.println("alert('Fail');");
			out.println("history.back();");
			out.println("</script>");
			out.close();
		}
		
		ActionForward forward = new ActionForward("boardManagementForm.ad", true);
		return forward;
	}

}
