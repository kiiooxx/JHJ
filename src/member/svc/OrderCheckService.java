package member.svc;

import static db.JdbcUtil.*;

import java.sql.Connection;
import java.util.ArrayList;

import dao.OrderDAO;
import vo.OrderCancel;


public class OrderCheckService {
	
	public boolean orderChk(String sel_status, String sel_num) {
			boolean isUpdateCount = false;
			Connection con = getConnection();
			OrderDAO orderDAO =  OrderDAO.getInstance();
			orderDAO.setConnection(con);
			int updateChk = orderDAO.updateorderChk(sel_status,sel_num);
			
			if(updateChk > 0) {
				commit(con);
				isUpdateCount = true; 
			}else{
				rollback(con);
			}
			close(con);
			return isUpdateCount;
		}
		
		
}