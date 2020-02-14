<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<table width="100%">
   <tr>
    <td align="right" width="65%">
     &nbsp;&nbsp;&nbsp;&nbsp; <a href="index.jsp"> 
     </td>
    <td align="right" width="35%" valign="middle">
    <b>아이디 : </b>
    <input type="text" size="10" maxlength="15" name="id" class="inputid">&nbsp;
      <b>비밀번호 : </b>
     <input type="password" size="10" maxlength="15"name="pass" class="inputpass">
      <br>
      <br>
      <a href="Findidready.jsp"><b>아이디 찾기 </b></a>
      
      <a href="Findpassready.jsp"><b>비밀번호 찾기</b></a>
      |&nbsp;
     <a href="member/regFormImpl.jsp"> <b>회원가입</b>
     </a></td>
   </tr>
  </table>
</body>
</html>


