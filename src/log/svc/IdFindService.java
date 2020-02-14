package log.svc;

import static db.JdbcUtil.*;

import java.sql.Connection;

import log.dao.LoginDAO;
import vo.Member;

public class IdFindService {

	public Member memberSelect(String name, String phone) {
		// TODO Auto-generated method stub
		LoginDAO loginDAO = LoginDAO.getInstance();
		Connection con = getConnection();
		loginDAO.setConnection(con);
		Member member = loginDAO.memberSelect(name, phone);
		
		close(con);
		return member;
	}

	
	public Member memberSelect1(String name2, String email) {
		// TODO Auto-generated method stub
		LoginDAO loginDAO = LoginDAO.getInstance();
		Connection con = getConnection();
		loginDAO.setConnection(con);
		Member member1 = loginDAO.memberSelect1(name2,email);
		close(con);
		return member1;
	}
	
}
