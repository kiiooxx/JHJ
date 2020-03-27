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
		<td><a href="#" title="자동전송메일 수정" onclick="window.open('mailModifyForm.ad?openInit=true&col_title=new_mem_title&col_content=new_mem_content','','width=1200, height=900')">수정</a> | 
			<a href="#" title="자동전송메일 미리보기" onclick="window.open('mailModifyForm.ad?openInit=true&col_title=new_mem_title&col_content=new_mem_content&preview=true','','width=1200, height=900')">미리보기</a>
		</td>
	</tr>
	<tr>
		<td>회원탈퇴</td>
		<td><input type="checkbox" name="B" value="${mailOption.quit_mem }" id="fChk"/></td>
		<td><a href="#" title="자동전송메일 수정" onclick="window.open('mailModifyForm.ad?openInit=true&col_title=quit_mem_title&col_content=quit_mem_content','','width=1200, height=900')">수정</a> | 
		    <a href="#" title="자동전송메일 미리보기" onclick="window.open('mail_form/new_mem_preview.jsp?openInit=true','','width=1200, height=900')">미리보기</a>
		</td>
	</tr>
	<tr>
		<td>신규주문내역</td>
		<td><input type="checkbox" name="C" value="${mailOption.order_info }" id="fChk"/></td>
		<td><a href="#" title="자동전송메일 수정" onclick="window.open('mailModifyForm.ad?openInit=true&col_title=order_info_title&col_content=order_info_content','','width=1200, height=900')">수정</a> | 
			<a href="#" title="자동전송메일 미리보기" onclick="window.open('mail_form/new_mem_preview.jsp?openInit=true','','width=1200, height=900')">미리보기</a>
		</td>
	</tr>
	<tr>
		<td>결제확인</td>
		<td><input type="checkbox" name="D" value="${mailOption.check_paid }" id="fChk"/></td>
		<td><a href="#" title="자동전송메일 수정" onclick="window.open('mailModifyForm.ad?openInit=true&col_title=check_paid_title&col_content=check_paid_content','','width=1200, height=900')">수정</a> | 
			<a href="#" title="자동전송메일 미리보기" onclick="window.open('mail_form/new_mem_preview.jsp?openInit=true','','width=1200, height=900')">미리보기</a>
		</td>
	</tr>
	<tr>
		<td>상품발송</td>
		<td><input type="checkbox" name="E" value="${mailOption.send_pro }" id="fChk"/></td>
		<td><a href="#" title="자동전송메일 수정" onclick="window.open('mailModifyForm.ad?openInit=true&col_title=send_pro_title&col_content=send_pro_content','','width=1200, height=900')">수정</a> | 
			<a href="#" title="자동전송메일 미리보기" onclick="window.open('mail_form/new_mem_preview.jsp?openInit=true','','width=1200, height=900')">미리보기</a>
		</td>
	</tr>
	<tr>
		<td>배송중</td>
		<td><input type="checkbox" name="F" value="${mailOption.deli_ing }" id="fChk"/></td>
		<td><a href="#" title="자동전송메일 수정" onclick="window.open('mailModifyForm.ad?openInit=true&col_title=deli_ing_title&col_content=deli_ing_content','','width=1200, height=900')">수정</a> | 
			<a href="#" title="자동전송메일 미리보기" onclick="window.open('mail_form/new_mem_preview.jsp?openInit=true','','width=1200, height=900')">미리보기</a>
		</td>
	</tr>
	<tr>
		<td>배송완료</td>
		<td><input type="checkbox" name="G" value="${mailOption.deli_fin }" id="fChk"/></td>
		<td><a href="#" title="자동전송메일 수정" onclick="window.open('mailModifyForm.ad?openInit=true&col_title=deli_fin_title&col_content=deli_fin_content','','width=1200, height=900')">수정</a> | 
			<a href="#" title="자동전송메일 미리보기" onclick="window.open('mail_form/new_mem_preview.jsp?openInit=true','','width=1200, height=900')">미리보기</a>
		</td>
	</tr>
	<tr>
		<td>구매확정</td>
		<td><input type="checkbox" name="H" value="${mailOption.confirm_order }" id="fChk"/></td>
		<td><a href="#" title="자동전송메일 수정" onclick="window.open('mailModifyForm.ad?openInit=true&col_title=confirm_order_title&col_content=confirm_order_content','','width=1200, height=900')">수정</a> | 
			<a href="#" title="자동전송메일 미리보기" onclick="window.open('mail_form/new_mem_preview.jsp?openInit=true','','width=1200, height=900')">미리보기</a>
		</td>
	</tr>
	<tr>
		<td>주문취소승인</td>
		<td><input type="checkbox" name="I" value="${mailOption.acc_cancel }" id="fChk"/></td>
		<td><a href="#" title="자동전송메일 수정" onclick="window.open('mailModifyForm.ad?openInit=true&col_title=acc_cancel_title&col_content=acc_cancel_content','','width=1200, height=900')">수정</a> | 
			<a href="#" title="자동전송메일 미리보기" onclick="window.open('mail_form/new_mem_preview.jsp?openInit=true','','width=1200, height=900')">미리보기</a>
		</td>
	</tr>
	<tr>
		<td>문의글 답변알림</td>
		<td><input type="checkbox" name="J" value="${mailOption.qna_re }" id="fChk"/></td>
		<td><a href="#" title="자동전송메일 수정" onclick="window.open('mailModifyForm.ad?openInit=true&col_title=qna_re_title&col_content=qna_re_content','','width=1200, height=900')">수정</a> | 
			<a href="#" title="자동전송메일 미리보기" onclick="window.open('mail_form/new_mem_preview.jsp?openInit=true','','width=1200, height=900')">미리보기</a>
		</td>
	</tr>	
</table>
<input type="submit" value="변경사항저장">
</form>
</body>
</html>