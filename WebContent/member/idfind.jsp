<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<script type="text/javascript">
$(document).ready(function() {
	//검색하고 나서 결과를 보여줄때 검색 조건을 그대로 노출
	//if("${serviceType}" == "byemail"){ //serviceType 이 이메일 일 경우 셋팅
	if ("byemail" == "byemail") {

		//라디오 버튼 값으로 선택
		$('input:radio[name="serviceType"][value="byemail"]').prop(
				'checked', true);
		//셀렉트 박스 값으로 선택
		$("select[name='byemailMainCategory']").val("2").attr(
				"selected", "selected");
		// 폰 hide
		$("#viewbyphoneCategory").hide();
		//이메일 show
		$("#viewbyemailCategory").show();

	}

	//라디오 버튼 변경시 이벤트
	$("input[name='serviceType']:radio").change(function() {
		//라디오 버튼 값을 가져온다.
		var serviceType = this.value;

		if (serviceType == "byemail") {//스포츠인 경우
			//폰 일때 공연/전시 카테고리 hide
			$("#viewbyphoneCategory").hide();
			//스포츠 일때 스포츠 카테고리 show
			$("#viewbyemailCategory").show();
			console.log("이메일인경우");
		} else if (serviceType == "byphone") {//공연/전시인 경우
			//공연/전시 일때 공연/전시 카테고리 show
			$("#viewbyphoneCategory").show();
			//공연/전시 일때 스포츠 카테고리 show
			$("#viewbyemailCategory").hide();
			console.log("폰번호인경우");
		}

	});
});

function domainCheck(value) {
	if (value == 0) {
	    document.getElementById("emailInput").value = "";
	} else {
	    document.getElementById("emailInput").value = value;
	}
}

function id_search1() { //이름,핸드폰으로 '찾기' 버튼
	var frm = document.idfindscreen;

	if (frm.name.value.length < 1) {
		alert("이름을 입력해주세요");
		return;
	}

	if (frm.phone1.value.length<2 || frm.phone1.value.length>4) {
		alert("핸드폰번호를 정확하게 입력해주세요");
		return;
	}
	if (frm.phone2.value.length<2 || frm.phone2.value.length>4) {
		alert("핸드폰번호를 정확하게 입력해주세요");
		return;
	}

	frm.method = "post";
	frm.action = "idfind.log"; //넘어간화면
	frm.submit();
}

function id_search2() { //이름,이메일로 '찾기' 버튼

	var frm = document.idfindscreen;

	if (frm.name2.value.length < 1) {
		alert("이름을 입력해주세요");
		return;
	}
	if (frm.email.value.length < 1 || frm.e_domain.value.length < 1) {
		alert("이메일을 입력해주세요");
		return;
	}

	frm.method = "post";
	frm.action = "idfind.log"; //넘어간화면
	frm.submit();
}

//이메일 부분
function checkid() {

	var frm = document.idfindscreen;

	var regExp = '/^([/\w/g\.-]+)@([\da-z\.-]+)\.([a-z\.]{2,6})$/';

	if (!regExp.test(frm.email.value)) {

		alert('올바른 email을 입력해주세요.');

		frm.email.focus();

	}
}
</script>


<body>

<div class="login">
	<h1 align=center style="height:30px;">FIND ID</h1>
	<fieldset>
		<div class="loginBx">
			<ul class="forget">
				<input type="radio" name="serviceType" value="byphone" checked> TEL
				<input type="radio" name="serviceType" value="byemail"> E-MAIL
			</ul>

			<form action="idfindAction.log" name="idfindscreen" method="post">
				<span id="viewbyphoneCategory">
					<p class="nna">NAME</p>
					<label class="id"><input type="text" name="name"></label>
					
					<p class="nna">PHONE</p>
					<label class="pass">
						<select name="phone">
								<option value="010" selected="selected">010</option>
								<option value="011">011</option>
								<option value="016">016</option>
								<option value="017">017</option>
						</select>
						- 
						<input type="text" name="phone1" style="width: 70px"> 
						-
						<input type="text" name="phone2" style="width: 70px">
					</label>
					
					<p class="btx">
						<a href="javascript:idfindscreen.submit()" class="login_btn" id="id_search1">FIND</a>
					</p>
				</span>
			</form>

			<form action="idfindAction.log" name="idfindscreen2" method="post" class="form-control byemailMainCategory">
				<span id="viewbyemailCategory" style="display: none"> 
					<p class="nna">id</p>
					<label class="id"><input type="text" name="name2"></label>
					<p class="nna">e-mail</p>
					<label class="pass">
						<input type="text" name="email" style="width: 80px" onblur="checkid()">
						@
						<input type="text" id="emailInput" name="e_domain" style="width: 80px">
						<select id="emailDomain" name="domain" onchange="domainCheck(this.value);">
					        <option value="0" selected="selected">직접입력</option>
					        <option value="naver.com">naver.com</option>
					        <option value="hanmail.net">hanmail.net</option>
					        <option value="nate.com">nate.com</option>
					        <option value="yahoo.com">yahoo.com</option>
					    </select>
					</label>
					<p class="btx">
						<a href="javascript:idfindscreen2.submit()" class="login_btn" >찾기</a>
					</p>
				</span>
			</form>
		</div>
	</fieldset>
</div>