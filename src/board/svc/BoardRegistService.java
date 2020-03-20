package board.svc;

import static db.JdbcUtil.close;
import static db.JdbcUtil.commit;
import static db.JdbcUtil.getConnection;
import static db.JdbcUtil.rollback;

import java.sql.Connection;

import dao.BoardDAO;
import vo.BoardBean;

public class BoardRegistService {

	//게시글 등록
	public boolean registBoard(BoardBean boardBean) {
		// TODO Auto-generated method stub
		BoardDAO boardDAO = BoardDAO.getInstance();
		Connection con = getConnection();
		boardDAO.setConnection(con);
		boolean isRegistSuccess = false;
		int insertCount = boardDAO.insertBoard(boardBean);
		
		if(insertCount > 0) {
			commit(con);
			isRegistSuccess = true;
		}else {
			rollback(con);
		}
		close(con);
		return isRegistSuccess;
	}

}
