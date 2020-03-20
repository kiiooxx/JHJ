package board.svc;

import static db.JdbcUtil.close;
import static db.JdbcUtil.commit;
import static db.JdbcUtil.getConnection;
import static db.JdbcUtil.rollback;

import java.sql.Connection;

import dao.BoardDAO;
import vo.QnABean;

public class QnAModifyService {

	public boolean modifyQnA(QnABean qnaBean, int qna_num) {
		// TODO Auto-generated method stub
		BoardDAO boardDAO = BoardDAO.getInstance();
		Connection con = getConnection();
		boardDAO.setConnection(con);
		boolean isUpdateSuccess = boardDAO.updateQnA(qnaBean, qna_num);
		
		if(isUpdateSuccess) {
			commit(con);
		}else {
			rollback(con);
		}
		close(con);
		return isUpdateSuccess;
	}

}
