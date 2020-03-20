package admin.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import action.Action;
import board.svc.BoardViewService;
import product.svc.ProductDetailService;
import vo.ActionForward;
import vo.BoardBean;
import vo.ProductBean;

public class BoardViewAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// TODO Auto-generated method stub
		
		int board_num = Integer.parseInt(request.getParameter("board_num"));
		int pro_num = 0;
		if(!(request.getParameter("pro_num") == null || request.getParameter("pro_num").equals(""))) {
			pro_num = Integer.parseInt(request.getParameter("pro_num"));
		}
		//상품 번호
		BoardViewService boardViewService = new BoardViewService();
		BoardBean boardBean = boardViewService.getBoard(board_num);
		
		ProductDetailService prdDetailService = new ProductDetailService();
		ProductBean prd = prdDetailService.getProduct(pro_num);	//상품 정보 불러오기
		request.setAttribute("board", boardBean);
		request.setAttribute("prd", prd);
		
		String path = request.getParameter("path") + ".jsp";
		ActionForward forward = new ActionForward("/admin/" + path, false);
		return forward;
		
	}

}
