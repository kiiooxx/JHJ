<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
	<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<title>my info</title>
	<link rel="stylesheet" href="common.css" type="text/css">
	<link rel="stylesheet" href="style.css" type="text/css">
	<script
		src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
	<script
		src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
	<script src="http://dmaps.daum.net/map_js_init/postcode.v2.js"></script>
	<script>
	<c:forTokens var="birth" items="${member.birth}" delims="-" varStatus="i">
	<c:if test="${i.index eq 0 }">
		<c:set var="birthY" value="${birth }"/>
	</c:if>
	<c:if test="${i.index eq 1 }">
		<c:set var="birthM" value="${birth }"/>
	</c:if>
	<c:if test="${i.index eq 2 }">
		<c:set var="birthD" value="${birth }"/>
	</c:if>
</c:forTokens>
		//생년월일
		$(document).ready(function() {
			setDateBox();
		});

		// select box 생년월일 표시
		function setDateBox() {
			
			var dt = new Date();
			var year = "";
			var com_year = dt.getFullYear();
			// 발행 뿌려주기
			$("#birthY").append("<option value=''>년도</option>");
			// 생년
			for (var y = (com_year - 1); y >= (com_year - 100); y--) {
				$("#birthY").append('<option value="'+y+'" ${birthY=="'+y+'"?"selected":""}>' + y + '년</option>');
			}
			// 월 
			var month;
			$("#birthM").append("<option value=''>월</option>");
			for (var i = 1; i <= 12; i++) {
				if (i < 10) {
					$("#birthM").append(
							"<option value='0"+ i +"'>" + i + "월"
									+ "</option>");
				} else {
					$("#birthM")
							.append(
									"<option value='"+ i +"'>" + i + "월"
											+ "</option>");
				}
			}
			//일
			var day;
			$("#birthD").append("<option value=''>일</option>");
			for (var i = 1; i <= 31; i++) {
				if (i < 10) {
					$("#birthD").append(
							"<option value='0"+ i +"'>" + i + "일"
									+ "</option>");
				} else {
					$("#birthD")
							.append(
									"<option value='"+ i +"'>" + i + "일"
											+ "</option>");
				}

			}
			$("#birthY").val("${birthY}").prop("selected", true);
			$("#birthM").val("${birthM}").prop("selected", true);
			$("#birthD").val("${birthD}").prop("selected", true);
		}
	</script>
</head>
<body>${birthY} ${birthM} ${birthD}
	
	<c:choose>
		<c:when test="${grade == 'N'.charAt(0)}">
			<c:set var="grade1" value="일반회원"/>
		</c:when>
		<c:when test="${grade == 'A'.charAt(0)}">
			<c:set var="grade1" value="관리자"/>
		</c:when>		
	</c:choose>

	저희 쇼핑몰을 이용해 주셔서 감사합니다. ${member.user_name} 님은 [${grade1}] 회원이십니다.


	<form action="myinfoModprocess.mem" id="myinfo" name="myinfoMod" method="post">

		<div class="join_table">
			<table border="1" summary>
				<h3 class="join_title">기본정보</h3>
				<tbody>
					<tr>
						<th><label for="id">아이디</label><b class="req">*</b></th>
						<td><input type="text" name="id" id="id"
							value="${member.user_id }" /></td>
					</tr>
					<tr>
						<th><label for="pass">비밀번호</label><b class="req">*</b></th>
						<td><input type="password" name="pass" id="pass"
							value="${member.user_pw }" /></td>
					</tr>
					<tr>
						<th><label for="passChk">비밀번호확인</label><b class="req">*</b></th>
						<td><input type="password" name="passChk" id="passChk"
							value="${member.user_pw }" /></td>
					</tr>
					<tr>
						<th><label for="name">이름 </label><b class="req">*</b></th>
						<td><input type="text" name="name" id="name"
							value="${member.user_name}" /></td>
					</tr>
					<tr>
						<th>주소<b class="req">*</b></th>
						<td><input type="text" name="postcode" id="postcode" size="6"
							value="${member.postcode}" /> <a href="javascript:void(0);"
							name="zipSearch" id="zipSearch" class="btn"
							onclick="sample6_execDaumPostcode()">우편번호</a><br> 
							
							
							<input type="text" name="addr1" id="addr1" value="${member.addr1}" size=50 readonly /> 
							기본주소<br>
							
							
							
							<input type="text" name="addr2" id="addr2" value="${member.addr2}" size=50 /> 
							상세주소</td>
						</tr>
						<tr>
							<th><label for="tel">휴대전화</label><b class="req">*</b></th>
							<td><input type="tel" name="tel" id="tel" value="${member.tel}" /></td>
						</tr>
						
					
						
						<tr>
							<th><label for="email">이메일 주소</label><b class="req">*</b></th>
							<td><input type="text" name="email" id="email" value="${member.email}"/></td>
						</tr>
					</tbody>
				</table>
			</div> 


			<div class="join_table">

				<table>
					<h3 class="join_title">추가정보</h3>
					<tr>
						<th><label for="gender"></label>성별</th>
						<td><input type="radio" name="sex" id="gender1"  
						value="남자" ${member.sex == "남" ? 'checked' : '' } />
	
								남자
						 <input type="radio" name="sex" value="여"
							id="gender2" value="여자" ${member.sex == "여" ? 'checked' : ''}/>여자</td>
					</tr>
					<tr>
						<th><label for="birth">생년월일</label></th>
						<td>
						
							
							
							<select name="birthY" id="birthY" title="년도" class="select w80"></select> 
							<select name="birthM" id="birthM" title="월" class="select w80"></select> 
							<select name="birthD" id="birthD" title="일" class="select w80"></select></td>
					</tr>
					
					
				</table>
			</div>
			
			
			   <p class="btx">
         	<a href="javascript:myinfo.submit()" class="login_btn" onclick="sendit()">회원정보수정</a>
         	<a href="joinForm.mem" class="join_btn">회원 탈퇴</a>
         	
         	<input type="button" class="btn btn-danger"                                  
         	onclick="location.href='secession'" value="회원탈퇴">
        
         </p>
        
        
 </form>      
</body>
</html>