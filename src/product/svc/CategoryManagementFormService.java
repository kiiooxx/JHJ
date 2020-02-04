package product.svc;

import static db.JdbcUtil.*;

import java.sql.Connection;
import java.util.ArrayList;

import dao.ProductDAO;
import vo.CategoryBean;
import vo.ProductBean;

public class CategoryManagementFormService {

	public ArrayList<CategoryBean> selectCategoryList() {
		// TODO Auto-generated method stub
		ProductDAO productDAO = ProductDAO.getInstance();
		Connection con = getConnection();
		productDAO.setConnection(con);
		ArrayList<CategoryBean> categoryList = productDAO.selectCategoryList();
		close(con);
		
		return categoryList;
	}

	public boolean addCategory(String cate_large, String cate_name, int i, int j) {
		// TODO Auto-generated method stub
		ProductDAO productDAO = ProductDAO.getInstance();
		Connection con = getConnection();
		productDAO.setConnection(con);
		boolean addSuccess = false;
		int insertCount = productDAO.insertCategory(cate_large, cate_name, i, j);
		
		if(insertCount>0) {
			commit(con);
			addSuccess = true;
		}else {
			rollback(con);
		}
		close(con);
		return addSuccess;
	}

}
