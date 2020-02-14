package admin.svc;

import static db.JdbcUtil.close;
import static db.JdbcUtil.commit;
import static db.JdbcUtil.getConnection;
import static db.JdbcUtil.rollback;

import java.sql.Connection;
import java.util.ArrayList;

import dao.AdminDAO;
import vo.ProDetBean;
import vo.ProductBean;

public class ProductModifyService {

	public ProductBean selectProInfo(int pro_num) {
		// TODO Auto-generated method stub
		ProductBean productBean = new ProductBean();
		Connection con = getConnection();
		AdminDAO adminDAO = AdminDAO.getInstance();
		adminDAO.setConnection(con);
		productBean = adminDAO.selectProInfo(pro_num);
		close(con);
		return productBean;
	}

	public ArrayList<ProDetBean> selectProDet(int pro_num) {
		// TODO Auto-generated method stub
		ArrayList<ProDetBean> proDetList = null;
		Connection con = getConnection();
		AdminDAO adminDAO = AdminDAO.getInstance();
		adminDAO.setConnection(con);
		proDetList = adminDAO.selectProDet(pro_num);
		close(con);
		return proDetList;
	}

	public boolean updateProduct(int pro_num, ProductBean productBean, ArrayList<ProDetBean> proDetInfo) {
		// TODO Auto-generated method stub
		AdminDAO adminDAO = AdminDAO.getInstance();
		Connection con = getConnection();
		adminDAO.setConnection(con);
		boolean isUpdateSuccess = false;
		
		isUpdateSuccess = adminDAO.updateProduct(pro_num, productBean);
		
		
		if(isUpdateSuccess) {
			isUpdateSuccess = false;
			for(int i=0; i<proDetInfo.size(); i++) {
				isUpdateSuccess = adminDAO.updatePro_Det(pro_num, proDetInfo.get(i).getColor(), proDetInfo.get(i).getPro_size(), proDetInfo.get(i).getStock_qnt(), i+1);
				if(isUpdateSuccess = false) {
					rollback(con);
					break;
				}
			}
			commit(con);	
		}else {
			rollback(con);
		}
		close(con);
		return isUpdateSuccess;
	}

}
