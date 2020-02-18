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
		
		
		//상품 등록이 성공하면
		if(pro_num > 0) {
			// 상품상세코드 (상품 번호 + 색상 순서 )
			String num = String.format("%04d", pro_num);

			for(int i=0; i<proDetInfo.size(); i++) {
				String stock_num = adminDAO.selectStockCount();	//오늘 등록한 재고의 개수
				String color_num = adminDAO.selectProDetColorNum(pro_num);
				String pro_det_num = num + color_num + proDetInfo.get(i).getPro_size().substring(0,1);	//상품 상세 코드
				insertCount = adminDAO.insertPro_Det(pro_num, pro_det_num, stock_num, proDetInfo.get(i).getColor(), proDetInfo.get(i).getPro_size(), proDetInfo.get(i).getStock_qnt());
				if(insertCount == 0) {
					isRegistSuccess = false;
					break;
				}
			}
		}
		
		if(insertCount > 0) {
			commit(con);
			isRegistSuccess = true;
		}else {
			rollback(con);
		}
		close(con);
		return isRegistSuccess;
	}


}
