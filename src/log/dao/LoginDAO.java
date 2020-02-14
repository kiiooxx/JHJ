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
				member.setTel(rs.getString("TEL"));
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

	

	public Member memberSelect(String name, String phone) {
		// TODO Auto-generated method stub
		Member member = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			pstmt = con.prepareStatement("select * from member where user_name=? and tel=?");
			pstmt.setString(1, name);
			pstmt.setString(2, phone);
			rs = pstmt.executeQuery();
		//tel은 int라서 그런가?,,,	
			if(rs.next()) {
				member = new Member();
				member.setUser_id(rs.getString("USER_ID"));
				member.setUser_pw(rs.getString("USER_PW"));
				member.setUser_name(rs.getString("USER_NAME"));
				member.setTel(rs.getString("TEL"));
				member.setBirth(rs.getString("BIRTH"));
				member.setJoindate(rs.getString("JOINDATE"));
				member.setSex(rs.getString("SEX"));
				member.setEmail(rs.getString("EMAIL"));
				member.setGrade(rs.getString("GRADE"));
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
	public Member memberSelect1(String name2, String email) {
		// TODO Auto-generated method stub
		Member member1 = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		System.out.println("1" + name2);
		System.out.println("2" + email);
		try {
			pstmt = con.prepareStatement("select * from member where user_name=? and email=?");
			pstmt.setString(1, name2);
			pstmt.setString(2, email);
			rs = pstmt.executeQuery();
		//tel은 int라서 그런가?,,,	
			if(rs.next()) {
				member1 = new Member();
				member1.setUser_id(rs.getString("USER_ID"));
				member1.setUser_pw(rs.getString("USER_PW"));
				member1.setUser_name(rs.getString("USER_NAME"));
				member1.setTel(rs.getString("TEL"));
				member1.setBirth(rs.getString("BIRTH"));
				member1.setJoindate(rs.getString("JOINDATE"));
				member1.setSex(rs.getString("SEX"));
				member1.setEmail(rs.getString("EMAIL"));
				member1.setGrade(rs.getString("GRADE"));
				member1.setAddr1(rs.getString("ADDR1"));
				member1.setAddr2(rs.getString("ADDR2"));
				
				System.out.println("sㅇㅇ" + member1.getUser_id());
			
			}
		}catch(Exception ex) {
			System.out.println("select 에러 : " + ex);
		}finally {
			close(rs);
			close(pstmt);
		}
		return member1;
	}

	public Member selectMember3(String id, String reciver) {
		// TODO Auto-generated method stub
		Member member3 = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		System.out.println("1" + id);
		System.out.println("2" + reciver);
		try {
			pstmt = con.prepareStatement("select * from member where user_id=? and email=?");
			pstmt.setString(1, id);
			pstmt.setString(2, reciver);   
			rs = pstmt.executeQuery();
	 
			if(rs.next()) {
				member3 = new Member();
				member3.setUser_id(rs.getString("USER_ID"));
				member3.setUser_pw(rs.getString("USER_PW"));
				member3.setUser_name(rs.getString("USER_NAME"));
				member3.setTel(rs.getString("TEL"));
				member3.setBirth(rs.getString("BIRTH"));
				member3.setJoindate(rs.getString("JOINDATE"));
				member3.setSex(rs.getString("SEX"));
				member3.setEmail(rs.getString("EMAIL"));
				member3.setGrade(rs.getString("GRADE"));
				member3.setAddr1(rs.getString("ADDR1"));
				member3.setAddr2(rs.getString("ADDR2"));
								
			}
		}catch(Exception ex) {
			System.out.println("select 에러 : " + ex);
		}finally {
			close(rs);
			close(pstmt);
		}
		return member3;
	}
	
	public boolean updateTempPW(String id, String uuid) {
		// TODO Auto-generated method stub
	
		Member member = null;
		PreparedStatement pstmt = null;
		System.out.println("1" + id);
		System.out.println("2" + uuid);
		int updateCount = 0;
		boolean isRight = false;
		
		try {
			pstmt = con.prepareStatement("update member set user_pw = ? where user_id= ?");
			pstmt.setString(1, uuid);
			pstmt.setString(2, id);   
			updateCount = pstmt.executeUpdate();
			
			if(updateCount > 0) {
				isRight = true;
			}
			
		}catch(Exception ex) {
			System.out.println("select 에러 : " + ex);
		}finally {
			close(pstmt);
		}
		return isRight;
	}
}