<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<style>
img{
	width: 92px;
}

</style>
<body>
<form action="orderResult.pro" method="post">
<h1>주문이 완료되었습니다.</h1>

<table>
	<tr>
		<td>주문번호 : ${order.sel_num }</td>
	</tr>
	<c:forEach var="list" items="${cartList }">
	<tr>
		<td><img src="<%=request.getContextPath() %>/upload/${list.pro_photo }"></td>
		<td>${list.pro_name }<br>[옵션 : ${list.color } / ${list.pro_size }]</td>
		<td>${list.bas_pro_qnt }</td>
		<fmt:formatNumber var="total2" value="${list.pro_price * list.bas_pro_qnt }" pattern="#,###"/>
		<td>${total2 }</td>
	</tr>
	</c:forEach>
	<tr>
		<td>배송지 정보 : ${deliInfo.rec_name }<br> 전화번호 : ${deliInfo.rec_tel}<br> 배송주소: ${deliInfo.deli_postcode }<br>${deliInfo.deli_addr1 } ${deliInfo.deli_addr2 }</td>
	</tr>
	
	
	<tr>
		<td> 
		<c:if test="${payInfo.pay_type eq 'mutong'}">
		결제정보 : 무통장입금
		입금자명 : ${payInfo.acc_name }<br> 
		입금은행 : <c:if test="${payInfo.acc_bank eq 'kookmin' }">국민은행:0000000000000(주)희희 </c:if>
		<c:if test="${payInfo.acc_bank eq 'daegu' }">대구은행:0000000000000(주)희희</c:if>
		<c:if test="${payInfo.acc_bank eq 'nonghyeop' }">농협:000000000000(주)희희</c:if>
		<br> 
		입금기한 : ${payInfo.pay_exp }
		</c:if>
		<c:if test="${payInfo.pay_type eq 'silsi'}">
		결제정보: 실시간 계좌이체
		예금주명: ${payInfo.acc_name }<br>
		입금은행 : ${payInfo.acc_bank }<br>
		</c:if>
		<c:if test="${payInfo.pay_type eq 'credit'}">
		결제정보: 카드결제  현대(3210-****-****-****) - 일시불
		</c:if>
		</td> 
	</tr>
	
	
	
	<tr>
		<td>총 상품금액 : ${total2 } 원</td>
		<td>배송비 : (+) ${order.deli_price }원 </td>
		<td>적립금 : (-) 원</td>
		<td>결제금액 :  ${totalMoney }원</td>
	</tr>
	<tr>
		<td>구매확정 시, 00원 적립예정 <br> 리뷰 작성 시, 00원 추가 적립</td>
	</tr>
</table>

<a href="#">메인으로 돌아가기</a>

</form>
</body>
</html>