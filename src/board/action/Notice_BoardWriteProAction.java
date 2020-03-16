package board.action;

import java.io.PrintWriter;
import java.io.PrintWriter;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;

import action.Action;
import board.svc.Notice_BoardWriteProService;
import vo.ActionForward;
import vo.Member;
import vo.Notice_BoardBean;

public class Notice_BoardWriteProAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// TODO Auto-generated method stub
		System.out.println("ㅇㅇㅇ");
		ActionForward forward = null;
		Notice_BoardBean notice_boardBean = null;
		Member member = null;
		notice_boardBean = new Notice_BoardBean();
		notice_boardBean.setNotice_title(request.getParameter("notice_title"));
		notice_boardBean.setNotice_content(request.getParameter("notice_content"));
		Notice_BoardWriteProService notice_boardwriteprosercvice = new Notice_BoardWriteProService();
		
		//메소드랑 연결
		boolean isWriteSuccess = notice_boardwriteprosercvice.registArticle(notice_boardBean);
		 if(!isWriteSuccess) {
			response.setContentType("text/html;charset=UTF-8");
			PrintWriter out = response.getWriter();
			out.println("<script>");
			out.println("alert('등록실패')");
			out.println("history.back()");
			out.println("</script>");
		}else {
			forward = new ActionForward("notice_boardList.bo",true);
			
		
		}
		return forward;
	}
	
}

		
		
	