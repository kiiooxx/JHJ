<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">

<script src="http://code.jquery.com/jquery-latest.js"></script>
<script>
	function domainCheck(value) {
		if (value == 0) {
			document.getElementById("emailInput").value = "";
		} else {
			document.getElementById("emailInput").value = value;
		}
	}
</script>
<title>비밀번호 찾기</title>
</head>
<body>

	<form action="pwfindAction.log" name="pwfindscreen" method="post"
		 style="width: 200px">
		<table width="380px" height="70px" align="center" border="0"
			style="font-size: 16px;">
			<tr>

				<td>아이디</td>
				<td><input type="text" name="id"></td>
			</tr>
			<tr>
				<td style="text-align: center;">e-mail&nbsp;</td>
				<td><input type="text" name="email" style="width: 80px"
					onblur="checkid()"> @ <input type="text" id="emailInput"
					name="e_domain" style="width: 80px"> 
					<select id="emailDomain" name="domain" onchange="domainCheck(this.value);">
						<option value="0" selected="selected">직접입력</option>
						<option value="naver.com">naver.com</option>
						<option value="hanmail.net">hanmail.net</option>
						<option value="nate.com">nate.com</option>
						<option value="yahoo.com">yahoo.com</option>
				</select></td>
				<br>
				<br>
			<table width="140px" height="10px" border="0" style="margin-top: 2%;" align="center">
			<tr>
				<td><a href="javascript:pwfindscreen.submit()">
				<input type="button" name="enter2" value="임시비밀번호보내기"></a></td>
				<td><input type="button" name="cancle2" value="  취소  " align="center"></td>
			</tr>
			</table>
</body>
</html>