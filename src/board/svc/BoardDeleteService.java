package board.svc;

import static db.JdbcUtil.close;
import static db.JdbcUtil.commit;
import static db.JdbcUtil.getConnection;
import static db.JdbcUtil.rollback;

import java.sql.Connection;

import dao.BoardDAO;

public class BoardDeleteService {

	public boolean deleteBoard(String[] board_num) {
		// TODO Auto-generated method stub
		Connection con = getConnection();
		BoardDAO boardDAO = BoardDAO.getInstance();
		boardDAO.setConnection(con);
		boolean isDeleteSuccess = false;
		
		for(int i=0; i<board_num.length; i++) {
			isDeleteSuccess = false;
			isDeleteSuccess = boardDAO.deleteBoard(Integer.parseInt(board_num[i]));
			if(!isDeleteSuccess) {
				rollback(con);
				break;
			}
		}
		
		if(isDeleteSuccess) {
			commit(con);
		}else {
			rollback(con);
		}
		
		close(con);
		return isDeleteSuccess;
	}

	

}
