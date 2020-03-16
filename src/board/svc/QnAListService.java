package board.svc;

import static db.JdbcUtil.close;
import static db.JdbcUtil.getConnection;

import java.sql.Connection;
import java.util.ArrayList;

import dao.BoardDAO;
import vo.QnABean;

public class QnAListService {

	public int getListCount() {
		// TODO Auto-generated method stub
		int listCount = 0;
		Connection con = getConnection();
		BoardDAO boardDAO = BoardDAO.getInstance();
		boardDAO.setConnection(con);
		listCount = boardDAO.selectQnAListCount();
		close(con);
		return listCount;
	}

	public ArrayList<QnABean> getQnAList(int page, int limit) {
		// TODO Auto-generated method stub
		ArrayList<QnABean> qnaList = null;
		Connection con = getConnection();
		BoardDAO boardDAO = BoardDAO.getInstance();
		boardDAO.setConnection(con);
		qnaList = boardDAO.selectQnAList(page, limit);
		close(con);
		return qnaList;
	}

	public ArrayList<QnABean> getQnAList(int pro_num, int page, int limit) {
		// TODO Auto-generated method stub
		ArrayList<QnABean> qnaList = null;
		Connection con = getConnection();
		BoardDAO boardDAO = BoardDAO.getInstance();
		boardDAO.setConnection(con);
		qnaList = boardDAO.selectQnAList(pro_num, page, limit);
		close(con);
		return qnaList;
	}

}
