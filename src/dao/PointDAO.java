package dao;

import static db.JdbcUtil.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import vo.Point;


public class PointDAO {
	Connection con;
	private static PointDAO pointDAO;
	
	private PointDAO() {
		
	}
	public void setConnection(Connection con) {
		this.con = con;
	}
	public static PointDAO getInstance() {
		if(pointDAO == null) {
			pointDAO = new PointDAO();
		}
		return pointDAO;
	}

	
	//회원가입 적립금
	//point_man 테이블에서 회원가입 시 적립금 설정 여부 확인, if p_newmem >0 이면 point테이블에 값 넣기
	public int checkNewmemOption (String user_id) {
		int insertCount = 0;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		int newMem = 0;
		String point_reason = "회원가입 축하";
		String sql1 = "SELECT p_newmem FROM point_man WHERE p_seq=1";
		String sql2 = "INSERT INTO point (point_date, point_price, point_final, increase, user_id, point_reason) "
				+ "VALUES (NOW(), ?, ?,'+', ?, ?)";
			
		try {
				pstmt = con.prepareStatement(sql1);
				rs = pstmt.executeQuery();
				
				if(rs.next()) {
					newMem = rs.getInt("p_newmem");				
					if(newMem == 0) {
						point_reason = "";
					}
					pstmt = con.prepareStatement(sql2);
					pstmt.setInt(1,newMem);
					pstmt.setInt(2, newMem);
					pstmt.setString(3, user_id);
					pstmt.setString(4, point_reason);
					insertCount = pstmt.executeUpdate();
					
				}
				
			}catch(Exception e) {
				e.printStackTrace();
				System.out.println("PointDAO - checkNewmemOptionError");
				System.out.println("sql:"+sql2);
			}finally {
				close(rs);
				close(pstmt);
			}
		
			return insertCount;
	}
	
	//적립금 사용해서 주문했을 때 point테이블에서 적립금 빼기
	public int orderUsePoint(String sel_num) {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		int insertCount = 0;
		int usePoint = 0;
		int beforeFinal = 0;//적립금 빼기 전
		int afterFinal = 0;//적립금 빼고난 후
		String user_id = "";
		String sql1 = "SELECT user_id, point_use FROM order_page WHERE sel_num="+sel_num;
		String sql2 = "SELECT point_final FROM point WHERE user_id=? ORDER BY point_date DESC LIMIT 1";
		String sql3 = "INSERT INTO point (point_date, point_price, point_final, increase, user_id, point_reason)"
				+" VALUES (NOW(), ?, ?, '-', ?, '주문 시 사용')";
		try {
			pstmt = con.prepareStatement(sql1);
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				usePoint = rs.getInt("point_use");
				user_id = rs.getString("user_id");
				if(usePoint > 0) {
					pstmt = con.prepareStatement(sql2);
					pstmt.setString(1, user_id);
					rs = pstmt.executeQuery();
					if(rs.next()) {
						beforeFinal = rs.getInt("point_final");
						afterFinal = beforeFinal-usePoint;
						
						pstmt = con.prepareStatement(sql3);
						pstmt.setInt(1, usePoint);
						pstmt.setInt(2, afterFinal);
						pstmt.setString(3, user_id);
						insertCount = pstmt.executeUpdate();
						System.out.println("orderUsePoint insertCount in : " + insertCount);
					}
					
				}
			}
		}catch(Exception e) {
			e.printStackTrace();
			System.out.println("PointDAO - orderUsePoint error: "+e);
			System.out.println("sql1:"+sql1);
			System.out.println("sql2:"+sql2);
			System.out.println("sql3:"+sql3);
		}finally {
			close(rs);
			close(pstmt);
		}
		System.out.println("orderUsePoint insertCount out : " + insertCount);
		return insertCount;
	}
	
	
	//구매확정 시 적립금 추가[옵션: 적립금 미사용 주문 , 적립금 사용 주문(실결제액/적립금 제외/적립안함)]
	public int orderPoint(String sel_num, String user_id) {
		int insertCount = 0;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String standard = "";//적립금 사용해서 결제 시 적립기준
		float rate = 0;//적립율
		int deliPrice = 0;//배송비
		int pointUse = 0;//사용한 적립금
		int finalPrice = 0;//최종결제액(배송비 포함, 사용적립금 미포함)
		int applyRate = 0; //적립율 적용한 적립예정액
		int pointFinal = 0;//적립금을 더해줄 가장 최근 적립금
		String sql1 = "SELECT p_stand, p_rate FROM point_man WHERE p_seq";
		String sql2 = "SELECT deli_price, point_use, final_price FROM order_page WHERE sel_num=?";
		String sql3 = "SELECT point_final FROM point WHERE user_id=? AND point_date=(SELECT MAX(point_date) FROM point)";
		String sql4 = "INSERT INTO point (point_date, point_price, point_final, increase, user_id, point_reason) "
				+ "VALUES (NOW(), ?, ?, '+', ?, '구매확정 적립')";
		
		try {
			pstmt = con.prepareStatement(sql1);
			rs = pstmt.executeQuery(); 
			
			if(rs.next()) {
				standard = rs.getString("p_stand");//적립기준
				rate = (float) (rs.getFloat("p_rate")*0.01);//적립율
				
				pstmt = con.prepareStatement(sql2);
				pstmt.setString(1, sel_num);
				rs = pstmt.executeQuery();
				
				if(rs.next()) {
					deliPrice = rs.getInt("deli_price");
					pointUse = rs.getInt("point_use");
					finalPrice = rs.getInt("final_price");
				
					if(standard.equals("as") || pointUse == 0) {//사용적립금 포함해서 적립율 계산 || 적립금 미사용 주문 시
						applyRate = (int) ((finalPrice - deliPrice + pointUse)*rate);
					}else if(standard.equals("act")) {//사용적립금 제외하고 적립율 계산
						applyRate = (int) ((finalPrice - deliPrice)*rate);
					}
					
					pstmt = con.prepareStatement(sql3);
					pstmt.setString(1, user_id);
					rs = pstmt.executeQuery();
					
					if(rs.next()) {
						pointFinal += rs.getInt("point_final")+applyRate;
						
						pstmt = con.prepareStatement(sql4);
						pstmt.setInt(1, applyRate);
						pstmt.setInt(2, pointFinal);
						pstmt.setString(3,user_id);
						insertCount = pstmt.executeUpdate();
						
					}else {//회원가입적립금 설정 되어있지 않은 경우, 가입 후 첫 적립 시 point테이블에 값이 없다
						pstmt = con.prepareStatement(sql4);
						pstmt.setInt(1, applyRate);
						pstmt.setInt(2, applyRate);
						pstmt.setString(3, user_id);
						insertCount = pstmt.executeUpdate();
					}
				}
			}
	
		}catch(Exception e) {
			e.printStackTrace();
			System.out.println("PointDAO - orderPoint error : " + e);
			System.out.println("sql1: " + sql1);
			System.out.println("sql2: " + sql2);
			System.out.println("sql3: " + sql3);
			System.out.println("sql4: " + sql4);
		}finally {
			close(rs);
			close(pstmt);
		}
		return insertCount;
		
	}
	
	
	// 리뷰 적립금
	// 리뷰작성 시 point_man 테이블에서 리뷰적립금 설정 여부 확인, if p_review >0 이면 point테이블에 값 넣기
	public int checkReviewOption(String user_id) {
		int insertCount = 0;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		int pReview = 0;//리뷰 적립금
		int afterPoint = 0; //가장 최근 적립금 + 리뷰 적립금
		String sql1 = "SELECT p_review FROM point_man WHERE p_seq=1";
		String sql2 = "SELECT point_final FROM point WHERE user_id=? AND point_date=(SELECT MAX(point_date) FROM point)";
		String sql3 = "INSERT INTO point (point_date, point_price, point_final, increase, user_id, point_reason) "
				+ "VALUES (NOW(), ?, ?,'+', ?, '리뷰적립')";
			
		try {
				pstmt = con.prepareStatement(sql1);
				rs = pstmt.executeQuery();
				
				if(rs.next()) {
					pReview = rs.getInt("p_review");				
					if(pReview > 0) {
						pstmt = con.prepareStatement(sql2);
						pstmt.setString(1, user_id);
						rs = pstmt.executeQuery();
						
						if(rs.next()) {
							afterPoint = rs.getInt("point_final") + pReview;
							pstmt = con.prepareStatement(sql3);
							pstmt.setInt(1,pReview);
							pstmt.setInt(2, afterPoint);
							pstmt.setString(3, user_id);
							insertCount = pstmt.executeUpdate();
							System.out.println("pointDAO : insertCount in : " + insertCount);
						}
						
					}
				}
				
			}catch(Exception e) {
				e.printStackTrace();
				System.out.println("PointDAO - checkNewmemOptionError : " + e);
				System.out.println("sql:"+sql2);
			}finally {
				close(rs);
				close(pstmt);
			}
		System.out.println("pointDAO : insertCount out : " + insertCount);
			return insertCount;
	}
	
	//회원 적립금 조회
	public Point selectMemberPoint(String user_id) {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Point memberPoint = null;
		String sql = "SELECT * FROM point WHERE user_id=? order by point_date desc limit 1";
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, user_id);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				memberPoint = new Point();
				memberPoint.setPoint_num(rs.getInt("point_num"));
				memberPoint.setPoint_date(rs.getString("point_date"));
				memberPoint.setPoint_price(rs.getInt("point_price"));
				memberPoint.setPoint_final(rs.getInt("point_final"));
				memberPoint.setIncrease(rs.getString("increase").charAt(0));
				memberPoint.setUser_id(user_id);
				memberPoint.setPoint_reason(rs.getString("point_reason"));
			}
		}catch(Exception e) {
			e.printStackTrace();
			System.out.println("PointDAO - selectMemberPoint error");
		}finally {
			close(rs);
			close(pstmt);
		}
		return memberPoint;
	}
	


	//회원적립금 내역 확인
	public ArrayList<Point> selectMyPoint(String user_id, int page, int limit) {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		ArrayList<Point> pointList = new ArrayList<>();
		Point point = null;
		
		int startrow = (page-1)*limit;	//읽기 시작할 row 번호
		
		try {
			pstmt = con.prepareStatement("select * from point where user_id=? order by point_date desc limit ?,?");
			pstmt.setString(1, user_id);
			pstmt.setInt(2, startrow);
			pstmt.setInt(3, limit);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				point = new Point();
				point.setUser_id(user_id);
				point.setPoint_num(rs.getInt("point_num"));
				point.setPoint_final(rs.getInt("point_final"));
				point.setPoint_date(rs.getString("point_date"));
				point.setPoint_price(rs.getInt("point_price"));
				point.setPoint_reason(rs.getString("point_reason"));
				point.setIncrease(rs.getString("increase").charAt(0));
				
				pointList.add(point);
			}
			
		}catch(Exception e) {
			e.printStackTrace();
			System.out.println("MemberDAO - selectMyPoint error");
		}finally {
			close(rs);
			close(pstmt);
		}
		return pointList;
	}
	



	public int selectPointListCount(String user_id) {
		// TODO Auto-generated method stub
		int listCount = 0;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "select count(*) from point where user_id=?";
		
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, user_id);
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				listCount = rs.getInt(1);
			}
		}catch(Exception e) {
			System.out.println("getPointListCount 에러 : " +e);
		}finally {
			close(rs);
			close(pstmt);
		}
		return listCount;
	}
	
}
	
	
