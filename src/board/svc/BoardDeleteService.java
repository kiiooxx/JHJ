package board.svc;

import static db.JdbcUtil.close;
import static db.JdbcUtil.commit;
import static db.JdbcUtil.getConnection;
import static db.JdbcUtil.rollback;

import java.sql.Connection;

import dao.BoardDAO;
import vo.BoardBean;

public class BoardDeleteService {

	public boolean deleteBoard(String[] board_num) {
		// TODO Auto-generated method stub
		Connection con = getConnection();
		BoardDAO boardDAO = BoardDAO.getInstance();
		boardDAO.setConnection(con);
		boolean isDeleteSuccess = false;
		for(int i=0; i<board_num.length; i++) {
			isDeleteSuccess = false;

			//삭제할 글을 불러온다.
			BoardBean boardBean = boardDAO.selectBoard(Integer.parseInt(board_num[i]), "board_num = ?");
			
			//만약 그 글이 답글이라면
			if(boardBean.getBoard_num() != boardBean.getBoard_ref()) {
				//관련글 번호의 답글여부(board_step)를 N으로 바꾼다.
				boolean isUpdateSuccess = boardDAO.updateBoardStep("N", boardBean.getBoard_ref());
				
				if(!isUpdateSuccess) {
					rollback(con);
					break;
				}
			}
			isDeleteSuccess = boardDAO.deleteBoard(Integer.parseInt(board_num[i]));
			if(!isDeleteSuccess) {
				rollback(con);
				break;
			}	
			
		}
		
		if(isDeleteSuccess) {
			commit(con);
		}else {
			rollback(con);
		}
		
		close(con);
		return isDeleteSuccess;
	}

	

}
