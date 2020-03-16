package point.svc;

import static db.JdbcUtil.close;
import static db.JdbcUtil.commit;
import static db.JdbcUtil.getConnection;
import static db.JdbcUtil.rollback;

import java.sql.Connection;

import dao.PointDAO;

public class PointService {

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

}
