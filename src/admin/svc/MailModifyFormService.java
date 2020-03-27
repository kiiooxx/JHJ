package admin.svc;

import java.sql.Connection;
import static db.JdbcUtil.*;
import dao.AdminDAO;
import vo.MailOption;

public class MailModifyFormService {

	public MailOption getMailForm(String col_title, String col_content) {

		MailOption mailOption = null;
		Connection con = getConnection();
		AdminDAO adminDAO = AdminDAO.getInstance();
		adminDAO.setConnection(con);
		mailOption = adminDAO.selectMailForm(col_title, col_content);
		close(con);
		return mailOption;
	}

	

}
