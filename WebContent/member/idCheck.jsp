<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>아이디 중복 확인</title>
</head>
<style>
table {
	margin: auto;
	width: 300px;
	text-align: center;
}

input {
	outline: 0;
	background: #f2f2f2;
	width: 100%;
	border: 0;
	padding: 10px;
	box-sizing: border-box;
	font-size: 12px;
}

#testform {
	width: 300px;
	text-align: center;
	margin: 0 auto;
}

a img {
	height: 16px;
}

a  {
	text-decoration: none;
	color: gray;
	font-weight: bold;
}
a:hover, a:active{
text-decoration: none;
	color: purple;
	font-weight: bold;

}

</style>
<script>


function init(){
	if("${param.openInit != null && param.openInit eq 'true'}"){
		document.getElementById("id").value = opener.document.getElementById("id").value;
	}
}

//아이디 사용하기 눌렀을 때 실행되는 함수.  joinForm 에 있는 idcheck.
function ok(v){ 
	opener.idcheck=v.trim();
	opener.document.getElementById("id").value=v;
	opener.chkId=true;
	window.close();
}

function chkForm(f){
	var id = document.getElementById("id");
	var re = /^[a-zA-Z0-9]{4,12}$/;
	
	if(f.id.value.trim()==""){
		alert("아이디를 입력해주세요.");
		f.id.focus();
		return false;
	}
	if(!check(re,id,"아이디는 4~12자의 영문 대소문자 + 숫자로만 입력해주세요.")) {
        return false;
    }
	
	f.submit();
}

function check(re, what, message) {
    if(re.test(what.value)) {
        return true;
    }
    alert(message);
    what.value = "";
    what.focus();
    //return false;
}
</script>
<!--  -->
<body onload="init()">
<form action="idCheck.mem" method="post" name=f>
<table id="addRow">
	<tr>
		<td><h3>아이디 중복검사</h3></td>
	</tr>
	<tr>
		<td><input type="text" name="id" id="id" placeholder="아이디" required></td>
	</tr>
	<tr>
		<td>
		<a href="javascript:chkForm(document.f);">중복확인</a>
		</td>
	</tr>
</table>
		
	

</form>

<hr>
<br>
<div id="testform">
<c:choose>
<c:when test="${useableId ne null }">
	<c:choose>
		<c:when test="${useableId == 'true' }">
			${id }는 사용가능한 아이디입니다.<a href="#" onclick="ok('${id}')"><br>사용하기</a>
		</c:when>
		<c:otherwise>
			${id }는 사용 중인 아이디입니다.
		</c:otherwise>
	</c:choose>
</c:when>
<c:otherwise>
	아이디를 입력하세요. <br>( 4~12자의 영문 대소문자 + 숫자 )
</c:otherwise>
</c:choose>

</div>

</body>
</html>