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
import vo.ProDetBean;
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

	//상품 리스트의 갯수 구하기
	public int selectListCount(int cate_num) {
		// TODO Auto-generated method stub
		int listCount = 0;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			//상품 관련 카테고리가 cate_num 인 값의 count 구하기
			pstmt = con.prepareStatement("select count(*) from category where ca_ref=?");
			pstmt.setInt(1, cate_num);
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

	//상품 리스트
	public ArrayList<ProductBean> selectProductList(int cate_num, int page, int limit) {
		// TODO Auto-generated method stub
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "select * from pro_info p inner join category c on p.cate_num = c.cate_num where c.ca_ref=? order by p.pro_date desc limit ?,?";
		ArrayList<ProductBean> prdList = new ArrayList<ProductBean>();
		ProductBean prd = null;
		int startrow = (page-1)*limit;	//읽기 시작할 row 번호
		
		try {	
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, cate_num);
			pstmt.setInt(2, startrow);
			pstmt.setInt(3, limit);
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
				prd.setCategory(rs.getString("category"));
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

	//상품 정보 불러오기
	public ProductBean selectProduct(int pro_num) {
		// TODO Auto-generated method stub
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		ProductBean prd = null;
		
		try {
			pstmt = con.prepareStatement("select * from pro_info where pro_num = ?");
			pstmt.setInt(1, pro_num);
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				prd = new ProductBean();
				prd.setPro_num(pro_num);
				prd.setPro_name(rs.getString("pro_name"));
				prd.setPro_price(rs.getInt("pro_price"));
				prd.setPro_detail(rs.getString("pro_detail"));
				prd.setPro_content(rs.getString("pro_content"));
				prd.setPro_photo(rs.getString("pro_photo"));
				prd.setCate_num(rs.getInt("cate_num"));
				prd.setActive(rs.getString("active").charAt(0));
				prd.setMain_nb(rs.getString("main_nb").charAt(0));
				prd.setPro_date(rs.getString("pro_date"));
			}
		}catch(Exception e) {
			System.out.println("selectProduct 에러 : " + e);
		}finally {
			close(rs);
			close(pstmt);
		}
		return prd;
	}

	public ArrayList<ProDetBean> selectProductDetail(int pro_num) {
		// TODO Auto-generated method stub
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		ProDetBean prdDet = null;
		ArrayList<ProDetBean> prdDetList = new ArrayList<>();
		try {
			pstmt = con.prepareStatement("select * from pro_det where pro_num = ?");
			pstmt.setInt(1, pro_num);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				prdDet = new ProDetBean();
				prdDet.setPro_det_num(rs.getString("pro_det_num"));
				prdDet.setPro_num(pro_num);
				prdDet.setPro_size(rs.getString("pro_size"));
				prdDet.setColor(rs.getString("color"));
				prdDetList.add(prdDet);
			}
		}catch(Exception e) {
			System.out.println("selectProductDetail 에러 : " + e);
		}finally {
			close(rs);
			close(pstmt);
		}
		return prdDetList;
	}

}
