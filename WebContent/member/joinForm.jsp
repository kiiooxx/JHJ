<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>회원 관리 시스템 회원 가입 페이지</title>
<link rel="stylesheet" href="common.css" type="text/css">
<link rel="stylesheet" href="style.css" type="text/css">
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
<script>
var chkId = false;
var op = false;	//idCheck.jsp
var idcheck;
function chkForm(f) {
	if(!chkId || idcheck!=f.id.value.trim()) {
		alert("아이디 중복을 확인하세요!!");
		return false;
	}
	
	if(f.pass.value.trim()=="") {
		alert("비밀번호를 입력하세요.");
		f.pass.focus();
		return false;
	}
	
	if(f.pass.value.trim() != f.passChk.value.trim()) {
		alert("비밀번호가 일치하지 않습니다.");
		
	}
}

//
$(document).ready(function(){
        setDateBox();
    });    
 
    // select box 생년월일 표시
    function setDateBox(){
        var dt = new Date();
        var year = "";
        var com_year = dt.getFullYear();
        // 발행 뿌려주기
        $("#YEAR").append("<option value=''>년도</option>");
        // 생년
        for(var y = (com_year-1); y >= (com_year-100); y--){
            $("#YEAR").append("<option value='"+ y +"'>"+ y + " 년" +"</option>");
        }
        // 월 
        var month;
        $("#MONTH").append("<option value=''>월</option>");
        for(var i = 1; i <= 12; i++){
            $("#MONTH").append("<option value='"+ i +"'>"+ i + " 월" +"</option>");
        }
        //일
        var day;
        $("#DAY").append("<option value=''>일</option>");
        for(var i = 1; i <= 31; i++){
            $("#DAY").append("<option value='"+ i +"'>"+ i + " 일" +"</option>");
        }
    }

</script>
</head>
<body>
<jsp:include page="../path.jsp"></jsp:include>
<div id="join_form">
<form id="memForm" name="joinform" action="memberJoinProcess.mem" method="post" onsubmit="return chkForm(this.form())">
<div class="join_table">
	<table border="1" summary>
	<h3 class="join_title">기본정보</h3>
	<tbody>
		<tr>
			<th><label for="id">아이디</label><b class="req">*</b></th>
			<td><input type="text" name="id" id="id" required readonly/>
			<a href="javascript:void(0);" name="idCheck" id="idCheck" class="btn" 
			onclick="window.open('member/idCheck.jsp?openInit=true','','width=300, height=200')">중복확인</a>
	 		</td>
		</tr>
		<tr>
			<th><label for="pass">비밀번호</label><b class="req">*</b></th>
			<td><input type="password" name="pass" id="pass" required/></td>
		</tr>
		<tr>
			<th><label for="passChk">비밀번호확인</label><b class="req">*</b></th>
			<td><input type="password" name="passChk" id="passChk" required/></td>
		</tr>
		<tr>
			<th><label for="name">이름 </label><b class="req">*</b></th>
			<td><input type="text" name="name" id="name" required/></td>
		</tr>
		<tr>
			<th>주소<b class="req">*</b></th>
			<td>
				<input type="text" name="zip" id="zip" size="6" value="<%//=rs.getString("zip") %>" readonly />
				<a href="javascript:void(0);" name="zipSearch" id="zipSearch" class="btn" onclick="window.open('member/zip1.jsp', '', 'width=800, height=600')">우편번호</a><br>
				<input type="text" name="addr1" id="addr1" value="<%//=rs.getString("addr1") %>" size=50 readonly /> 기본주소<br>
				<input type="text" name="addr2" id="addr2" value="<%//=rs.getString("addr2") %>" size=50 /> 나머지주소
			</td>
		</tr>
		<tr>
			<th><label for="tel">휴대전화</label><b class="req">*</b></th>
			<td>
				<input type="tel" name="tel" id="tel" required/>
			</td>
		</tr>
		<tr>
			<th><label for="email">이메일 주소</label><b class="req">*</b></th>
			<td><input type="text" name="email" id="email" required/></td>
		</tr>
	</tbody>
	</table>
</div>


<div class="join_table">
	
	<table>
		<h3 class="join_title">추가정보</h3>
		<tr>
			<th><label for="gender"></label>성별</th>
			<td>
			<input type="radio" name="gender" value="남" checked id="gender1"/>남자
			<input type="radio" name="gender" value="여" id="gender2"/>여자
			</td>
		</tr>
		<tr>
			<th><label for="birth">생년월일</label></th>
			<td>
					<select name="YEAR" id="YEAR" title="년도" class="select w80"></select>    
            		<select name="MONTH" id="MONTH" title="월" class="select w80"></select>
            		<select name="DAY" id="DAY" title="일" class="select w80"></select>
			</td>
		
		
		</tr>
	</table>
</div>

<div class="agree_Area">
				<!-- 이용약관 -->
	<div class="terms">
		<p>■ 수집하는 개인정보 항목</p>

		<p>회사는 회원가입, 상담, 서비스 신청 등등을 위해 아래와 같은 개인정보를 수집하고 있습니다.</p>
		<p>ο 수집항목 : 이름 , 생년월일 , 성별 , 로그인ID , 비밀번호 , 자택 주소 , 휴대전화번호 , 이메일 , 직업 , 회사명 , 부서 , 직책 , 회사전화번호 , 취미 , 결혼여부 , 기념일 , 법정대리인정보 , 주민등록번호 , 서비스 이용기록 , 접속 로그 , 접속 IP 정보 , 결제기록
			ο 개인정보 수집방법 : 홈페이지(회원가입) , 서면양식</p>
				
		<p>■ 개인정보의 수집 및 이용목적</p>
				
		<p>회사는 수집한 개인정보를 다음의 목적을 위해 활용합니다.</p>
				
		<p>ο 서비스 제공에 관한 계약 이행 및 서비스 제공에 따른 요금정산 콘텐츠 제공 , 구매 및 요금 결제 , 물품배송 또는 청구지 등 발송</p>
		<p>ο 회원 관리</p>
		<p>회원제 서비스 이용에 따른 본인확인 , 개인 식별 , 연령확인 , 만14세 미만 아동 개인정보 수집 시 법정 대리인 동의여부 확인 , 고지사항 전달 ο 마케팅 및 광고에 활용</p>
		<p>접속 빈도 파악 또는 회원의 서비스 이용에 대한 통계</p>
				
		<p>■ 개인정보의 보유 및 이용기간</p>
				
		<p>회사는 개인정보 수집 및 이용목적이 달성된 후에는 예외 없이 해당 정보를 지체 없이 파기합니다.</p>
	</div>
	<p class="check">
	[필수] 개인정보 수집 및 이용에 동의하십니까? <input type="checkbox" id="service_agree">동의함
	</p>
</div>
	<div class="jo_btn">
		<a href="javascript:joinform.submit()">JOIN MEMBER</a>&nbsp;&nbsp;
	</div>
</form>
</div>
</body>
</html>