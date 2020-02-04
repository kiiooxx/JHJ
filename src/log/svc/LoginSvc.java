package log.svc;

import static db.JdbcUtil.*;

import java.sql.Connection;

import log.dao.LoginDAO;
import vo.Member;

public class LoginSvc {

	public Member memberLogin(String id) {
		// TODO Auto-generated method stub
		LoginDAO loginDAO = LoginDAO.getInstance();
		Connection con = getConnection();
		loginDAO.setConnection(con);
		Member loginMember = loginDAO.selectMember(id);
		close(con);
		return loginMember;
		
	}


	
}