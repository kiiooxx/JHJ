package dao;

import static db.JdbcUtil.close;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import vo.CategoryBean;
import vo.DeliInfo;
import vo.MailOption;
import vo.Member;
import vo.Order;
import vo.OrderProView;
import vo.PayInfo;
import vo.PointMan;
import vo.ProDetBean;
import vo.ProductBean;
import vo.StockBean;

public class AdminDAO {
	Connection con;
	private static AdminDAO adminDAO;
	
	private AdminDAO() {
		
	}
	
	public void setConnection(Connection con) {
		// TODO Auto-generated method stub
		this.con = con;
	}
	
	public static AdminDAO getInstance() {
		// TODO Auto-generated method stub
		if(adminDAO == null) {
			adminDAO = new AdminDAO();
		}
		
		return adminDAO;
	}
	
	
	//===========================카테고리===========================
	
	//1. 카테고리 리스트 불러오기
	public ArrayList<CategoryBean> selectCategoryList() {
		// TODO Auto-generated method stub
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		ArrayList<CategoryBean> categoryList = new ArrayList<>();
		CategoryBean categoryBean = null;
		
		try {
			pstmt = con.prepareStatement("select * from category order by ca_ref, ca_seq asc");
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				categoryBean = new CategoryBean();
				categoryBean.setCate_num(rs.getInt("cate_num"));
				categoryBean.setCategory(rs.getString("category"));
				categoryBean.setCa_ref(rs.getInt("ca_ref"));
				categoryBean.setCa_lev(rs.getInt("ca_lev"));
				categoryBean.setCa_seq(rs.getInt("ca_seq"));
				categoryList.add(categoryBean);
			}
		}catch(SQLException e) {
			System.out.println("categoryList_select 에러 " + e);
		}finally {
			close(rs);
			close(pstmt);
		}
		
		return categoryList;
	}
	
				
	//2. 카테고리 추가
	public int insertCategory(String cate_large, String cate_name, int i, int j) {
		// TODO Auto-generated method stub
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		int cate_num = 0;
		int insertCount = 0;
		int ca_ref = 0;
		String sql = "insert into category values (?, ?, ?, ?, ?)";
		
		try {
			//카테고리 번호 불러오기
			pstmt = con.prepareStatement("select max(cate_num) from category");
			rs = pstmt.executeQuery();
			if(rs.next()) {
				cate_num = rs.getInt(1) + 1;
			}else {
				cate_num = 1;
			}
			
			//등록된 카테고리가 없거나, 대분류 카테고리를 등록할시에
			if(cate_num == 1 || i == 0) {
				//카테고리 관련글번호를 등록할 카테고리 번호랑 똑같이 바꿔주고
				ca_ref = cate_num;
			}else {
				//소분류 카테고리를 등록할때는 대분류 카테고리 번호를 받아서 관련글번호로 지정한다.
				ca_ref = Integer.parseInt(cate_large);
			}
			
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, cate_num);
			pstmt.setString(2, cate_name);
			pstmt.setInt(3, ca_ref);
			pstmt.setInt(4, i);
			pstmt.setInt(5, j);
			
			insertCount = pstmt.executeUpdate();
			
		}catch(SQLException e) {
			System.out.println("카테고리 추가 에러 " + e);
		}finally {
			close(rs);
			close(pstmt);
		}
		
		return insertCount;
	}
	
	
	
	// 상품 정보 등록
	public int insertProduct(ProductBean productBean) {
		// TODO Auto-generated method stub
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		int insertCount = 0;
		int num = 0;
		String sql = "";
		
		try {
			pstmt = con.prepareStatement("select max(pro_num) from pro_info");
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				num = rs.getInt(1) + 1;
				
				sql = "insert into pro_info values (?, ?, ?, ?, ?, ?, ?, ?, ?, NOW())";
				pstmt = con.prepareStatement(sql);
				pstmt.setInt(1, num);
				pstmt.setString(2, productBean.getPro_name());	//상품 이름
				pstmt.setInt(3, productBean.getPro_price());	//상품 가격
				pstmt.setString(4, productBean.getPro_detail());	//상품 설명
				pstmt.setString(5, productBean.getPro_content());	//상품 내용
				pstmt.setString(6, productBean.getPro_photo());		//상품 사진
				pstmt.setInt(7, productBean.getCate_num());	//카테고리 번호
				pstmt.setString(8, String.valueOf(productBean.getMain_nb()));	//메인진열 N(NEW),B(BEST)
				pstmt.setString(9, String.valueOf(productBean.getActive()));	//활성화 여부 Y,N
				
				insertCount = pstmt.executeUpdate();
				
				if(insertCount == 0) {
					num = 0;
				}
			}
		}catch(SQLException e) {
			System.out.println("상품 등록 에러 " + e);
		}finally {
			close(rs);
			close(pstmt);
		}
		return num;
	}
	
	//3. 카테고리삭제. 관련 카테고리의 상품도 삭제
	public boolean deleteCategory(int cate_num) {
		// TODO Auto-generated method stub
		PreparedStatement pstmt = null;
		int deleteCount = 0;
		boolean isDeleteSuccess = false;
		String sql = "delete a, b, c from pro_info a inner join pro_det b on a.pro_num = b.pro_num ";
		sql += "inner join stock c on b.pro_det_num = c.pro_det_num where a.cate_num=?";
		
		try {
			pstmt = con.prepareStatement("delete from category where cate_num=?");
			pstmt.setInt(1, cate_num);
			
			deleteCount = pstmt.executeUpdate();
			
			if(deleteCount > 0) {
				pstmt = con.prepareStatement(sql);
				pstmt.setInt(1, cate_num);

				isDeleteSuccess = true;
			}
			
		}catch(SQLException e) {
			System.out.println("카테고리 삭제 에러 " + e);
		}finally {
			close(pstmt);
		}
		
		return isDeleteSuccess;
	}

	//4. 카테고리 수정
	public boolean updateCategory(int ca_ref, String cate_name, int cate_num) {
		// TODO Auto-generated method stub
		PreparedStatement pstmt = null;
		int updateCount = 0;
		boolean isUpdateSuccess = false;
		
		try {
			pstmt = con.prepareStatement("update category set ca_ref=?, category=? where cate_num=?");
			pstmt.setInt(1, ca_ref);
			pstmt.setString(2, cate_name);
			pstmt.setInt(3, cate_num);
			updateCount = pstmt.executeUpdate();
			
			if(updateCount > 0) {
				isUpdateSuccess = true;
			}
		}catch(SQLException e) {
			System.out.println("카테고리 수정 에러 " + e);
		}finally {
			close(pstmt);
		}
		return isUpdateSuccess;
	}
	
	//5. ca_ref로 하위 카테고리 리스트 만들기
	public ArrayList<CategoryBean> selectCategoryList(int ca_ref) {
		// TODO Auto-generated method stub
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		ArrayList<CategoryBean> categorySubList = new ArrayList<>();
		CategoryBean categoryBean = null;
		
		try {
			pstmt = con.prepareStatement("select * from category where ca_lev=1 and ca_ref=? order by ca_ref, ca_seq asc");
			pstmt.setInt(1, ca_ref);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				categoryBean = new CategoryBean();
				categoryBean.setCate_num(rs.getInt("cate_num"));
				categoryBean.setCategory(rs.getString("category"));
				categoryBean.setCa_ref(rs.getInt("ca_ref"));
				categoryBean.setCa_lev(rs.getInt("ca_lev"));
				categoryBean.setCa_seq(rs.getInt("ca_seq"));
				categorySubList.add(categoryBean);
			}
		}catch(SQLException e) {
			System.out.println("categoryList_select 에러 " + e);
		}finally {
			close(rs);
			close(pstmt);
		}
		
		return categorySubList;
	}
	
	//===========================상품 등록============================
	
	//1. 상품 상세 코드 색상 순서 불러오기
	public String selectProDetColorNum(int pro_num) {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		int cnt = 0;
		String color_num = "";
		
		// 상품상세코드 상품번호
		String num = String.format("%04d", pro_num);
		
		try {
			//상품 상세 코드의 
			pstmt = con.prepareStatement("SELECT count(if(pro_det_num like '" + num + "%', pro_det_num, null)) FROM pro_det");
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				cnt = rs.getInt(1) + 1;	//컬러 순서
				color_num = String.format("%02d", cnt);
			}
		}catch(SQLException e) {
			System.out.println("상품 상세 코드 색상 순서 불러오기 에러  " + e);
		}finally {
			close(rs);
			close(pstmt);
		}
		return color_num;
	}
		
	//2. 오늘 등록한 재고 번호의 개수 구하기
	public String selectStockCount() {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String stock_num = "";
		
		//재고 번호 등록하기 위해 날짜 형식 저장
		SimpleDateFormat format1 = new SimpleDateFormat ("yyyyMMdd");
		Date time = new Date();
		String time1 = format1.format(time);
		
		try {
			pstmt = con.prepareStatement("SELECT count(if(stock_num like '" + time1 + "%', stock_num, null)) FROM stock");
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				// 재고 갯수를 stock_cnt 변수에 저장하고 +1
				int stock_cnt = rs.getInt(1) + 1;
				stock_num = time1 + String.format("%04d", stock_cnt);
			}
		}catch(SQLException e) {
			System.out.println("오늘 등록한 재고 번호의 개수 구하기 에러 " + e);
		}finally {
			close(rs);
			close(pstmt);
		}
		return stock_num;
		
	}
	
	//3. 상품 상세 등록 + 처음 재고 설정
	public int insertPro_Det(int pro_num, String pro_det_num, String stock_num, String color, String size, int stock) {
		// TODO Auto-generated method stub
		PreparedStatement pstmt = null;
		int insertCount = 0;
		
		try {
			// 상품상세정보를 등록할 sql문
			pstmt = con.prepareStatement("insert into pro_det values(?, ?, ?, ?)");
			pstmt.setString(1, pro_det_num);	//상품 상세 코드
			pstmt.setInt(2, pro_num);	//상품 번호
			pstmt.setString(3, size);
			pstmt.setString(4, color);
				
			insertCount = pstmt.executeUpdate();
			
			// 성공적으로 등록이 되면
			if(insertCount > 0) {
				//입출고 테이블 - 입고 수량 추가
				pstmt = con.prepareStatement("insert into in_out_table select ifnull(max(in_out_num)+1, 1), ?, ?, 'IN', NOW() from in_out_table");
				pstmt.setString(1, pro_det_num);
				pstmt.setInt(2, stock);
				insertCount = pstmt.executeUpdate();
				
				if(insertCount > 0) {
					//재고 테이블 추가
					pstmt = con.prepareStatement("insert into stock values(?,?,?,NOW())");
					pstmt.setString(1, stock_num);
					pstmt.setString(2, pro_det_num);
					pstmt.setInt(3, stock);
	
					insertCount = pstmt.executeUpdate();
				}
			}else {
				insertCount = 0;
			}
		}catch(SQLException e) {
			System.out.println("상품 상세 등록 에러 " + e);
		}finally {
			close(pstmt);
		}
		return insertCount;
	}

	//===============================상품 목록=================================
	//1. 상품 리스트 count
	public int selectListCount(String search_type, String search_text, String cate_type, int ca_ref, String pro_date, String active) {
		// TODO Auto-generated method stub
		int listCount = 0;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "select count(*) from pro_info p inner join category c on p.cate_num = c.cate_num ";
		sql += "where " + search_type + " like '%" + search_text + "%' ";
		
		if(!pro_date.equals("all")) {
			sql += "and pro_date > date_add(now(), interval ? day)";
		}
		
		if(ca_ref != 0) {
			sql += "and " + cate_type + " = " + ca_ref;
		}
		
		if(!active.equals("all")) {
			sql += "and active = " + active;
		}
		
		try {
			pstmt = con.prepareStatement(sql);
			if(!pro_date.equals("all")) {
				pstmt.setString(1, pro_date);
			}
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				listCount = rs.getInt(1);
			}
		}catch(Exception e) {
			System.out.println("getListCount 에러 : " + e);
		}finally {
			close(rs);
			close(pstmt);
		}
		return listCount;
	}
	
	//2. 상품 목록 리스트에 저장
	public ArrayList<ProductBean> selectProductList(String search_type, String search_text, String cate_type,
			int ca_ref, String pro_date, String active, int page, int limit) {
		// TODO Auto-generated method stub
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "select * from pro_info p inner join category c on p.cate_num = c.cate_num ";
		sql += "where " + search_type + " like '%" + search_text + "%' ";
		
		if(!pro_date.equals("all")) {
			sql += "and pro_date > date_add(now(), interval ? day)";
		}
		
		if(ca_ref != 0) {
			sql += "and " + cate_type + " = " + ca_ref;
		}
		
		if(!active.equals("all")) {
			sql += "and active = " + active;
		}
		sql += " order by pro_date desc limit ?,?";

		ArrayList<ProductBean> prdList = new ArrayList<ProductBean>();
		ProductBean prd = null;
		int startrow = (page-1)*limit;	//읽기 시작할 row 번호
		
		try {
			int cnt = 0;
			pstmt = con.prepareStatement(sql);
			if(!pro_date.equals("all")) {
				pstmt.setString(++cnt, pro_date);
			}
			pstmt.setInt(++cnt, startrow);
			pstmt.setInt(++cnt, limit);
			
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				prd = new ProductBean();
				prd.setPro_num(rs.getInt("pro_num"));
				prd.setPro_name(rs.getString("pro_name"));
				prd.setPro_price(rs.getInt("pro_price"));
				prd.setPro_detail(rs.getString("pro_detail"));
				prd.setPro_content(rs.getString("pro_content"));
				prd.setPro_photo(rs.getString("pro_photo"));
				prd.setCate_num(rs.getInt("cate_num"));
				prd.setMain_nb(rs.getString("main_nb").charAt(0));
				prd.setActive(rs.getString("active").charAt(0));
				prd.setPro_date(rs.getString("pro_date"));
				prdList.add(prd);
			}
		}catch(Exception e) {
			System.out.println("getProductList 에러 : " + e);
		}finally {
			close(rs);
			close(pstmt);
		}
		
		return prdList;
	}
	
	//3. 상품정보 가져오기
	public ProductBean selectProInfo(int pro_num) {
		// TODO Auto-generated method stub
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		ProductBean productBean = null;
		try {
			pstmt = con.prepareStatement("select * from pro_info p inner join category c on p.cate_num = c.cate_num where p.pro_num=?");
			pstmt.setInt(1, pro_num);
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				productBean = new ProductBean();
				productBean.setPro_num(pro_num);
				productBean.setPro_name(rs.getString("pro_name"));
				productBean.setPro_price(rs.getInt("pro_price"));
				productBean.setPro_detail(rs.getString("pro_detail"));
				productBean.setPro_content(rs.getString("pro_content"));
				productBean.setPro_photo(rs.getString("pro_photo"));
				productBean.setCate_num(rs.getInt("cate_num"));
				productBean.setCa_ref(rs.getInt("ca_ref"));
				productBean.setCategory(rs.getString("category"));
				productBean.setMain_nb(rs.getString("main_nb").charAt(0));
				productBean.setActive(rs.getString("active").charAt(0));
				productBean.setPro_date(rs.getString("pro_date"));
			}
		}catch(Exception e) {
			System.out.println("selectProInfo 에러 : " + e);
		}finally {
			close(rs);
			close(pstmt);
		}
		return productBean;
	}

	//4. 상품 상세 정보 가져오기
	public ArrayList<ProDetBean> selectProDet(int pro_num) {
		// TODO Auto-generated method stub
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		ArrayList<ProDetBean> proDetList = new ArrayList<>();
		ProDetBean proDetBean = null;
		
		try {
			pstmt = con.prepareStatement("select * from pro_det p inner join stock s on p.pro_det_num = s.pro_det_num where p.pro_num = ?");
			pstmt.setInt(1, pro_num);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				proDetBean = new ProDetBean();
				proDetBean.setPro_det_num(rs.getString("pro_det_num"));
				proDetBean.setPro_num(pro_num);
				proDetBean.setPro_size(rs.getString("pro_size"));
				proDetBean.setColor(rs.getString("color"));
				proDetBean.setStock_qnt(rs.getInt("stock_qnt"));
				proDetList.add(proDetBean);
			}
		}catch(Exception e) {
			System.out.println("selectProDet 에러 : " + e);
		}finally {
			close(rs);
			close(pstmt);
		}
		return proDetList;
	}
	
	//5. 상품 정보 수정
	public boolean updateProduct(int pro_num, ProductBean productBean) {
		// TODO Auto-generated method stub
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		int updateCount = 0;
		boolean isUpdateSuccess = false;
		String sql = "update pro_info set pro_name=?, pro_price=?, pro_detail=?, pro_content=?, cate_num=?, main_nb=?, active=? ";
		if(productBean.getPro_photo()!=null) {
			sql += ",pro_photo=? ";
		}
		sql += "where pro_num=?";
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, productBean.getPro_name());	//상품 이름
			pstmt.setInt(2, productBean.getPro_price());	//상품 가격
			pstmt.setString(3, productBean.getPro_detail());	//상품 설명
			pstmt.setString(4, productBean.getPro_content());	//상품 내용
			pstmt.setInt(5, productBean.getCate_num());	//카테고리 번호
			pstmt.setString(6, String.valueOf(productBean.getMain_nb()));	//메인진열 N(NEW),B(BEST)
			pstmt.setString(7, String.valueOf(productBean.getActive()));	//활성화 여부 Y,N
			
			int cnt = 7;
			if(productBean.getPro_photo()!=null) {
				pstmt.setString(++cnt, productBean.getPro_photo());		//상품 사진
			}
			pstmt.setInt(++cnt, pro_num);
			
			updateCount = pstmt.executeUpdate();
				
			if(updateCount > 0) {
				isUpdateSuccess = true;
			}
		}catch(SQLException e) {
			System.out.println("updateProduct 에러 " + e);
		}finally {
			close(pstmt);
		}
		return isUpdateSuccess;
	}

	//6. 상품 수정 - 재고 수정
	public boolean updateStock(int pro_num, String pro_det_num, int stock_qnt) {
		// TODO Auto-generated method stub
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		int updateCount = 0;
		boolean isUpdateSuccess = false;
		
		try {
			pstmt = con.prepareStatement("select * from in_out_table where pro_det_num=?");
			pstmt.setString(1, pro_det_num);
			rs = pstmt.executeQuery();
			int in_qnt = 0;	//입고 수량
			int out_qnt = 0;	//출고 수량
			int result_qnt = 0;	//입고 - 출고 결과 수량
			int in_out_num = 0;
			while(rs.next()) {
				if(rs.getString("in_out").equals("IN")) {
					in_qnt += rs.getInt("in_out_qnt");
					in_out_num = rs.getInt("in_out_num");	//입출고테이블 번호 저장
				}else {
					out_qnt += rs.getInt("in_out_qnt");
				}
			}
			
			result_qnt = in_qnt - out_qnt;
			int temp_qnt = 0;
			temp_qnt = stock_qnt - result_qnt;
			System.out.println("temp_qnt=" + temp_qnt);
			
			String sql = "update in_out_table set in_out_qnt=in_out_qnt";
			if(temp_qnt >= 0) {
				sql+="+";
			}
			sql+= temp_qnt + " where in_out_num=?";
			System.out.println(sql);
			
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, in_out_num);
			updateCount = pstmt.executeUpdate();
			
			if(updateCount > 0) {
					
				pstmt = con.prepareStatement("update stock set stock_qnt=? where pro_det_num=?");
				pstmt.setInt(1, stock_qnt);
				pstmt.setString(2, pro_det_num);
				
				updateCount = pstmt.executeUpdate();
				
				if(updateCount > 0) {
					isUpdateSuccess = true;
				}
			}
		}catch(SQLException e) {
			System.out.println("updateStock 에러 " + e);
		}finally {
			close(rs);
			close(pstmt);
		}
		return isUpdateSuccess;
	}
	
	
	//7. 상품 삭제
	public boolean deleteProduct(int pro_num) {
		// TODO Auto-generated method stub
		PreparedStatement pstmt = null;
		int deleteCount = 0;
		boolean isDelSuccess = false;
		String sql = "delete a, b, c, d from pro_info a inner join pro_det b on a.pro_num = b.pro_num ";
		sql += "inner join stock c on b.pro_det_num = c.pro_det_num ";
		sql += "inner join in_out_table d on c.pro_det_num = d.pro_det_num where a.pro_num=?";
		
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, pro_num);
			deleteCount = pstmt.executeUpdate();
			
			if(deleteCount > 0) {
				isDelSuccess = true;
			}
		}catch(SQLException e) {
			System.out.println("상품 삭제 에러 " + e);
		}finally {
			close(pstmt);
		}
		
		return isDelSuccess;
	}

	//8. 상품 진열 활성화 여부 수정
	public boolean updateActive(int pro_num, String active) {
		// TODO Auto-generated method stub
		PreparedStatement pstmt = null;
		int updateCount = 0;
		boolean isUpdateSuccess = false;
		
		try {
			pstmt = con.prepareStatement("update pro_info set active=? where pro_num=?");
			pstmt.setString(1, active);
			pstmt.setInt(2, pro_num);
			updateCount = pstmt.executeUpdate();
			
			if(updateCount > 0) {
				isUpdateSuccess = true;
			}
		}catch(SQLException e) {
			System.out.println("updateActive 에러 " + e);
		}finally {
			close(pstmt);
		}
		return isUpdateSuccess;
	}

	//9. 상품 메인 진열 활성화 여부 수정
	public boolean updateMain_nb(int pro_num, String main_nb) {
		// TODO Auto-generated method stub
		PreparedStatement pstmt = null;
		int updateCount = 0;
		boolean isUpdateSuccess = false;
		
		try {
			pstmt = con.prepareStatement("update pro_info set main_nb=? where pro_num=?");
			pstmt.setString(1, main_nb);
			pstmt.setInt(2, pro_num);
			updateCount = pstmt.executeUpdate();
			
			if(updateCount > 0) {
				isUpdateSuccess = true;
			}
		}catch(SQLException e) {
			System.out.println("updateMain_nb 에러 " + e);
		}finally {
			close(pstmt);
		}
		return isUpdateSuccess;
	}

	//10. 상품 옵션 삭제 (pro_det, in_out_table, stock 테이블 제거)
	public boolean deleteProductOption(String pro_det_num) {
		// TODO Auto-generated method stub
		PreparedStatement pstmt = null;
		int deleteCount = 0;
		boolean isDeleteSuccess = false;
		String sql = "delete a, b, c from pro_det a inner join stock b on a.pro_det_num = b.pro_det_num ";
		sql += "inner join in_out_table c on b.pro_det_num = c.pro_det_num where a.pro_det_num=?";
		
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, pro_det_num);
			
			deleteCount = pstmt.executeUpdate();
			
			if(deleteCount > 0) {
				isDeleteSuccess = true;
			}
			
		}catch(SQLException e) {
			System.out.println("상품 옵션 삭제 에러 " + e);
		}finally {
			close(pstmt);
		}
		
		return isDeleteSuccess;
	}
	
	
		//↓↓↓↓↓↓↓↓ admin - 회원관리 ↓↓↓↓↓↓↓
		
		//회원 수 구하기
		public int selectListCount(String searchType, String searchText, String searchGrade, int startPrice, int endPrice, String startDate, String endDate) {
			
			int listCount = 0; 
			PreparedStatement pstmt = null;
			ResultSet rs = null;
			String sql = "SELECT COUNT(DISTINCT member.user_id) FROM member LEFT JOIN order_page ON member.user_id=order_page.user_id WHERE member." 
					+ searchType + " LIKE ?";
			
			if(!searchGrade.trim().equals("")) {
				sql += " AND grade = '" + searchGrade + "'";
			}
			String inputText =  "%" + searchText.trim() + "%";
			
			if(startPrice != 0 && endPrice == 0) {
				sql += " AND sel_status='order_confirm' AND final_price>=" + startPrice ;
			}else if(startPrice != 0 && endPrice != 0) {
				sql += " AND sel_status='order_confirm' AND final_price>=" + startPrice + " AND final_price<=" + endPrice;
			}
			
			if(!(startDate.trim().equals("") && endDate.trim().equals(""))) {
				sql += " AND sel_date BETWEEN '" + startDate + "' AND '" + endDate + "'";
			}
			
			
			try {
				pstmt = con.prepareStatement(sql);
				pstmt.setString(1, inputText);
				
				rs = pstmt.executeQuery();
				
				if(rs.next()) {
					listCount = rs.getInt(1);
				}
				
			}catch(Exception e) {
				System.out.println("selectListCount error : " + e);
			}finally {
				close(rs);
				close(pstmt);
			}
			
			
			return listCount;
		}

		
		//회원 검색 결과 
		public ArrayList<Member> selectMemberList(String searchType, String searchText, String searchGrade, int startPrice, int endPrice, String startDate, String endDate, int page, int limit) {
			
			PreparedStatement pstmt = null;
			ResultSet rs = null;
			String sql = "SELECT * FROM member LEFT JOIN order_page ON member.user_id=order_page.user_id WHERE member." + searchType + " LIKE ?";
			
			//고객등급 검색한 경우
			if(!searchGrade.trim().equals("")) {
				sql += " AND grade = '" + searchGrade + "'";
			}
			//text 입력내용
			String inputText =  "%" + searchText.trim() + "%";
			
			//주문가격 입력한 경우
			if(startPrice != 0 && endPrice == 0) {
				sql += " AND sel_status='order_confirm' AND final_price >=" + startPrice ;
			}else if(startPrice == 0 && endPrice != 0) {
				sql += " AND sel_status='order_confirm' AND final_price <=" + endPrice ;
			}else if(startPrice != 0 && endPrice != 0) {
				sql += " AND sel_status='order_confirm' AND final_price >=" + startPrice + " AND final_price <=" + endPrice;
			}
			
			//날짜 입력한 경우
			if(!(startDate.trim().equals("") && endDate.trim().equals(""))) {
				sql += " AND sel_date BETWEEN '" + startDate + "' AND '" + endDate + "'";
			}
			sql += " GROUP BY member.user_id ORDER BY joindate LIMIT ?, ?";
			
			
			ArrayList<Member> memberList = new ArrayList<Member>();
			Member member = null;
			int startrow = (page-1)*limit;
			
			try {
				pstmt = con.prepareStatement(sql);
				
				pstmt.setString(1, inputText);
				pstmt.setInt(2, startrow);
				pstmt.setInt(3, limit);
				rs = pstmt.executeQuery();
				
				while(rs.next()) {
					member = new Member();
					member.setUser_id(rs.getString("user_id"));
					member.setUser_name(rs.getString("user_name"));
					member.setSex(rs.getString("sex"));
					member.setTel(rs.getString("tel"));
					member.setPostcode(rs.getString("postcode"));
					member.setAddr1(rs.getString("addr1"));
					member.setAddr2(rs.getString("addr2"));
					member.setEmail(rs.getString("email"));
					member.setJoindate(rs.getString("joindate"));
					member.setGrade(rs.getString("grade").charAt(0));
					memberList.add(member);
				}
				
			}catch(Exception e) {
				System.out.println("selectMemberList error :" + e);
			}finally {
				close(rs);
				close(pstmt);
			}
			
			return memberList;
		}
		
		//회원 1명 정보 상세
		public Member selectMember(String user_id) {
			PreparedStatement pstmt = null;
			ResultSet rs = null;
			Member member = null;
			
			try {
				pstmt = con.prepareStatement("SELECT * FROM member WHERE user_id=?");
				pstmt.setString(1, user_id);
				rs = pstmt.executeQuery();
				
				if(rs.next()) {
					member = new Member();
					member.setUser_id(rs.getString("user_id"));
					member.setUser_name(rs.getString("user_name"));
					member.setSex(rs.getString("sex"));
					member.setTel(rs.getString("tel"));
					member.setPostcode(rs.getString("postcode"));
					member.setAddr1(rs.getString("addr1"));
					member.setAddr2(rs.getString("addr2"));
					member.setEmail(rs.getString("email"));
					member.setBirth(rs.getString("birth"));
					member.setJoindate(rs.getString("joindate"));
					member.setGrade(rs.getString("grade").charAt(0));				
				}
				
				
			}catch(Exception e) {
				e.printStackTrace();
				System.out.println("AdminDAO - selectMember error");
			}finally {
				close(rs);
				close(pstmt);
			}
			
			return member;
		}

		//회원정보에서 보는 주문목록 리스트 카운트
		public int selectOrderListCount(String user_id) {
		
			int listCount = 0; 
			PreparedStatement pstmt = null;
			ResultSet rs = null;
			
			String sql = "SELECT COUNT(*) FROM order_page WHERE user_id=?";
			
			try {
				pstmt = con.prepareStatement(sql);
				pstmt.setString(1, user_id);
				rs = pstmt.executeQuery();
				
				if(rs.next()) {
					listCount = rs.getInt(1);
					System.out.println("selectOrderListCount rs:"+ rs.getInt(1));
					
				}
				
			}catch(Exception e) {
				System.out.println("AdminDAO error: selectOrderListCount :" + e);
			}finally{
				close(rs);
				close(pstmt);
			}
			System.out.println("selectOrderListCount sql:" + sql);
			return listCount;
			
		}

		//회원정보에서 보는 주문내역
		public ArrayList<Order> selectOrderList(String user_id, int page, int limit) {
			
			PreparedStatement pstmt = null;
			ResultSet rs = null;
			ArrayList<Order> orderList = new ArrayList<Order>();
			Order order = null;
			int startRow = (page-1)*limit;
			String sql = "SELECT * FROM pro_info AS a INNER JOIN pro_det AS b ON a.pro_num=b.pro_num INNER JOIN order_det AS c "
					+ "ON b.pro_det_num=c.pro_det_num INNER JOIN order_page AS d ON c.sel_num=d.sel_num WHERE d.user_id=? GROUP BY d.sel_date ORDER BY d.sel_date desc LIMIT ?,?";
			
			try {
				pstmt = con.prepareStatement(sql);
				pstmt.setString(1, user_id);
				pstmt.setInt(2, startRow);
				pstmt.setInt(3, limit);
				rs = pstmt.executeQuery();
				
				while(rs.next()) {
					
					order = new Order();
					
					order.setPro_num(rs.getInt("pro_num"));
					order.setPro_photo(rs.getString("pro_photo"));
					order.setPro_price(rs.getInt("pro_price"));
					order.setPro_qnt(rs.getInt("pro_qnt"));
					order.setPro_size(rs.getString("pro_size"));
					order.setColor(rs.getString("color"));
					
					order.setUser_id(rs.getString("user_id"));
					order.setSel_num(rs.getString("sel_num"));
					order.setSel_date(rs.getString("sel_date"));
					order.setDeli_num(rs.getString("deli_num"));
					order.setSel_status(rs.getString("sel_status"));
					order.setDeli_price(rs.getInt("deli_price"));
					order.setPoint_use(rs.getInt("point_use"));
					order.setFinal_price(rs.getInt("final_price"));
					order.setPro_name(rs.getString("a.pro_name"));
					order.setCancel_req(rs.getString("cancel_req").charAt(0));
					orderList.add(order);
									
				}
				
			}catch(Exception e) {
				System.out.println("AdminDAO error: ArrayList<Order> selectOrderList :" + e);
			}finally {
				close(rs);
				close(pstmt);
			}
			System.out.println("ArrayList<Order> selectOrderList sql:" + sql);
			
			return orderList;
		}
		
		
		//주문관리에서 보는 주문 리스트 카운트 
		public int selectOrderListCount(String searchType, String searchText, String orderDate, String[] deliStatus, String[] cancelReq) {
			
			int listCount = 0; 
			PreparedStatement pstmt = null;
			ResultSet rs = null;
			
			String inputText =  "%" + searchText.trim() + "%";
			
			String sql = "SELECT COUNT(*) FROM order_manage_main WHERE " + searchType + " LIKE ?";
			
			if(!(orderDate == null || orderDate.trim().equals(""))) {
				sql += " AND sel_date='" + orderDate + "'";
			}
			
			if(!(deliStatus == null)) {
				
				if(deliStatus.length == 1) {
					sql += " AND (sel_status='" + deliStatus[0] + "')";
				}else if(deliStatus.length > 1) {
					
					sql += " AND (sel_status='" + deliStatus[0] + "')";
					
					for(int i = 1; i < deliStatus.length; i++) {
						
						sql += " OR (sel_status='" + deliStatus[i] + "')";
						
					}
				}	
			}
			if(!(cancelReq == null)) {
				if(cancelReq.length == 1) {
					sql += " AND (cancel_req='" + cancelReq[0] + "')";
				}else if(cancelReq.length > 1) {
					sql += " AND (cancel_req='" + cancelReq[0] + "') OR cancel_req ='" + cancelReq[1] + "')";
				}
			}
			
			
			try {
				pstmt = con.prepareStatement(sql);
				pstmt.setString(1, inputText);
				rs = pstmt.executeQuery();
				
				if(rs.next()) {
					listCount = rs.getInt(1);
				}
				
			}catch(Exception e) {
				System.out.println("selectListCount(OrderManage) error : " + e);
			}finally {
				close(rs);
				close(pstmt);
			}
			
			
			return listCount;
			
		}

		//주문관리에서 보는 주문 리스트 
		public ArrayList<Order> selectOrderList(String searchType, String searchText, String orderDate,
				String deliStatus[], String[] cancelReq, int page, int limit) {
			
			PreparedStatement pstmt = null;
			ResultSet rs = null;
			ArrayList<Order> orderList = new ArrayList<Order>();
			Order order = null;
			int startRow = (page-1)*limit;
			String inputText =  "%" + searchText.trim() + "%";
			
			String sql = "SELECT * FROM order_manage_main WHERE " + searchType  + " LIKE ?";
			
			if(!(orderDate == null || orderDate.trim().equals(""))) {
				sql += " AND sel_date='" + orderDate + "'";
			}
			
			
			
			if(!(deliStatus == null)) {
				if(deliStatus.length == 1) {
					sql += " AND (sel_status='" + deliStatus[0] + "')";
					
				}else if(deliStatus.length > 1) {
					sql += " AND (sel_status='" + deliStatus[0] + "')";
					
					for(int i = 1; i < deliStatus.length; i++) {
						sql += " OR (sel_status='" + deliStatus[i] + "')";
					}
				}	
			}
			
			if(!(cancelReq == null)) {
				if(cancelReq.length==1) {
					sql += " AND (cancel_req='" + cancelReq[0] + "')";
				}else if(cancelReq.length > 1) {
					sql += " AND (cancel_req='" + cancelReq[0] + "') OR (cancel_req='" + cancelReq[1] + "')";
				}
			}
			sql +=  " GROUP BY sel_date ORDER BY sel_date DESC LIMIT ?, ?";
			
			
			try {
				pstmt = con.prepareStatement(sql);
				pstmt.setString(1, inputText);
				pstmt.setInt(2, startRow);
				pstmt.setInt(3, limit);
				rs = pstmt.executeQuery();
				
				while(rs.next()) {
					
					order = new Order();
					order.setUser_id(rs.getString("user_id"));
					order.setSel_num(rs.getString("sel_num"));
					order.setSel_date(rs.getString("sel_date"));
					order.setDeli_num(rs.getString("deli_num"));
					order.setSel_status(rs.getString("sel_status"));
					order.setDeli_price(rs.getInt("deli_price"));
					order.setPoint_use(rs.getInt("point_use"));
					order.setFinal_price(rs.getInt("final_price"));
					order.setPro_name(rs.getString("pro_name"));
					order.setCancel_req(rs.getString("cancel_req").charAt(0));
					
					orderList.add(order);
				}
				
				
			}catch(Exception e) {
				System.out.println("AdminDAO error: ArrayList<Order> selectOrderList(OrderManage) :" + e);
			}finally {
				close(rs);
				close(pstmt);
			}
			System.out.println("ArrayList<Order> selectOrderList(OrderManage) sql:" + sql);
			
			return orderList;
		}
		
		//주문 상세페이지 - 주문상품리스트
		public ArrayList<OrderProView> selectOrderDet(String sel_num) {
			PreparedStatement pstmt = null;
			ResultSet rs = null;
			
			ArrayList<OrderProView> orderProList = new ArrayList<OrderProView>();
			OrderProView orderProView = null;
			
			
			try {
				pstmt = con.prepareStatement("SELECT * FROM order_detail_view WHERE sel_num=?");//뷰테이블 사용
				pstmt.setString(1, sel_num);
				rs = pstmt.executeQuery();
				
				while(rs.next()) {
					orderProView = new OrderProView();
					orderProView.setPro_num(rs.getInt("pro_num"));
					orderProView.setPro_name(rs.getString("pro_name"));
					orderProView.setPro_price(rs.getInt("pro_price"));
					orderProView.setPro_photo(rs.getString("pro_photo"));
					orderProView.setPro_det_num(rs.getString("pro_det_num"));
					orderProView.setPro_size(rs.getString("pro_size"));
					orderProView.setColor(rs.getString("color"));
					orderProView.setPro_qnt(rs.getInt("pro_qnt"));
					orderProView.setSel_num(sel_num);
					
					orderProList.add(orderProView);//리스트는 에드 해줘야한다.
									
				}
				
				
			}catch(Exception e) {
				e.printStackTrace();
				System.out.println("AdminDAO - selectOrderDet error");
			}finally {
				close(rs);
				close(pstmt);
			}
			return orderProList;
		}

		//주문상세페이지 - 배송정보
		public DeliInfo selectDeliInfo(String user_id) {
			PreparedStatement pstmt = null;
			ResultSet rs = null;
			DeliInfo deliInfo = null;
			
			try {
				pstmt = con.prepareStatement("SELECT * FROM deli_info WHERE user_id=?");
				pstmt.setString(1, user_id);
				rs = pstmt.executeQuery();
				
				if(rs.next()) {
					deliInfo = new DeliInfo();
					deliInfo.setDeli_num(rs.getString("deli_num"));
					deliInfo.setDeli_postcode(rs.getString("deli_postcode"));
					deliInfo.setDeli_addr1(rs.getString("deli_addr1"));
					deliInfo.setDeli_addr2(rs.getString("deli_addr2"));
					deliInfo.setDeli_content(rs.getString("deli_content"));
					deliInfo.setRec_name(rs.getString("rec_name"));
					deliInfo.setRec_tel(rs.getString("rec_tel"));
					
				}
			}catch(Exception e) {
				e.printStackTrace();
				System.out.println("AdminDAO - selectDeliInfo error");
			}finally {
				close(rs);
				close(pstmt);
			}
			
			return deliInfo;
		}
		
		//주문상세페이지 - 결제정보
		public PayInfo selectPayInfo(String sel_num) {
			PreparedStatement pstmt = null;
			ResultSet rs = null;
			PayInfo payInfo = null;
			
			try {
				pstmt = con.prepareStatement("SELECT * FROM pay_info WHERE sel_num=?");
				pstmt.setString(1, sel_num);
				rs = pstmt.executeQuery();
				
				if(rs.next()) {
					payInfo = new PayInfo();
					payInfo.setPay_date(rs.getString("pay_date"));
					payInfo.setPay_type(rs.getString("pay_type"));
					payInfo.setAcc_name(rs.getString("acc_name"));
					payInfo.setAcc_bank(rs.getString("acc_bank"));
					payInfo.setPay_exp(rs.getString("pay_exp"));
				}
			}catch(Exception e) {
				e.printStackTrace();
				System.out.println("AdminDAO - selectPayInfo error");
			}finally {
				close(rs);
				close(pstmt);
			}
			
			return payInfo;
		}
		
		//주문상세페이지 - 주문 정보(주문상품 제외), 배송상태 및 취소요청여부,취소사유
		public Order selectOrderInfo(String sel_num) {
			PreparedStatement pstmt = null;
			ResultSet rs = null;
			Order orderInfo = null;
			
			try {
				pstmt = con.prepareStatement("SELECT a.*, b.user_name FROM order_page AS a LEFT JOIN member AS b ON a.user_id=b.user_id WHERE sel_num=?");
				pstmt.setString(1, sel_num);
				rs = pstmt.executeQuery();
				
				if(rs.next()) {
					orderInfo = new Order();
					orderInfo.setSel_num(sel_num);
					orderInfo.setSel_date(rs.getString("sel_date"));
					orderInfo.setUser_id(rs.getString("user_id"));
					orderInfo.setSel_status(rs.getString("sel_status"));
					orderInfo.setDeli_price(rs.getInt("deli_price"));
					orderInfo.setPoint_use(rs.getInt("point_use"));
					orderInfo.setFinal_price(rs.getInt("final_price"));
					orderInfo.setUser_name(rs.getString("user_name"));
					orderInfo.setCancel_req(rs.getString("cancel_req").charAt(0));
					orderInfo.setCancel_reason(rs.getString("cancel_reason"));
				}
						
			}catch(Exception e) {
				e.printStackTrace();
				System.out.println("AdminDAO - selectOrderInfo error");
			}finally {
				close(rs);
				close(pstmt);
			}
			return orderInfo;
		}
		
		
		//주문상세 - 배송상태 변경
		public int updateDeliStatus(String selNum, String deliStatus) {
			int updateCount = 0;
			PreparedStatement pstmt = null;
			String sql = "UPDATE order_page SET sel_status=? WHERE sel_num=?";
			
			try {
				pstmt = con.prepareStatement(sql);
				pstmt.setString(1, deliStatus);
				pstmt.setString(2, selNum);
				updateCount = pstmt.executeUpdate();
			}catch(Exception e) {
				e.printStackTrace();
				System.out.println("AdminDAO - updateDeliStatus error");
			}finally {
				close(pstmt);
			}
			
			return updateCount;
		}
		
		//주문상세 - 주문 취소 승인
		public int updateCancelReq(String sel_num) {
			int updateCount = 0;
			PreparedStatement pstmt = null;
			String sql = "UPDATE order_page SET cancel_req='C' WHERE sel_num=?";
			
			try {
				pstmt = con.prepareStatement(sql);
				pstmt.setString(1, sel_num);
				updateCount = pstmt.executeUpdate();
			}catch(Exception e) {
				e.printStackTrace();
				System.out.println("AdminDAO - updateCancelReq error:" + e);
			}finally {
				close(pstmt);
			}
			return updateCount;
		}
		
		
		//자동메일옵션 관리페이지 불러오기
		public MailOption viewMailOption(int seq) {
			PreparedStatement pstmt = null;
			ResultSet rs = null;
			MailOption mailOption = null;
			
			try {
				pstmt = con.prepareStatement("SELECT * FROM mail_option WHERE seq=?");
				pstmt.setInt(1, seq);
				rs = pstmt.executeQuery();
				if(rs.next()) {
					mailOption = new MailOption();
					mailOption.setSeq(seq);
					mailOption.setNew_mem(rs.getInt("new_mem"));
					mailOption.setQuit_mem(rs.getInt("quit_mem"));
					mailOption.setOrder_info(rs.getInt("order_info"));
					mailOption.setCheck_paid(rs.getInt("check_paid"));
					mailOption.setSend_pro(rs.getInt("send_pro"));
					mailOption.setDeli_ing(rs.getInt("deli_ing"));
					mailOption.setDeli_fin(rs.getInt("deli_fin"));
					mailOption.setConfirm_order(rs.getInt("confirm_order"));
					mailOption.setAcc_cancel(rs.getInt("acc_cancel"));
					mailOption.setQna_re(rs.getInt("qna_re"));
					mailOption.setTitle(rs.getString("new_mem_title"));
					mailOption.setContent(rs.getString("new_mem_content"));
				}
			}catch(Exception e) {
				e.printStackTrace();
				System.out.println("AdminDAO - viewMailOption error :" + e);
			}finally {
				close(rs);
				close(pstmt);
			}
			return mailOption;
		}
		
		//자동메일옵션 하나만 찾기
		public int oneMailOption(String col) {
			int option = 0;
			PreparedStatement pstmt = null;
			ResultSet rs = null;
			
			try {
				pstmt = con.prepareStatement("SELECT " + col + " FROM mail_option WHERE seq=1");
				rs = pstmt.executeQuery();
				if(rs.next()) {
					option = rs.getInt(col);
				}
			}catch(Exception e) {
				e.printStackTrace();
				System.out.println("AdminDAO - viewMailOption error :" + e);
			}finally {
				close(rs);
				close(pstmt);
			}
			return option;
		}
		
		//자동메일옵션 설정저장
		public int updateMailOption(int newMem, int quitMem, int newOrder, int checkPaid,
				int sendPro, int deliIng, int deliFin, int confirmOrder, int accCancel, int qnaRe) {
			int updateCount = 0;
			PreparedStatement pstmt = null;
			String sql = "UPDATE mail_option SET new_mem=?, quit_mem=?, order_info=?, check_paid=?, "
					+ "send_pro=?, deli_ing=?, deli_fin=?, confirm_order=?, acc_cancel=?, qna_re=? WHERE seq=1";

			try {
				pstmt = con.prepareStatement(sql);
				pstmt.setInt(1, newMem);
				pstmt.setInt(2, quitMem);
				pstmt.setInt(3, newOrder);
				pstmt.setInt(4, checkPaid);
				pstmt.setInt(5, sendPro);
				pstmt.setInt(6, deliIng);
				pstmt.setInt(7, deliFin);
				pstmt.setInt(8, confirmOrder);
				pstmt.setInt(9, accCancel);
				pstmt.setInt(10, qnaRe);
				updateCount = pstmt.executeUpdate();
			}catch(Exception e) {
				e.printStackTrace();
				System.out.println("AdminDAO - updateMailOption error : " + e );
			}finally {
				close(pstmt);
			}
			
			return updateCount;
		}
		
		//자동메일 디자인,내용 저장
		public int saveMailForm(String col_title, String col_content, String title, String content) {
			int updateCount = 0;
			PreparedStatement pstmt = null;
			String sql = "UPDATE mail_option SET " + col_title + "=?, " + col_content + "=? WHERE seq=1";
			System.out.println("col_title:"+col_title);
			System.out.println("col_content:"+col_content);
			System.out.println("sql: "+sql);
			try {
				pstmt = con.prepareStatement(sql);
				pstmt.setString(1, title);
				pstmt.setString(2, content);
				updateCount = pstmt.executeUpdate();
			}catch(Exception e) {
				e.printStackTrace();
				System.out.println("AdminDAO - saveMailForm error : " + e);
			}finally {
				close(pstmt);
			}
			
			return updateCount;
		}
		//자동메일 폼 불러오기
		public MailOption selectMailForm(String col_title, String col_content) {
			System.out.println("col_title:"+col_title);
			System.out.println("col_content:"+col_content);
			MailOption mailOption = null;
			PreparedStatement pstmt = null;
			ResultSet rs = null;
			String sql = "SELECT " + col_title + ", " + col_content + " FROM mail_option WHERE seq=1";
			
			try {
				pstmt = con.prepareStatement(sql);
				
				rs = pstmt.executeQuery();
				if(rs.next()) {
					mailOption = new MailOption();
					mailOption.setTitle(rs.getString(col_title));
					mailOption.setContent(rs.getString(col_content));
					
					
				}
			}catch(Exception e) {
				e.printStackTrace();
				System.out.println("AdminDAO - selectMailForm error : "+e);
			}finally {
				close(rs);
				close(pstmt);
			}
			return mailOption;
		}
		
		//적립금 설정 페이지(설정값 불러오기)
		public PointMan viewPointOption(int seq) {
			PreparedStatement pstmt = null;
			ResultSet rs = null;
			PointMan pointMan = null;
			
			try {
				pstmt = con.prepareStatement("SELECT * FROM point_man WHERE p_seq=?");
				pstmt.setInt(1, seq);
				rs = pstmt.executeQuery();
				if(rs.next()) {
					pointMan = new PointMan();
					pointMan.setP_seq(seq);
					pointMan.setP_date(rs.getInt("p_date"));
					pointMan.setP_mark(rs.getString("p_mark"));
					pointMan.setP_rate(rs.getFloat("p_rate"));
					pointMan.setP_stand(rs.getString("p_stand"));
					pointMan.setP_newmem(rs.getInt("p_newmem"));
					pointMan.setP_pricelimit(rs.getInt("p_pricelimit"));
					pointMan.setP_pointlimit(rs.getInt("p_pointlimit"));
					pointMan.setP_review(rs.getInt("p_review"));
				}
			}catch(Exception e) {
				e.printStackTrace();
				System.out.println("AdminDAO - viewPointOption error");  
			}finally {
				close(rs);
				close(pstmt);
			}
			
			return pointMan;
		}
		//적립금 설정 저장
		public int settingPointOption(int pointDate, String markOption, float rate, String usePointOpt, int newMem,
				int priceLimit, int pointLimit, int reviewP) {
			int updateCount = 0;
			PreparedStatement pstmt = null;
			String sql = "UPDATE point_man SET p_date=?, p_mark=?, p_rate=?, p_stand=?, "
					+ "p_newmem=?, p_pricelimit=?, p_pointlimit=?, p_review=? WHERE p_seq=1";
			
			try {
				
				pstmt = con.prepareStatement(sql);
				pstmt.setInt(1, pointDate);
				pstmt.setString(2, markOption);
				pstmt.setFloat(3, rate);
				pstmt.setString(4, usePointOpt);
				pstmt.setInt(5, newMem);
				pstmt.setInt(6, priceLimit);
				pstmt.setInt(7, pointLimit);
				pstmt.setInt(8, reviewP);
				
				updateCount = pstmt.executeUpdate();
			}catch(Exception e) {
				e.printStackTrace();
				System.out.println("AdminDAO - settingPointOption error");
				System.out.println("sql:"+sql);
			}finally {
				close(pstmt);
			}
			
			return updateCount;
		}
		

		//===========================재고 목록=============================
		//재고 리스트 불러오기
		public ArrayList<ProDetBean> selectStockList(String[] pro_num) {
			// TODO Auto-generated method stub
			PreparedStatement pstmt = null;
			ResultSet rs = null;
			ResultSet rs2 = null;
			String sql =  "select * from stock s inner join pro_det p on s.pro_det_num = p.pro_det_num where p.pro_num = ?";
			
			ArrayList<ProDetBean> stockList = new ArrayList<>();
			ProDetBean proDetBean = null;
			int stock_num;
			String pro_det_num;
			int stock_qnt;
			String in_out;
			String inout_date;
			
			try {
				for(int i=0; i<pro_num.length; i++) {
					pstmt = con.prepareStatement(sql);
					pstmt.setInt(1, Integer.parseInt(pro_num[i]));
					rs = pstmt.executeQuery();
					
					while(rs.next()) {
						proDetBean = new ProDetBean();
						proDetBean.setPro_num(rs.getInt("pro_num"));
						proDetBean.setStock_num(rs.getString("stock_num"));
						proDetBean.setPro_det_num(rs.getString("pro_det_num"));
						proDetBean.setColor(rs.getString("color"));
						proDetBean.setPro_size(rs.getString("pro_size"));
						proDetBean.setStock_qnt(rs.getInt("stock_qnt"));
						proDetBean.setPro_num(rs.getInt("pro_num"));
						
						//출고된 재고 구하기
						pstmt = con.prepareStatement("select * from in_out_table where pro_det_num=? and in_out='OUT'");
						pstmt.setString(1, proDetBean.getPro_det_num());
						rs2 = pstmt.executeQuery();
						int out_stock_qnt = 0;
						
						while(rs2.next()) {
							out_stock_qnt += rs2.getInt("stock_qnt");
						}
						
						proDetBean.setOut_stock_qnt(out_stock_qnt);
						
						stockList.add(proDetBean);
					}
				}
			}catch(Exception e) {
				System.out.println("getProductList 에러 : " + e);
			}finally {
				close(rs2);
				close(rs);
				close(pstmt);
			}
			
			return stockList;
		}
		public int updateGrade(String grade, String user_id) {
			int updateCount = 0;
			PreparedStatement pstmt =null;
			
			try {
				pstmt = con.prepareStatement("update member set grade=? where user_id=? ");
				pstmt.setString(1, grade);
				pstmt.setString(2,user_id );
				updateCount = pstmt.executeUpdate();
				
			} catch (Exception ex) {
				System.out.println("updateGrade 에러 :" +ex);
			} finally {
				close(pstmt);
			}
			
			return updateCount;
			
			
		}
		
		
		
		
		
		
		
		
		
		
		
}
