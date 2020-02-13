package product.svc;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import vo.Cart;

public class CartQtyService {

	public void upCartQty(String pro_det_num, HttpServletRequest request) {
		// TODO Auto-generated method stub
		HttpSession session = request.getSession();
		ArrayList<Cart> cartList = (ArrayList<Cart>)session.getAttribute("cartList");
		
		for(int i=0; i<cartList.size(); i++) {
			if(cartList.get(i).getPro_det_num().equals(pro_det_num)) {
				cartList.get(i).setBas_pro_qnt(cartList.get(i).getBas_pro_qnt()+1);
			}
		}
	}

	public void downCartQty(String pro_det_num, HttpServletRequest request) {
		// TODO Auto-generated method stub
		HttpSession session = request.getSession();
		ArrayList<Cart> cartList = (ArrayList<Cart>)session.getAttribute("cartList");
		
		for(int i=0; i<cartList.size(); i++) {
			if(cartList.get(i).getPro_det_num().equals(pro_det_num)) {
				cartList.get(i).setBas_pro_qnt(cartList.get(i).getBas_pro_qnt()-1);
			}
		}
	}

	public void delCartQty(String[] pro_det_num, HttpServletRequest request) {
		// TODO Auto-generated method stub
		HttpSession session = request.getSession();
		ArrayList<Cart> cartList = (ArrayList<Cart>)session.getAttribute("cartList");
		
		for(int i=0; i<pro_det_num.length; i++) {
			for(int j=0; j<cartList.size(); j++) {
				if(cartList.get(j).getPro_det_num().equals(pro_det_num[i])) {
					cartList.remove(cartList.get(j));
				}
			}
		}
	}

}
