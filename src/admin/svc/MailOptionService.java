package admin.svc;

import java.sql.Connection;
import java.util.ArrayList;
import static db.JdbcUtil.*;

import dao.AdminDAO;
import vo.MailOption;

public class MailOptionService {

	public ArrayList<MailOption> updateMailOption(char newMem, char quitMem, char newOrder, char checkPaid,
			char sendPro, char deliIng, char deliFin, char confirmOrder, char accCancel) {

		ArrayList<MailOption> mailList = null;
		Connection con = getConnection();
		AdminDAO adminDAO = AdminDAO.getInstance();
		adminDAO.setConnection(con);
		mailList = adminDAO.updateMailOption(newMem, quitMem, newOrder, checkPaid, sendPro, deliIng, deliFin, 
				confirmOrder, accCancel);

		close(con);
		
		return mailList;
	}

}
