package board.svc;

import static db.JdbcUtil.close;
import static db.JdbcUtil.commit;
import static db.JdbcUtil.getConnection;
import static db.JdbcUtil.rollback;

import java.sql.Connection;

import dao.BoardDAO;

public class BoardNoticeService {

	public boolean updateNoticeBoard(String[] board_num, String board_notice) {
		// TODO Auto-generated method stub
		Connection con = getConnection();
		BoardDAO boardDAO = BoardDAO.getInstance();
		boardDAO.setConnection(con);
		boolean isUpdateSuccess = false;
		
		for(int i=0; i<board_num.length; i++) {
			isUpdateSuccess = false;
			isUpdateSuccess = boardDAO.updateNoticeBoard(Integer.parseInt(board_num[i]), board_notice);
			if(!isUpdateSuccess) {
				rollback(con);
				break;
			}
		}
		
		if(isUpdateSuccess) {
			commit(con);
		}else {
			rollback(con);
		}
		
		close(con);
		return isUpdateSuccess;
	}

}
