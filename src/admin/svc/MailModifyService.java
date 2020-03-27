package admin.svc;

import java.sql.Connection;
import static db.JdbcUtil.*;

import dao.AdminDAO;

public class MailModifyService {

	public boolean modifyMailForm(String col_title, String title, String col_content, String content) {
		
		boolean isSuccess = false;
		Connection con = getConnection();
		AdminDAO adminDAO = AdminDAO.getInstance();
		adminDAO.setConnection(con);
		int updateCount = adminDAO.saveMailForm(col_title, col_content, title, content);
		
		if(updateCount > 0) {
			commit(con);
			isSuccess = true;
		}else {
			rollback(con);
		}
		close(con);
		
		return isSuccess;
	}
}
