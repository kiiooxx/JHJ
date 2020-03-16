package board.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import action.Action;
import board.svc.Notice_BoardDetailService;
import vo.ActionForward;
import vo.Notice_BoardBean;

public class Notice_BoardModifyFormAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// TODO Auto-generated method stub
		ActionForward forward = new ActionForward("board_num",true);
		int board_num = Integer.parseInt(request.getParameter("board_num"));
		String nowPage = request.getParameter("page");
		Notice_BoardDetailService notice_boardDetailService = new Notice_BoardDetailService();
		Notice_BoardBean article = notice_boardDetailService.getArticle(board_num);
		request.setAttribute("article", article);
		request.setAttribute("page", nowPage);
		forward.setPath("/board/qna_board_modify.jsp");
		return forward;
	}

}
