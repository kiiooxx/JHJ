package admin.action;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import action.Action;
import admin.svc.ProductListManagementService;
import admin.svc.StockListManagementService;
import vo.ActionForward;
import vo.PageInfo;
import vo.ProDetBean;
import vo.ProductBean;
import vo.StockBean;

public class StockListManagementAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// TODO Auto-generated method stub
		request.setCharacterEncoding("UTF-8");
		String search_type = "pro_name";
		if(request.getParameter("search_type") != null) {
			search_type = request.getParameter("search_type");	//검색분류
		}
		String search_text = "";	//검색분류 텍스트
		if(request.getParameter("search_text") != null) {
			search_text = request.getParameter("search_text");
		}
		
		String cate_type = "";
		int ca_ref = 0;
		
		String c1 = request.getParameter("ca_ref");
		String c2 = request.getParameter("cate_sub");

		if(c1 != null) {
			if(c2 != null) {
				ca_ref = Integer.parseInt(c2);
				cate_type = "p.cate_num";
			}else{
				ca_ref = Integer.parseInt(c1);	//하위 카테고리값 선택안하고 대분류값만 있을시..!
				cate_type = "c.ca_ref";
			}
		}
		
		String pro_date = "all";
		if(request.getParameter("pro_date") != null) {
			pro_date = request.getParameter("pro_date");
		}
		
		String active = "all";
		if(request.getParameter("active") != null) {
			active = request.getParameter("active");
		}
		
		ArrayList<ProductBean> prdList = new ArrayList<>();
		ArrayList<ProDetBean> stockList = new ArrayList<>();
		
		int page = 1;
		int limit = 3;	//페이지에 보여줄 목록 수
		int limitPage = 5;	//페이지 수
		int listCount = 0;
		
		if(request.getParameter("page") != null) {
			page = Integer.parseInt(request.getParameter("page"));
		}
		
		ProductListManagementService prdListService = new ProductListManagementService();
		listCount = prdListService.getListCount(search_type, search_text, cate_type, ca_ref, pro_date, active);
		//총 리스트 수를 받아옴
		prdList = prdListService.getPrdList(search_type, search_text, cate_type, ca_ref, pro_date, active, page, limit);
		//리스트를 받아옴
		//총 페이지 수
		int maxPage = (int)((double)listCount/limit+0.95);
		//0.95를 더해서 올림 처리
		//현재 페이지에 보여줄 시작 페이지 수(1, 11, 21 등...)
		int startPage = (((int)((double)page/limitPage+0.9)) -1) * limitPage + 1;
	
		//현재 페이지에 보여줄 마지막 페이지 수.(10,20,30 등..)
		int endPage = startPage + limitPage-1;
		
		if(endPage > maxPage) endPage = maxPage;
		
		StockListManagementService stockListService = new StockListManagementService();
		StockBean ProDetBean = new ProDetBean();
		String pro_num = "";
		for(int i=0; i<prdList.size(); i++) {
			pro_num += prdList.get(i).getPro_num() + ",";
		}
		stockList = stockListService.getStockList(pro_num.split(","));
		
		PageInfo pageInfo = new PageInfo();
		pageInfo.setEndPage(endPage);
		pageInfo.setListCount(listCount);
		pageInfo.setMaxPage(maxPage);
		pageInfo.setPage(page);
		pageInfo.setStartPage(startPage);
		request.setAttribute("pageInfo", pageInfo);
		request.setAttribute("prdList", prdList);
		request.setAttribute("stockList", stockList);
		
		request.setAttribute("pagefile", "/admin/stockList_management.jsp");
		ActionForward forward = new ActionForward("/admin_template.jsp", false);
		return forward;
	}

}
