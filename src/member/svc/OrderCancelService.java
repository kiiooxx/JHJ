package member.svc;

import static db.JdbcUtil.close;
import static db.JdbcUtil.commit;
import static db.JdbcUtil.getConnection;
import static db.JdbcUtil.rollback;

import java.sql.Connection;
import java.util.ArrayList;


import dao.OrderDAO;
import vo.OrderCancel;

public class OrderCancelService {
	
		public static ArrayList<OrderCancel> getOrderCancel(String sel_num) {
			
			ArrayList<OrderCancel> orderCancelList = null;
			Connection con = getConnection();
			OrderDAO orderDAO = OrderDAO.getInstance();
			orderDAO.setConnection(con);
			
			orderCancelList = orderDAO.selectOrderCancel(sel_num);
			close(con);
		
			return orderCancelList;
		}

		public boolean cancelReq(String cancel_reason, String sel_num) throws Exception {
			// TODO Auto-generated method stub
			boolean isUpdateCancel = false;
			Connection con = getConnection();
			OrderDAO orderDAO =  OrderDAO.getInstance();
			orderDAO.setConnection(con);
			int updateCount = orderDAO.updatecancelReq(cancel_reason,sel_num);
			
			if(updateCount > 0) {
				commit(con);
				isUpdateCancel = true;
			}else{
				rollback(con);
			}
			close(con);
			return isUpdateCancel;
		}

	
		
	
}
