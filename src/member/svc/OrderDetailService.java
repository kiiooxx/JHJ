package member.svc;

import static db.JdbcUtil.*;
import java.sql.Connection;
import java.util.ArrayList;

import dao.AdminDAO;
import dao.MemberDAO;
import vo.Order;
import vo.ProDetBean;

	public class OrderDetailService{
		
		public Order getOrderDetailInfo(String sel_num) {
			Order order = new Order();
			AdminDAO adminDAO = AdminDAO.getInstance();
			
			Connection con= getConnection();
			adminDAO.setConnection(con);
			order = adminDAO.selectOrderInfo(sel_num);
			
			close(con);
			
			return order;
		}
		

		
	}
		
	


