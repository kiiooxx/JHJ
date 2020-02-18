package member.svc;

import static db.JdbcUtil.close;
import static db.JdbcUtil.commit;
import static db.JdbcUtil.getConnection;
import static db.JdbcUtil.rollback;

import java.sql.Connection;

import dao.MemberDAO;

public class MemberQuitService {

	public boolean memberDel(String delete_id) {
		// TODO Auto-generated method stub
		// TODO Auto-generated method stub
				boolean isRemoveSuccess = false;
				Connection con = getConnection();
				MemberDAO memberDAO = MemberDAO.getInstance();
				memberDAO.setConnection(con);
				int deleteCount = memberDAO.memberDelete(delete_id);
				
				if(deleteCount > 0) {
					commit(con);
					isRemoveSuccess = true;
				}else {
					rollback(con);
				}
				close(con);
				return isRemoveSuccess;
			}

		}
