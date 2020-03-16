package board.svc;

import static db.JdbcUtil.close;
import static db.JdbcUtil.commit;
import static db.JdbcUtil.getConnection;
import static db.JdbcUtil.getConnectionBoard;
import static db.JdbcUtil.rollback;

import java.sql.Connection;

import dao.MemberDAO;
import dao.Notice_BoardDAO;
import vo.Notice_BoardBean;

public class Notice_BoardWriteProService {
public boolean registArticle(Notice_BoardBean notice_boardBean) throws Exception {
		
		boolean isWriteSuccess = false;
		Connection con = getConnectionBoard();
		Notice_BoardDAO boardDAO = Notice_BoardDAO.getInstance();
		boardDAO.setConnection(con);
		int insertCount = boardDAO.insertArticle(notice_boardBean);
		
		if(insertCount > 0) {
			commit(con);
			isWriteSuccess = true;
		}else {
			rollback(con);
		}
		close(con);
		return isWriteSuccess;
	}
}