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
import vo.Member;
import vo.Order;
import vo.ProDetBean;
import vo.ProductBean;

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
	
	//카테고리 리스트 불러오기
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
			close(pstmt);
			close(rs);
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
		SimpleDateFormat format1 = new SimpleDateFormat ("yyyyMMdd");
		Date time = new Date();
		String time1 = format1.format(time);
		
		// 재고번호 카운트
		int stock_cnt = 0;
		
		// 상품상세코드 상품번호 + 순서 + 사이즈
		String num = String.format("%04d", pro_num);
		String color_num = String.format("%02d", cnt);
		String pro_det_num = num + color_num + size.substring(0,1);

		try {
			// 상품상세정보를 등록할 sql문
			pstmt = con.prepareStatement("insert into pro_det values(?, ?, ?, ?)");
			pstmt.setString(1, pro_det_num);	//상품 상세 코드
			pstmt.setInt(2, pro_num);	//상품 번호
			pstmt.setString(3, size);
			pstmt.setString(4, color);
			
			insertCount = pstmt.executeUpdate();
			System.out.println("들어갔니?" + insertCount);
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
			close(rs);
		}
		return insertCount;
	}

	//카테고리삭제. 관련 카테고리의 상품도 삭제
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

	public boolean updateCategory(int ca_ref, String cate_name, int cate_num) {
		// TODO Auto-generated method stub
		PreparedStatement pstmt = null;
		ResultSet rs = null;
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
	
	//ca_ref로 하위 카테고리 리스트 만들기
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

	//상품 리스트 검색하고 나온 갯수
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

	//상품 목록 리스트에 저장
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

	//상품정보 가져오기
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

	//상품 상세 정보 가져오기
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

	//상품 정보 수정
	public boolean updateProduct(int pro_num, ProductBean productBean) {
		// TODO Auto-generated method stub
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		int updateCount = 0;
		boolean isUpdateSuccess = false;
		String sql = "update pro_info set pro_name=?, pro_price=?, pro_detail=?, pro_content=?, cate_num=? main_nb=?, active=? ";
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
				
			if(updateCount == 0) {
				isUpdateSuccess = true;
			}
		}catch(SQLException e) {
			System.out.println("updateProduct 에러 " + e);
		}finally {
			close(pstmt);
		}
		return isUpdateSuccess;
	}

		
		
		//↓↓↓↓↓↓↓↓ admin - 회원관리 ↓↓↓↓↓↓↓
		
		//회원 수 구하기
		public int selectListCount(String searchType, String searchText, String searchGrade, int startPrice, int endPrice, String startDate, String endDate) {
			
			int listCount = 0; 
			PreparedStatement pstmt = null;
			ResultSet rs = null;
			String sql = "SELECT COUNT(DISTINCT member.user_id) FROM member LEFT JOIN order_page ON member.user_id=order_page.user_id WHERE member." + searchType + " LIKE ?";
			
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

		//회원정보에서 보는 주문내역 내용 보여주는
		public ArrayList<Order> selectOrderList(String user_id, int page, int limit) {
			
			PreparedStatement pstmt = null;
			ResultSet rs = null;
			ArrayList<Order> orderList = new ArrayList<Order>();
			Order order = null;
			int startRow = (page-1)*limit;
			System.out.println("startRow" + startRow);
			String sql = "SELECT * FROM order_page WHERE user_id=? LIMIT ?, ?";
			
			try {
				pstmt = con.prepareStatement(sql);
				pstmt.setString(1, user_id);
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

		public boolean deleteProduct(int pro_num) {
			// TODO Auto-generated method stub
			PreparedStatement pstmt = null;
			int deleteCount = 0;
			boolean isDeleteSuccess = false;
			String sql = "delete a, b, c from pro_info a inner join pro_det b on a.pro_num = b.pro_num ";
			sql += "inner join stock c on b.pro_det_num = c.pro_det_num where a.pro_num=?";
			
			try {
				pstmt = con.prepareStatement(sql);
				pstmt.setInt(1, pro_num);
				deleteCount = pstmt.executeUpdate();
				
				if(deleteCount > 0) {
					isDeleteSuccess = true;
				}
				
			}catch(SQLException e) {
				System.out.println("상품 삭제 에러 " + e);
			}finally {
				close(pstmt);
			}
			
			return isDeleteSuccess;
		}
}
