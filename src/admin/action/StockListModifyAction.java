package admin.action;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import action.Action;
import admin.svc.StockListModifyService;
import vo.ActionForward;

public class StockListModifyAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// TODO Auto-generated method stub
		
		String[] pro_num = request.getParameterValues("pro_num");	//상품 번호
		String[] pro_det_num = request.getParameterValues("pro_det_num");	//상품 상세 코드
		String[] stock_qnt = request.getParameterValues("stock_qnt");	//변경할 재고 수량
		
		StockListModifyService stockListModifyService = new StockListModifyService();
		boolean isUpdateSuccess = stockListModifyService.updateStock(pro_num, pro_det_num, stock_qnt);
		
		if(!isUpdateSuccess) {
			response.setContentType("text/html;charset=UTF-8");
			PrintWriter out = response.getWriter();
			out.println("<script>");
			out.println("alert('재고 수정 실패');");
			out.println("history.back()");
			out.println("</script>");
		}
		
		ActionForward forward = null;
		forward = new ActionForward("stockListManagement.ad", true);
		return forward;
	}

}
