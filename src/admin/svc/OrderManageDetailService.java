package admin.svc;

import static db.JdbcUtil.close;
import static db.JdbcUtil.getConnection;

import java.sql.Connection;
import java.util.ArrayList;


import dao.AdminDAO;
import vo.DeliInfo;
import vo.Order;
import vo.OrderProView;
import vo.PayInfo;


public class OrderManageDetailService {

	public ArrayList<OrderProView> getOrderDetail(String sel_num) {
		
		ArrayList<OrderProView> orderProList = null;
		Connection con = getConnection();
		AdminDAO adminDAO = AdminDAO.getInstance();
		adminDAO.setConnection(con);
		
		orderProList = adminDAO.selectOrderDet(sel_num);
		close(con);
		
		return orderProList;
	}

	public DeliInfo getDeliInfo(String user_id) {
		
		DeliInfo deliInfo = null;
		Connection con = getConnection();
		AdminDAO adminDAO = AdminDAO.getInstance();
		adminDAO.setConnection(con);
		deliInfo = adminDAO.selectDeliInfo(user_id);
		close(con);
		
		return deliInfo;
	}

	public PayInfo getPayInfo(String sel_num) {

		PayInfo payInfo = null;
		Connection con = getConnection();
		AdminDAO adminDAO = AdminDAO.getInstance();
		adminDAO.setConnection(con);
		payInfo = adminDAO.selectPayInfo(sel_num);
		close(con);
		
		return payInfo;
	}

	public Order getOrderInfo(String sel_num) {
		
		Order orderInfo = null;
		Connection con = getConnection();
		AdminDAO adminDAO = AdminDAO.getInstance();
		adminDAO.setConnection(con);
		orderInfo = adminDAO.selectOrderInfo(sel_num);
		close(con);
		
		return orderInfo;
	}

}
