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
		
		//선택한 대분류 카테고리(cate_ref가 cate_num인 상품)에 있는 상품 and 진열 활성화가 되어 있는 상품만 select count 
		String sql = "select count(*) from pro_info a inner join category b on a.cate_num = b.cate_num ";
		sql += "where b.ca_ref=? and a.active='Y'";
		try {
			pstmt = con.prepareStatement(sql);
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
		
		String sql = "select * from pro_det a inner join stock b on a.pro_det_num = b.pro_det_num where a.pro_num=?";
		
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, pro_num);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				prdDet = new ProDetBean();
				prdDet.setPro_det_num(rs.getString("pro_det_num"));
				prdDet.setPro_num(pro_num);
				prdDet.setPro_size(rs.getString("pro_size"));
				prdDet.setColor(rs.getString("color"));
				prdDet.setStock_qnt(rs.getInt("stock_qnt"));
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

	public ArrayList<ProductBean> selectProductList() {
		// TODO Auto-generated method stub
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "select * from pro_info p inner join category c on p.cate_num = c.cate_num order by p.pro_date";
		ArrayList<ProductBean> prdList = new ArrayList<ProductBean>();
		ProductBean prd = null;
		
		try {	
			pstmt = con.prepareStatement(sql);
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

	
	//상품 리스트 - 베스트 or 신상품 개수 불러오기
	public int selectListCount(String main_nb) {
		// TODO Auto-generated method stub
		int listCount = 0;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		//pro_info 테이블의 main_nb 컬럼 값이 main_nb인 상품 and 진열 활성화가 되어 있는 상품만 select count 
		String sql = "select count(*) from pro_info where main_nb=? and active='Y'";
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, main_nb);
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

	public ArrayList<ProductBean> selectProductList(String main_nb, String orderBy, int page, int limit) {
		// TODO Auto-generated method stub
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		String sql = "select * from pro_info where main_nb=? order by ";
		sql+= orderBy + " limit ?,?";
		ArrayList<ProductBean> prdList = new ArrayList<ProductBean>();
		ProductBean prd = null;
		int startrow = (page-1)*limit;	//읽기 시작할 row 번호
		
		try {	
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, main_nb);
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
}
