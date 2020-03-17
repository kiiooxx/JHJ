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
		ActionForward forward = null;
		int board_num=Integer.parseInt(request.getParameter("board_num"));
		Notice_BoardDetailService notice_boardDetailService = new Notice_BoardDetailService();
		Notice_BoardBean article = notice_boardDetailService.getselectArticle(board_num);
		request.setAttribute("notice", article);
		request.setAttribute("pagefile", "/board/notice_board_modify.jsp");
		forward = new ActionForward("template.jsp", false);
		return forward;
	}

}
                               