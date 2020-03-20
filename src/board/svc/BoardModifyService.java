package board.svc;

import static db.JdbcUtil.close;
import static db.JdbcUtil.commit;
import static db.JdbcUtil.getConnection;
import static db.JdbcUtil.rollback;

import java.sql.Connection;

import dao.BoardDAO;
import vo.BoardBean;

public class BoardModifyService {

	public boolean modifyBoard(BoardBean boardBean, int board_num) {
		// TODO Auto-generated method stub
		BoardDAO boardDAO = BoardDAO.getInstance();
		Connection con = getConnection();
		boardDAO.setConnection(con);
		boolean isUpdateSuccess = boardDAO.updateBoard(boardBean, board_num);
		
		if(isUpdateSuccess) {
			commit(con);
		}else {
			rollback(con);
		}
		close(con);
		return isUpdateSuccess;
	}

}
