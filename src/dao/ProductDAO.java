package dao;

import static db.JdbcUtil.close;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import vo.Cart;
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
	public ArrayList<ProductBean> selectProductList(int cate_num, int cate_sub_num, String orderBy, int page, int limit) {
		// TODO Auto-generated method stub
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		String sql = "select * from pro_info p inner join category c on p.cate_num = c.cate_num where ";
		if(cate_sub_num != 0) {
			sql += "p.cate_num=" + cate_sub_num + " order by ";
		}else {
			sql += "c.ca_ref=" + cate_num + " order by ";
		}
		
		sql+= orderBy + " limit ?,?";
		ArrayList<ProductBean> prdList = new ArrayList<ProductBean>();
		ProductBean prd = null;
		int startrow = (page-1)*limit;	//읽기 시작할 row 번호
		
		try {	
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, startrow);
			pstmt.setInt(2, limit);
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

	//1. 장바구니 등록
	public int addCart(Cart cart, String id) {
		// TODO Auto-generated method stub
		PreparedStatement pstmt1 = null;
		PreparedStatement pstmt2 = null;
		ResultSet rs = null;
		
		int insertCount = 0;
		
		int mem_bas_num = 0;
		
		//장바구니 번호 불러오기
		String sql1 = "select ifnull(max(mem_bas_num)+1, 1) from basket where user_id = '" + id + "'";
		
		String sql2 = "insert into basket(mem_bas_num, pro_det_num, user_id, bas_pro_qnt) values(?, ?, ?, ?)";
		
		try {
			pstmt1 = con.prepareStatement(sql1);
			rs = pstmt1.executeQuery();
			
			if(rs.next()) {
				mem_bas_num = rs.getInt(1);
				
				pstmt2 = con.prepareStatement(sql2);
				pstmt2.setInt(1, mem_bas_num);
				pstmt2.setString(2, cart.getPro_det_num());
				pstmt2.setString(3, id);
				pstmt2.setInt(4, cart.getBas_pro_qnt());
				
				insertCount = pstmt2.executeUpdate();
				
			}

		}catch(SQLException e) {
			System.out.println("장바구니 추가 에러 " + e);
		}finally {
			close(rs);
			close(pstmt2);
			close(pstmt1);
		}
		return insertCount;
	}

	//2. 장바구니 수량 수정
	public int updateQntCart(Cart cart, String id) {
		// TODO Auto-generated method stub
		PreparedStatement pstmt = null;
		
		int updateCount = 0;
		
		String sql = "update basket set bas_pro_qnt=? where pro_det_num=? and user_id=?";
		
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, cart.getBas_pro_qnt());
			pstmt.setString(2, cart.getPro_det_num());
			pstmt.setString(3, id);
			updateCount = pstmt.executeUpdate();
		}catch(SQLException e) {
			System.out.println("장바구니 수량 수정 에러 " + e);
		}finally {
			close(pstmt);
		}
		return updateCount;
	}

	//3. 장바구니 삭제
	public int deleteCart(Cart cart, String id) {
		// TODO Auto-generated method stub
		PreparedStatement pstmt = null;
		
		int deleteCount = 0;
		
		String sql = "delete from basket where pro_det_num=? and user_id=?";
		
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, cart.getPro_det_num());
			pstmt.setString(2, id);
			deleteCount = pstmt.executeUpdate();
		}catch(SQLException e) {
			System.out.println("장바구니 삭제 에러 " + e);
		}finally {
			close(pstmt);
		}
		return deleteCount;
	}

	//4. 로그인한 아이디의 장바구니 불러오기
	public ArrayList<Cart> selectCartList(String id) {
		// TODO Auto-generated method stub
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		String sql = "select * from basket a inner join pro_det b on a.pro_det_num = b.pro_det_num " +
				 	"inner join pro_info c on b.pro_num = c.pro_num inner join stock d on b.pro_det_num = d.pro_det_num "
				 	+ "where a.user_id=? and d.stock_qnt>0 order by a.mem_bas_num desc";
		ArrayList<Cart> cartList = new ArrayList<Cart>();
		Cart cart = null;

		try {	
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, id);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				cart = new Cart();
				cart.setBas_num(rs.getInt("bas_num"));
				cart.setMem_bas_num(rs.getInt("mem_bas_num"));
				cart.setPro_num(rs.getInt("pro_num"));
				cart.setPro_name(rs.getString("pro_name"));
				cart.setPro_price(rs.getInt("pro_price"));
				cart.setPro_photo(rs.getString("pro_photo"));
				cart.setColor(rs.getString("color"));
				cart.setPro_size(rs.getString("pro_size"));
				cart.setPro_det_num(rs.getString("pro_det_num"));
				cart.setBas_pro_qnt(rs.getInt("bas_pro_qnt"));
				cart.setUser_id(rs.getString("user_id"));
			
				cartList.add(cart);
			}
		}catch(Exception e) {
			System.out.println("getCartList 에러 : " + e);
		}finally {
			close(rs);
			close(pstmt);
		}
		
		return cartList;
	}
}
