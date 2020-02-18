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

	//상품 정보 불러오기
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

	//상품 상세 정보 불러오기
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

	//상품 정보 수정하기
	public boolean updateProduct(int pro_num, ProductBean productBean, ArrayList<ProDetBean> proDetInfo2) {
		// TODO Auto-generated method stub
		AdminDAO adminDAO = AdminDAO.getInstance();
		Connection con = getConnection();
		adminDAO.setConnection(con);
		boolean isUpdateSuccess = false;
		
		isUpdateSuccess = adminDAO.updateProduct(pro_num, productBean);
				
		if(isUpdateSuccess) {
			isUpdateSuccess = false;
			for(int i=0; i<proDetInfo2.size(); i++) {
				isUpdateSuccess = adminDAO.updateStock(pro_num, proDetInfo2.get(i).getPro_det_num(), proDetInfo2.get(i).getStock_qnt());
				if(!isUpdateSuccess) {
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

	
	//상품 옵션 추가하기 + 재고 테이블에 재고 넣기
	public boolean registProductOption(int pro_num, ArrayList<ProDetBean> proDetInfo) {
		// TODO Auto-generated method stub
		AdminDAO adminDAO = AdminDAO.getInstance();
		Connection con = getConnection();
		adminDAO.setConnection(con);
		boolean isRegistSuccess = false;
		int insertCount = 0;
		
		// 상품상세코드 (상품 번호 + 색상 순서 )
		String num = String.format("%04d", pro_num);
		String color_num = adminDAO.selectProDetColorNum(pro_num);
					
		String stock_num = adminDAO.selectStockCount();	//오늘 등록한 재고의 개수
					
		if(!(color_num.equals("")|| stock_num.equals(""))) {
						
			for(int i=0; i<proDetInfo.size(); i++) {
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
