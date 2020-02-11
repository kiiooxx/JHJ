package log.dao;

import static db.JdbcUtil.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import javax.sql.DataSource;


import vo.Member;


public class LoginDAO {
	

	DataSource ds;
	Connection con;
	private static LoginDAO loginDAO;
	
	private LoginDAO() {
	}
	
	public static LoginDAO getInstance() {
		if(loginDAO == null) {
			loginDAO = new LoginDAO();
		}
		return loginDAO;
	}
	
	public void setConnection(Connection con) {
		this.con = con;
	}
	
	//회원 정보 불러오기
	public Member selectMember(String id) {
		Member member = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			pstmt = con.prepareStatement("select * from member where USER_ID=?");
			pstmt.setString(1, id);
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				member = new Member();
				member.setUser_id(id);
				member.setUser_pw(rs.getString("USER_PW"));
				member.setUser_name(rs.getString("USER_NAME"));
				member.setBirth(rs.getString("BIRTH"));
				member.setJoindate(rs.getString("JOINDATE"));
				member.setSex(rs.getString("SEX"));
				member.setEmail(rs.getString("EMAIL"));
				member.setGrade(rs.getString("GRADE").charAt(0));
				member.setAddr1(rs.getString("ADDR1"));
				member.setAddr2(rs.getString("ADDR2"));
				
			
			}
		}catch(Exception ex) {
			System.out.println("select 에러 : " + ex);
		}finally {
			close(rs);
			close(pstmt);
		}
		return member;
	}
	
}