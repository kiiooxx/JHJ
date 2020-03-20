package board.svc;

import static db.JdbcUtil.close;
import static db.JdbcUtil.commit;
import static db.JdbcUtil.getConnection;
import static db.JdbcUtil.rollback;

import java.sql.Connection;

import dao.BoardDAO;

public class QnADeleteService {

	public boolean deleteQnA(int qna_num) {
		// TODO Auto-generated method stub
		Connection con = getConnection();
		BoardDAO boardDAO = BoardDAO.getInstance();
		boardDAO.setConnection(con);
		
		boolean isDeleteSuccess = boardDAO.deleteQnA(qna_num);
		if(isDeleteSuccess) {
			commit(con);
		}else {
			rollback(con);
		}
		
		close(con);
		return isDeleteSuccess;
	}

}
