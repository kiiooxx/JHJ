package admin.svc;

import java.sql.Connection;

import dao.AdminDAO;

import static db.JdbcUtil.*;

public class AcceptCancelService {

	public boolean acceptCancel(String sel_num) {
		
		boolean isSuccess = false;
		int count = 0;
		Connection con = getConnection();
		AdminDAO adminDAO = AdminDAO.getInstance();
		adminDAO.setConnection(con);
		
		count = adminDAO.updateCancelReq(sel_num);
		
		if(count > 0) {
			commit(con);
			isSuccess = true;
		}else {
			rollback(con);
		}
		
		close(con);
		
		return isSuccess;
	}

}
