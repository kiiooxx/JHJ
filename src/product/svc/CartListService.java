package product.svc;

import static db.JdbcUtil.close;
import static db.JdbcUtil.getConnection;

import java.sql.Connection;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import dao.ProductDAO;
import vo.Cart;

public class CartListService {

	public ArrayList<Cart> getCartList(HttpServletRequest request) {
		HttpSession session = request.getSession();
		ArrayList<Cart> cartList = (ArrayList<Cart>)session.getAttribute("cartList");
		return cartList;
	}

	public ArrayList<Cart> getCheckedCartList(String[] pro_det_num, HttpServletRequest request) {
		HttpSession session = request.getSession();
		
		ArrayList<Cart> cartList = (ArrayList<Cart>)session.getAttribute("cartList");
		System.out.println(cartList);
		ArrayList<Cart> cartList2 = new ArrayList<Cart>();
		int bas_num = 0;
		
		for(int i = 0; i < pro_det_num.length; i++) {
			
			for(int j = 0; j < cartList.size(); j++) {
				
				if(cartList.get(j).getPro_det_num().equals(pro_det_num[i])) {
					
					Cart cart = new Cart();
					bas_num += 1;
					cart.setBas_num(bas_num);
					cart.setPro_num(cartList.get(j).getPro_num());
					cart.setPro_name(cartList.get(j).getPro_name());
					cart.setPro_price(cartList.get(j).getPro_price());
					cart.setPro_photo(cartList.get(j).getPro_photo());
					cart.setColor(cartList.get(j).getColor());
					cart.setPro_size(cartList.get(j).getPro_size());
					cart.setPro_det_num(cartList.get(j).getPro_det_num());
					cart.setBas_pro_qnt(cartList.get(j).getBas_pro_qnt());
					cartList2.add(cart);
					System.out.println(cart.getPro_det_num() + "+" + cart.getBas_pro_qnt() );
				}	
			}
		}
		for(int i = 0; i<cartList2.size(); i++) {
			System.out.println("ㅋㅋㅋ" + cartList2.get(i).getPro_det_num());
		}
		return cartList2;
	}

	//로그인한 아이디의 장바구니 불러오기
	public ArrayList<Cart> getCartList(String id) {
		// TODO Auto-generated method stub
		ArrayList<Cart> cartList = null;
		Connection con = getConnection();
		ProductDAO productDAO = ProductDAO.getInstance();
		productDAO.setConnection(con);
		cartList = productDAO.selectCartList(id);
		close(con);
		return cartList;
	}

	

}
