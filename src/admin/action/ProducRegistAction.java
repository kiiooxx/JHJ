package admin.action;

import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;
import com.sun.org.apache.xml.internal.security.utils.Base64;

import action.Action;
import admin.svc.ProductRegistService;
import vo.ActionForward;
import vo.ProDetBean;
import vo.ProductBean;
import vo.StockBean;

public class ProducRegistAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// TODO Auto-generated method stub
		
		ActionForward forward = null;
		ProductBean productBean = null;

		String realFolder = "";
		String saveFolder = "/upload";
		int fileSize = 5*1024*1024;
		ServletContext context = request.getServletContext();
		realFolder = context.getRealPath(saveFolder);
		MultipartRequest multi = new MultipartRequest(request, realFolder, fileSize, "UTF-8", new DefaultFileRenamePolicy());
		String image = multi.getFilesystemName("photo");
		System.out.println("이미지 " + image);
		productBean = new ProductBean();
		productBean.setPro_name(multi.getParameter("pro_name"));
		productBean.setPro_price(Integer.parseInt(multi.getParameter("pro_price")));
		productBean.setPro_detail(multi.getParameter("pro_detail"));
		productBean.setPro_content(multi.getParameter("pro_content"));
		productBean.setPro_photo(image);
		productBean.setActive(multi.getParameter("active").charAt(0));
		productBean.setMain_nb(multi.getParameter("main_nb").charAt(0));
		
		//대분류만 선택했을시
		if(multi.getParameter("cate_sub").equals("none")) {
			productBean.setCate_num(Integer.parseInt(multi.getParameter("ca_ref")));
		}else {
			productBean.setCate_num(Integer.parseInt(multi.getParameter("cate_sub")));
		}
		
		System.out.println(multi.getParameter("pro_content"));
		System.out.println(multi.getFilesystemName("photo"));
		
		if(!multi.getParameter("optionChk").equals("1")) {
			response.setContentType("text/html;charset=UTF-8");
			PrintWriter out = response.getWriter();
			out.println("<script>");
			out.println("alert('옵션품목 만들기 버튼을 눌러주세요!')");
			out.println("history.back()");
			out.println("</script>");
		}
		
		String[] color = multi.getParameterValues("pro_color");
		String[] pro_size = multi.getParameterValues("pro_size");
		String[] stock = multi.getParameterValues("stock");
		
		ArrayList<ProDetBean> proDetInfo = new ArrayList<>();
		for(int i=0; i<stock.length; i++) {
			proDetInfo.add(new ProDetBean(color[i], pro_size[i], Integer.parseInt(stock[i])));
		}
		
		ProductRegistService productRegistService = new ProductRegistService();
		boolean isRegistSuccess = productRegistService.registProduct(productBean, proDetInfo);
		
		if(isRegistSuccess) {
			forward = new ActionForward("productManagement.ad", true);
		}else {
			response.setContentType("text/html;charset=UTF-8");
			PrintWriter out = response.getWriter();
			out.println("<script>");
			out.println("alert('등록실패');");
			out.println("history.back()");
			out.println("</script>");
		}
		
		return forward;
	}

}
