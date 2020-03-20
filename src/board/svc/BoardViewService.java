package board.svc;

import static db.JdbcUtil.close;
import static db.JdbcUtil.getConnection;

import java.sql.Connection;

import dao.BoardDAO;
import vo.BoardBean;

public class BoardViewService {

	public BoardBean getBoard(int board_num) {
		// TODO Auto-generated method stub
		BoardBean boardBean = null;
		Connection con = getConnection();
		BoardDAO boardDAO = BoardDAO.getInstance();
		boardDAO.setConnection(con);
		boardBean = boardDAO.selectBoard(board_num);
		close(con);
		return boardBean;
	}

}
