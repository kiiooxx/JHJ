package board.action;

import java.io.PrintWriter;
import java.util.ArrayList;

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
		ActionForward forward = null;
		BoardBean boardBean = null;
		BoardBean boardBean_answer = null;	//답글
		BoardViewService boardViewService = new BoardViewService();
		
		//상품 번호
		int pro_num = 0;
		if(!(request.getParameter("pro_num") == null || request.getParameter("pro_num").equals(""))) {
			pro_num = Integer.parseInt(request.getParameter("pro_num"));
		}
		ProductDetailService prdDetailService = new ProductDetailService();
		ProductBean prd = prdDetailService.getProduct(pro_num);	//상품 정보 불러오기
		
		
		int board_num = Integer.parseInt(request.getParameter("board_num"));
		//DAO select문 조건
		String where = "board_num = ?";
		boardBean = boardViewService.getBoard(board_num, where);
		
		//답글이 있으면 불러오기
		if(boardBean.getBoard_step().equals("Y")) {
			where = "board_ref = ? and board_num != board_ref";
			boardBean_answer = boardViewService.getBoard(board_num, where);
		}
		
		boolean isUpdateSuccess = false;
		
		//게시글 불러왔으면 조회수 +1
		if(boardBean != null) {
			isUpdateSuccess = boardViewService.updateHits(board_num);
//			
//			//답글 조회수도 +1
//			if(boardBean.getBoard_step().equals("Y")) {
//				isUpdateSuccess = boardViewService.updateHits(boardBean_answer.getBoard_num());
//			}
		}
		
		
		if(!isUpdateSuccess) {
			response.setContentType("text/html;charset=UTF-8");
			PrintWriter out = response.getWriter();
			out.println("<script>");
			out.println("alert('fail!')");
			out.println("history.back()");
			out.println("</script>");
		}
		
		request.setAttribute("board", boardBean);
		request.setAttribute("board_answer", boardBean_answer);
		request.setAttribute("prd", prd);
		
		String path = request.getParameter("path");
		if(path.contains("/admin/")) {
			forward = new ActionForward(path + ".jsp", false);
		}else {
			request.setAttribute("pagefile", path + ".jsp");
			forward = new ActionForward("/template.jsp", false);
		}
		return forward;
	}

}
