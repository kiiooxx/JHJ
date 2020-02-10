<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<script>
function init(){
	if(<%=(request.getParameter("openInit")!=null&&request.getParameter("openInit").equals(true))?true:false%>){
		document.getElementById("id").value = opener.document.getElementById("id").value;  //부모페이지에 있는 아이디의 value를 자식페이지로
		
	}  
}


//아이디 사용하기 눌렀을 때 실행되는 함수.  joinForm 에 있는 idcheck.
function ok(v){ 
	opener.idcheck=v.trim();
	opener.document.getElementById("id").value=v;
	opener.chkId=true;
	window.close();
}
</script>

<body onload="init()">
<form action="<%=request.getContextPath() %>/idCheck.mem" method="post" name=f>
	<input type="text" name="id" id="userId" >	
	<input type="submit" value="중복확인">
</form>

<hr>

<%if(request.getAttribute("useableId") != null){ %>
	<%if((boolean)request.getAttribute("useableId")){ %>
	<h3><%=request.getAttribute("id") %>는 사용 가능한 아이디입니다.<a href="#" onclick="ok('<%=request.getAttribute("id") %>')">사용하기</a></h3> 
	<%}else{ %>
	<h3><%=request.getAttribute("id") %>는 사용 중인 아이디입니다.</h3>
	<%}%>	
<%}else{ %>
	아이디를 입력하세요.
<%} %>


</body>
</html>