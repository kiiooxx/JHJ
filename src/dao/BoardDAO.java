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
		PreparedStatement pstmt1 = null;
		PreparedStatement pstmt2 = null;
		ResultSet rs = null;
		
		int insertCount = 0;
		
		int board_num = 0;
		String board_step = "";
		if(!(boardBean.getBoard_type().equals("notice") || boardBean.getBoard_type().equals("answer"))) {
			board_step = "N";
		}
		
		String sql1 = "select ifnull(max(board_num)+1, 1) from board";
		String sql2 = "insert into board values(?, ?, ?, ?, ?, NOW(), ?, ?, ?, ?, ?, ?, ?, ?, ?, 0, ?)";
		
		try {
			pstmt1 = con.prepareStatement(sql1);
			rs = pstmt1.executeQuery();
			
			if(rs.next()) {
				board_num = rs.getInt(1);
				
				pstmt2 = con.prepareStatement(sql2);
				pstmt2.setInt(1, board_num);
				pstmt2.setString(2, boardBean.getBoard_type());
				pstmt2.setString(3, boardBean.getBoard_title());
				pstmt2.setString(4, boardBean.getBoard_writer());
				pstmt2.setString(5, boardBean.getBoard_content());
				pstmt2.setString(6, boardBean.getBoard_photo());
				pstmt2.setInt(7, boardBean.getPro_num());
				pstmt2.setString(8, boardBean.getSel_num());
				pstmt2.setString(9, board_step);
				pstmt2.setString(10, boardBean.getQna_email());
				pstmt2.setString(11, boardBean.getQna_type());
				pstmt2.setString(12, String.valueOf(boardBean.getQna_open()));
				pstmt2.setInt(13, boardBean.getReview_score());
				//관련글 번호가 없으면 board_num을 board_ref에 넣는다.
				pstmt2.setInt(14, boardBean.getBoard_ref() == 0 ? board_num : boardBean.getBoard_ref());
				pstmt2.setString(15, boardBean.getBoard_notice());
				
				insertCount = pstmt2.executeUpdate();
				
			}

		}catch(SQLException e) {
			System.out.println("게시글 등록 에러 " + e);
		}finally {
			close(rs);
			close(pstmt2);
			close(pstmt1);
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
		sql += "qna_email=?, qna_type=?, qna_open=?, review_score=?, board_notice=? where board_num=?";
		
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, boardBean.getBoard_title());
			pstmt.setString(2, boardBean.getBoard_content());
			pstmt.setString(3, boardBean.getBoard_photo());
			pstmt.setString(4, boardBean.getQna_email());
			pstmt.setString(5, boardBean.getQna_type());
			pstmt.setString(6, String.valueOf(boardBean.getQna_open()));
			pstmt.setInt(7, boardBean.getReview_score());
			pstmt.setString(8, boardBean.getBoard_notice());
			pstmt.setInt(9, board_num);
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
	public BoardBean selectBoard(int board_num, String where) {
		// TODO Auto-generated method stub
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		BoardBean boardBean = null;
		String sql = "select * from board where " + where;
		
		try {
			pstmt = con.prepareStatement(sql);
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
				boardBean.setBoard_ref(rs.getInt("board_ref"));
				boardBean.setBoard_hits(rs.getInt("board_hits"));
				boardBean.setBoard_notice(rs.getString("board_notice"));
			}
		}catch(Exception e) {
			System.out.println("selectBoard 에러 : " + e);
		}finally {
			close(rs);
			close(pstmt);
		}
		
		return boardBean;
	}

	
	//게시글 개수
	public int selectBoardListCount(String board_type, String id, int pro_num) {
		// TODO Auto-generated method stub
		int listCount = 0;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "select count(*) from board where board_type=?";
		
		if(!id.equals("")) {
			sql += " and board_writer = '" + id + "'";
		}
		if(pro_num != 0) {
			sql += " and pro_num = " + pro_num;
		}
		
		try {
			//전체 리뷰 구하기.
			pstmt = con.prepareStatement(sql);
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

	//게시글 목록 리스트로 불러오기
	public ArrayList<BoardBean> selectBoardList(String board_type, String id, int pro_num, int page, int limit) {
		// TODO Auto-generated method stub
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		ArrayList<BoardBean> boardList = new ArrayList<>();
		BoardBean boardBean = null;
		int startrow = (page-1)*limit;	//읽기 시작할 row 번호
		String sql = "select * from board where board_type=?";
		
		if(!id.equals("")) {
			sql += " and board_writer = '" + id + "'";
		}
		if(pro_num != 0) {
			sql += " and pro_num = " + pro_num;
		}
		sql += " order by board_notice='Y' desc, board_num desc limit ?,?";
		
		try {
			pstmt = con.prepareStatement(sql);
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
				boardBean.setBoard_ref(rs.getInt("board_ref"));
				boardBean.setBoard_hits(rs.getInt("board_hits"));
				boardBean.setBoard_notice(rs.getString("board_notice"));
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
			String board_step, String board_photo, String board_notice) {
		// TODO Auto-generated method stub
		int listCount = 0;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "select count(*) from board ";
		
		int cnt = 0;
		
		if(!board_date.equals("all") || !board_type.equals("all") || !search_text.equals("") || !board_step.equals("all")
				|| !board_photo.equals("all") || !board_notice.equals("all")) {
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
		if(!board_notice.equals("all")) {
			if(cnt > 0) {
				sql += " and";
			}
			sql+= " board_notice = '" + board_notice + "'";
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
			String search_text, String board_step, String board_photo, String board_notice, int page, int limit) {
		// TODO Auto-generated method stub
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		ArrayList<BoardBean> boardList = new ArrayList<>();
		BoardBean boardBean = null;
		int startrow = (page-1)*limit;	//읽기 시작할 row 번호
		
		String sql = "select * from board ";
		
		int cnt = 0;
		
		if(!board_date.equals("all") || !board_type.equals("all") || !search_text.equals("") || !board_step.equals("all")
				|| !board_photo.equals("all") || !board_notice.equals("all")) {
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
		if(!board_notice.equals("all")) {
			if(cnt > 0) {
				sql += " and";
			}
			sql+= " board_notice = '" + board_notice + "'";
			cnt++;
		}
		
		sql += " order by board_ref desc limit ?, ?";
		
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
				boardBean.setBoard_ref(rs.getInt("board_ref"));
				boardBean.setBoard_hits(rs.getInt("board_hits"));
				boardBean.setBoard_notice(rs.getString("board_notice"));
				
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


	//게시글 답글 등록 완료되면 board_step을 'Y'로 바꿔준다.
	public boolean updateBoardStep(int board_num) {
		// TODO Auto-generated method stub
		PreparedStatement pstmt = null;
		int updateCount = 0;
		boolean isUpdateSuccess = false;
		
		String sql = "update board set board_step='Y' where board_num=?";
		
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, board_num);
			updateCount = pstmt.executeUpdate();
			
			if(updateCount > 0) {
				isUpdateSuccess = true;
			}
		}catch(SQLException e) {
			System.out.println("게시글 답변 여부 수정 에러 " + e);
		}finally {
			close(pstmt);
		}
		return isUpdateSuccess;
	}

	//조회수 +1
	public boolean updateHits(int board_num) {
		// TODO Auto-generated method stub
		PreparedStatement pstmt = null;
		int updateCount = 0;
		boolean isUpdateSuccess = false;
		
		String sql = "update board set board_hits=board_hits+1 where board_num=?";
		
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, board_num);
			updateCount = pstmt.executeUpdate();
			
			if(updateCount > 0) {
				isUpdateSuccess = true;
			}
		}catch(SQLException e) {
			System.out.println("조회수+1 수정 에러 " + e);
		}finally {
			close(pstmt);
		}
		return isUpdateSuccess;
	}

	
	//공지사항 Y/N
	public boolean updateNoticeBoard(int board_num, String board_notice) {
		// TODO Auto-generated method stub
		PreparedStatement pstmt = null;
		int updateCount = 0;
		boolean isUpdateSuccess = false;
		
		String sql = "update board set board_notice=? where board_num=?";
		
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, board_notice);
			pstmt.setInt(2, board_num);
			updateCount = pstmt.executeUpdate();
			
			if(updateCount > 0) {
				isUpdateSuccess = true;
			}
		}catch(SQLException e) {
			System.out.println("공지사항 Y/N 여부 수정 에러 " + e);
		}finally {
			close(pstmt);
		}
		return isUpdateSuccess;
	}
	
}
