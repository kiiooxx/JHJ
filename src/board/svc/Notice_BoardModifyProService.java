package board.svc;

import static db.JdbcUtil.*;

import java.sql.Connection;
import dao.Notice_BoardDAO;
import vo.Notice_BoardBean;

public class Notice_BoardModifyProService {

		public boolean modifyArticle(Notice_BoardBean article) throws Exception{
			
			
			boolean isModifySuccess = false;
			Connection con = getConnection();
			Notice_BoardDAO notice_boardDAO = Notice_BoardDAO.getInstance();
			notice_boardDAO.setConnection(con);
			int updateCount = notice_boardDAO.updateArticle(article);
			
			if(updateCount >0) {
				commit(con);
				isModifySuccess=true;
			}
			else {
				rollback(con);
			}
		
			close(con);
			return isModifySuccess;
		}
	}