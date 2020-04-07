package board.svc;

import static db.JdbcUtil.close;
import static db.JdbcUtil.getConnection;

import java.sql.Connection;
import java.util.ArrayList;

import dao.BoardDAO;
import vo.BoardBean;

public class BoardListService {

	//(board_type / id / pro_num 에 해당하는)게시글 개수
	public int getBoardListCount(String board_type, String id, int pro_num) {
		// TODO Auto-generated method stub
		int listCount = 0;
		Connection con = getConnection();
		BoardDAO boardDAO = BoardDAO.getInstance();
		boardDAO.setConnection(con);
		listCount = boardDAO.selectBoardListCount(board_type, id, pro_num);
		close(con);
		return listCount;
	}

	//(board_type / id / pro_num 에 해당하는)게시글 리스트
	public ArrayList<BoardBean> getBoardList(String board_type, String id, int pro_num, int page, int limit) {
		// TODO Auto-generated method stub
		ArrayList<BoardBean> boardList = null;
		Connection con = getConnection();
		BoardDAO boardDAO = BoardDAO.getInstance();
		boardDAO.setConnection(con);
		boardList = boardDAO.selectBoardList(board_type, id, pro_num, page, limit);
		close(con);
		return boardList;
	}

	//게시글 검색 
	public int getBoardListCount(String board_date, String board_type, String search_type, String search_text,
			String board_step, String board_photo, String board_notice) {
		// TODO Auto-generated method stub
		int listCount = 0;
		Connection con = getConnection();
		BoardDAO boardDAO = BoardDAO.getInstance();
		boardDAO.setConnection(con);
		listCount = boardDAO.selectBoardListCount(board_date, board_type, search_type, search_text, board_step, board_photo, board_notice);
		close(con);
		return listCount;
	}

	public ArrayList<BoardBean> getBoardList(String board_date, String board_type, String search_type,
			String search_text, String board_step, String board_photo, String board_notice, int page, int limit) {
		// TODO Auto-generated method stub
		ArrayList<BoardBean> boardList = null;
		Connection con = getConnection();
		BoardDAO boardDAO = BoardDAO.getInstance();
		boardDAO.setConnection(con);
		boardList = boardDAO.selectBoardList(board_date, board_type, search_type, search_text, board_step, board_photo, board_notice, page, limit);
		close(con);
		return boardList;
	}

	//공지 등록한거 불러오기
	public ArrayList<BoardBean> getNoticeListCount(String board_type) {
		// TODO Auto-generated method stub
		ArrayList<BoardBean> boardList = null;
		Connection con = getConnection();
		BoardDAO boardDAO = BoardDAO.getInstance();
		boardDAO.setConnection(con);
		boardList = boardDAO.selectNoticeList(board_type);
		close(con);
		return boardList;
	}

}
