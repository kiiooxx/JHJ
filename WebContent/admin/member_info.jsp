<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ page import="vo.Member" %>    
<%@ page import="vo.Order" %>
<c:set var="pageInfo" value="${requestScope.pageInfo }"/>
<c:set var="member" value="${requestScope.member }"/>
<c:set var="order" value="${requestScope.order }"/>

<c:set var="user_id"/>

<c:if test="${user_id ne null }">
	<c:set var="user_id" value="${requestScope.user_id }"/>
</c:if>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<style>
table{
	border: 1px solid;
	width: 70%;
}
th, td{
	border: 1px solid;
}
</style>
<body>
<form action="memberInfo.ad" method="post">
<h2>회원 정보 조회</h2>

	<table>	
	
		<tr>
			<td>아이디</td><td>${member.user_id }</td>
		</tr>
		<tr>
			<td>회원등급</td><td>${member.grade }</td>
		</tr>
		<tr>
			<td>이름</td><td>${member.user_name }</td>
		</tr>
		<tr>
			<td>연락처</td><td>${member.tel }</td>
		</tr>
		<tr>
			<td>주소</td><td>${member.addr1} ${member.addr2}</td>
		</tr>
		<tr>
			<td>우편번호</td><td>${member.postcode }</td>
		</tr>
		<tr>
			<td>이메일</td><td>${member.email }</td>
		</tr>
		<tr>
			<td>성별</td>
			<td>
			<c:choose>
				<c:when test="${member.sex eq null || member.sex eq ''} ">
					체크안함
				</c:when>
				<c:otherwise>
					${member.sex }
				</c:otherwise>
			</c:choose>
			</td>
		</tr>
		<tr>
			<td>생년월일</td>
			<td>
			<c:choose>
				<c:when test="${member.birth eq null || member.birth eq ''} ">
					기입안함
				</c:when>
				<c:otherwise>
					${member.birth }
				</c:otherwise>
			</c:choose>
			</td>
		</tr>
		<tr>
			<td>가입일</td><td>${member.joindate }</td>
		</tr>	
	
	</table>

		<br><br><br>
</form>

<form action="orderList.ad" method="post">
<h2>회원 주문 내역</h2>

	<table>
		<tr>
			<td>주문번호 </td><td>주문일자</td><td>주문상품정보</td><td>결제금액</td><td>상태</td><td>비고</td>
		</tr>
	<!-- 여기서부터 데이터 뿌려주기 -->
		<c:choose>
		<c:when test="${!empty order}">
		
			<c:forEach items="${order }" var="order">
				<tr>
					<td><a href="orderManageDetail.ad?sel_num=${order.sel_num }&user_id=${member.user_id}">${order.sel_num }&nbsp;[상세내역보기]</a></td>
					<td>${order.sel_date }</td>
					<td>${order.pro_name }</td>
					<td>${order.final_price }원</td>
					<td>
					<c:if test="${order.sel_status eq 'order_done' }">주문완료</c:if>
					<c:if test="${order.sel_status eq 'check_paid' }">입금확인</c:if>
					<c:if test="${order.sel_status eq 'send_pro' }">상품발송</c:if>
					<c:if test="${order.sel_status eq 'deli_ing' }">배송중</c:if>
					<c:if test="${order.sel_status eq 'deli_fin' }">배송완료</c:if>
					<c:if test="${order.sel_status eq 'order_confirm' }">구매확정</c:if>
					</td>
					<td>
					<c:if test="${fn:contains(order.cancel_req,'Y')}">
					<span style="color:red">취소요청</span>
					</c:if>
					<c:if test="${fn:contains(order.cancel_req,'C')}">
					<span style="color:blue">취소완료</span>
					</c:if>
					</td>
				</tr>	
			</c:forEach>
		</c:when>
		<c:otherwise>
			<tr>
				<td colspan="5">
				 주문 내역이 없습니다.
				</td>
			</tr>
		</c:otherwise>
	</c:choose>
	</table>
	
<!-- 여기서부터 페이징 -->
<section id="page">
			<c:choose>
				<c:when test="${pageInfo.page <= 1 }">
					[이전]&nbsp;	
				</c:when>
				<c:otherwise>
					<a href="orderList.ad?page=${pageInfo.page-1 }&user_id=${order.user_id}">[이전]</a>&nbsp;	
				</c:otherwise>
			</c:choose>
	
			<c:forEach var="a" begin="${pageInfo.startPage }" end="${pageInfo.endPage }" step="1">
			
				<c:choose>
					<c:when test="${a eq pageInfo.page }"> 
						[${a}]				<!-- 현재페이지는 링크 안걸어도 되니까. -->
					</c:when>
					<c:otherwise>
						<a href="orderList.ad?page=${a}&user_id=${order.user_id }">[${a}]</a>
					</c:otherwise>
				</c:choose>
			</c:forEach>
			<c:choose>
				<c:when test="${pageInfo.page >= pageInfo.maxPage }">
					&nbsp;[다음]
				</c:when>
				<c:otherwise>
					<a href="orderList.ad?page=${pageInfo.page+1 }&user_id=${order.user_id }">[다음]</a>	
				</c:otherwise>
			</c:choose>
	</section>

<!-- 여기까지 페이징 -->			
	


</form>

</body>
</html>