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
import vo.ProductBean;


public class ProductDAO {
	Connection con;
	private static ProductDAO productDAO;
	
	private ProductDAO() {
		
	}
	
	public void setConnection(Connection con) {
		// TODO Auto-generated method stub
		this.con = con;
	}
	
	public static ProductDAO getInstance() {
		// TODO Auto-generated method stub
		if(productDAO == null) {
			productDAO = new ProductDAO();
		}
		
		return productDAO;
	}

	//카테고리 리스트 불러오기
	public ArrayList<CategoryBean> selectCategoryList() {
		// TODO Auto-generated method stub
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		ArrayList<CategoryBean> categoryList = new ArrayList<>();
		
		
		try {
			pstmt = con.prepareStatement("select * from category order by ca_ref, ca_seq asc");
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				categoryList.add(new CategoryBean(
							rs.getInt("cate_num"),
							rs.getString("category"),
							rs.getInt("ca_ref"),
							rs.getInt("ca_lev"),
							rs.getInt("ca_seq")));
			}
		}catch(SQLException e) {
			System.out.println("categoryList_select 에러 " + e);
		}finally {
			close(rs);
			close(pstmt);
		}
		
		return categoryList;
	}

	
	//카테고리 추가
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
			pstmt = con.prepareStatement("select max(pro_num) from pro_info2");
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				num = rs.getInt(1) + 1;
				
				sql = "insert into pro_info2 values (?, ?, ?, ?, ?, ?, ?, ?, ?, NOW())";
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
			close(pstmt);
		}
		return num;
	}

	//상품 상세 등록 + 처음 재고 설정
	public int insertPro_Det(int pro_num, String color, String size, int stock, int cnt) {
		// TODO Auto-generated method stub
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		int insertCount = 0;
		
		//재고 번호 등록하기 위해 날짜 형식 저장
		SimpleDateFormat format1 = new SimpleDateFormat ( "yyyyMMdd");
		Date time = new Date();
		String time1 = format1.format(time);
		
		// 재고번호 카운트
		int stock_cnt = 0;
		
		// 상품상세코드 상품번호 + 색상순서 + 사이즈
		String num = String.format("%04d", pro_num);
		String color_num = String.format("%02d", cnt);
		String pro_det_num = num + color_num + size;

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
				// 오늘 등록한 재고 번호의 갯수 구함.
				pstmt = con.prepareStatement("SELECT count(if(stock_num like '" + time1 + "%', stock_num, null)) FROM stock");
				rs = pstmt.executeQuery();
				
				if(rs.next()) {
					// 재고 갯수를 stock_cnt 변수에 저장하고 +1
					stock_cnt = rs.getInt(1) + 1;
					String stock_num = time1 + String.format("%04d", stock_cnt);
					
					// 재고 테이블 등록
					pstmt = con.prepareStatement("insert into stock values(?,?,?,'IN',NOW())");
					pstmt.setString(1, stock_num);
					pstmt.setString(2, pro_det_num);
					pstmt.setInt(3, stock);

					insertCount = pstmt.executeUpdate();
				}else {
					insertCount = 0;
				}
				
			}
		}catch(SQLException e) {
			System.out.println("상품 상세 등록 에러 " + e);
		}finally {
			close(pstmt);
		}
		return insertCount;
	}

}
