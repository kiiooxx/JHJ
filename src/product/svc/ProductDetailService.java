package product.svc;

import static db.JdbcUtil.*;

import java.sql.Connection;
import java.util.ArrayList;

import dao.ProductDAO;
import vo.ProDetBean;
import vo.ProductBean;

public class ProductDetailService {

	//pro_info 불러오기
	public ProductBean getProduct(int pro_num) {
		// TODO Auto-generated method stub
		ProductBean prd = null;
		Connection con = getConnection();
		ProductDAO productDAO = ProductDAO.getInstance();
		productDAO.setConnection(con);
		prd = productDAO.selectProduct(pro_num);
		close(con);
		return prd;
	}

	//pro_det 불러오기
	public ArrayList<ProDetBean> getProductDetail(int pro_num) {
		// TODO Auto-generated method stub
		ArrayList<ProDetBean> prdDetList = null;
		Connection con = getConnection();
		ProductDAO productDAO = ProductDAO.getInstance();
		productDAO.setConnection(con);
		prdDetList = productDAO.selectProductDetail(pro_num);
		close(con);
		return prdDetList;
	}
}
