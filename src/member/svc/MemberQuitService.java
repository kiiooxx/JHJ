package member.svc;

import static db.JdbcUtil.close;
import static db.JdbcUtil.commit;
import static db.JdbcUtil.getConnection;
import static db.JdbcUtil.rollback;

import java.sql.Connection;

import dao.MemberDAO;

public class MemberQuitService {

	public boolean memberDelUpdate(String delete_id) {
		// TODO Auto-generated method stub
		// TODO Auto-generated method stub
				boolean isUpdateSuccess = false;
				Connection con = getConnection();
				MemberDAO memberDAO = MemberDAO.getInstance();
				memberDAO.setConnection(con);
				int updateCount = memberDAO.memberDeleteUpdate(delete_id);
				
				if(updateCount  > 0) {
					commit(con);
					isUpdateSuccess = true;
				}else {
					rollback(con);
				}
				close(con);
				return isUpdateSuccess;
			}

		}
