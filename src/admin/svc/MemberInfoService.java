package admin.svc;

import java.sql.Connection;

import dao.AdminDAO;

import static db.JdbcUtil.*;

import vo.Member;

public class MemberInfoService {

	public Member getMemberInfo(String user_id) {
		
		Member member = null;
		Connection con = getConnection();
		AdminDAO adminDAO = AdminDAO.getInstance();
		adminDAO.setConnection(con);
		
		member = adminDAO.selectMember(user_id);
		
		close(con);
		
		return member;
	}

}
