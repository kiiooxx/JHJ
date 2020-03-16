<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="vo.OrderProView" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%-- <c:set var="orderView" value="${requestScope.orderView }" /> --%>


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
table{
	width: 70%;
}

</style>
<script>

//배송상태변경
function changeBtn(f){
	if(confirm("배송상태를 변경하시겠습니까?") == true ){
		f.submit();
	}else{
		return;
	}
}
</script>
<body>
<!-- <form action="orderManageDetail.ad" method="post"> -->
<h2>주문 상세 내역</h2>
<table border=1>
	<tr>
		<th>주문번호</th><td colspan="5">${orderInfo.sel_num }</td>
	</tr>
	<tr>
		<th>주문일</th><td colspan="5">${orderInfo.sel_date }</td>
	</tr>
	<tr>	
		<th>주문자ID</th><td colspan="5">${orderInfo.user_id }</td>
	</tr>
	<tr>
		<th>주문자 이름</th><td colspan="5">${orderInfo.user_name }</td>
	</tr>
	<tr>	
		<th colspan="6">주문상품</th>
	</tr>

	<c:choose>
		<c:when test="${orderProList ne null }">
			<c:forEach items="${orderProList }" var="orderProList">
	<tr>
		
		<td><img src="${pageContext.request.contextPath}/upload/${orderProList.pro_photo }" class="proImage"/></td>
		<td>상품번호:${orderProList.pro_det_num }<br>상품명:${orderProList.pro_name }<br>옵션:${orderProList.pro_size }[${orderProList.color }]</td>
		<fmt:formatNumber var="pro_price" value="${orderProList.pro_price }" pattern="#,###"/>
		<td>${pro_price }원</td>
		<td>${orderProList.pro_qnt }개</td>
		<fmt:formatNumber var="price" value="${orderProList.pro_price*orderProList.pro_qnt}" pattern="#,###"/>
		<td>${price }원</td>
	</tr>
			</c:forEach>
		</c:when>
	</c:choose>
	<tr>
		<th>배송비</th>
		<fmt:formatNumber var="deli_price" value="${orderInfo.deli_price }" pattern="#,###"/>
		<td colspan="5">${deli_price }원</td>
	</tr>	
	<tr>
		<th>사용적립금</th>
		<fmt:formatNumber var="point_use" value="${orderInfo.point_use }" pattern="#,###"/>
		<td colspan="5">${point_use }원</td>
	</tr>
	<tr>
		<fmt:formatNumber var="total" value="${orderInfo.final_price }" pattern="#,###"/>
		<th>최종결제금액</th><td colspan="5">${total}원</td>
	</tr>
</table>
<br>
<h2>배송 정보</h2>
<table border=1>
	<c:if test="${deliInfo ne null }">
	<tr>
		<td>배송번호</td>
		<td>${deliInfo.deli_num }</td>
	</tr>
	<tr>
		<td>수령인 이름</td>
		<td>${deliInfo.rec_name }</td>
	</tr>
	<tr>
		<td>수령인 연락처</td>
		<td>${deliInfo.rec_tel }</td>
	</tr>
	<tr>
		<td>배송주소</td>
		<td>
			${deliInfo.deli_addr1 } ${deliInfo.deli_addr2 }<br>
			우편번호: ${deliInfo.deli_postcode }
		</td>
	</tr>
	<tr>
		<td>배송메시지</td>
		<td>
			<c:choose>
				<c:when test="${deliInfo.deli_content ne '' }">
					${deliInfo.deli_content }
				</c:when>
				<c:otherwise>
					없음
				</c:otherwise>
			</c:choose>
		</td>
	</tr>
	</c:if>
</table>
<br>
<h2>결제정보</h2>
<table border=1>
	<tr>
		<td>결제방식</td>
		<td>
			<c:if test="${payInfo.pay_type eq 'mutong'}">무통장입금</c:if>
			<c:if test="${payInfo.pay_type eq 'silsi'}">실시간계좌이체</c:if>
			<c:if test="${payInfo.pay_type eq 'credit'}">카드결제</c:if>
		</td>
	</tr>
	<tr>
		<td>입금자 성명</td>
		<td>${payInfo.acc_name }</td>
	</tr>
	<tr>
		<td>입금은행</td>
		<td>
			<c:if test="${payInfo.acc_bank eq 'kookmin'}">국민은행:0000000000000(주)희희</c:if>
			<c:if test="${payInfo.acc_bank eq 'daegu'}">대구은행:0000000000000(주)희희</c:if>
			<c:if test="${payInfo.acc_bank eq 'nonghyeop'}">농협:000000000000(주)희희</c:if>
		</td>
	</tr>
	<tr>
		<td>결제기한</td>
		<td>${payInfo.pay_exp }</td>
	</tr>
	<tr>
		<td>결제일</td>
		<td>
			<c:choose>
				<c:when test="${payInfo.pay_date ne null }">
					${payInfo.pay_date }
				</c:when>
				<c:otherwise>
					미결제
				</c:otherwise>
			</c:choose>
		</td>
	</tr>
</table>
<br>
<form action="changeStatus.ad" method="post" name="f">
<input type="hidden" value="${orderInfo.sel_num }" name="sel_num"/>
<input type="hidden" value="${orderInfo.user_id }" name="user_id"/>
<h2>배송상태</h2>
<c:if test="${orderInfo.sel_status eq 'order_done' }">주문완료</c:if>
<c:if test="${orderInfo.sel_status eq 'check_paid' }">결제확인</c:if>
<c:if test="${orderInfo.sel_status eq 'send_pro' }">상품발송</c:if>
<c:if test="${orderInfo.sel_status eq 'deli_ing' }">배송중</c:if>
<c:if test="${orderInfo.sel_status eq 'deli_fin' }">배송완료</c:if>
<c:if test="${orderInfo.sel_status eq 'order_confirm' }">구매확정</c:if>
<br>

<input type="radio" id="deliStatus" name="deliStatus" value="order_done">주문완료 
<input type="radio" id="deliStatus" name="deliStatus" value="check_paid">결제확인
<input type="radio" id="deliStatus" name="deliStatus" value="send_pro">상품발송 
<input type="radio" id="deliStatus" name="deliStatus" value="deli_ing">배송중
<input type="radio" id="deliStatus" name="deliStatus" value="deli_fin">배송완료
<input type="radio" id="deliStatus" name="deliStatus" value="order_confirm">구매확정
<br>
<input type="button" name="changeStatus" value="상태변경하기" onclick="javascript:changeBtn(document.f);"> alert띄우기.상태를 변경하시겠습니까? 원래상태->변경상태
</form>


<h2>주문취소 신청여부</h2>
<c:choose>
	<c:when test="${fn:contains(orderInfo.cancel_req,'N') }">
		취소요청 내역이 없습니다.	
	</c:when>
	<c:otherwise>
		주문 취소 요청이 있습니다.<input type="button" name="checkCancel" value="내역조회" onclick="window.open('주문취소내역.pro?openInit=true', '' , 'width=300, height=200')"/>
	</c:otherwise>
</c:choose>
<br>- 누르면 새창 or 같은창에 취소 사유랑 같이 승인버튼 나오게.승인버튼 누르면 취소되게. 
취소되면 취소가 완료되었습니다 알림 메세지 띄우고, 회원한테 메일 보내고, 회원 주문내역에도 취소완료 나오게. 
</body>
<!-- </form> -->
</html>