<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<script>

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
			$("#birthY").append("<option value='"+ y +"'>" + y + "년" + "</option>");
		}
		// 월 
		var month;
		$("#birthM").append("<option value=''>월</option>");
		for (var i = 1; i <= 12; i++) {
			if(i<10){
				$("#birthM").append("<option value='-0"+ i +"'>" + i + "월" + "</option>");
			}else{
				$("#birthM").append("<option value='-"+ i +"'>" + i + "월" + "</option>");	
			}
		}
		//일
		var day;
		$("#birthD").append("<option value=''>일</option>");
		for (var i = 1; i <= 31; i++) {
			if(i<10){
				$("#birthD").append("<option value='-0"+ i +"'>" + i + "일" + "</option>");	
			}else{
				$("#birthD").append("<option value='-"+ i +"'>" + i + "일" + "</option>");
			}
		}
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
	
var chkId = false;
var op = false; //idCheck.jsp
var idcheck;
function chkForm(f) {
	if (!chkId || idcheck != f.id.value.trim()) {
		alert("아이디 중복확인이 필요합니다.");
		return false;
	}

	if (f.pass.value.trim() == "") {
		alert("비밀번호를 입력하세요.");
		f.pass.focus();
		return false;
	}

	if (f.pass.value.trim() != f.passChk.value.trim()) {
		alert("비밀번호가 일치하지 않습니다.");
		f.passChk.value = "";
		return false;
	}
	
    if (f.name.value.trim() == "") {
        alert("이름을 입력해 주세요.");
        f.name.focus();
        return false;
    }
    
    if (f.postcode.value.trim() == ""){
    	alert("주소를 입력해 주세요.");
    	f.postcode.focus();
    	return false;
    	
    }
    
    if(f.addr2.value.trim() == ""){
    	alert("상세주소를 입력하세요.");
    	f.addr2.focus();
    	return false;
    }
    
    if(f.tel.value.trim() == "" ){
    	alert("휴대전화번호를 입력하세요.");
    	f.tel.focus();
    	return false;
    }
    
    if(f.email.value.trim() == ""){
    	alert("이메일 주소를 입력하세요.");
    	f.email.focus();
    	return false;
    }

	//개인정보이용약관 동의
    if(f.personalChk.checked == false){
    	alert("개인정보 이용 약관 동의가 필요합니다.");
    	return false;
    }
	
	
	f.submit();
	
}
	
	
	
</script>

<div id="join_form">
	<form action="memberJoinProcess.mem" id="joinform" name="f" method="post">
	<input type="hidden" name="col" value="new_mem">
	<input type="hidden" name="col_title" value="new_mem_title">
	<input type="hidden" name="col_content" value="new_mem_content">
		
		<div class="join_table">
			<table border="1" summary>
				<h3 class="join_title">기본정보</h3>
				<tbody>
					<tr>
						<th><label for="id">아이디</label><b class="req">*</b></th>
						<td><input type="text" name="id" id="id" required readonly />
							<a href="javascript:void(0);" name="idCheck" id="idCheck"
							class="btn"
							onclick="window.open('idCheckForm.mem?openInit=true','','width=300, height=200')">중복확인</a>
					
					
					</td>
				</tr>
				<tr>
					<th><label for="pass">비밀번호</label><b class="req">*</b></th>
					<td><input type="password" name="pass" id="pass" required /></td>
				</tr>
				<tr>
					<th><label for="passChk">비밀번호확인</label><b class="req">*</b></th>
					<td><input type="password" name="passChk" id="passChk"
						required /></td>
				</tr>
				<tr>
					<th><label for="name">이름 </label><b class="req">*</b></th>
					<td><input type="text" name="name" id="name" required /></td>
				</tr>
				<tr>
					<th>주소<b class="req">*</b></th>
					<td><input type="text" name="postcode" id="postcode" size="6"
						readonly /> <a
						href="javascript:void(0);" name="zipSearch" id="zipSearch"
						class="btn"
						onclick="sample6_execDaumPostcode()">우편번호</a><br>
						<input type="text" name="addr1" id="addr1"
						value="<%//=rs.getString("addr1")%>" size=50 readonly /> 기본주소<br>
						<input type="text" name="addr2" id="addr2"
						value="<%//=rs.getString("addr2")%>" size=50 /> 상세주소</td>
				</tr>
				<tr>
					<th><label for="tel">휴대전화</label><b class="req">*</b></th>
					<td><input type="tel" name="tel" id="tel" required /></td>
				</tr>
				<tr>
					<th><label for="email">이메일 주소</label><b class="req">*</b></th>
					<td><input type="text" name="email" id="email" required /></td>
				</tr>
			</tbody>
		</table>
	</div>


	<div class="join_table">

		<table>
			<h3 class="join_title">추가정보</h3>
			<tr>
				<th><label for="gender"></label>성별</th>
				<td><input type="radio" name="sex" value="남"
					id="gender1" />남자 <input type="radio" name="sex" value="여"
					id="gender2" />여자</td>
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

	<div class="agree_Area">
		<!-- 이용약관 -->
			<div class="terms">
				<p>■ 수집하는 개인정보 항목</p>

				<p>회사는 회원가입, 상담, 서비스 신청 등등을 위해 아래와 같은 개인정보를 수집하고 있습니다.</p>
				<p>ο 수집항목 : 이름 , 생년월일 , 성별 , 로그인ID , 비밀번호 , 자택 주소 , 휴대전화번호 ,
					이메일 , 직업 , 회사명 , 부서 , 직책 , 회사전화번호 , 취미 , 결혼여부 , 기념일 , 법정대리인정보 ,
					주민등록번호 , 서비스 이용기록 , 접속 로그 , 접속 IP 정보 , 결제기록 ο 개인정보 수집방법 :
					홈페이지(회원가입) , 서면양식</p>

				<p>■ 개인정보의 수집 및 이용목적</p>

				<p>회사는 수집한 개인정보를 다음의 목적을 위해 활용합니다.</p>

				<p>ο 서비스 제공에 관한 계약 이행 및 서비스 제공에 따른 요금정산 콘텐츠 제공 , 구매 및 요금 결제 ,
					물품배송 또는 청구지 등 발송</p>
				<p>ο 회원 관리</p>
				<p>회원제 서비스 이용에 따른 본인확인 , 개인 식별 , 연령확인 , 만14세 미만 아동 개인정보 수집 시 법정
					대리인 동의여부 확인 , 고지사항 전달 ο 마케팅 및 광고에 활용</p>
				<p>접속 빈도 파악 또는 회원의 서비스 이용에 대한 통계</p>

				<p>■ 개인정보의 보유 및 이용기간</p>

				<p>회사는 개인정보 수집 및 이용목적이 달성된 후에는 예외 없이 해당 정보를 지체 없이 파기합니다.</p>
			</div>
			<p class="check">
				[필수] 개인정보 수집 및 이용에 동의하십니까? <input type="checkbox"
					id="service_agree" name="personalChk">동의함
			</p>
		</div>
		<div class="jo_btn">
			<a href="javascript:chkForm(document.f);">JOIN MEMBER</a>&nbsp;&nbsp;
		</div>
	</form>
</div>