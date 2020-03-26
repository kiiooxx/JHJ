package admin.svc;

import java.sql.Connection;

import dao.AdminDAO;

import static db.JdbcUtil.*;
import vo.PointMan;

public class PointManageFormService {

	public PointMan getPointOption(int seq) {
		PointMan pointMan = null;
		Connection con = getConnection();
		AdminDAO adminDAO = AdminDAO.getInstance();
		adminDAO.setConnection(con);
		pointMan = adminDAO.viewPointOption(seq);
		close(con);
		return pointMan;
	}

}
