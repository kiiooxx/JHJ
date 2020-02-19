package dao;

import static db.JdbcUtil.close;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import vo.ReviewBean;

public class BoardDAO {
	Connection con;
	private static BoardDAO boardDAO;
	
	private BoardDAO() {
		
	}
	
	public void setConnection(Connection con) {
		// TODO Auto-generated method stub
		this.con = con;
	}
	
	public static BoardDAO getInstance() {
		// TODO Auto-generated method stub
		if(boardDAO == null) {
			boardDAO = new BoardDAO();
		}
		
		return boardDAO;
	}

	//리뷰 등록
	public int insertReview(ReviewBean reviewBean) {
		// TODO Auto-generated method stub
		PreparedStatement pstmt = null;
		int insertCount = 0;
		String sql = "insert into review select ifnull(max(rev_num)+1, 1), ?, ?, NOW(),";
		sql += "?, ?, ?, 0, ? from review";
		
		try {
			
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, reviewBean.getRev_subject());
			pstmt.setString(2, reviewBean.getRev_content());
			pstmt.setInt(3, reviewBean.getScore());
			pstmt.setString(4, reviewBean.getUser_id());
			pstmt.setInt(5, reviewBean.getPro_num());
			pstmt.setString(6, reviewBean.getRev_photo());
			
			insertCount = pstmt.executeUpdate();
		
		}catch(SQLException e) {
			System.out.println("리뷰 등록 에러 " + e);
		}finally {
			close(pstmt);
		}
		return insertCount;
	}

	//전체 리뷰 개수 구하기
	public int selectReviewListCount() {
		// TODO Auto-generated method stub
		int listCount = 0;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			//전체 리뷰 구하기.
			pstmt = con.prepareStatement("select count(*) from review");
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				listCount = rs.getInt(1);
			}
		}catch(Exception e) {
			System.out.println("getReviewListCount 에러 : " + e);
		}finally {
			close(rs);
			close(pstmt);
		}
		return listCount;
	}

	public ArrayList<ReviewBean> selectReviewList(int page, int limit) {
		// TODO Auto-generated method stub
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		ArrayList<ReviewBean> reviewList = new ArrayList<>();
		ReviewBean reviewBean = null;
		int startrow = (page-1)*limit;	//읽기 시작할 row 번호
		
		try {
			pstmt = con.prepareStatement("select * from review a inner join pro_info b on a.pro_num = b.pro_num order by rev_num desc limit ?,?");
			pstmt.setInt(1, startrow);
			pstmt.setInt(2, limit);
			
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				reviewBean = new ReviewBean();
				reviewBean.setRev_num(rs.getInt("rev_num"));
				reviewBean.setRev_subject(rs.getString("rev_subject"));
				reviewBean.setRev_content(rs.getString("rev_content"));
				reviewBean.setRev_date(rs.getString("rev_date"));
				reviewBean.setScore(rs.getInt("score"));
				reviewBean.setUser_id(rs.getString("user_id"));
				reviewBean.setPro_num(rs.getInt("pro_num"));
				reviewBean.setRev_photo(rs.getString("rev_photo"));
				reviewBean.setPro_photo(rs.getString("pro_photo"));
				reviewList.add(reviewBean);
			}
		}catch(Exception e) {
			System.out.println("selectReviewList 에러 : " + e);
		}finally {
			close(rs);
			close(pstmt);
		}
		
		return reviewList;
	}

	//rev_num에 해당하는 리뷰 내용 불러오기
	public ReviewBean selectReview(int rev_num) {
		// TODO Auto-generated method stub
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		ReviewBean reviewBean = null;
		
		try {
			pstmt = con.prepareStatement("select * from review where rev_num=?");
			pstmt.setInt(1, rev_num);
			
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				reviewBean = new ReviewBean();
				reviewBean.setRev_num(rs.getInt("rev_num"));
				reviewBean.setRev_subject(rs.getString("rev_subject"));
				reviewBean.setRev_content(rs.getString("rev_content"));
				reviewBean.setRev_date(rs.getString("rev_date"));
				reviewBean.setScore(rs.getInt("score"));
				reviewBean.setUser_id(rs.getString("user_id"));
				reviewBean.setPro_num(rs.getInt("pro_num"));
				reviewBean.setRev_photo(rs.getString("rev_photo"));
			}
		}catch(Exception e) {
			System.out.println("selectReview 에러 : " + e);
		}finally {
			close(rs);
			close(pstmt);
		}
		
		return reviewBean;
	}

	//해당하는 상품 번호의 리뷰 리스트 불러오기
	public int selectReviewListCount(int pro_num) {
		// TODO Auto-generated method stub
		int listCount = 0;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			//전체 리뷰 구하기.
			pstmt = con.prepareStatement("select count(*) from review where pro_num=?");
			pstmt.setInt(1, pro_num);
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				listCount = rs.getInt(1);
			}
		}catch(Exception e) {
			System.out.println("getReviewListCount 에러 : " + e);
		}finally {
			close(rs);
			close(pstmt);
		}
		return listCount;
	}

	public ArrayList<ReviewBean> selectReviewList(int pro_num, int page, int limit) {
		// TODO Auto-generated method stub
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		ArrayList<ReviewBean> reviewList = new ArrayList<>();
		ReviewBean reviewBean = null;
		int startrow = (page-1)*limit;	//읽기 시작할 row 번호
		
		try {
			pstmt = con.prepareStatement("select * from review where pro_num=? order by rev_num desc limit ?,?");
			pstmt.setInt(1, pro_num);
			pstmt.setInt(2, startrow);
			pstmt.setInt(3, limit);
			
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				reviewBean = new ReviewBean();
				reviewBean.setRev_num(rs.getInt("rev_num"));
				reviewBean.setRev_subject(rs.getString("rev_subject"));
				reviewBean.setRev_content(rs.getString("rev_content"));
				reviewBean.setRev_date(rs.getString("rev_date"));
				reviewBean.setScore(rs.getInt("score"));
				reviewBean.setUser_id(rs.getString("user_id"));
				reviewBean.setPro_num(rs.getInt("pro_num"));
				reviewBean.setRev_photo(rs.getString("rev_photo"));
				reviewList.add(reviewBean);
			}
		}catch(Exception e) {
			System.out.println("selectReviewList 에러 : " + e);
		}finally {
			close(rs);
			close(pstmt);
		}
		
		return reviewList;
	}
}
