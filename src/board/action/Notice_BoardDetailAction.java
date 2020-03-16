package board.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import action.Action;
import board.svc.Notice_BoardDetailService;
import vo.ActionForward;
import vo.Notice_BoardBean;

public class Notice_BoardDetailAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// TODO Auto-generated method stub
			
		int notice_num = Integer.parseInt(request.getParameter("board_num"));
		System.out.println("board_num");
		String page = request.getParameter("page");
		Notice_BoardDetailService boardDetailService = new Notice_BoardDetailService();
		Notice_BoardBean selectArticle = boardDetailService.getselectArticle(notice_num);
		System.out.println("BoardDetailAction 페이지 " + page);
		request.setAttribute("page", page);
		request.setAttribute("article", selectArticle);
		request.setAttribute("pagefile","/board/notice_board_view.jsp");
		ActionForward forward = new ActionForward("/template.jsp",false);
		return forward;
	}

}

	