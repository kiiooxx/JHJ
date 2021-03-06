package admin.svc;

import static db.JdbcUtil.*;

import java.sql.Connection;
import java.util.ArrayList;


import dao.AdminDAO;

import vo.Order;

public class OrderManageListService {

	//회원정보에서 주문목록
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

	//관리자페이지에서 주문관리 목록
	public int getOrderListCount(String searchType, String searchText, String startDate, String endDate, int startPrice, int endPrice, String[] deliStatus, String[] cancelReq) {
		int listCount = 0;
		Connection con = getConnection();
		AdminDAO adminDAO = AdminDAO.getInstance();
		adminDAO.setConnection(con);
		listCount = adminDAO.selectOrderListCount(searchType, searchText, startDate, endDate, startPrice, endPrice, deliStatus, cancelReq);
		close(con);
		
		
		return listCount;
	}

	public ArrayList<Order> getOrderList(String searchType, String searchText, String startDate, String endDate, int startPrice, int endPrice, String[] deliStatus, String[] cancelReq, int page, int limit) {
		Connection con = getConnection();
		AdminDAO adminDAO = AdminDAO.getInstance();
		adminDAO.setConnection(con);
		ArrayList<Order> orderList = null;
		orderList = adminDAO.selectOrderList(searchType, searchText, startDate, endDate, startPrice, endPrice, deliStatus, cancelReq, page, limit);
		close(con);
		
		
		return orderList;
	}

	
	
	
	
	

}
