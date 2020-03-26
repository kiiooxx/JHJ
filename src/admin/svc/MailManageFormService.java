package admin.svc;

import java.sql.Connection;
import static db.JdbcUtil.*;
import dao.AdminDAO;
import vo.MailOption;

public class MailManageFormService {

	public MailOption getMailOption(int seq) {
		
		MailOption mailOption = null;
		Connection con = getConnection();
		AdminDAO adminDAO = AdminDAO.getInstance();
		adminDAO.setConnection(con);
		mailOption = adminDAO.viewMailOption(seq);
		
		return mailOption;
	}

}
