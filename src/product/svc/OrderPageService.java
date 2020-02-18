package product.svc;

import java.sql.Connection;

import dao.AdminDAO;
import vo.Member;
import static db.JdbcUtil.*;

public class OrderPageService {

	public Member getOrderUserInfo(String user_id) {
		
		Member member = null;
		Connection con = getConnection();
		AdminDAO adminDAO = AdminDAO.getInstance();
		adminDAO.setConnection(con);
		
		member = adminDAO.selectMember(user_id);
		
		close(con);
		
		return member;
	}

}
