package product.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import action.Action;
import product.svc.CartQtyService;
import vo.ActionForward;

public class CartDeleteAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// TODO Auto-generated method stub
		String pro_det_num[] = request.getParameter("pro_det_num").split(",");
		CartQtyService cartQtyService = new CartQtyService();
		cartQtyService.delCartQty(pro_det_num, request);
		
		ActionForward forward = new ActionForward("cartList.pro", true);
		
		return forward;
	}

}
