package board.svc;

import static db.JdbcUtil.close;
import static db.JdbcUtil.getConnection;

import java.sql.Connection;

import dao.BoardDAO;
import vo.QnABean;

public class QnADetailService {

	public static QnABean getQnA(int qna_num) {
		// TODO Auto-generated method stub
		QnABean qnaBean = null;
		Connection con = getConnection();
		BoardDAO boardDAO = BoardDAO.getInstance();
		boardDAO.setConnection(con);
		qnaBean = boardDAO.selectQnA(qna_num);
		close(con);
		return qnaBean;
	}

}
