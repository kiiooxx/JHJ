package board.action;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import action.Action;
import board.svc.Notice_BoardDeleteProService;
import vo.ActionForward;

public class Notice_BoardDeleteProAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// TODO Auto-generated method stub
		ActionForward forward = null;
		int notice_num = Integer.parseInt(request.getParameter("board_num"));
		String nowPage = request.getParameter("page");
		Notice_BoardDeleteProService boardDeleteProService = new Notice_BoardDeleteProService();
		
			boolean isDeleteSuccess = boardDeleteProService.removeArticle(notice_num);
			
			if(!isDeleteSuccess) {
				response.setContentType("text/html;charset=UTF-8");
				PrintWriter out = response.getWriter();
				out.println("<script>");
				out.println("alert('삭제 실패')");
				out.println("history.back()");
				out.println("</script>");
				out.close();
			}else {
				forward = new ActionForward("notice_boardList.bo?page=" + nowPage, true);
			}
		
		return forward;
	}

}
