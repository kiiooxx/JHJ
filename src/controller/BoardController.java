package controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import action.Action;
import board.action.Notice_BoardDeleteProAction;
import board.action.Notice_BoardDetailAction;
import board.action.Notice_BoardListAction;
import board.action.Notice_BoardModifyAction;
import board.action.Notice_BoardModifyFormAction;
import board.action.Notice_BoardWriteProAction;
import board.action.QnADeleteAction;
import board.action.QnADetailAction;
import board.action.QnAListAction;
import board.action.QnAModifyAction;
import board.action.QnAModifyFormAction;
import board.action.QnARegistAction;
import board.action.QnAWriteFormAction;
import board.action.ReviewDeleteAction;
import board.action.ReviewDetailAction;
import board.action.ReviewListAction;
import board.action.ReviewModifyAction;
import board.action.ReviewModifyFormAction;
import board.action.ReviewRegistAction;
import board.action.ReviewWriteFormAction;
import vo.ActionForward;

/**
 * Servlet implementation class BoardController
 */
@WebServlet("*.bo")
public class BoardController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public BoardController() {
        super();
        // TODO Auto-generated constructor stub
    }

    protected void doProcess(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		request.setCharacterEncoding("UTF-8");
		//1. 요청파악
		String requestURI = request.getRequestURI();
		//요청 URL : http://localhost:8088/boardProject/boardWriteForm.bo
		//requestURI : /boardProject/boardWriteForm.bo 반환
		
		String contextPath = request.getContextPath();
		//	/boardProject 반환
		
		String command = requestURI.substring(contextPath.length());
		Action action = null;
		ActionForward forward = null;
		
		//====================리뷰====================
		//1. 리뷰 글 쓰기 폼
		if(command.equals("/reviewWriteForm.bo")) {
			action = new ReviewWriteFormAction();
			try {
				forward = action.execute(request, response);
			}catch(Exception e) {
				e.printStackTrace();
			}
    	}
		//2. 리뷰 글 등록 액션
		else if(command.equals("/reviewRegist.bo")) {
			action = new ReviewRegistAction();
			try {
				forward = action.execute(request, response);
			}catch(Exception e) {
				e.printStackTrace();
			}
    	}
		//3. 리뷰 리스트 목록 가져오는 액션
		else if(command.equals("/reviewList.bo")) {
			action = new ReviewListAction();
			try {
				forward = action.execute(request, response);
			}catch(Exception e) {
				e.printStackTrace();
			}
    	}
		//4. 리뷰 글 상세 보기 액션
		else if(command.equals("/reviewDetail.bo")) {
			action = new ReviewDetailAction();
			try {
				forward = action.execute(request, response);
			}catch(Exception e) {
				e.printStackTrace();
			}
    	}
		//5. 리뷰 글 삭제하는 액션
		else if(command.equals("/reviewDelete.bo")) {
			action = new ReviewDeleteAction();
			try {
				forward = action.execute(request, response);
			}catch(Exception e) {
				e.printStackTrace();
			}
    	}
		//6. 리뷰 글 수정 폼
		else if(command.equals("/reviewModifyForm.bo")) {
			action = new ReviewModifyFormAction();
			try {
				forward = action.execute(request, response);
			}catch(Exception e) {
				e.printStackTrace();
			}
    	}
		//7. 리뷰 글 수정하는 액션
		else if(command.equals("/reviewModify.bo")) {
			action = new ReviewModifyAction();
			try {
				forward = action.execute(request, response);
			}catch(Exception e) {
				e.printStackTrace();
			}
    	}
		
		//====================QnA====================
		//1. QnA 게시판 등록 폼
		else if(command.equals("/qnaWriteForm.bo")) {
			action = new QnAWriteFormAction();
			try {
				forward = action.execute(request, response);
			}catch(Exception e) {
				e.printStackTrace();
			}
    	}
		//2. QnA 게시판 등록 액션
		else if(command.equals("/qnaRegist.bo")) {
			action = new QnARegistAction();
			try {
				forward = action.execute(request, response);
			}catch(Exception e) {
				e.printStackTrace();
			}
    	}
		//3. QnA 리스트 목록 가져오는 액션
		else if(command.equals("/qnaList.bo")) {
			action = new QnAListAction();
			try {
				forward = action.execute(request, response);
			}catch(Exception e) {
				e.printStackTrace();
			}
    	}
		//4. QnA 게시글 상세 보기 액션
		else if(command.equals("/qnaDetail.bo")) {
			action = new QnADetailAction();
			try {
				forward = action.execute(request, response);
			}catch(Exception e) {
				e.printStackTrace();
			}
    	}
		//5. QnA 글 삭제하는 액션
		else if(command.equals("/qnaDelete.bo")) {
			action = new QnADeleteAction();
			try {
				forward = action.execute(request, response);
			}catch(Exception e) {
				e.printStackTrace();
			}
    	}
		//6. QnA 글 수정 폼
		else if(command.equals("/qnaModifyForm.bo")) {
			action = new QnAModifyFormAction();
			try {
				forward = action.execute(request, response);
			}catch(Exception e) {
				e.printStackTrace();
			}
    	}
		//7. QnA 글 수정하는 액션
		else if(command.equals("/qnaModify.bo")) {
			action = new QnAModifyAction();
			try {
				forward = action.execute(request, response);
			}catch(Exception e) {
				e.printStackTrace();
			}
    	}
		
		//====================공지사항====================
		//1. 공지사항 등록 폼
		if(command.equals("/notice_boardWriteForm.bo")) {
    		request.setAttribute("pagefile", "/board/notice_board_write.jsp");
    		forward = new ActionForward("/template.jsp",false);
    		
    	}
		//2. 공지사항 등록 액션
		else if(command.equals("/notice_boardWritePro.bo")) {
    		action = new Notice_BoardWriteProAction();
    		try {
    			forward = action.execute(request, response);
    		}catch(Exception e) {
    			e.printStackTrace();
    		}
    	}
		//3. 공지사항 리스트
		else if(command.equals("/notice_boardList.bo")) {
    		action = new Notice_BoardListAction();
    		try {
    			forward = action.execute(request, response);
    		}catch(Exception e) {
    			e.printStackTrace();
    		}
    	}
		//4. 공지사항 상세
		else if(command.equals("/notice_boardDetail.bo")) {
    		action = new Notice_BoardDetailAction();
    		try {
    			forward = action.execute(request, response);
    		}catch(Exception e) {
    			e.printStackTrace();
    		}
    	}
		//5. 공지사항 수정 폼
		else if(command.equals("/notice_boardModifyForm.bo")) {
    		action = new Notice_BoardModifyFormAction();
    		try {
    			forward = action.execute(request, response);
    		}catch(Exception e) {
    			e.printStackTrace();
    		}
    	
    	}
		//공지사항 수정 액션
		else if(command.equals("/notice_boardModify.bo")) {
    		action = new Notice_BoardModifyAction();
    		try {
    			forward = action.execute(request, response);
    		}catch(Exception e) {
    			e.printStackTrace();
    		}
 
    	}
		//공지사항 삭제 액션
		else if(command.equals("/notice_boardDeletePro.bo")) {
    		action = new Notice_BoardDeleteProAction();
    		try {
    			forward = action.execute(request, response);
    		}catch(Exception e) {
    			e.printStackTrace();
    		}
    	}
		
		//3.포워딩
		if(forward != null) {
			if(forward.isRedirect()) {
				response.sendRedirect(forward.getPath());
			}else {
				RequestDispatcher dispatcher = request.getRequestDispatcher(forward.getPath());
				dispatcher.forward(request, response);
			}
		}
	}
    
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doProcess(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doProcess(request, response);
	}

}
