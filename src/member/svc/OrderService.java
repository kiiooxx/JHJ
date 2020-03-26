package member.svc;


import static db.JdbcUtil.close;
import static db.JdbcUtil.getConnection;
import java.sql.Connection;
import java.util.ArrayList;

import dao.MemberDAO;
import vo.Member;
import vo.Order;

public class OrderService {

	public int getOrderListCount(String id) {
		// TODO Auto-generated method stub
		int listCount = 0;
		Connection con = getConnection();
		MemberDAO memberDAO = MemberDAO.getInstance();
		memberDAO.setConnection(con);
		listCount = memberDAO.selectOrderListCount(id);
		close(con);
		return listCount;
	}
	
	public ArrayList<Order> selectOrderList(String id, int page, int limit) {
		// TODO Auto-generated method stub
		ArrayList<Order> orderList = new ArrayList<>();
		MemberDAO memberDAO = MemberDAO.getInstance();
		
		Connection con = getConnection();
		memberDAO.setConnection(con);
		orderList = memberDAO.selectOrderList(id, page, limit);
		
		close(con);
		return orderList;
	}

	

}
