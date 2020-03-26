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
import vo.OrderCancel;
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

	///////////////////////////(사용자)주문 = 주문내역 insert
	
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
		String sql ="INSERT INTO order_page(sel_num, sel_date, user_id, deli_num, "  
	            + "sel_status, deli_price, point_use, final_price, cancel_req) VALUES (?,?,?,?,?,?,?,?,'N')";
		try {
			pstmt = con.prepareStatement(sql);
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
	//지우면될듯/
	public ArrayList<OrderCancel> selectOrderCancel(String sel_num) {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		ArrayList<OrderCancel> ocList = new ArrayList<OrderCancel>();
		OrderCancel oc = null;
		String sql = "select a.sel_num, d.pro_photo, d.pro_num, c.pro_size, c.color, b.pro_qnt, "
				+ "a.deli_price, a.final_price, a.cancel_req, a.cancel_reason from order_page "
				+ "as a left join order_det as b on a.sel_num=b.sel_num left join pro_det as c"
				+ " on b.pro_det_num=c.pro_det_num left join pro_info as d on c.pro_num=d.pro_num"
				+ " where a.sel_num = ?";
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, sel_num);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				oc = new OrderCancel();
				oc.setSel_num(rs.getString("sel_num"));
				oc.setPro_photo(rs.getString("pro_photo"));
				oc.setPro_num(rs.getInt("pro_num"));
				oc.setPro_size(rs.getString("pro_size"));
				oc.setPro_qnt(rs.getInt("pro_qnt"));
				oc.setColor(rs.getString("color"));
				oc.setDeli_price(rs.getInt("deli_price"));
				oc.setFinal_price(rs.getInt("final_price"));
				oc.setCancel_req(rs.getString("cancel_req").charAt(0));
				oc.setCancel_reason(rs.getString("cancel_reason"));
				ocList.add(oc);
				}
			}catch(Exception e) {
				e.printStackTrace();
				System.out.println("OrderDAO - ordercancel error");
			}finally {
				close(rs);
				close(pstmt);
				
			}

			return ocList;
		}

	
	public int updatecancelReq(String cancel_reason, String sel_num) {
		
		int updateCount = 0;
		PreparedStatement pstmt = null;
		OrderCancel oc= null;
		
		try {
			pstmt = con.prepareStatement("update order_page set cancel_req='Y', cancel_reason=? where sel_num=?");
			pstmt.setString(1, cancel_reason);
			pstmt.setString(2, sel_num);
			updateCount = pstmt.executeUpdate();
		} catch (Exception ex) {
			System.out.println("cancelReq 에러 : " + ex);
		} finally {
			close(pstmt);
		}
		System.out.println("DAO"+updateCount);
		return updateCount;
	}
	
//	//(관리자)주문취소승인= 주문내역(주문정보, 주문상세, 배송정보, 결제정보) delete
//	public int deleteDeliInfo(String sel_num) {
//	
//		PreparedStatement pstmt = null;
//		int deleteCount = 0;
//		String sql = "DELETE a, b, c, d from deli_info AS a inner join order_page AS b on a.deli_num=b.deli_num "
//				+ "inner join order_det AS c on b.sel_num=c.sel_num inner join pay_info AS d on c.sel_num=d.sel_num WHERE d.sel_num=? ";
//		
//		try {
//			pstmt = con.prepareStatement(sql);
//			pstmt.setString(1, sel_num);
//			deleteCount = pstmt.executeUpdate();
//			
//		}catch(Exception e) {
//			e.printStackTrace();
//			System.out.println("OrderDAO - deleteDeliInfo error");
//			System.out.println("sql: " + sql);
//		}
//		
//		return deleteCount;
//	}
	
	

}
