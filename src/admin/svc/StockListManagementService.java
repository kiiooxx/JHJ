package admin.svc;

import static db.JdbcUtil.close;
import static db.JdbcUtil.getConnection;

import java.sql.Connection;
import java.util.ArrayList;

import dao.AdminDAO;
import vo.ProDetBean;
import vo.StockBean;

public class StockListManagementService {

	public ArrayList<ProDetBean> getStockList(String[] pro_num) {
		// TODO Auto-generated method stub
		ArrayList<ProDetBean> stockList = null;
		Connection con = getConnection();
		AdminDAO adminDAO = AdminDAO.getInstance();
		adminDAO.setConnection(con);
		stockList = adminDAO.selectStockList(pro_num);
		close(con);
		return stockList;
	}

}
