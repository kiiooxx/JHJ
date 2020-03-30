package point.svc;

import static db.JdbcUtil.*;
import java.sql.Connection;
import java.util.ArrayList;

import dao.PointDAO;
import vo.Point;

public class MyPointService {

		public int getMyPointListCount(String user_id) {
			int listCount = 0;
			Connection con = getConnection();
			PointDAO pointDAO = PointDAO.getInstance();
			pointDAO.setConnection(con);
			listCount = pointDAO.selectPointListCount(user_id);
			close(con);
			return listCount;
		}
	
	
	
	
		public ArrayList<Point> selectMyPoint(String user_id, int page, int limit) {
		
			ArrayList<Point> myPointList = new ArrayList<>();
			Connection con = getConnection();
			PointDAO pointDAO = PointDAO.getInstance();
			pointDAO.setConnection(con);
			
			myPointList = pointDAO.selectMyPoint(user_id,page,limit);
			close(con);
			
			
		
			return myPointList;
			
			
		}
}
