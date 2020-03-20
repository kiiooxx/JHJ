package admin.svc;

import static db.JdbcUtil.close;
import static db.JdbcUtil.commit;
import static db.JdbcUtil.getConnection;
import static db.JdbcUtil.rollback;

import java.sql.Connection;

import dao.AdminDAO;

public class MailManagementService {
	public boolean updateMailOption(int newMem, int quitMem, int newOrder, int checkPaid,
			int sendPro, int deliIng, int deliFin, int confirmOrder, int accCancel, int qnaRe) {

		boolean isSuccess = false;
		int count = 0;
		Connection con = getConnection();
		AdminDAO adminDAO = AdminDAO.getInstance();
		adminDAO.setConnection(con);
		count = adminDAO.updateMailOption(newMem, quitMem, newOrder, checkPaid, sendPro, deliIng, deliFin, 
				confirmOrder, accCancel, qnaRe);
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
