package admin.svc;

import static db.JdbcUtil.*;

import java.sql.Connection;
import java.util.ArrayList;

import dao.AdminDAO;
import vo.Order;

public class OrderListService {

	public int getOrderListCount(String user_id) {
		
		int listCount = 0;
		Connection con = getConnection();
		AdminDAO adminDAO = AdminDAO.getInstance();
		adminDAO.setConnection(con);
		listCount = adminDAO.selectOrderListCount(user_id);
		close(con);
		
		
		return listCount;
	}

	public ArrayList<Order> getOrderList(String user_id, int page, int limit) {
		
		Connection con = getConnection();
		AdminDAO adminDAO = AdminDAO.getInstance();
		adminDAO.setConnection(con);
		ArrayList<Order> orderList = null;
		orderList = adminDAO.selectOrderList(user_id, page, limit);
		close(con);
		
		return orderList;
	}

}
