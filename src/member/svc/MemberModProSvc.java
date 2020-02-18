package member.svc;

import static db.JdbcUtil.*;
import java.sql.Connection;

import dao.MemberDAO;
import vo.Member;

public class MemberModProSvc {

	public boolean modifyMember(Member member) throws Exception{
		// TODO Auto-generated method stub
		boolean isModifySuccess = false;
		Connection con = getConnection();
		MemberDAO memberDAO = MemberDAO.getInstance();
		memberDAO.setConnection(con);
		int updateCount = memberDAO.updateMember(member);
		
		if(updateCount > 0) {
			commit(con);
			isModifySuccess = true;
		}else {
			rollback(con);
		}
		close(con);
		return isModifySuccess;
	}

}
