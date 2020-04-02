package product.svc;

import java.sql.Connection;
import java.util.ArrayList;

import dao.OrderDAO;

import static db.JdbcUtil.*;

import vo.DeliInfo;
import vo.Order;
import vo.OrderDet;
import vo.PayInfo;

public class OrderProcessService {

	public boolean addOrder(DeliInfo deliInfo, Order order, ArrayList<OrderDet> orderDet2, PayInfo payInfo) {
		
		boolean addOrderSuccess = false;
		Connection con = getConnection();
		OrderDAO orderDAO = OrderDAO.getInstance();
		orderDAO.setConnection(con);
		
		int deliCount = orderDAO.insertDeliInfo(deliInfo);
		int orderCount = orderDAO.insertOrderInfo(order);
		int orderDetCount = orderDAO.insertOrderDet(orderDet2);
		int payInfoCount = orderDAO.insertPayInfo(payInfo);
		int stockCount = 0;
		
		for(int i=0; i<orderDet2.size(); i++) {
			stockCount = orderDAO.outStock(orderDet2.get(i).getPro_det_num(), orderDet2.get(i).getPro_qnt());
			
			if(stockCount == 0) {
				rollback(con);
				close(con);
				return false;
			}
		}
		
		if(deliCount > 0 && orderCount > 0 && orderDetCount > 0 && payInfoCount > 0 && stockCount > 0) {
			commit(con);
			addOrderSuccess = true;
		}else {
			rollback(con);
		}
		
		close(con);
		
		return addOrderSuccess;
		
	}
}
