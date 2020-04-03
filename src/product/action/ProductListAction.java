package product.action;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import action.Action;
import product.svc.ProductListService;
import vo.ActionForward;
import vo.PageInfo;
import vo.ProductBean;

public class ProductListAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// TODO Auto-generated method stub
		ArrayList<ProductBean> prdList = new ArrayList<>();
		
		//카테고리 이름 받아온다.
		int cate_num = Integer.parseInt(request.getParameter("cate_num"));
		String category = request.getParameter("category");
		
		//서브 카테고리 이름 선택했을 때
		int cate_sub_num = 0;
		if(request.getParameter("cate_sub_num") != null) {
			cate_sub_num = Integer.parseInt(request.getParameter("cate_sub_num"));
		}
		
		//진열 순서
		String orderBy = "p.pro_date desc";
		if(request.getParameter("orderBy") != null) {
			orderBy = request.getParameter("orderBy");
		}
		System.out.println(orderBy);
		int page = 1;
		int limit = 6;	//페이지에 보여줄 목록 수
		int limitPage = 3;	//페이지 수
		int listCount = 0;
		
		if(request.getParameter("page") != null) {
			page = Integer.parseInt(request.getParameter("page"));
		}
		
		ProductListService prdListService = new ProductListService();
		listCount = prdListService.getListCount(cate_num, cate_sub_num, orderBy, page,limit);
		//총 리스트 수를 받아옴
		prdList = prdListService.getPrdList(cate_num, cate_sub_num, orderBy, page,limit);
		//리스트를 받아옴
		//총 페이지 수
		int maxPage = (int)((double)listCount/limit+0.95);
		//0.95를 더해서 올림 처리
		//현재 페이지에 보여줄 시작 페이지 수(1, 11, 21 등...)
		int startPage = (((int)((double)page/limitPage+0.9)) -1) * limitPage + 1;
	
		//현재 페이지에 보여줄 마지막 페이지 수.(10,20,30 등..)
		int endPage = startPage + limitPage-1;
		
		if(endPage > maxPage) endPage = maxPage;
		
		PageInfo pageInfo = new PageInfo();
		pageInfo.setEndPage(endPage);
		pageInfo.setListCount(listCount);
		pageInfo.setMaxPage(maxPage);
		pageInfo.setPage(page);
		pageInfo.setStartPage(startPage);
		request.setAttribute("pageInfo", pageInfo);
		request.setAttribute("prdList", prdList);
		request.setAttribute("cate_num", cate_num);
		request.setAttribute("category", category);
		request.setAttribute("cate_sub_num", cate_sub_num);
		request.setAttribute("orderBy", orderBy);
		
		request.setAttribute("pagefile", "/product/product_list.jsp");
		ActionForward forward = new ActionForward("/template.jsp", false);
		return forward;
	}

}
