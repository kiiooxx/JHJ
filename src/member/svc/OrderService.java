package member.svc;


import static db.JdbcUtil.close;
import static db.JdbcUtil.getConnection;
import java.sql.Connection;
import java.util.ArrayList;

import dao.MemberDAO;
import vo.Member;
import vo.Order;

public class OrderService {

	public ArrayList<Order> selectorder(String id) {
		// TODO Auto-generated method stub
		ArrayList<Order> orderList = new ArrayList<>();
		MemberDAO memberDAO = MemberDAO.getInstance();
		
		Connection con = getConnection();
		memberDAO.setConnection(con);
		orderList = memberDAO.selectorder(id);
		
		close(con);
		return orderList;
	}

}
