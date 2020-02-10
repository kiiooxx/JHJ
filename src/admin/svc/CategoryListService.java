package admin.svc;

import static db.JdbcUtil.close;
import static db.JdbcUtil.getConnection;

import java.sql.Connection;
import java.util.ArrayList;

import dao.AdminDAO;
import dao.ProductDAO;
import vo.CategoryBean;

public class CategoryListService {

	public ArrayList<CategoryBean> selectCategoryList() {
		// TODO Auto-generated method stub
		AdminDAO adminDAO = AdminDAO.getInstance();
		Connection con = getConnection();
		adminDAO.setConnection(con);
		ArrayList<CategoryBean> categoryList = adminDAO.selectCategoryList();
		close(con);
		
		return categoryList;
	}

}
