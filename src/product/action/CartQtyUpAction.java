package product.action;
 
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import action.Action;
import product.svc.CartQtyService;
import vo.ActionForward;

public class CartQtyUpAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// TODO Auto-generated method stub
		String pro_det_num = request.getParameter("pro_det_num");
		CartQtyService cartQtyService = new CartQtyService();
		
		
		cartQtyService.upCartQty(pro_det_num, request);
		
		ActionForward forward = new ActionForward("cartList.pro", true);
		
		return forward;
	}

}
