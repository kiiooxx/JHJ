<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
<script>
$(function(){
 $('input:checkbox[id="fChk"]').each(function() {
     if(this.value == 1){
            this.checked = true;
      }else{
    	  this.checked = false;
      }
 });
});



</script>
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
		<td><input type="checkbox" name="A" value="${mailOption.new_mem }" id="fChk"/></td>
		<td><a href="#" title="새창 열림" class="btnNormal" onclick="popup()">수정</a> | 
			<a href="#" title="새창 열림" class="btnNormal" onclick="popup()">미리보기</a>
		</td>
	</tr>
	<tr>
		<td>회원탈퇴</td>
		<td><input type="checkbox" name="B" value="${mailOption.quit_mem }" id="fChk"/></td>
		<td>수정 | 미리보기</td>
	</tr>
	<tr>
		<td>신규주문내역</td>
		<td><input type="checkbox" name="C" value="${mailOption.order_info }" id="fChk"/></td>
		<td>수정 | 미리보기</td>
	</tr>
	<tr>
		<td>결제확인</td>
		<td><input type="checkbox" name="D" value="${mailOption.check_paid }" id="fChk"/></td>
		<td>수정 | 미리보기</td>
	</tr>
	<tr>
		<td>상품발송</td>
		<td><input type="checkbox" name="E" value="${mailOption.send_pro }" id="fChk"/></td>
		<td>수정 | 미리보기</td>
	</tr>
	<tr>
		<td>배송중</td>
		<td><input type="checkbox" name="F" value="${mailOption.deli_ing }" id="fChk"/></td>
		<td>수정 | 미리보기</td>
	</tr>
	<tr>
		<td>배송완료</td>
		<td><input type="checkbox" name="G" value="${mailOption.deli_fin }" id="fChk"/></td>
		<td>수정 | 미리보기</td>
	</tr>
	<tr>
		<td>구매확정</td>
		<td><input type="checkbox" name="H" value="${mailOption.confirm_order }" id="fChk"/></td>
		<td>수정 | 미리보기</td>
	</tr>
	<tr>
		<td>주문취소승인</td>
		<td><input type="checkbox" name="I" value="${mailOption.acc_cancel }" id="fChk"/></td>
		<td>수정 | 미리보기</td>
	</tr>
	<tr>
		<td>문의글 답변알림</td>
		<td><input type="checkbox" name="J" value="${mailOption.qna_re }" id="fChk"/></td>
		<td>수정 | 미리보기</td>
	</tr>	
</table>
<input type="submit" value="변경사항저장">
</form>
</body>
</html>