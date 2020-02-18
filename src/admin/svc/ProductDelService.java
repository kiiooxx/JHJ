package admin.svc;

import static db.JdbcUtil.*;

import java.sql.Connection;

import dao.AdminDAO;

public class ProductDelService {

	public boolean deleteProduct(String[] pro_num) {
		// TODO Auto-generated method stub
		boolean isDeleteSuccess = false;
		Connection con = getConnection();
		AdminDAO adminDAO = AdminDAO.getInstance();
		adminDAO.setConnection(con);
		
		for(int i=0; i<pro_num.length; i++) {
			isDeleteSuccess = false;
			isDeleteSuccess = adminDAO.deleteProduct(Integer.parseInt(pro_num[i]));
			if(!isDeleteSuccess) {
				rollback(con);
				break;
			}
		}
		
		if(isDeleteSuccess) {
			commit(con);
		}else {
			rollback(con);
		}
		
		close(con);
		return isDeleteSuccess;
	}

	
	//상품 옵션 삭제
	public boolean deleteProductOption(String pro_det_num) {
		// TODO Auto-generated method stub
		boolean isDeleteSuccess = false;
		Connection con = getConnection();
		AdminDAO adminDAO = AdminDAO.getInstance();
		adminDAO.setConnection(con);
		
		isDeleteSuccess = adminDAO.deleteProductOption(pro_det_num);
			
		if(isDeleteSuccess) {
			commit(con);
		}else {
			rollback(con);
		}
		
		close(con);
		return isDeleteSuccess;
	}

}
