package admin.svc;

import java.sql.Connection;
import java.util.ArrayList;

import dao.AdminDAO;

import static db.JdbcUtil.*;

import vo.Member;

public class MemberListService {

	public int getListCount(String searchType, String searchText, String searchGrade, int startPrice, int endPrice, String startDate, String endDate) throws Exception{
		
		int listCount = 0;
		Connection con = getConnection();
		AdminDAO adminDAO = AdminDAO.getInstance();
		adminDAO.setConnection(con);
		listCount = adminDAO.selectListCount(searchType, searchText, searchGrade, startPrice, endPrice, startDate, endDate);
		close(con);
		
		return listCount;
	}

	public ArrayList<Member> getMemberList(String searchType, String searchText, String searchGrade, int startPrice, int endPrice, String startDate, String endDate, int page, int limit) {
		
		Connection con = getConnection();
		AdminDAO adminDAO = AdminDAO.getInstance();
		adminDAO.setConnection(con);
		ArrayList<Member> memberList = null;
		memberList = adminDAO.selectMemberList(searchType, searchText, searchGrade, startPrice, endPrice, startDate, endDate, page, limit);
		close(con);
		
		return memberList;
	}

}
