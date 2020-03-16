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
import board.action.Notice_BoardModifyFormAction;
import board.action.Notice_BoardModifyProAction;
import board.action.Notice_BoardWriteProAction;
import board.action.ReviewDetailAction;
import board.action.ReviewListAction;
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
		
		
		if(command.equals("/qnaWriteForm.bo")) {
			request.setAttribute("pagefile", "/board/qna_write_form.jsp");
	    	forward = new ActionForward("/template.jsp", false);
    	}
		
		if(command.equals("/notice_boardWriteForm.bo")) {
    		request.setAttribute("pagefile", "/board/notice_board_write.jsp");
    		forward = new ActionForward("/template.jsp",false);
    		
    	}else if(command.equals("/notice_boardWritePro.bo")) {
    		action = new Notice_BoardWriteProAction();
    		try {
    			forward = action.execute(request, response);
    		}catch(Exception e) {
    			e.printStackTrace();
    		}
    	}else if(command.equals("/notice_boardList.bo")) {
    		action = new Notice_BoardListAction();
    		try {
    			forward = action.execute(request, response);
    		}catch(Exception e) {
    			e.printStackTrace();
    		}
    	}else if(command.equals("/notice_boardDetail.bo")) {
    		action = new Notice_BoardDetailAction();
    		try {
    			forward = action.execute(request, response);
    		}catch(Exception e) {
    			e.printStackTrace();
    		}
    	}else if(command.equals("/notice_boardModifyForm.bo")) {
    		action = new Notice_BoardModifyFormAction();
    		try {
    			forward = action.execute(request, response);
    		}catch(Exception e) {
    			e.printStackTrace();
    		}
    	}else if(command.equals("/notice_boardModifyPro.bo")) {
    		action = new Notice_BoardModifyProAction();
    		try {
    			forward = action.execute(request, response);
    		}catch(Exception e) {
    			e.printStackTrace();
    		}
    	}else if(command.equals("/notice_boardDeleteForm.bo")) {
    		String nowPage = request.getParameter("page");
    		request.setAttribute("page", nowPage);
    		int board_num = Integer.parseInt(request.getParameter("board_num"));
    		request.setAttribute("board_num", board_num);
    		forward = new ActionForward("/board/notice_board_delete.jsp",true);
 
    	}else if(command.equals("/boardDeletePro.bo")) {
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
