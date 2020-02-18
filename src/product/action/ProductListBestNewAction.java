package product.action;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import action.Action;
import product.svc.ProductListBestNewService;
import product.svc.ProductListService;
import vo.ActionForward;
import vo.PageInfo;
import vo.ProductBean;

public class ProductListBestNewAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// TODO Auto-generated method stub
		ArrayList<ProductBean> prdList = new ArrayList<>();
		
		//베스트or신상품 여부 B = Best , N = New
		String main_nb = request.getParameter("main_nb");
		
		//진열 순서
		String orderBy = "pro_date desc";
		if(request.getParameter("orderBy") != null) {
			orderBy = request.getParameter("orderBy");
		}
		System.out.println(orderBy);
		int page = 1;
		int limit = 9;	//페이지에 보여줄 목록 수
		int limitPage = 3;	//페이지 수
		int listCount = 0;
		
		if(request.getParameter("page") != null) {
			page = Integer.parseInt(request.getParameter("page"));
		}
		
		ProductListBestNewService prdListBestNewService = new ProductListBestNewService();
		listCount = prdListBestNewService.getListCount(main_nb);
		//총 리스트 수를 받아옴
		prdList = prdListBestNewService.getPrdList(main_nb, orderBy, page,limit);
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
		request.setAttribute("main_nb", main_nb);
		
		request.setAttribute("pagefile", "/product/product_list_best_new.jsp");
		ActionForward forward = new ActionForward("/template.jsp", false);
		return forward;
	}

}
