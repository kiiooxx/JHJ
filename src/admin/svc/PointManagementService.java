package admin.svc;

import static db.JdbcUtil.*;

import java.sql.Connection;

import dao.AdminDAO;
import vo.PointMan;


public class PointManagementService {

	public boolean setPointOption(int pointDate, String markOption, float rate, String usePointOpt, int newMem,
			int priceLimit, int pointLimit, int reviewP) {
		
		boolean isSuccess = false;
		int count = 0;
		Connection con = getConnection();
		AdminDAO adminDAO = AdminDAO.getInstance();
		adminDAO.setConnection(con);
		
		count = adminDAO.settingPointOption(pointDate, markOption, rate, usePointOpt, 
				newMem, priceLimit, pointLimit, reviewP);
		if(count > 0) {
			commit(con);
			isSuccess = true;
			
		}else {
			rollback(con);
		}
		
		close(con);
		
		return isSuccess;
	}

}
