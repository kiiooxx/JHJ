package admin.svc;

import static db.JdbcUtil.*;

import java.sql.Connection;
import java.util.ArrayList;

import dao.AdminDAO;
import vo.ProDetBean;
import vo.ProductBean;

public class ProductRegistService {

	public boolean registProduct(ProductBean productBean, ArrayList<ProDetBean> proDetInfo) {
		// TODO Auto-generated method stub
		AdminDAO adminDAO = AdminDAO.getInstance();
		Connection con = getConnection();
		adminDAO.setConnection(con);
		boolean isRegistSuccess = false;
		int pro_num = adminDAO.insertProduct(productBean);
		int insertCount = 0;
		
		
		if(pro_num > 0) {
			commit(con);
			isRegistSuccess = true;
		}else {
			rollback(con);
		}
		//상품 등록이 성공하면
		if(pro_num > 0) {
			for(int i=0; i<proDetInfo.size(); i++) {
				insertCount = adminDAO.insertPro_Det(pro_num, proDetInfo.get(i).getColor(), proDetInfo.get(i).getPro_size(), proDetInfo.get(i).getStock_qnt(), i+1);
				if(insertCount == 0) {
					isRegistSuccess = false;
					break;
				}
			}
			commit(con);
			isRegistSuccess = true;
		}else {
			rollback(con);
		}
		close(con);
		return isRegistSuccess;
	}


}
