package board.svc;

import static db.JdbcUtil.close;
import static db.JdbcUtil.commit;
import static db.JdbcUtil.getConnectionBoard;
import static db.JdbcUtil.rollback;

import java.sql.Connection;

import dao.Notice_BoardDAO;


public class Notice_BoardDeleteProService {
	
	
	public boolean removeArticle(int notice_num) throws Exception {
		boolean isRemoveSuccess = false;
		Connection con = getConnectionBoard();
		Notice_BoardDAO boardDAO = Notice_BoardDAO.getInstance();
		boardDAO.setConnection(con);
		int deleteCount = boardDAO.deleteArticle(notice_num);
		
		if(deleteCount > 0) {
			commit(con);
			isRemoveSuccess = true;
		}else {
			rollback(con);
		}
		
		close(con);
		return isRemoveSuccess;
	}
}
