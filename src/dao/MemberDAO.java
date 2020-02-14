package dao;

import static db.JdbcUtil.close;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import javax.sql.DataSource;

import vo.Member;

public class MemberDAO {
	
	DataSource ds;
	Connection con;
	private static MemberDAO memberDAO;

	public static MemberDAO getInstance() {
		if(memberDAO == null) {
			memberDAO = new MemberDAO();
		}
		return memberDAO;
	}

	public void setConnection(Connection con) {
		this.con = con;
		
	}
	
	
	
	
	//회원 id중복확인
			public Member idCheck(String id) {
				PreparedStatement pstmt = null;
				ResultSet rs = null;
				Member member = null;
				
				try {
					pstmt = con.prepareStatement("SELECT * FROM member WHERE user_id=?");
					pstmt.setString(1, id);
					rs = pstmt.executeQuery();
					
					if(rs.next()) {
						member = new Member();
						member.setUser_id(id);
						member.setUser_pw(rs.getString("user_pw"));
						member.setUser_name(rs.getString("user_name"));
						member.setSex(rs.getString("sex"));
						member.setTel(rs.getString("tel"));
						
					}
					
				}catch(Exception e) {
					e.printStackTrace();
					System.out.println("MemberDAO -idCheck error");
				}finally {
					close(rs);
					close(pstmt);
				}
				
				return member;
				
			}
	
			
	//회원가입
	public int insertMember(Member member) {
		PreparedStatement pstmt = null;
		int insertCount = 0;
		
		try {
			
			pstmt = con.prepareStatement("INSERT INTO member VALUES (?,?,?,?,?,?,?,?,?,?,NOW(),'N')");
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
			
		}catch(Exception e) {
			e.printStackTrace();
			System.out.println("join insert member error");
		}finally {
			close(pstmt);
		}
		
		return insertCount;
	}
	
	

}
