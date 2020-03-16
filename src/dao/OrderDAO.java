package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import static db.JdbcUtil.*;

import vo.DeliInfo;
import vo.Order;
import vo.OrderDet;
import vo.PayInfo;

public class OrderDAO {
	
	Connection con;
	private static OrderDAO orderDAO;
	
	private OrderDAO() {
		
	}
	
	public void setConnection(Connection con) {
		this.con = con;
	}
	
	public static OrderDAO getInstance() {
		if(orderDAO == null) {
			orderDAO = new OrderDAO();
		}
		return orderDAO;
	}

	
	//배송지 정보 입력
	public int insertDeliInfo(DeliInfo deliInfo) {
		PreparedStatement pstmt = null;
		int insertCount = 0;
		
		try {
				pstmt = con.prepareStatement("INSERT INTO deli_info VALUES(?,?,?,?,?,?,?,?)");
				pstmt.setString(1, deliInfo.getDeli_num());
				pstmt.setString(2, deliInfo.getDeli_postcode());
				pstmt.setString(3, deliInfo.getDeli_addr1());
				pstmt.setString(4, deliInfo.getDeli_addr2());
				pstmt.setString(5, deliInfo.getDeli_content());
				pstmt.setString(6, deliInfo.getRec_name());
				pstmt.setString(7, deliInfo.getRec_tel());
				pstmt.setString(8, deliInfo.getUser_id());
				insertCount = pstmt.executeUpdate();
			
		}catch(Exception e) {
			e.printStackTrace();
			System.out.println("OrderDAO - deli_info error");
		}finally {
			close(pstmt);
			
		}

		return insertCount;
	}

	public int insertOrderInfo(Order order) {
		PreparedStatement pstmt = null;
		int insertCount = 0;
		
		try {
			pstmt = con.prepareStatement("INSERT INTO order_page VALUES (?,?,?,?,?,?,?,?,'N')");
			pstmt.setString(1, order.getSel_num());
			pstmt.setString(2, order.getSel_date());
			pstmt.setString(3, order.getUser_id());
			pstmt.setString(4, order.getDeli_num());
			pstmt.setString(5, order.getSel_status());
			pstmt.setInt(6, order.getDeli_price());
			pstmt.setInt(7, order.getPoint_use());
			pstmt.setInt(8, order.getFinal_price());
			//9번째는 주문취소 디폴트값
			insertCount = pstmt.executeUpdate();
		}catch(Exception e) {
			e.printStackTrace();
			System.out.println("OrderDAO - order_page error");
		}finally {
			close(pstmt);
		}
		return insertCount;
	}

	public int insertOrderDet(ArrayList<OrderDet> orderDet2) {
		
		PreparedStatement pstmt = null;
		int insertCount = 0;
		
		try {
			
			for(int i = 0; i < orderDet2.size(); i++) {
				pstmt = con.prepareStatement("INSERT INTO order_det VALUES (?,?,?)");
				pstmt.setString(1, orderDet2.get(i).getSel_num());
				pstmt.setString(2, orderDet2.get(i).getPro_det_num());
				pstmt.setInt(3, orderDet2.get(i).getPro_qnt()); 
				insertCount = pstmt.executeUpdate();
			}
			
		}catch(Exception e){
			e.printStackTrace();
			System.out.println("orderDAO error  - orderDet");
		}
		return insertCount;
	}
	
	public int insertPayInfo(PayInfo payInfo) {
		
		PreparedStatement pstmt = null;
		int insertCount = 0;
		
		try {
			pstmt = con.prepareStatement("INSERT INTO pay_info VALUES (?,?,?,?,?,?)");
			pstmt.setString(1, payInfo.getSel_num());
			pstmt.setString(2, null);
			pstmt.setString(3, payInfo.getPay_type());
			pstmt.setString(4, payInfo.getAcc_name());
			pstmt.setString(5, payInfo.getAcc_bank());
			pstmt.setString(6, payInfo.getPay_exp());
			insertCount = pstmt.executeUpdate();
		}catch(Exception e) {
			e.printStackTrace();
			System.out.println("orderDAO pay_info error");
		}finally {
			close(pstmt);
		}
		return insertCount;
	}
	

}
