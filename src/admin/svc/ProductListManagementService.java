package admin.svc;

import static db.JdbcUtil.close;
import static db.JdbcUtil.getConnection;

import java.sql.Connection;
import java.util.ArrayList;

import dao.AdminDAO;
import vo.ProductBean;

public class ProductListManagementService {

	public int getListCount(String search_type, String search_text, String cate_type, int ca_ref, String pro_date, String active) {
		// TODO Auto-generated method stub
		int listCount = 0;
		Connection con = getConnection();
		AdminDAO adminDAO = AdminDAO.getInstance();
		adminDAO.setConnection(con);
		listCount = adminDAO.selectListCount(search_type, search_text, cate_type, ca_ref, pro_date, active);
		close(con);
		return listCount;
	}

	public ArrayList<ProductBean> getPrdList(String search_type, String search_text, String cate_type, int ca_ref,
			String pro_date, String active, int page, int limit) {
		// TODO Auto-generated method stub
		ArrayList<ProductBean> prdList = null;
		Connection con = getConnection();
		AdminDAO adminDAO = AdminDAO.getInstance();
		adminDAO.setConnection(con);
		prdList = adminDAO.selectProductList(search_type, search_text, cate_type, ca_ref, pro_date, active, page, limit);
		close(con);
		return prdList;
	}

}
