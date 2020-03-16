package board.svc;

import static db.JdbcUtil.close;
import static db.JdbcUtil.commit;
import static db.JdbcUtil.getConnectionBoard;
import static db.JdbcUtil.rollback;

import java.sql.Connection;

import board.dao.Notice_BoardDAO;

public class Notice_BoardDeleteProService {
	public boolean isArticleWriter(int board_num, String pass) throws Exception {
		boolean isArticleWriter = false;
		Connection con = getConnectionBoard();
		Notice_BoardDAO notice_boardDAO = Notice_BoardDAO.getInstance();
		notice_boardDAO.setConnection(con);
		isArticleWriter = notice_boardDAO.isArticleBoardWriter(board_num, pass);
		close(con);
		return isArticleWriter;
	}
	
	public boolean removeArticle(int board_num) throws Exception {
		boolean isRemoveSuccess = false;
		Connection con = getConnectionBoard();
		Notice_BoardDAO boardDAO = Notice_BoardDAO.getInstance();
		boardDAO.setConnection(con);
		int deleteCount = boardDAO.deleteArticle(board_num);
		
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
