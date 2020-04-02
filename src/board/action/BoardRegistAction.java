package board.action;

import java.io.PrintWriter;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;

import action.Action;
import admin.action.SendMailAction;
import board.svc.BoardRegistService;
import board.svc.BoardViewService;
import point.svc.PointService;
import vo.ActionForward;
import vo.BoardBean;


public class BoardRegistAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// TODO Auto-generated method stub
		ActionForward forward = null;
		BoardBean boardBean = null;

		String realFolder = "";
		String saveFolder = "/upload";
		int fileSize = 5*1024*1024;
		ServletContext context = request.getServletContext();
		realFolder = context.getRealPath(saveFolder);
		MultipartRequest multi = new MultipartRequest(request, realFolder, fileSize, "UTF-8", new DefaultFileRenamePolicy());
		boardBean = new BoardBean();
		
		String board_type = "";
		if(!(multi.getParameter("board_type") == null || multi.getParameter("board_type").equals(""))) {
			board_type = multi.getParameter("board_type");	
		}
		boardBean.setBoard_type(board_type);	//게시글 종류
		
		boardBean.setBoard_title(multi.getParameter("board_title"));	//게시글 제목
		boardBean.setBoard_writer(multi.getParameter("board_writer"));	//작성자
		boardBean.setBoard_content(multi.getParameter("board_content"));	//게시글 내용
		
		String board_photo = "";
		if(!(multi.getFilesystemName("board_photo") == null || multi.getFilesystemName("board_photo").equals(""))) {
			board_photo = multi.getFilesystemName("board_photo");	//첨부 파일
		}
		boardBean.setBoard_photo(board_photo);
		
		//상품 번호
		int pro_num = 0;
		if(!(multi.getParameter("pro_num") == null || multi.getParameter("pro_num").equals(""))) {
			pro_num = Integer.parseInt(multi.getParameter("pro_num"));
		}
		boardBean.setPro_num(pro_num);
		
		//주문 번호
		String sel_num = "";
		if(!(multi.getParameter("sel_num") == null || multi.getParameter("sel_num").equals(""))) {
			sel_num = multi.getParameter("sel_num");
		}
		boardBean.setSel_num(sel_num);
		
		
		//QnA게시판 이메일
		String qna_email = "";
		if(!(multi.getParameter("qna_email") == null || multi.getParameter("qna_email").equals(""))) {
			qna_email = multi.getParameter("qna_email");
		}
		boardBean.setQna_email(qna_email);
		
		//QnA게시판 문의구분
		String qna_type = "";
		if(!(multi.getParameter("qna_type") == null || multi.getParameter("qna_type").equals(""))) {
			qna_type = multi.getParameter("qna_type");
		}
		boardBean.setQna_type(qna_type);
		
		//QnA게시판 공개여부
		String qna_open = "";
		if(!(multi.getParameter("qna_open") == null || multi.getParameter("qna_open").equals(""))) {
			qna_open = multi.getParameter("qna_open");
		}
		boardBean.setQna_open(qna_open);
		
		//리뷰게시판 평점
		int review_score = 0;
		if(!(multi.getParameter("review_score") == null || multi.getParameter("review_score").equals(""))) {
			review_score = Integer.parseInt(multi.getParameter("review_score"));
		}
		boardBean.setReview_score(review_score);
		
		//관련 게시글 번호
		int board_ref = 0;
		if(!(multi.getParameter("board_ref") == null || multi.getParameter("board_ref").equals(""))) {
			board_ref = Integer.parseInt(multi.getParameter("board_ref"));
		}
		boardBean.setBoard_ref(board_ref);
		
		//공지사항 등록 여부
		String board_notice = "N";
		if(!(multi.getParameter("board_notice") == null || multi.getParameter("board_notice").equals(""))) {
			board_notice = multi.getParameter("board_notice");
		}
		boardBean.setBoard_notice(board_notice);
		
		BoardRegistService boardRegistService = new BoardRegistService();
		boolean isRegistSuccess = boardRegistService.registBoard(boardBean);
		
		if(isRegistSuccess) {
//			if(board_type.equals("answer")) {
//				
//				BoardViewService boardViewService = new BoardViewService();
//				BoardBean boardBean_ref = boardViewService.getBoard(board_ref, "board_num = ?");
//				
//				//관련글이 문의게시판이고 이메일이 빈값이 아닌경우
//				if(boardBean_ref.getBoard_type().equals("qna")) {
//					SendMailAction sendMailAction = new SendMailAction();
//					request.setAttribute("boardBean_ref", boardBean_ref);
//					request.setAttribute("boardBean", boardBean);
//					sendMailAction.mailling(request, response);
//				}
//			}
			
			//리뷰 작성시 
			if(board_type.equals("review")) {
				PointService pointService = new PointService();
				boolean isReviewPoint = pointService.reviewPoint(boardBean.getBoard_writer());
				
				if(isReviewPoint) {
					System.out.println("적립금 지급 완료!");
				}
			}
			
			forward = new ActionForward("boardListAction.bo?board_type="+boardBean.getBoard_type(), true);
		}else {
			response.setContentType("text/html;charset=UTF-8");
			PrintWriter out = response.getWriter();
			out.println("<script>");
			out.println("alert('등록실패');");
			out.println("history.back()");
			out.println("</script>");
		}
		
		return forward;
	}

}
