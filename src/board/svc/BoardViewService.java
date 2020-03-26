package board.svc;

import static db.JdbcUtil.close;
import static db.JdbcUtil.commit;
import static db.JdbcUtil.getConnection;
import static db.JdbcUtil.rollback;

import java.sql.Connection;
import java.util.ArrayList;

import dao.BoardDAO;
import vo.BoardBean;

public class BoardViewService {

	//게시글 불러오기 
	public BoardBean getBoard(int board_num, String where) {
		// TODO Auto-generated method stub
		BoardBean boardBean = null;
		Connection con = getConnection();
		BoardDAO boardDAO = BoardDAO.getInstance();
		boardDAO.setConnection(con);
		boardBean = boardDAO.selectBoard(board_num, where);
		close(con);
		return boardBean;
	}

	//조회수 +1
	public boolean updateHits(int board_num) {
		// TODO Auto-generated method stub
		 
		Connection con = getConnection();
		BoardDAO boardDAO = BoardDAO.getInstance();
		boardDAO.setConnection(con);
		boolean isUpdateSuccess = boardDAO.updateHits(board_num);
		
		if(isUpdateSuccess) {
			commit(con);
		}else {
			rollback(con);
		}
		close(con);
		return isUpdateSuccess;
	}

}
