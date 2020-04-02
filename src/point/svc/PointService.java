package point.svc;

import static db.JdbcUtil.close;
import static db.JdbcUtil.commit;
import static db.JdbcUtil.getConnection;
import static db.JdbcUtil.rollback;

import java.sql.Connection;

import dao.PointDAO;
import vo.Point;

public class PointService {

	//신규회원 적립금 지급
	public boolean pointForNewmem(String user_id) {
		boolean isPoint = false;
		Connection con = getConnection();
		PointDAO pointDAO = PointDAO.getInstance();
		pointDAO.setConnection(con);
		int insertCount = pointDAO.checkNewmemOption(user_id);
		
		if(insertCount > 0 ) {
			commit(con);
			isPoint = true;
		}else {
			rollback(con);
		}
		close(con);
		
		return isPoint;
	}

	//구매확정 적립금 지급
	public boolean orderPoint(String sel_num, String user_id) {
		boolean isPoint = false;
		Connection con = getConnection();
		PointDAO pointDAO = PointDAO.getInstance();
		pointDAO.setConnection(con);
		int insertCount = pointDAO.orderPoint(sel_num, user_id);
		
		if(insertCount > 0) {
			commit(con);
			isPoint = true;
		}else {
			rollback(con);
		}
		close(con);
		
		return isPoint;
	}
	
	public boolean reviewPoint(String user_id) {
		boolean isPoint = false;
		Connection con = getConnection();
		PointDAO pointDAO = PointDAO.getInstance();
		pointDAO.setConnection(con);
		int insertCount = pointDAO.checkReviewOption(user_id);
		
		if(insertCount > 0) {
			commit(con);
			isPoint = true;
		}else {
			rollback(con);
		}
		close(con);
		
		return isPoint;
	}
	
	//회원 적립금 조회
	public Point memberPoint(String user_id) {
		
		Point memberPoint = null;
		Connection con = getConnection();
		PointDAO pointDAO = PointDAO.getInstance();
		pointDAO.setConnection(con);
		memberPoint = pointDAO.selectMemberPoint(user_id);
		
		close(con);
		
		return memberPoint;
		
	}
	
	public boolean usePoint(String sel_num) {
		boolean isUse = false;
		Connection con = getConnection();
		PointDAO pointDAO = PointDAO.getInstance();
		pointDAO.setConnection(con);
		int insertCount = pointDAO.orderUsePoint(sel_num);
		System.out.println("PointService insertCount: " + insertCount);
		if(insertCount > 0) {
			commit(con);
			isUse = true;
		}else {
			rollback(con);
		}
		close(con);
		
		return isUse;
	}

}
