package board.action;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import action.Action;
import board.svc.Notice_BoardModifyProService;
import board.svc.Notice_BoardModifyformProService;

import vo.ActionForward;

import vo.Notice_BoardBean;

public class Notice_BoardModifyAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// TODO Auto-generated method stub
		ActionForward forward = null;
		boolean isModifySuccess = false;
		int board_num = Integer.parseInt(request.getParameter("board_num"));
		String nowPage = request.getParameter("page");
		Notice_BoardBean article = new Notice_BoardBean();
		Notice_BoardModifyProService notice_boardModifyProService = new Notice_BoardModifyProService();
//		boolean isRightUser = notice_boardModifyProService.isArticleWriter(board_num, request.getParameter("BOARD_PASS"));
//		
//		if(!isRightUser) {
//			response.setContentType("text/html;charset=UTF-8");
//			PrintWriter out = response.getWriter();
//			out.println("<script>");
//			out.println("alert('수정할 권한이 없습니다.')");
//			out.println("history.back()");
//			out.println("</script>");
//		}else {
		
			article.setNotice_num(board_num);
			article.setNotice_title(request.getParameter("notice_title"));
			article.setNotice_content(request.getParameter("notice_content"));
			isModifySuccess = notice_boardModifyProService.modifyArticle(article);
			
			if(!isModifySuccess) {
				response.setContentType("text/html;charset=UTF-8");
				PrintWriter out = response.getWriter();
				out.println("<script>");
				out.println("alert('수정 실패')");
				out.println("history.back()");
				out.println("</script>");
			}else {
				request.setAttribute("article", article);
				request.setAttribute("pagefile", "/board/notice_board_list.jsp");
				forward = new ActionForward("template.jsp", false);
				return forward;
			}
		
		
		return forward;
	}

}

		
		
		
		
		
		
		
//		
//		
//		
//		
//		
//		ActionForward forward = null;
//		
//		int board_num = Integer.parseInt(request.getParameter("board_num"));
//		String nowPage = request.getParameter("page");
//		Notice_BoardModifyformProService notice_boardDetailfromService = new Notice_BoardModifyformProService();
//		Notice_BoardBean article = Notice_BoardModifyformProService.getupdateArticle(board_num);
//		request.setAttribute("article", article);
//		request.setAttribute("page", nowPage);
//		request.setAttribute("pagefile","/board/notice_board_modify.jsp");
//		ActionForward forward = new ActionForward("/template.jsp",false);
//		return forward;
//	}
//
//}
//
