<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<style>
table{
	border: 1px solid;
	width: 50%;
	text-align: center;
	
}
tr td{
	border: 1px solid;
	
}
tr{
	height: 50px;
}

</style>
<body>
<form action="mailManagement.ad" method="post">
<table>
	<tr>
		<td>메일항목</td>
		<td>사용여부</td>
		<td>수정</td>
	</tr>
	<tr>
		<td>신규회원가입</td>
		<td><input type="checkbox" name="A" value="T"/></td>
		<td>수정 | 미리보기</td>
	</tr>
	<tr>
		<td>회원탈퇴</td>
		<td><input type="checkbox" name="B" value="T"/></td>
		<td>수정 | 미리보기</td>
	</tr>
	<tr>
		<td>신규주문내역</td>
		<td><input type="checkbox" name="C" value="T"/></td>
		<td>수정 | 미리보기</td>
	</tr>
	<tr>
		<td>결제확인</td>
		<td><input type="checkbox" name="D" value="T"/></td>
		<td>수정 | 미리보기</td>
	</tr>
	<tr>
		<td>상품발송</td>
		<td><input type="checkbox" name="E" value="T"/></td>
		<td>수정 | 미리보기</td>
	</tr>
	<tr>
		<td>배송중</td>
		<td><input type="checkbox" name="F" value="T"/></td>
		<td>수정 | 미리보기</td>
	</tr>
	<tr>
		<td>배송완료</td>
		<td><input type="checkbox" name="G" value="T"/></td>
		<td>수정 | 미리보기</td>
	</tr>
	<tr>
		<td>구매확정</td>
		<td><input type="checkbox" name="H" value="T"/></td>
		<td>수정 | 미리보기</td>
	</tr>
	<tr>
		<td>주문취소승인</td>
		<td><input type="checkbox" name="I" value="T"/></td>
		<td>수정 | 미리보기</td>
	</tr>	
</table>
<input type="submit" value="변경사항저장">
</form>
</body>
</html>