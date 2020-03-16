package board.svc;

import static db.JdbcUtil.close;
import static db.JdbcUtil.getConnectionBoard;

import java.sql.Connection;
import java.util.ArrayList;

import dao.Notice_BoardDAO;
import vo.Notice_BoardBean;

public class Notice_BoardListService {
	public int getListCount() throws Exception {
		int listCount = 0;
		Connection con = getConnectionBoard();
		Notice_BoardDAO boardDAO = Notice_BoardDAO.getInstance();
		boardDAO.setConnection(con);
		listCount = boardDAO.selectListCount();
		close(con);
		return listCount;
	}
	
	public ArrayList<Notice_BoardBean> getArticleList(int page, int limit) throws Exception {
		ArrayList<Notice_BoardBean> articleList = null;
		Connection con = getConnectionBoard();
		Notice_BoardDAO notice_boardDAO = Notice_BoardDAO.getInstance();
		notice_boardDAO.setConnection(con);
		articleList = notice_boardDAO.selectArticleList(page, limit);
		close(con);
		return articleList;
	}
}
