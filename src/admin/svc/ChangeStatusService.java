package admin.svc;

import dao.AdminDAO;
import vo.Order;
import static db.JdbcUtil.*;

import java.sql.Connection;

public class ChangeStatusService {

	public boolean changeDeliStatus(String selNum, String deliStatus) {
		boolean isChangeSuccess = false;
		Connection con = getConnection();
		AdminDAO adminDAO = AdminDAO.getInstance();
		adminDAO.setConnection(con);
		int updateCount = adminDAO.updateDeliStatus(selNum, deliStatus);
		System.out.println("updateCount:"+updateCount);
		if(updateCount > 0) {
			commit(con);
			isChangeSuccess = true;
		}else {
			rollback(con);
		}
		
		close(con);
		
		return isChangeSuccess;
	}

}
