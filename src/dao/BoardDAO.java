package dao;

import static db.JdbcUtil.close;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import vo.BoardBean;

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
	
	//게시글 등록
	public int insertBoard(BoardBean boardBean) {
		// TODO Auto-generated method stub
		PreparedStatement pstmt = null;
		int insertCount = 0;
		String board_step = "";
		if(!boardBean.getBoard_type().equals("notice")) {
			board_step = "N";
		}
		
		String sql = "insert into board(board_type, board_title, board_writer, board_content, board_date, "
				+ "board_photo, pro_num, sel_num, board_step, qna_email, qna_type, qna_open, review_score) "
				+ "values(?, ?, ?, ?, NOW(), ?, ?, ?, ?, ?, ?, ?, ?)";
		
		try {
			
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, boardBean.getBoard_type());
			pstmt.setString(2, boardBean.getBoard_title());
			pstmt.setString(3, boardBean.getBoard_writer());
			pstmt.setString(4, boardBean.getBoard_content());
			pstmt.setString(5, boardBean.getBoard_photo());
			pstmt.setInt(6, boardBean.getPro_num());
			pstmt.setString(7, boardBean.getSel_num());
			pstmt.setString(8, board_step);
			pstmt.setString(9, boardBean.getQna_email());
			pstmt.setString(10, boardBean.getQna_type());
			pstmt.setString(11, String.valueOf(boardBean.getQna_open()));
			pstmt.setInt(12, boardBean.getReview_score());
			
			insertCount = pstmt.executeUpdate();
		
		}catch(SQLException e) {
			System.out.println("게시글 등록 에러 " + e);
		}finally {
			close(pstmt);
		}
		return insertCount;
	}

	//게시글 수정
	public boolean updateBoard(BoardBean boardBean, int board_num) {
		// TODO Auto-generated method stub
		PreparedStatement pstmt = null;
		int updateCount = 0;
		boolean isUpdateSuccess = false;
		
		String sql = "update board set board_title=?, board_content=?, board_photo=?, ";
		sql += "qna_email=?, qna_type=?, qna_open=?, review_score=? where board_num=?";
		
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, boardBean.getBoard_title());
			pstmt.setString(2, boardBean.getBoard_content());
			pstmt.setString(3, boardBean.getBoard_photo());
			pstmt.setString(4, boardBean.getQna_email());
			pstmt.setString(5, boardBean.getQna_type());
			pstmt.setString(6, String.valueOf(boardBean.getQna_open()));
			pstmt.setInt(7, boardBean.getReview_score());
			pstmt.setInt(8, board_num);
			updateCount = pstmt.executeUpdate();
			
			if(updateCount > 0) {
				isUpdateSuccess = true;
			}
		}catch(SQLException e) {
			System.out.println("게시글 수정 에러 " + e);
		}finally {
			close(pstmt);
		}
		return isUpdateSuccess;
	}

	//3. 게시글 삭제
	public boolean deleteBoard(int board_num) {
		// TODO Auto-generated method stub
		PreparedStatement pstmt = null;
		boolean isDeleteSuccess = false;
		int deleteCount = 0;
		try {
			//전체 리뷰 구하기.
			pstmt = con.prepareStatement("delete from board where board_num=?");
			pstmt.setInt(1, board_num);
			deleteCount = pstmt.executeUpdate();
			
			if(deleteCount > 0) {
				isDeleteSuccess = true;
			}
		}catch(Exception e) {
			System.out.println("BoardDelete 에러 : " + e);
		}finally {
			close(pstmt);
		}
		return isDeleteSuccess;
	}

	//4. 게시글 가져오기
	public BoardBean selectBoard(int board_num) {
		// TODO Auto-generated method stub
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		BoardBean boardBean = null;
		
		try {
			pstmt = con.prepareStatement("select * from board where board_num=?");
			pstmt.setInt(1, board_num);
			
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				boardBean = new BoardBean();
				boardBean.setBoard_num(board_num);
				boardBean.setBoard_type(rs.getString("board_type"));
				boardBean.setBoard_title(rs.getString("board_title"));
				boardBean.setBoard_writer(rs.getString("board_writer"));
				boardBean.setBoard_content(rs.getString("board_content"));
				boardBean.setBoard_date(rs.getString("board_date"));
				boardBean.setBoard_photo(rs.getString("board_photo"));
				boardBean.setPro_num(rs.getInt("pro_num"));
				boardBean.setSel_num(rs.getString("sel_num"));
				boardBean.setBoard_step(rs.getString("board_step"));
				boardBean.setQna_email(rs.getString("qna_email"));
				boardBean.setQna_type(rs.getString("qna_type"));
				boardBean.setQna_open(rs.getString("qna_open"));
				boardBean.setReview_score(rs.getInt("review_score"));
			}
		}catch(Exception e) {
			System.out.println("selectBoard 에러 : " + e);
		}finally {
			close(rs);
			close(pstmt);
		}
		
		return boardBean;
	}

	
	//board_type에 해당하는 게시글 개수
	public int selectBoardListCount(String board_type) {
		// TODO Auto-generated method stub
		int listCount = 0;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			//전체 리뷰 구하기.
			pstmt = con.prepareStatement("select count(*) from board where board_type=?");
			pstmt.setString(1, board_type);
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				listCount = rs.getInt(1);
			}
		}catch(Exception e) {
			System.out.println("getBoardListCount 에러 : " + e);
		}finally {
			close(rs);
			close(pstmt);
		}
		return listCount;
	}

	//board_type에 해당하는 게시글 목록
	public ArrayList<BoardBean> selectBoardList(String board_type, int page, int limit) {
		// TODO Auto-generated method stub
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		ArrayList<BoardBean> boardList = new ArrayList<>();
		BoardBean boardBean = null;
		int startrow = (page-1)*limit;	//읽기 시작할 row 번호
		
		try {
			pstmt = con.prepareStatement("select * from board where board_type=? order by board_num desc limit ?,?");
			pstmt.setString(1, board_type);
			pstmt.setInt(2, startrow);
			pstmt.setInt(3, limit);
			
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				boardBean = new BoardBean();
				boardBean.setBoard_num(rs.getInt("board_num"));
				boardBean.setBoard_type(rs.getString("board_type"));
				boardBean.setBoard_title(rs.getString("board_title"));
				boardBean.setBoard_writer(rs.getString("board_writer"));
				boardBean.setBoard_content(rs.getString("board_content"));
				boardBean.setBoard_date(rs.getString("board_date"));
				boardBean.setBoard_photo(rs.getString("board_photo"));
				boardBean.setPro_num(rs.getInt("pro_num"));
				boardBean.setSel_num(rs.getString("sel_num"));
				boardBean.setBoard_step(rs.getString("board_step"));
				boardBean.setQna_email(rs.getString("qna_email"));
				boardBean.setQna_type(rs.getString("qna_type"));
				boardBean.setQna_open(rs.getString("qna_open"));
				boardBean.setReview_score(rs.getInt("review_score"));
				boardList.add(boardBean);
			}
		}catch(Exception e) {
			System.out.println("selectBoardList 에러 : " + e);
		}finally {
			close(rs);
			close(pstmt);
		}
		
		return boardList;
	}

	//pro_num(상품번호)에 해당하는 board_type(게시글 종류) 개수 불러오기
	public int selectBoardListCount(int pro_num, String board_type) {
		// TODO Auto-generated method stub
		int listCount = 0;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			//전체 리뷰 구하기.
			pstmt = con.prepareStatement("select count(*) from board where pro_num=? and board_type=?");
			pstmt.setInt(1, pro_num);
			pstmt.setString(2, board_type);
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				listCount = rs.getInt(1);
			}
		}catch(Exception e) {
			System.out.println("getBoardListCount 에러 : " + e);
		}finally {
			close(rs);
			close(pstmt);
		}
		return listCount;
	}

	//pro_num(상품번호)에 해당하는 board_type(게시글 종류) 목록
	public ArrayList<BoardBean> selectBoardList(int pro_num, String board_type, int page, int limit) {
		// TODO Auto-generated method stub
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		ArrayList<BoardBean> boardList = new ArrayList<>();
		BoardBean boardBean = null;
		int startrow = (page-1)*limit;	//읽기 시작할 row 번호
		
		try {
			pstmt = con.prepareStatement("select * from board where pro_num=? and board_type=? order by board_num desc limit ?,?");
			pstmt.setInt(1, pro_num);
			pstmt.setString(2, board_type);
			pstmt.setInt(3, startrow);
			pstmt.setInt(4, limit);
			
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				boardBean = new BoardBean();
				boardBean.setBoard_num(rs.getInt("board_num"));
				boardBean.setBoard_type(rs.getString("board_type"));
				boardBean.setBoard_title(rs.getString("board_title"));
				boardBean.setBoard_writer(rs.getString("board_writer"));
				boardBean.setBoard_content(rs.getString("board_content"));
				boardBean.setBoard_date(rs.getString("board_date"));
				boardBean.setBoard_photo(rs.getString("board_photo"));
				boardBean.setPro_num(rs.getInt("pro_num"));
				boardBean.setSel_num(rs.getString("sel_num"));
				boardBean.setBoard_step(rs.getString("board_step"));
				boardBean.setQna_email(rs.getString("qna_email"));
				boardBean.setQna_type(rs.getString("qna_type"));
				boardBean.setQna_open(rs.getString("qna_open"));
				boardBean.setReview_score(rs.getInt("review_score"));
				boardList.add(boardBean);
			}
		}catch(Exception e) {
			System.out.println("selectBoardList 에러 : " + e);
		}finally {
			close(rs);
			close(pstmt);
		}
		
		return boardList;
	}

	//id(유저 아이디)에 해당하는 board_type(게시글 종류) 개수
	public int selectBoardListCount(String id, String board_type) {
		// TODO Auto-generated method stub
		int listCount = 0;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			//전체 리뷰 구하기.
			pstmt = con.prepareStatement("select count(*) from board where board_writer=? and board_type=?");
			pstmt.setString(1, id);
			pstmt.setString(2, board_type);
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				listCount = rs.getInt(1);
			}
		}catch(Exception e) {
			System.out.println("getBoardListCount 에러 : " + e);
		}finally {
			close(rs);
			close(pstmt);
		}
		return listCount;
	}

	//id(유저 아이디)에 해당하는 board_type(게시글 종류) 목록
	public ArrayList<BoardBean> selectBoardList(String id, String board_type, int page, int limit) {
		// TODO Auto-generated method stub
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		ArrayList<BoardBean> boardList = new ArrayList<>();
		BoardBean boardBean = null;
		int startrow = (page-1)*limit;	//읽기 시작할 row 번호
		
		try {
			pstmt = con.prepareStatement("select * from board where board_writer=? and board_type=? order by board_num desc limit ?,?");
			pstmt.setString(1, id);
			pstmt.setString(2, board_type);
			pstmt.setInt(3, startrow);
			pstmt.setInt(4, limit);
			
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				boardBean = new BoardBean();
				boardBean.setBoard_num(rs.getInt("board_num"));
				boardBean.setBoard_type(rs.getString("board_type"));
				boardBean.setBoard_title(rs.getString("board_title"));
				boardBean.setBoard_writer(rs.getString("board_writer"));
				boardBean.setBoard_content(rs.getString("board_content"));
				boardBean.setBoard_date(rs.getString("board_date"));
				boardBean.setBoard_photo(rs.getString("board_photo"));
				boardBean.setPro_num(rs.getInt("pro_num"));
				boardBean.setSel_num(rs.getString("sel_num"));
				boardBean.setBoard_step(rs.getString("board_step"));
				boardBean.setQna_email(rs.getString("qna_email"));
				boardBean.setQna_type(rs.getString("qna_type"));
				boardBean.setQna_open(rs.getString("qna_open"));
				boardBean.setReview_score(rs.getInt("review_score"));
				boardList.add(boardBean);
			}
		}catch(Exception e) {
			System.out.println("selectBoardList 에러 : " + e);
		}finally {
			close(rs);
			close(pstmt);
		}
		
		return boardList;
	}

	
	//게시글 검색 개수
	public int selectBoardListCount(String board_date, String board_type, String search_type, String search_text,
			String board_step, String board_photo) {
		// TODO Auto-generated method stub
		int listCount = 0;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "select count(*) from board ";
		int cnt = 0;
		
		if(!board_date.equals("all") || !board_type.equals("all") || !search_text.equals("") || !board_step.equals("all")
				|| !board_photo.equals("all")) {
			sql += "where ";
		}
				
		if(!board_date.equals("all")) {
			sql += "board_date > date_add(now(), interval " + board_date + " day)";
			cnt++;
		}
		
		if(!board_type.equals("all")) {
			if(cnt > 0) {
				sql += " and";
			}
			sql += " board_type = '" + board_type + "'";
			cnt++;
		}
		
		if(!search_text.equals("")) {
			if(cnt > 0) {
				sql += " and ";
			}
			sql += search_type + " like '%" + search_text + "%'";
			cnt++;
		}
		if(!board_step.equals("all")) {
			if(cnt > 0) {
				sql += " and";
			}
			sql += " board_step = '" + board_step + "'";
			cnt++;
		}
		if(!board_photo.equals("all")) {
			if(cnt > 0) {
				sql += " and";
			}
			if(board_photo.equals("Y")) {
				sql += " board_photo != ''";
			}else if(board_photo.equals("N")) {
				sql += " board_photo = ''";
			}
			cnt++;
		}
		
		
		try {
			//전체 리뷰 구하기.
			pstmt = con.prepareStatement(sql);
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				listCount = rs.getInt(1);
				System.out.println("카운트 : " + listCount);
			}
		}catch(Exception e) {
			System.out.println("getBoardListCount 에러 : " + e);
		}finally {
			close(rs);
			close(pstmt);
		}
		return listCount;
	}

	
	public ArrayList<BoardBean> selectBoardList(String board_date, String board_type, String search_type,
			String search_text, String board_step, String board_photo, int page, int limit) {
		// TODO Auto-generated method stub
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		ArrayList<BoardBean> boardList = new ArrayList<>();
		BoardBean boardBean = null;
		int startrow = (page-1)*limit;	//읽기 시작할 row 번호
		
		String sql = "select * from board ";
		
		int cnt = 0;
		
		if(!board_date.equals("all") || !board_type.equals("all") || !search_text.equals("") || !board_step.equals("all")
				|| !board_photo.equals("all")) {
			sql += "where ";
		}
				
		if(!board_date.equals("all")) {
			sql += "board_date > date_add(now(), interval " + board_date + " day)";
			cnt++;
		}
		
		if(!board_type.equals("all")) {
			if(cnt > 0) {
				sql += " and";
			}
			sql += " board_type = '" + board_type + "'";
			cnt++;
		}
		
		if(!search_text.equals("")) {
			if(cnt > 0) {
				sql += " and ";
			}
			sql += search_type + " like '%" + search_text + "%'";
			cnt++;
		}
		if(!board_step.equals("all")) {
			if(cnt > 0) {
				sql += " and";
			}
			sql += " board_step = '" + board_step + "'";
			cnt++;
		}
		if(!board_photo.equals("all")) {
			if(cnt > 0) {
				sql += " and";
			}
			if(board_photo.equals("Y")) {
				sql += " board_photo != ''";
			}else if(board_photo.equals("N")) {
				sql += " board_photo = ''";
			}
			cnt++;
		}
		sql += " order by board_num desc limit ?, ?";
		
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, startrow);
			pstmt.setInt(2, limit);
			
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				boardBean = new BoardBean();
				boardBean.setBoard_num(rs.getInt("board_num"));
				boardBean.setBoard_type(rs.getString("board_type"));
				boardBean.setBoard_title(rs.getString("board_title"));
				boardBean.setBoard_writer(rs.getString("board_writer"));
				boardBean.setBoard_content(rs.getString("board_content"));
				boardBean.setBoard_date(rs.getString("board_date"));
				boardBean.setBoard_photo(rs.getString("board_photo"));
				boardBean.setPro_num(rs.getInt("pro_num"));
				boardBean.setSel_num(rs.getString("sel_num"));
				boardBean.setBoard_step(rs.getString("board_step"));
				boardBean.setQna_email(rs.getString("qna_email"));
				boardBean.setQna_type(rs.getString("qna_type"));
				boardBean.setQna_open(rs.getString("qna_open"));
				boardBean.setReview_score(rs.getInt("review_score"));
				boardList.add(boardBean);
			}
		}catch(Exception e) {
			System.out.println("selectBoardList 에러 : " + e);
		}finally {
			close(rs);
			close(pstmt);
		}
		
		return boardList;
	}

	
}
