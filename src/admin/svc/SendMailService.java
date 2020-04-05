package admin.svc;

import java.sql.Connection;
import static db.JdbcUtil.*;

import dao.AdminDAO;
import vo.MailOption;

public class SendMailService {

	//관리자 - 자동메일관리 페이지 메일옵션 설정여부 조회
	public MailOption checkOptionValue() {
		MailOption mailOption = null;
		Connection con = getConnection();
		AdminDAO adminDAO = AdminDAO.getInstance();
		adminDAO.setConnection(con);
		mailOption = adminDAO.viewMailOption(1);
		close(con);
		
		return mailOption;
	}

	//자동메일 옵션 사용여부 확인 및 메일전송
	public MailOption getMailForm(String col, String col_title, String col_content) {
		MailOption mailOption = null;
		Connection con = getConnection();
		AdminDAO adminDAO = AdminDAO.getInstance();
		adminDAO.setConnection(con);
		mailOption = adminDAO.selectMailForm(col, col_title, col_content);
		close(con);
		
		return mailOption;
	}

}
