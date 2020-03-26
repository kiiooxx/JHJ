package product.svc;

import static db.JdbcUtil.*;

import java.sql.Connection;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import dao.ProductDAO;
import vo.Cart;

public class CartQtyService {

	public void upCartQty(String pro_det_num, HttpServletRequest request) {
		// TODO Auto-generated method stub
		HttpSession session = request.getSession();
		String id = (String)session.getAttribute("id");	//로그인 한 아이디
		
		ArrayList<Cart> cartList = (ArrayList<Cart>)session.getAttribute("cartList");
		
		for(int i=0; i<cartList.size(); i++) {
			if(cartList.get(i).getPro_det_num().equals(pro_det_num)) {
				cartList.get(i).setBas_pro_qnt(cartList.get(i).getBas_pro_qnt()+1);
				
				if(id != null) {
					cartQtyUpdate(cartList.get(i), id);
				}
			}
		}
		
		
	}
	
	public void downCartQty(String pro_det_num, HttpServletRequest request) {
		// TODO Auto-generated method stub
		HttpSession session = request.getSession();
		String id = (String)session.getAttribute("id");	//로그인 한 아이디
		ArrayList<Cart> cartList = (ArrayList<Cart>)session.getAttribute("cartList");
		
		for(int i=0; i<cartList.size(); i++) {
			if(cartList.get(i).getPro_det_num().equals(pro_det_num)) {
				cartList.get(i).setBas_pro_qnt(cartList.get(i).getBas_pro_qnt()-1);
				
				if(id != null) {
					cartQtyUpdate(cartList.get(i), id);
				}
			}
		}
	}

	public void delCartQty(String[] pro_det_num, HttpServletRequest request) {
		// TODO Auto-generated method stub
		HttpSession session = request.getSession();
		String id = (String)session.getAttribute("id");	//로그인 한 아이디
		ArrayList<Cart> cartList = (ArrayList<Cart>)session.getAttribute("cartList");
		
		for(int i=0; i<pro_det_num.length; i++) {
			for(int j=0; j<cartList.size(); j++) {
				if(cartList.get(j).getPro_det_num().equals(pro_det_num[i])) {
					if(id != null)	{
						cartDelete(cartList.get(j), id);
					}
					cartList.remove(cartList.get(j));
				}
			}
		}
	}
	
	//수량변경
	public void cartQtyUpdate(Cart cart, String id) {
		// TODO Auto-generated method stub
		ProductDAO productDAO = ProductDAO.getInstance();
		Connection con = getConnection();
		productDAO.setConnection(con);
		
		int updateCount = 0;
		
		updateCount = productDAO.updateQntCart(cart, id);
		
		if(updateCount > 0) {
			commit(con);
		}else {
			rollback(con);
		}
		
		close(con);
	}

	//장바구니 삭제
	public void cartDelete(Cart cart, String id) {
		ProductDAO productDAO = ProductDAO.getInstance();
		Connection con = getConnection();
		productDAO.setConnection(con);
		
		int deleteCount = 0;
		
		deleteCount = productDAO.deleteCart(cart, id);
		
		if(deleteCount > 0) {
			commit(con);
		}else {
			rollback(con);
		}
		
		close(con);
	}
	
	
	//장바구니 추가
	public void addCart(ArrayList<Cart> cartList, String id) {
		// TODO Auto-generated method stub
		ProductDAO productDAO = ProductDAO.getInstance();
		Connection con = getConnection();
		productDAO.setConnection(con);
		
		boolean isSuccess = true;
		
		int insertCount = 0;
		int updateCount = 0;
		
		for(int i=0; i<cartList.size(); i++) {
			//만약 같은 상품을 장바구니에 넣은경우 수량만 변경할수있도록..
			updateCount = productDAO.updateQntCart(cartList.get(i), id);
			
			if(updateCount == 0) {
				insertCount = productDAO.addCart(cartList.get(i), id);
				
				if(insertCount == 0) {
					isSuccess = false;
					rollback(con);
				}
			}
			updateCount = 0;
		}
		
		
		if(isSuccess) {
			commit(con);
		}else {
			rollback(con);
		}
		
		close(con);
	}

	
	public ArrayList<Cart> addCart(HttpServletRequest request) {
		
		String pro_det_num[] = request.getParameter("pro_det_num").split(",");
		String qnt[] = request.getParameter("qnt").split(",");
		String color[] = request.getParameter("color").split(",");
		String size[] = request.getParameter("pro_size").split(",");
		int pro_num = Integer.parseInt(request.getParameter("pro_num"));
		String pro_name = request.getParameter("pro_name");
		String pro_photo = request.getParameter("pro_photo");
		int pro_price = Integer.parseInt(request.getParameter("pro_price"));
		
		HttpSession session =request.getSession();
		String id = (String)session.getAttribute("id");	//로그인 한 아이디
		ArrayList<Cart> cartList = (ArrayList<Cart>)session.getAttribute("cartList");
		
		if(cartList == null) {
			cartList = new ArrayList<Cart>();
			session.setAttribute("cartList", cartList);
		}
		
		int bas_num = 0;
		for(int i=0; i<cartList.size(); i++) {
			Cart cart = new Cart();
			bas_num = cart.getBas_num();
		}
		
		// 지금 장바구니에 담는 항목이 새로 추가되는 항목인지를 저장할 변수
		boolean isNewCart = true;
		
		for(int i=0; i<pro_det_num.length; i++) {
			for(int j=0; j<cartList.size(); j++) {
				if(pro_det_num[i].equals(cartList.get(j).getPro_det_num())) {
					isNewCart = false;
					cartList.get(j).setBas_pro_qnt(cartList.get(j).getBas_pro_qnt()+Integer.parseInt(qnt[i]));
				}
			}
			
			if(isNewCart) {
				Cart cart = new Cart();
				bas_num += 1;
				cart.setBas_num(bas_num);
				cart.setPro_num(pro_num);
				cart.setPro_name(pro_name);
				cart.setPro_price(pro_price);
				cart.setPro_photo(pro_photo);
				cart.setColor(color[i]);
				cart.setPro_size(size[i]);
				cart.setPro_det_num(pro_det_num[i]);
				cart.setBas_pro_qnt(Integer.parseInt(qnt[i]));
				
				cartList.add(cart);
			}
			
			isNewCart = true;
		}
		
		//로그인한 아이디가 있다면 장바구니 DB에 저장
		if(id != null) {
			addCart(cartList, id);
		}
		
		return cartList;
	}

	//세션에 있는 장바구니를 DB에 넣기
	public ArrayList<Cart> addCart(ArrayList<Cart> cartList2, ArrayList<Cart> cartList, String id) {
		// TODO Auto-generated method stub
		
		
		// 지금 장바구니에 담는 항목이 새로 추가되는 항목인지를 저장할 변수
		boolean isNewCart = true;
		
		//cartList : 세션에 있는 장바구니
		//cartList2 : DB에 있는 장바구니
		for(int i=0; i<cartList.size(); i++) {
			for(int j=0; j<cartList2.size(); j++) {
				if(cartList.get(i).getPro_det_num().equals(cartList2.get(j).getPro_det_num())) {
					isNewCart = false;
					cartList2.get(j).setBas_pro_qnt(cartList2.get(j).getBas_pro_qnt()+cartList.get(i).getBas_pro_qnt());
				}
			}
			
			if(isNewCart) {		
				cartList2.add(cartList.get(i));
			}
			
			isNewCart = true;
		}
		
		addCart(cartList2, id);
		
		return cartList2;
	}
}
