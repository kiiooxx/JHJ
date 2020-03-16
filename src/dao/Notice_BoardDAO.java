package dao;

import static db.JdbcUtil.close;
import static db.JdbcUtil.commit;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.sql.DataSource;
import vo.Notice_BoardBean;

public class Notice_BoardDAO {
	DataSource ds;
	Connection con;
	private static Notice_BoardDAO notice_boardDAO;
	
	private Notice_BoardDAO() {
		
	}
	
	public static Notice_BoardDAO getInstance() {
		if(notice_boardDAO == null) {
			notice_boardDAO = new Notice_BoardDAO();
		}
		return notice_boardDAO;
	}
	
	public void setConnection(Connection con) {
		this.con = con;
	}
	
	//글의 개수 구하기
	public int selectListCount() {
		int listCount = 0;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			pstmt = con.prepareStatement("select count(*) from notice");
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				listCount = rs.getInt(1);
			}
		}catch(Exception ex) {
			System.out.println("getListCount 에러 : " + ex);
		}finally {
			close(rs);
			close(pstmt);
		}
		return listCount;
	}
	
	//글 목록 보기
	public ArrayList<Notice_BoardBean> selectArticleList(int page, int limit) {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String board_list_sql = "select * from notice order by notice_num desc limit ?,?";
		ArrayList<Notice_BoardBean> articleList = new ArrayList<Notice_BoardBean>();
		Notice_BoardBean board = null;
		int startrow = (page-1)*limit;	//읽기 시작할 row 번호

		try {
			pstmt = con.prepareStatement(board_list_sql);
			pstmt.setInt(1, startrow);
			pstmt.setInt(2, limit);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				board = new Notice_BoardBean();
				board.setNotice_num(rs.getInt("notice_num"));
				board.setNotice_title(rs.getString("notice_title"));
				board.setNotice_content(rs.getString("notice_content"));
				board.setNotice_date(rs.getString("notice_date"));
				articleList.add(board);
			}
		}catch(Exception ex) {
			System.out.println("getBoardList 에러 : " + ex);
		}finally {
			close(rs);
			close(pstmt);
		}
		
		return articleList;
	}
	
	//글 내용 보기
	public Notice_BoardBean selectArticle(int notice_num) {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Notice_BoardBean boardBean = null;
		
		try {
			pstmt = con.prepareStatement("select * from board where notice_num = ?");
			pstmt.setInt(1, notice_num);
			
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				boardBean = new Notice_BoardBean();
				boardBean.setNotice_num(rs.getInt("BOARD_NUM"));
				boardBean.setNotice_title(rs.getString("BOARD_NAME"));
				boardBean.setNotice_content(rs.getString("BOARD_SUBJECT"));
				boardBean.setNotice_date(rs.getString("BOARD_CONTENT"));
//				boardBean.setBOARD_FILE(rs.getString("BOARD_FILE"));
//				boardBean.setBOARD_RE_REF(rs.getInt("BOARD_RE_REF"));
//				boardBean.setBOARD_RE_LEV(rs.getInt("BOARD_RE_LEV"));
//				boardBean.setBOARD_RE_SEQ(rs.getInt("BOARD_RE_SEQ"));
//				boardBean.setBOARD_READCOUNT(rs.getInt("BOARD_READCOUNT"));
//				boardBean.setBOARD_RE_COUNT(rs.getInt("BOARD_RE_COUNT"));
//				boardBean.setBOARD_DATE(rs.getDate("BOARD_DATE"));
			}
		}catch(Exception ex) {
			System.out.println("getDetail 에러 : " + ex);
		}finally {
			close(rs);
			close(pstmt);
		}
		
		return boardBean;
	}
	
	//글 등록
	public int insertArticle(Notice_BoardBean article) {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		int num = 0;
		String sql = "";
		int insertCount = 0;
		try {
			pstmt = con.prepareStatement("select max(notice_num) from notice");
			rs = pstmt.executeQuery();
			if(rs.next()) {
				num = rs.getInt(1) + 1;
			}else {
				num = 1;
			}

			sql = "insert into notice values(?,?,?,now())";
			
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, num);
			pstmt.setString(2, article.getNotice_title());
			pstmt.setString(3, article.getNotice_content());
			insertCount = pstmt.executeUpdate();

		}catch(Exception ex) {
			System.out.println("boardInsert 에러 : " + ex);
		}finally {
			close(rs);
			close(pstmt);
		}
		return insertCount;
	}
	
	//글 수정
	public int updateArticle(Notice_BoardBean article) {
		
		int updateCount = 0;
		PreparedStatement pstmt = null;
		String sql = "update board set notice_title=?, notice_content=? where notice_num=?";
		
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, article.getNotice_title());
			pstmt.setString(2, article.getNotice_content());
			pstmt.setInt(3, article.getNotice_num());
			updateCount = pstmt.executeUpdate();
		}catch(Exception ex) {
			System.out.println("boardModify 에러 : " + ex);
		}finally {
			close(pstmt);
		}
		
		return updateCount;
	}
	
//	//글 삭제
//	public int deleteArticle(int notice_num) {
//		PreparedStatement pstmt = null;
//		ResultSet rs = null;
//		String sql ='';
//		int deleteCount = 0;
//		
//			if(	String sql = "delete from board where BOARD_NUM=?") {
//				pstmt = con.prepareStatement(sql);
//				pstmt.setInt(1, notice_num);
//				deleteCount = pstmt.executeUpdate();
//			}
//		}catch(Exception ex) {
//			System.out.println("boardDelete 에러 : " + ex);
//		}finally {
//			close(pstmt);
//		}
//		return deleteCount;
//	}
//	
	//조회수 업데이트
	public int updateReadCount(int board_num) {
		
		PreparedStatement pstmt = null;
		int updateCount = 0;
		String sql = "update board set BOARD_READCOUNT = " + "BOARD_READCOUNT+1 where BOARD_NUM = " + board_num;
		
		try {
			pstmt = con.prepareStatement(sql);
			updateCount = pstmt.executeUpdate();
		}catch(SQLException ex) {
			System.out.println("setReadCountUpdate 에러 : " + ex);
		}finally {
			close(pstmt);
		}
		return updateCount;
	}
}
//	//글쓴이인지 확인
//	public boolean isArticleBoardWriter(String Grade) {
//		PreparedStatement pstmt = null;
//		ResultSet rs = null;
//		String board_sql = "select * from board where grade=?";
//		boolean isWriter = false;
//		
//		try {
//			pstmt = con.prepareStatement(board_sql);
//			pstmt.setInt(1, notice_num);
//			rs = pstmt.executeQuery();
//			rs.next();
//			
//			if(pass.equals(rs.getString("BOARD_PASS"))) {
//				isWriter = true;
//			}
//		}catch(SQLException ex) {
//			System.out.println("isBoardWriter 에러 : " + ex);
//		}finally {
//			close(pstmt);
//		}
//		
//		return isWriter;
//	}
//}
