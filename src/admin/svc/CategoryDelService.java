package admin.svc;

import static db.JdbcUtil.*;

import java.sql.Connection;

import dao.AdminDAO;

public class CategoryDelService {

	public boolean deleteCategory(int cate_num) {
		// TODO Auto-generated method stub
		boolean isDeleteSuccess = false;
		Connection con = getConnection();
		AdminDAO adminDAO = AdminDAO.getInstance();
		adminDAO.setConnection(con);
		
		//카테고리 삭제
		isDeleteSuccess = adminDAO.deleteCategory(cate_num);
		
		if(isDeleteSuccess) {
			commit(con);
		}else {
			rollback(con);
		}
		
		close(con);
		return isDeleteSuccess;
	}

}
