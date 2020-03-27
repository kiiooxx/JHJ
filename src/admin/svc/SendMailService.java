package admin.svc;

import java.sql.Connection;
import static db.JdbcUtil.*;

import dao.AdminDAO;
import vo.MailOption;

public class SendMailService {

	public MailOption checkOptionValue() {
		MailOption mailOption = null;
		Connection con = getConnection();
		AdminDAO adminDAO = AdminDAO.getInstance();
		adminDAO.setConnection(con);
		mailOption = adminDAO.viewMailOption(1);
		close(con);
		
		return mailOption;
	}
	
	
	//option 한개 항목만 가져오기
	public int oneOptionValue(String col) {
		int option = 0;
		Connection con = getConnection();
		AdminDAO adminDAO = AdminDAO.getInstance();
		adminDAO.setConnection(con);
		option = adminDAO.oneMailOption(col);
		close(con);
		
		return option;
	}

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
