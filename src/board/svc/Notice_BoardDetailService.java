package board.svc;

import static db.JdbcUtil.close;
import static db.JdbcUtil.commit;
import static db.JdbcUtil.getConnectionBoard;
import static db.JdbcUtil.rollback;

import java.sql.Connection;


import dao.Notice_BoardDAO;
import vo.Notice_BoardBean;

public class Notice_BoardDetailService {
	public Notice_BoardBean getselectArticle(int notice_num) throws Exception {
		Notice_BoardBean article = null;
		Connection con = getConnectionBoard();
		Notice_BoardDAO notice_boardDAO = Notice_BoardDAO.getInstance();
		notice_boardDAO.setConnection(con);
		
		
		article = notice_boardDAO.selectArticle(notice_num);
		close(con);
		return article;
	}
}
