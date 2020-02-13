

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
import vo.CategoryBean;

/**
 * Servlet implementation class subCategoryList
 */
@WebServlet("/subCategoryList")
public class subCategoryList extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public subCategoryList() {
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
		System.out.println("들어왔니?");
		System.out.println("ㅇㅇ" + request.getParameter("ca_ref"));
		int ca_ref = Integer.parseInt(request.getParameter("ca_ref"));
		
		ArrayList<CategoryBean> categorySubList = null;
		CategoryListService categoryListService = new CategoryListService();
		categorySubList = categoryListService.selectCategoryList(ca_ref);
		
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		
		//gson : 어레이 리스트를 json 형태로 바꿔줌 
		String gson = new Gson().toJson(categorySubList);
		
		
		try {
			response.getWriter().write(gson);
		}catch(JsonIOException e) {
			e.printStackTrace();
		}catch(IOException e) {
			e.printStackTrace();
		}
	}
}
