package admin.svc;

import static db.JdbcUtil.*;

import java.sql.Connection;

import dao.AdminDAO;

public class StockListModifyService {

	public boolean updateStock(String[] pro_num, String[] pro_det_num, String[] stock_qnt) {
		// TODO Auto-generated method stub
		AdminDAO adminDAO = AdminDAO.getInstance();
		Connection con = getConnection();
		adminDAO.setConnection(con);
		boolean isUpdateSuccess = false;
		
		for(int i=0; i<stock_qnt.length; i++) {
			isUpdateSuccess = adminDAO.updateStock(Integer.parseInt(pro_num[i]), pro_det_num[i], Integer.parseInt(stock_qnt[i]));
			if(!isUpdateSuccess) {
				rollback(con);
				break;
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
