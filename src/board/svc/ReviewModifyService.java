package board.svc;

import static db.JdbcUtil.close;
import static db.JdbcUtil.commit;
import static db.JdbcUtil.getConnection;
import static db.JdbcUtil.rollback;

import java.sql.Connection;

import dao.BoardDAO;
import vo.ReviewBean;

public class ReviewModifyService {

	public boolean modifyReview(ReviewBean reviewBean, int rev_num) {
		// TODO Auto-generated method stub
		BoardDAO boardDAO = BoardDAO.getInstance();
		Connection con = getConnection();
		boardDAO.setConnection(con);
		boolean isUpdateSuccess = boardDAO.updateReview(reviewBean, rev_num);
		
		if(isUpdateSuccess) {
			commit(con);
		}else {
			rollback(con);
		}
		close(con);
		return isUpdateSuccess;
	}

}
