<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

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

<c:choose>
	<c:when test="${grade == 'N'.charAt(0)}">
		<c:set var="grade1" value="일반회원"/>
	</c:when>
	<c:when test="${grade == 'A'.charAt(0)}">
		<c:set var="grade1" value="관리자"/>
	</c:when>		
</c:choose>

<script>
//탈퇴버튼 눌렀을때 
$(document).ready(function() {
 
	$('#memberdelbtn').on('click', function(){
		if(confirm('정말 탈퇴하시겠습니까?')) {
			location.href='myinfoquit.mem';
		}else{
			return false;
		}
 	});
 });
 





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

//우편번호
function sample6_execDaumPostcode() {
	new daum.Postcode(
		{
			oncomplete : function(data) {
				// 팝업에서 검색결과 항목을 클릭했을때 실행할 코드를 작성하는 부분.

					// 각 주소의 노출 규칙에 따라 주소를 조합한다.
					// 내려오는 변수가 값이 없는 경우엔 공백('')값을 가지므로, 이를 참고하여 분기 한다.
			var fullAddr = ''; // 최종 주소 변수
			var extraAddr = ''; // 조합형 주소 변수

					// 사용자가 선택한 주소 타입에 따라 해당 주소 값을 가져온다.
			if (data.userSelectedType === 'R') { // 사용자가 도로명 주소를 선택했을 경우
				fullAddr = data.roadAddress;

			} else { // 사용자가 지번 주소를 선택했을 경우(J)
				fullAddr = data.jibunAddress;
			}

			// 사용자가 선택한 주소가 도로명 타입일때 조합한다.
			if (data.userSelectedType === 'R') {
				//법정동명이 있을 경우 추가한다.
				if (data.bname !== '') {
					extraAddr += data.bname;
				}
				// 건물명이 있을 경우 추가한다.
				if (data.buildingName !== '') {
					extraAddr += (extraAddr !== '' ? ', '
							+ data.buildingName : data.buildingName);
				}
				// 조합형주소의 유무에 따라 양쪽에 괄호를 추가하여 최종 주소를 만든다.
				fullAddr += (extraAddr !== '' ? ' (' + extraAddr
						+ ')' : '');
			}

			// 우편번호와 주소 정보를 해당 필드에 넣는다.
			document.getElementById('postcode').value = data.zonecode; //5자리 새우편번호 사용
			document.getElementById('addr1').value = fullAddr;

			// 커서를 상세주소 필드로 이동한다.
			document.getElementById('addr2').focus();
		}
	}).open();
}
</script>


<div class="info_grade">
	<p class="info_thumb">
		<img src="//img.echosting.cafe24.com/skin/base_ko_KR/member/img_member_default.gif" alt="" onerror="this.src='//img.echosting.cafe24.com/skin/base/member/img_member_default.gif';">
	</p>
	저희 쇼핑몰을 이용해 주셔서 감사합니다. ${member.user_name} 님은 [${grade1}] 회원이십니다.
</div>


<form action="myinfomodify.mem" id="myinfomod" name="f" method="post">

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
						name="zipSearch" id="zipSearch" class="small_btn"
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
		
		<div class="info_btn_area">
        	<a href="javascript:myinfomod.submit()" class="medium_btn" onclick="sendit()">회원정보수정</a>
        	<a href="#" id="memberdelbtn"class="medium_btn_w">회원 탈퇴</a>
        </div>
 </form>      