<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>주문취소요청 조회</title>
<script src="https://code.jquery.com/jquery-3.4.1.min.js"></script>
</head>
<script>
function init() {
	   if("${param.openInit != null && param.openInit eq 'true'}") {
	      document.getElementById("reason").value = opener.document.getElementById("reason").value;
	      document.getElementById("sel_num").value = opener.document.getElementById("sel_num").value;
	   }else {
	      document.getElementById("reason").value = "${requestScope.reason}";
	   }
	}
	
function acceptBtn(f){
	if(confirm("취소요청을 승인하시겠습니까?")==true){
		f.submit;
	}else{
		return;
	}
}

/* function cancel() {
	location.href="acceptCancel.ad?sel_num=" + $('#sel_num').val();
} */
</script>
<body onload="init()">
<form action="acceptCancel.ad" method="post" name="f">

<input type="hidden" value="" id="sel_num" name="sel_num">

<h4>주문 취소 사유</h4>
<textarea id="reason" name="reason" cols="50" rows="10" readonly></textarea>

<input type="submit" value="취소승인" onclick="javascript:acceptBtn(document.f);">
<input type="button" value="닫기" onclick="self.close();">
</form>
</body>
</html>