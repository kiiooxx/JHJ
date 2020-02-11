package sh.USER;
//디비에서 값을 가져오는것, 직접 붙는애.
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import sh.DB.ConnectionMgr;
import sh.DB.DBClose;

 

public class UserDAO {

 ConnectionMgr cm = new ConnectionMgr();
 Connection con=null;

 

public String id_search(String name, String phone){  //이름,핸드폰번호로 찾기
  con=cm.getConnection();
  ResultSet rs=null;
  PreparedStatement pstmt =null; //보안적으로좋다
  String user_id=null; //찾을아이디
  String sql="select user_id from USERDB_JSH2 where USER_NAME=? and USER_PHONE=?";
  
  try{
   pstmt=con.prepareStatement(sql); //쿼리
   pstmt.setString(1, name); //첫번째 ?를 스트링 id로 넣음
   pstmt.setString(2, phone); //두번째 ?에 스트링 pw 넣음
   
   rs=pstmt.executeQuery(); //쿼리를 실행해서 결과값을 rs로 저장
   while(rs.next()){ //rs가 끝날때까지 반복
    user_id=rs.getString("user_id"); //cnt를 디비에서 가져온 cnt에 저장  
   }

  }catch(Exception e){
   System.out.println(e);
  }finally{
   new DBClose().close(con,pstmt,rs); //디비연결 종료
  }
  return user_id;
 }
 
 public String id_search2(String name, String email){  //이름,이메일로 찾기
  con=cm.getConnection();
  ResultSet rs=null;
  PreparedStatement pstmt =null; //보안적으로좋다
  String user_id=null ; //찾을아이디
  
  String sql="select user_id from USERDB_JSH2 where USER_NAME=? and USER_EMAIL=?";
  
  try{
   pstmt=con.prepareStatement(sql); //쿼리
   pstmt.setString(1, name); //첫번째 ?를 스트링 id로 넣음
   pstmt.setString(2, email); //두번째 ?에 스트링 pw 넣음
   
   rs=pstmt.executeQuery(); //쿼리를 실행해서 결과값을 rs로 저장
   while(rs.next()){  //rs가 끝날때까지 반복
    user_id=rs.getString("user_id"); //cnt를 디비에서 가져온 cnt에 저장  
   }
   new DBClose().close(con,pstmt,rs); //디비연결 종료

  }catch(Exception e){
   System.out.println(e);
  }
  return user_id;
 }

}