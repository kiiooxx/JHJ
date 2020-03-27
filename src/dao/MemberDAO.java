package dao;

import static db.JdbcUtil.close;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import javax.sql.DataSource;

import vo.Member;
import vo.Order;

public class MemberDAO {

	DataSource ds;
	Connection con;
	private static MemberDAO memberDAO;

	public static MemberDAO getInstance() {
		if (memberDAO == null) {
			memberDAO = new MemberDAO();
		}
		return memberDAO;
	}

	public void setConnection(Connection con) {
		this.con = con;

	}

	// 회원 id중복확인
	public Member idCheck(String id) {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Member member = null;

		try {
			pstmt = con.prepareStatement("SELECT * FROM member WHERE user_id=?");
			pstmt.setString(1, id);
			rs = pstmt.executeQuery();

			if (rs.next()) {
				member = new Member();
				member.setUser_id(id);
				member.setUser_pw(rs.getString("user_pw"));
				member.setUser_name(rs.getString("user_name"));
				member.setSex(rs.getString("sex"));
				member.setTel(rs.getString("tel"));

			}

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("MemberDAO -idCheck error");
		} finally {
			close(rs);
			close(pstmt);
		}

		return member;

	}

	// 회원가입
	public int insertMember(Member member) {
		PreparedStatement pstmt = null;
		int insertCount = 0;

		try {

			pstmt = con.prepareStatement("INSERT INTO member VALUES (?,?,?,?,?,?,?,?,?,?,NOW(),'N', 'N')");
			pstmt.setString(1, member.getUser_id());
			pstmt.setString(2, member.getUser_pw());
			pstmt.setString(3, member.getUser_name());
			pstmt.setString(4, member.getSex());
			pstmt.setString(5, member.getTel());
			pstmt.setString(6, member.getPostcode());
			pstmt.setString(7, member.getAddr1());
			pstmt.setString(8, member.getAddr2());
			pstmt.setString(9, member.getEmail());
			pstmt.setString(10, member.getBirth());
			insertCount = pstmt.executeUpdate();

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("join insert member error");
		} finally {
			close(pstmt);
		}

		return insertCount;
	}

	public int updateMember(Member member) {
		// TODO Auto-generated method stub
		int updateCount = 0;
		PreparedStatement pstmt = null; System.out.println(member.getUser_id());
		String sql = "UPDATE member SET user_pw=?, user_name=?, sex=?, tel=?, postcode=?, addr1=?, addr2=?, email=? ,birth=? where user_id=?";
		try {
			
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, member.getUser_pw());
			pstmt.setString(2, member.getUser_name());
			pstmt.setString(3, member.getSex());
			pstmt.setString(4, member.getTel());
			pstmt.setString(5, member.getPostcode());
			pstmt.setString(6, member.getAddr1());
			pstmt.setString(7, member.getAddr2());
			pstmt.setString(8, member.getEmail());
			pstmt.setString(9, member.getBirth());
			pstmt.setString(10, member.getUser_id());
			updateCount = pstmt.executeUpdate();
		} catch (Exception ex) {
			System.out.println("memberModify 에러 : " + ex);
		} finally {
			close(pstmt);
		}
		System.out.println("DAO"+updateCount);
		return updateCount;
	}

	public int memberDelete(String delete_id) {
		PreparedStatement pstmt = null;
		Member member = null;
		boolean isMemberDel = false;
	
		int result = 0;
		try { System.out.println("회원탈퇴 :"+delete_id);
			pstmt = con.prepareStatement("DELETE FROM member WHERE user_id=?");
			pstmt.setString(1, delete_id);
			result = pstmt.executeUpdate();
			
			
		}catch(Exception ex) {
			System.out.println("idCheckMember 에러 : " + ex);
		}finally {
			close(pstmt);
		}
		return result;
	}

	public ArrayList<Order> selectOrderList(String id, int page, int limit) {
		PreparedStatement pstmt = null;
		Order order= null;
		ResultSet  rs= null;
		ArrayList<Order> orderList = new ArrayList<>();
		
		String sql= "select * From order_page as a " + 
				"left join order_det as b on a.sel_num=b.sel_num " + 
				"left join pro_det as c on b.pro_det_num=c.pro_det_num " + 
				"left join pro_info as d on c.pro_num=d.pro_num " + 
				"where user_id = ? order by a.sel_date desc limit ?,?";
		// TODO Auto-generated method stub
		
		int startrow = (page-1)*limit;	//읽기 시작할 row 번호
		
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, id);
			pstmt.setInt(2, startrow);
			pstmt.setInt(3, limit);
			rs = pstmt.executeQuery();
			
			while(rs.next()) { 
				order = new Order();

				order.setPro_num(rs.getInt("pro_num"));
				order.setSel_num(rs.getString("sel_num"));
				order.setPro_name(rs.getString("pro_name"));
				order.setPro_photo(rs.getString("pro_photo"));
				order.setCancel_req(rs.getString("cancel_req").charAt(0));
				order.setPro_qnt(rs.getInt("pro_qnt"));
				order.setFinal_price(rs.getInt("final_price"));
				order.setPro_price(rs.getInt("pro_price"));
				order.setSel_status(rs.getString("sel_status"));
				order.setSel_date(rs.getString("sel_date"));
				order.setPro_size(rs.getString("pro_size"));
				order.setColor(rs.getString("color"));
				     
				orderList.add(order);
			}
			
		}catch(Exception ex) {
			System.out.println("Order 에러 : " +ex);
		}finally {
			close(rs);
			close(pstmt);
		}
		
		return orderList;
	}

	//id에 해당하는 주문내역 개수
	public int selectOrderListCount(String id) {
		// TODO Auto-generated method stub
		int listCount = 0;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "select count(*) from order_page where user_id=?";
		
		try {
			//전체 리뷰 구하기.
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, id);
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				listCount = rs.getInt(1);
			}
		}catch(Exception e) {
			System.out.println("getOrderListCount 에러 : " + e);
		}finally {
			close(rs);
			close(pstmt);
		}
		return listCount;
	}
	
}