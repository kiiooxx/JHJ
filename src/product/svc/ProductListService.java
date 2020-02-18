package product.svc;

import static db.JdbcUtil.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import dao.ProductDAO;
import vo.ProductBean;


public class ProductListService {

	public int getListCount(int cate_num) {
		// TODO Auto-generated method stub
		int listCount = 0;
		Connection con = getConnection();
		ProductDAO productDAO = ProductDAO.getInstance();
		productDAO.setConnection(con);
		listCount = productDAO.selectListCount(cate_num);
		close(con);
		return listCount;
	}


	public ArrayList<ProductBean> getPrdList(int cate_num, int page, int limit) {
		// TODO Auto-generated method stub
		ArrayList<ProductBean> prdList = null;
		Connection con = getConnection();
		ProductDAO productDAO = ProductDAO.getInstance();
		productDAO.setConnection(con);
		prdList = productDAO.selectProductList(cate_num, page, limit);
		close(con);
		return prdList;
	}
}
