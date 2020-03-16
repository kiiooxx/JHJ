package board.svc;

import static db.JdbcUtil.close;
import static db.JdbcUtil.commit;
import static db.JdbcUtil.getConnectionBoard;
import static db.JdbcUtil.rollback;

import java.sql.Connection;

import board.dao.Notice_BoardDAO;
import vo.Notice_BoardBean;

public class Notice_BoardModifyProService {
	public boolean isArticleWriter(int board_num, String pass) throws Exception {
		boolean isArticleWriter = false;
		Connection con = getConnectionBoard();
		Notice_BoardDAO notice_boardDAO = Notice_BoardDAO.getInstance();
		notice_boardDAO.setConnection(con);
		isArticleWriter = notice_boardDAO.isArticleBoardWriter(board_num, pass);
		close(con);
		return isArticleWriter;
	}
	
	public boolean modifyArticle(Notice_BoardBean article) throws Exception {
		boolean isModifySuccess = false;
		Connection con = getConnectionBoard();
		Notice_BoardDAO notice_boardDAO = Notice_BoardDAO.getInstance();
		notice_boardDAO.setConnection(con);
		int updateCount = notice_boardDAO.updateArticle(article);
		
		if(updateCount > 0) {
			commit(con);
			isModifySuccess = true;
		}else {
			rollback(con);
		}
		
		close(con);
		return isModifySuccess;
	}
}
