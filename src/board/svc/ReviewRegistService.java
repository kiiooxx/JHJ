package board.svc;

import static db.JdbcUtil.*;

import java.sql.Connection;

import dao.BoardDAO;
import vo.ReviewBean;

public class ReviewRegistService {

	public boolean registReview(ReviewBean reviewBean) {
		// TODO Auto-generated method stub
		BoardDAO boardDAO = BoardDAO.getInstance();
		Connection con = getConnection();
		boardDAO.setConnection(con);
		boolean isRegistSuccess = false;
		int insertCount = boardDAO.insertReview(reviewBean);
		
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
