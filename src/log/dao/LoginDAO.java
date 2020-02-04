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
				member.setUSER_ID(id);
				member.setUSER_PW(rs.getString("USER_PW"));
				member.setUSER_NAME(rs.getString("USER_NAME"));
				member.setBIRTH(rs.getString("BIRTH"));
				member.setJOINDATE(rs.getString("JOINDATE"));
				member.setSEX(rs.getString("SEX"));
				member.setEMAIL(rs.getString("EMAIL"));
				member.setGRADE(rs.getString("GRADE"));
				member.setADDR1(rs.getString("ADDR1"));
				member.setADDR2(rs.getString("ADDR2"));
				
			
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