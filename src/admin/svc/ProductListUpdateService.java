package admin.svc;

import static db.JdbcUtil.close;
import static db.JdbcUtil.commit;
import static db.JdbcUtil.getConnection;
import static db.JdbcUtil.rollback;

import java.sql.Connection;

import dao.AdminDAO;

public class ProductListUpdateService {

	public boolean updateActive(String[] pro_num, String active) {
		// TODO Auto-generated method stub
		AdminDAO adminDAO = AdminDAO.getInstance();
		Connection con = getConnection();
		adminDAO.setConnection(con);
		boolean isUpdateSuccess = false;
		for(int i=0; i<pro_num.length; i++) {
			isUpdateSuccess = adminDAO.updateActive(Integer.parseInt(pro_num[i]), active);
			if(!isUpdateSuccess) {
				rollback(con);
			}
		}
		
		if(isUpdateSuccess) {
			commit(con);
		}else {
			rollback(con);
		}
		
		close(con);
		return isUpdateSuccess;
	}

	public boolean updateMain_nb(String[] pro_num, String main_nb) {
		// TODO Auto-generated method stub
		AdminDAO adminDAO = AdminDAO.getInstance();
		Connection con = getConnection();
		adminDAO.setConnection(con);
		boolean isUpdateSuccess = false;
		for(int i=0; i<pro_num.length; i++) {
			isUpdateSuccess = adminDAO.updateMain_nb(Integer.parseInt(pro_num[i]), main_nb);
			if(!isUpdateSuccess) {
				rollback(con);
			}
		}
		
		if(isUpdateSuccess) {
			commit(con);
		}else {
			rollback(con);
		}
		
		close(con);
		return isUpdateSuccess;
	}

}
