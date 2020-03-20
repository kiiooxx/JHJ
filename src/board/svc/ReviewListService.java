package board.svc;

import static db.JdbcUtil.*;

import java.sql.Connection;
import java.util.ArrayList;

import dao.AdminDAO;
import dao.BoardDAO;
import vo.ReviewBean;

public class ReviewListService {

	public int getListCount() {
		// TODO Auto-generated method stub
		int listCount = 0;
		Connection con = getConnection();
		BoardDAO boardDAO = BoardDAO.getInstance();
		boardDAO.setConnection(con);
		listCount = boardDAO.selectReviewListCount();
		close(con);
		return listCount;
	}

	public ArrayList<ReviewBean> getReviewList(int page, int limit) {
		// TODO Auto-generated method stub
		ArrayList<ReviewBean> reviewList = null;
		Connection con = getConnection();
		BoardDAO boardDAO = BoardDAO.getInstance();
		boardDAO.setConnection(con);
		reviewList = boardDAO.selectReviewList(page, limit);
		close(con);
		return reviewList;
	}

	//해당하는 상품 번호의 리뷰 리스트 불러오기
	public int getListCount(int pro_num) {
		// TODO Auto-generated method stub
		int listCount = 0;
		Connection con = getConnection();
		BoardDAO boardDAO = BoardDAO.getInstance();
		boardDAO.setConnection(con);
		listCount = boardDAO.selectReviewListCount(pro_num);
		close(con);
		return listCount;
	}

	public ArrayList<ReviewBean> getReviewList(int pro_num, int page, int limit) {
		// TODO Auto-generated method stub
		ArrayList<ReviewBean> reviewList = null;
		Connection con = getConnection();
		BoardDAO boardDAO = BoardDAO.getInstance();
		boardDAO.setConnection(con);
		reviewList = boardDAO.selectReviewList(pro_num, page, limit);
		close(con);
		return reviewList;
	}

	//id가 작성한 리뷰 글 개수 가져오기
	public int getListCount(String id) {
		// TODO Auto-generated method stub
		int listCount = 0;
		Connection con = getConnection();
		BoardDAO boardDAO = BoardDAO.getInstance();
		boardDAO.setConnection(con);
		listCount = boardDAO.selectReviewListCount(id);
		close(con);
		return listCount;
	}
		
	//id가 작성한 리뷰 글 불러오기.
	public ArrayList<ReviewBean> getReviewList(String id, int page, int limit) {
		// TODO Auto-generated method stub
		ArrayList<ReviewBean> reviewList = null;
		Connection con = getConnection();
		BoardDAO boardDAO = BoardDAO.getInstance();
		boardDAO.setConnection(con);
		reviewList = boardDAO.selectReviewList(id, page, limit);
		close(con);
		return reviewList;
	}
}
