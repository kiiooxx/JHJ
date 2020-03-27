package admin.svc;

import static db.JdbcUtil.*;
import java.sql.Connection;

import dao.AdminDAO;

public class MemberGradeManagementSvc {
	
	
	public boolean updateGrade(String grade, String[] user_id) throws Exception {
		boolean isUpdateMember = false;
		Connection con = getConnection();
		AdminDAO adminDAO = AdminDAO.getInstance();
		adminDAO.setConnection(con);
		int updateCount = 0;
		
		for(int i=0; i<user_id.length; i++) {
			updateCount = adminDAO.updateGrade(grade, user_id[i]);
			
			if(updateCount == 0) {
				rollback(con);
			}
		}
		
		if(updateCount > 0) {
			commit(con);
			isUpdateMember = true;
		}else{
			rollback(con);
		}
		
		close(con);
		return isUpdateMember;
	}

}
