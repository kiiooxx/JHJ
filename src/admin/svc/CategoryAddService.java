package admin.svc;

import static db.JdbcUtil.close;
import static db.JdbcUtil.commit;
import static db.JdbcUtil.getConnection;
import static db.JdbcUtil.rollback;

import java.sql.Connection;

import dao.AdminDAO;



public class CategoryAddService {

	public boolean addCategory(String cate_large, String cate_name, int i, int j) {
		// TODO Auto-generated method stub
		AdminDAO adminDAO = AdminDAO.getInstance();
		Connection con = getConnection();
		adminDAO.setConnection(con);
		boolean addSuccess = false;
		int insertCount = adminDAO.insertCategory(cate_large, cate_name, i, j);
		
		if(insertCount>0) {
			commit(con);
			addSuccess = true;
		}else {
			rollback(con);
		}
		close(con);
		return addSuccess;
	}

}
