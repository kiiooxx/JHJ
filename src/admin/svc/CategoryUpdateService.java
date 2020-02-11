package admin.svc;

import static db.JdbcUtil.*;

import java.sql.Connection;

import dao.AdminDAO;

public class CategoryUpdateService {

	public boolean updateCategory(int ca_ref, String cate_name, int cate_num) {
		// TODO Auto-generated method stub
		AdminDAO adminDAO = AdminDAO.getInstance();
		Connection con = getConnection();
		adminDAO.setConnection(con);
		boolean isUpdateSuccess = adminDAO.updateCategory(ca_ref,cate_name,cate_num);
		
		if(isUpdateSuccess) {
			commit(con);
		}else {
			rollback(con);
		}
		
		close(con);
		return isUpdateSuccess;
	}

}
