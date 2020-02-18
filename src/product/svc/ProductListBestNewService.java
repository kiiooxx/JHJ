package product.svc;

import static db.JdbcUtil.close;
import static db.JdbcUtil.getConnection;

import java.sql.Connection;
import java.util.ArrayList;

import dao.ProductDAO;
import vo.ProductBean;

public class ProductListBestNewService {

	public int getListCount(String main_nb) {
		// TODO Auto-generated method stub
		int listCount = 0;
		Connection con = getConnection();
		ProductDAO productDAO = ProductDAO.getInstance();
		productDAO.setConnection(con);
		listCount = productDAO.selectListCount(main_nb);
		close(con);
		return listCount;
	}

	public ArrayList<ProductBean> getPrdList(String main_nb, String orderBy, int page, int limit) {
		// TODO Auto-generated method stub
		ArrayList<ProductBean> prdList = null;
		Connection con = getConnection();
		ProductDAO productDAO = ProductDAO.getInstance();
		productDAO.setConnection(con);
		prdList = productDAO.selectProductList(main_nb, orderBy, page, limit);
		close(con);
		return prdList;
	}

}
