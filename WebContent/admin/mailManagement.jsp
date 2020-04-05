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


function saveBtn(){
	b.submit();
}
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
.thead{
	background: #F6F6F6;
}
p{
	text-align: center;
}
</style>

<body>
<form action="mailManagement.ad" method="post" name="b">
<div class="card shadow mb-4">
	<div class="card-header py-3">
		<h6 class="m-0 font-weight-bold text-primary">자동 메일발송 설정</h6>
    </div>
    <div class="card-body">
     		<table class="table table-bordered">
				<tr>
					<th class="thead">메일항목</th>
					<th class="thead">사용여부</th>
					<th class="thead">수정</th>
				</tr>
				<tr>
					<th>신규회원가입</th>
					<td><input type="checkbox" name="A" value="${mailOption.new_mem }" id="fChk"/></td>
					<td><a href="#" title="자동전송메일 수정" onclick="window.open('mailModifyForm.ad?openInit=true&col_title=new_mem_title&col_content=new_mem_content&p=false','','width=1000, height=900')">수정</a> | 
						<a href="#" title="자동전송메일 미리보기" onclick="window.open('mailModifyForm.ad?openInit=true&col_title=new_mem_title&col_content=new_mem_content&p=true','','width=1000, height=900')">미리보기</a>
					</td>
				</tr>
				<tr>
					<th>회원탈퇴</th>
					<td><input type="checkbox" name="B" value="${mailOption.quit_mem }" id="fChk"/></td>
					<td><a href="#" title="자동전송메일 수정" onclick="window.open('mailModifyForm.ad?openInit=true&col_title=quit_mem_title&col_content=quit_mem_content&p=false','','width=1000, height=900')">수정</a> | 
					    <a href="#" title="자동전송메일 미리보기" onclick="window.open('mail_form/new_mem_preview.jsp?openInit=true','','width=1200, height=900')">미리보기</a>
					</td>
				</tr>
				<tr>
					<th>신규주문내역</th>
					<td><input type="checkbox" name="C" value="${mailOption.order_info }" id="fChk"/></td>
					<td><a href="#" title="자동전송메일 수정" onclick="window.open('mailModifyForm.ad?openInit=true&col_title=order_info_title&col_content=order_info_content&p=false','','width=1000, height=900')">수정</a> | 
						<a href="#" title="자동전송메일 미리보기" onclick="window.open('mailModifyForm.ad?openInit=true&col_title=order_info_title&col_content=order_info_content&p=true','','width=1000, height=900')">미리보기</a>
					</td>
				</tr>
				<tr>
					<th>결제확인</th>
					<td><input type="checkbox" name="D" value="${mailOption.check_paid }" id="fChk"/></td>
					<td><a href="#" title="자동전송메일 수정" onclick="window.open('mailModifyForm.ad?openInit=true&col_title=check_paid_title&col_content=check_paid_content&p=false','','width=1000, height=900')">수정</a> | 
						<a href="#" title="자동전송메일 미리보기" onclick="window.open('mailModifyForm.ad?openInit=true&col_title=check_paid_title&col_content=check_paid_content&p=true','','width=1000, height=900')">미리보기</a>
					</td>
				</tr>
				<tr>
					<th>상품발송</th>
					<td><input type="checkbox" name="E" value="${mailOption.send_pro }" id="fChk"/></td>
					<td><a href="#" title="자동전송메일 수정" onclick="window.open('mailModifyForm.ad?openInit=true&col_title=send_pro_title&col_content=send_pro_content&p=false','','width=1000, height=900')">수정</a> | 
						<a href="#" title="자동전송메일 미리보기" onclick="window.open('mailModifyForm.ad?openInit=true&col_title=send_pro_title&col_content=send_pro_content&p=true','','width=1000, height=900')">미리보기</a>
					</td>
				</tr>
				<tr>
					<th>배송중</th>
					<td><input type="checkbox" name="F" value="${mailOption.deli_ing }" id="fChk"/></td>
					<td><a href="#" title="자동전송메일 수정" onclick="window.open('mailModifyForm.ad?openInit=true&col_title=deli_ing_title&col_content=deli_ing_content&p=false','','width=1000, height=900')">수정</a> | 
						<a href="#" title="자동전송메일 미리보기" onclick="window.open('mailModifyForm.ad?openInit=true&col_title=deli_ing_title&col_content=deli_ing_content&p=true','','width=1000, height=900')">미리보기</a>
					</td>
				</tr>
				<tr>
					<th>배송완료</th>
					<td><input type="checkbox" name="G" value="${mailOption.deli_fin }" id="fChk"/></td>
					<td><a href="#" title="자동전송메일 수정" onclick="window.open('mailModifyForm.ad?openInit=true&col_title=deli_fin_title&col_content=deli_fin_content&p=false','','width=1000, height=900')">수정</a> | 
						<a href="#" title="자동전송메일 미리보기" onclick="window.open('mailModifyForm.ad?openInit=true&col_title=deli_fin_title&col_content=deli_fin_content&p=true','','width=1000, height=900')">미리보기</a>
					</td>
				</tr>
				<tr>
					<th>구매확정</th>
					<td><input type="checkbox" name="H" value="${mailOption.confirm_order }" id="fChk"/></td>
					<td><a href="#" title="자동전송메일 수정" onclick="window.open('mailModifyForm.ad?openInit=true&col_title=confirm_order_title&col_content=confirm_order_content&p=false','','width=1000, height=900')">수정</a> | 
						<a href="#" title="자동전송메일 미리보기" onclick="window.open('mailModifyForm.ad?openInit=true&col_title=confirm_order_title&col_content=confirm_order_content&p=true','','width=1000, height=900')">미리보기</a>
					</td>
				</tr>
				<tr>
					<th>주문취소승인</th>
					<td><input type="checkbox" name="I" value="${mailOption.acc_cancel }" id="fChk"/></td>
					<td><a href="#" title="자동전송메일 수정" onclick="window.open('mailModifyForm.ad?openInit=true&col_title=acc_cancel_title&col_content=acc_cancel_content&p=false','','width=1000, height=900')">수정</a> | 
						<a href="#" title="자동전송메일 미리보기" onclick="window.open('mailModifyForm.ad?openInit=true&col_title=acc_cancel_title&col_content=acc_cancel_content&p=true','','width=1000, height=900')">미리보기</a>
					</td>
				</tr>
				<tr>
					<th>문의글 답변알림</th>
					<td><input type="checkbox" name="J" value="${mailOption.qna_re }" id="fChk"/></td>
					<td><a href="#" title="자동전송메일 수정" onclick="window.open('mailModifyForm.ad?openInit=true&col_title=qna_re_title&col_content=qna_re_content&p=false','','width=1000, height=900')">수정</a> | 
						<a href="#" title="자동전송메일 미리보기" onclick="window.open('mailModifyForm.ad?openInit=true&col_title=qna_re_title&col_content=qna_re_content&p=true','','width=1000, height=900')">미리보기</a>
					</td>
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