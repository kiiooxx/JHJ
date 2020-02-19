package board.svc;

import static db.JdbcUtil.*;

import java.sql.Connection;
import java.util.ArrayList;

import dao.BoardDAO;
import vo.ReviewBean;

public class ReviewDetailService {

	public ReviewBean getReview(int rev_num) {
		// TODO Auto-generated method stub
		ReviewBean reviewBean = null;
		Connection con = getConnection();
		BoardDAO boardDAO = BoardDAO.getInstance();
		boardDAO.setConnection(con);
		reviewBean = boardDAO.selectReview(rev_num);
		close(con);
		return reviewBean;
	}
}
