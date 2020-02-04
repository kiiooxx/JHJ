package dao;

import static db.JdbcUtil.close;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

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
			//카테고리 번호 불러오기ㄺ
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

}
