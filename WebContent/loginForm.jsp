<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" href="common.css" type="text/css">
<link rel="stylesheet" href="style.css" type="text/css">
</head>
<body>
<div class="path">
	<ol>
		<li><a href="#">경로</a></li>
		<li><a href="#">LOGIN</a></li>
	</ol>
</div>

<div class="login">
	<fieldset>
	<form action="login.log" name="loginform" method="post">
      <div class="loginBx">
      	<p class="nna">id</p>
      	<label class="id">
      		<input type="text" name="id" id="id"/>
      	</label>
      	<p class="nna">password</p>
         <label class="pass">
            <input type="password" name="pass" id="pass"/>
         </label>
         <p class="idsave">
         	<input type="checkbox" name="idsave" value="saveOk">SAVE ID
         </p>
         
         <p class="btx">
         	<a href="javascript:loginform.submit()" class="login_btn">LOGIN</a>
         	<a href="joinForm.mem" class="join_btn">JOIN MEMBER</a>
         </p>
         
        <ul class="forget">
           <li><a href="#">FIND ID</a> &nbsp;/&nbsp;
      			<a href="#">FIND PASSWORD</a></li>
        </ul>
      </div>
   </form>
   </fieldset>
</div>


</body>
</html>