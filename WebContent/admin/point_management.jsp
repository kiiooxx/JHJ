<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
<script>
//숫자만 입력가능
function onlyNumber(){
    if((event.keyCode<48)||(event.keyCode>57))
       event.returnValue=false;
}

//적립금 지급 시점 설정
$(document).ready(function(){
	setPointDate();
});

function setPointDate(){
	$("#pointDate").append("<option value='0'>즉시</option>");
	$("#pointDate").append("<option value='1'>익일</option>");
	for(var i = 2; i <= 10; i++){
		$("#pointDate").append("<option value='" + i + "'>" + i +"일 후</option>");
	}
}

$(function(){
	$("#chk").change(function(){
		if($("#chk").prop("checked")){
			$("#reviewP").attr("disabled", false);
		}else{
			$("#reviewP").attr("disabled", true);
			$("#reviewP").val("0");
			
		}
	});
});

$(function(){
	$("#pointDate").val("${pointMan.p_date }").prop("selected", true);
	$("#markOption").val("${pointMan.p_mark }").prop("selected", true);
	$("#usePointOption").val("${pointMan.p_stand }").prop("selected", true);
});

$(function(){
	 $('input:checkbox[id="chk"]').each(function() {
	     if(${pointMan.p_review} != 0){
	            this.checked = true;
	      }else{
	    	  this.checked = false;
	      }
	 });
	});
	
$(function(){
	 $('input:text[id="reviewP"]').each(function() {
	     if(${pointMan.p_review} == 0){
	            this.disabled = true;
	      }else{
	    	  this.disabled = false;
	      }
	 });
	});
	
function saveBtn(){
	b.submit();
}
</script>
</head>
<style>
table{
	border: 1px solid;
	width: 80%;
}

tr td{
	border: 1px solid;
	 
}
tr th{
	border: 1px solid;
	width: 20%;
	background: #F6F6F6;
	text-align: center;
}
tr{
	height: 50px;
}
p{
	text-align: center;
}
</style>
<body>
<form action="pointManagement.ad" method="post" name="b">
<div class="card shadow mb-4">
	<div class="card-header py-3">
		<h6 class="m-0 font-weight-bold text-primary">적립금 설정</h6>
    </div>
    <div class="card-body">
     		<table class="table table-bordered">
				<tr>
					<th>적립금 지급 시점 설정</th>
					<td>구매확정 후 <select name="pointDate" id="pointDate" title="---기간설정---"></select>지급</td>
				</tr>
				<tr>
					<th>적립금 항목 노출 설정</th>
					<td>
						<select name="markOption" id="markOption">
							<option value="percent">정율 단일 표기(1%)</option>
							<option value="won">정액 단일 표기(100원)</option>
							<option value="double">정액/정율 동시 표기(100원(1%))</option>
						</select>
					</td>
				</tr>
				<tr>
					<th>기본설정 적립금 비율</th>
					<td>구매 가격의 <input type="text" name="rate" value="${pointMan.p_rate }" pattern="\d{1,2}.\d{1}" title="xx.x%" required>%</td>
				</tr>
				<tr>
					<th>적립금으로 구매 시 적립기준 설정</th>
					<td>
						<select name="usePointOption" id="usePointOption">
							<option value="as">적립금 그대로 적립</option>
							<option value="no">적립안함</option>
							<option value="act">실 결제금액 기준으로 적립</option>
						</select>
					</td>
				</tr>
				<tr>
					<th>회원가입 시 적립금 설정</th>
					<td>신규 회원 가입 시 <input type="text" value="${pointMan.p_newmem }" name="newMem" onkeypress="onlyNumber();">원 적립</td>
				</tr>
				<tr>
					<th>적립금 사용 가능 최소 상품 구매 합계액 설정</th>
					<td>상품 구매 합계액 최소 <input type="text" value="${pointMan.p_pricelimit }" name="priceLimit" onkeypress="onlyNumber();">원 이상일 때 적립금 사용 가능</td>
				</tr>
				<tr>
					<th>적립금 사용 가능 최소 누적 적립금액 설정</th>
					<td>누적 적립금액 최소 <input type="text" value="${pointMan.p_pointlimit }" name="pointLimit" onkeypress="onlyNumber();">원 이상일 때 적립금 사용 가능</td>
				</tr>
				<tr>
					<th rowspan="2">리뷰 작성 시 적립금 설정</th><!-- 체크박스 체크하면 적립 설정칸 활성화  -->
					<td><input type="checkbox" name="chk" id="chk" value="true" onkeypress="onlyNumber();"
					<%-- <c:if test="${pointMan.p_review ne 0}">checked</c:if> --%>
					>사용하기</td>
				</tr>
				<tr>
					<td rowspan="2">리뷰 작성 시 <input type="text" id="reviewP" name="reviewP" value="${pointMan.p_review }" onkeypress="onlyNumber();"
					 <%-- <c:if test="${pointMan.p_review eq 0}">disabled</c:if> --%>
					>원 적립</td>
				</tr>	
			</table>
		</div>
	</div>

<p>
<a href="#" class="btn btn-primary btn-icon-split">
	<span class="icon text-white-50">
		<i class="fas fa-flag"></i>
	</span>
	<span class="text" onclick="javascript:saveBtn(document.b);">변경사항 저장</span>
</a>
</p>
</form>
</body>
</html>