package board.svc;

import static db.JdbcUtil.close;
import static db.JdbcUtil.getConnection;

import java.sql.Connection;
import java.util.ArrayList;

import dao.BoardDAO;
import vo.BoardBean;

public class BoardListService {

	public int getBoardListCount(String board_type) {
		// TODO Auto-generated method stub
		int listCount = 0;
		Connection con = getConnection();
		BoardDAO boardDAO = BoardDAO.getInstance();
		boardDAO.setConnection(con);
		listCount = boardDAO.selectBoardListCount(board_type);
		close(con);
		return listCount;
	}

	public ArrayList<BoardBean> getBoardList(String board_type, int page, int limit) {
		// TODO Auto-generated method stub
		ArrayList<BoardBean> boardList = null;
		Connection con = getConnection();
		BoardDAO boardDAO = BoardDAO.getInstance();
		boardDAO.setConnection(con);
		boardList = boardDAO.selectBoardList(board_type, page, limit);
		close(con);
		return boardList;
	}

	//상품번호에 해당하는 게시글 불러오기 
	public int getBoardListCount(int pro_num, String board_type) {
		// TODO Auto-generated method stub
		int listCount = 0;
		Connection con = getConnection();
		BoardDAO boardDAO = BoardDAO.getInstance();
		boardDAO.setConnection(con);
		listCount = boardDAO.selectBoardListCount(pro_num, board_type);
		close(con);
		return listCount;
	}

	public ArrayList<BoardBean> getBoardList(String board_type, int pro_num, int page, int limit) {
		// TODO Auto-generated method stub
		ArrayList<BoardBean> boardList = null;
		Connection con = getConnection();
		BoardDAO boardDAO = BoardDAO.getInstance();
		boardDAO.setConnection(con);
		boardList = boardDAO.selectBoardList(pro_num, board_type, page, limit);
		close(con);
		return boardList;
	}

	
	//해당하는 id와 일치하는 board_type 게시글 불러오기
	public int getBoardListCount(String id, String board_type) {
		// TODO Auto-generated method stub
		int listCount = 0;
		Connection con = getConnection();
		BoardDAO boardDAO = BoardDAO.getInstance();
		boardDAO.setConnection(con);
		listCount = boardDAO.selectBoardListCount(id, board_type);
		close(con);
		return listCount;
	}

	public ArrayList<BoardBean> getBoardList(String id, String board_type, int page, int limit) {
		// TODO Auto-generated method stub
		ArrayList<BoardBean> boardList = null;
		Connection con = getConnection();
		BoardDAO boardDAO = BoardDAO.getInstance();
		boardDAO.setConnection(con);
		boardList = boardDAO.selectBoardList(id, board_type, page, limit);
		close(con);
		return boardList;
	}

	//게시글 검색 
	public int getBoardListCount(String board_date, String board_type, String search_type, String search_text,
			String board_step, String board_photo) {
		// TODO Auto-generated method stub
		int listCount = 0;
		Connection con = getConnection();
		BoardDAO boardDAO = BoardDAO.getInstance();
		boardDAO.setConnection(con);
		listCount = boardDAO.selectBoardListCount(board_date, board_type, search_type, search_text, board_step, board_photo);
		close(con);
		return listCount;
	}

	public ArrayList<BoardBean> getBoardList(String board_date, String board_type, String search_type,
			String search_text, String board_step, String board_photo, int page, int limit) {
		// TODO Auto-generated method stub
		ArrayList<BoardBean> boardList = null;
		Connection con = getConnection();
		BoardDAO boardDAO = BoardDAO.getInstance();
		boardDAO.setConnection(con);
		boardList = boardDAO.selectBoardList(board_date, board_type, search_type, search_text, board_step, board_photo, page, limit);
		close(con);
		return boardList;
	}

}
