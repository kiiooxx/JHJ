package member.svc;

import static db.JdbcUtil.*;

import java.sql.Connection;

import dao.MemberDAO;
import vo.Member;

//아이디 중복확인
public class IdCheckSVC {
	
	public Member memberId(String id) {
	
		Member member = null;
		Connection con = getConnection();
		MemberDAO memberDAO = MemberDAO.getInstance();
		memberDAO.setConnection(con);
		
		member = memberDAO.idCheck(id);
		
		close(con);
		
		return member;

	}
	
	
}
