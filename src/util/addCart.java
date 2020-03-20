package util;


import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.simple.JSONObject;

import com.google.gson.Gson;
import com.google.gson.JsonIOException;

import admin.svc.CategoryListService;
import vo.ActionForward;
import vo.Cart;
import vo.CategoryBean;
import vo.ProDetBean;
import vo.ProductBean;

/**
 * Servlet implementation class subCategoryList
 */
@WebServlet("/addCart")
public class addCart extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public addCart() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub

		String pro_det_num[] = request.getParameter("pro_det_num").split(",");
		String qnt[] = request.getParameter("qnt").split(",");
		String color[] = request.getParameter("color").split(",");
		String size[] = request.getParameter("pro_size").split(",");
		int pro_num = Integer.parseInt(request.getParameter("pro_num"));
		String pro_name = request.getParameter("pro_name");
		String pro_photo = request.getParameter("pro_photo");
		int pro_price = Integer.parseInt(request.getParameter("pro_price"));


		HttpSession session =request.getSession();
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
				System.out.println(pro_det_num[i]);
				System.out.println(qnt[i]);
				cartList.add(cart);
			}
			
			isNewCart = true;
		}
		
		for(int i=0; i<cartList.size(); i++) {
			System.out.println(cartList.get(i).getPro_det_num() + " : " + cartList.get(i).getBas_pro_qnt());
		}
		
	}
}
