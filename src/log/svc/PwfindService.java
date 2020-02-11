package log.svc;

import static db.JdbcUtil.*;


import java.sql.Connection;

import log.action.pwfindAction;
import log.dao.LoginDAO;
import vo.Member;


public class PwfindService {
	


	public Member selectMember3(String id, String email) {
		// TODO Auto-generated method stub
		LoginDAO loginDAO = LoginDAO.getInstance();
		Connection con = getConnection();
		loginDAO.setConnection(con);
		Member member3 = loginDAO.selectMember3(id, email);
		
		close(con);
		return member3;
	
	}

	public boolean updateTempPW(String id, String uuid) {
		// TODO Auto-generated method stub
		
		
		LoginDAO loginDAO = LoginDAO.getInstance();
		Connection con = getConnection();
		loginDAO.setConnection(con);
		boolean isRight = false;
		isRight = loginDAO.updateTempPW(id, uuid);
		
		if(!isRight) {
			
			rollback(con);
		}else {
			commit(con);
		}
		
		close(con);
		return isRight;
	}
	
	
	

}
