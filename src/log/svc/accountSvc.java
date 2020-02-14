package log.svc;

import static db.JdbcUtil.close;
import static db.JdbcUtil.getConnection;

import java.sql.Connection;

import log.dao.LoginDAO;
import vo.Member;

public class accountSvc {

	public Member selectmemberinfo(String id) {

		LoginDAO loginDAO = LoginDAO.getInstance();
		
		Connection con = getConnection();
		loginDAO.setConnection(con);
		Member member = loginDAO.selectmemberinfo(id);

		close(con);
		return member;

	}

}
