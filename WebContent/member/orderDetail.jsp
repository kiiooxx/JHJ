<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="vo.OrderProView" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<style>
.thumb {
	width: 90px;
}
</style>
<body>
		<div align ="center" class="titleArea">
			<h2>주문상세조회</h2>
		</div>
		<br><br>
		<!-- 주문정보 -->
		<div align ="center" class="orderArea">
			<div class="title">
				<h3>주문정보</h3>
			</div>
			
			<div class="boardView">
				<table border="1" summary="">
				
					<tbody>
					<tr>
						<th>주문번호</th>
						<td>${orderInfo.sel_num}</td>
						
					</tr>
					<tr>
						<th>주문일자</th>
						<td>${orderInfo.sel_date}</td>
					</tr>
					<tr>
						<th>주문자</th>
						<td>${orderInfo.user_name}</td>
					</tr>
					<tr>
					<tr>
						<th>주문처리상태</th>
						<td><c:if test="${orderInfo.sel_status eq 'order_done' }">주문완료</c:if>
<c:if test="${orderInfo.sel_status eq 'check_paid' }">결제확인</c:if>
<c:if test="${orderInfo.sel_status eq 'send_pro' }">상품발송</c:if>
<c:if test="${orderInfo.sel_status eq 'deli_ing' }">배송중</c:if>
<c:if test="${orderInfo.sel_status eq 'deli_fin' }">배송완료</c:if>
<c:if test="${orderInfo.sel_status eq 'order_confirm' }">구매확정</c:if></td>	
					</tbody>
				</table>
			</div>
			
		
			<div align ="center" class="orderArea">
				<div class="title">
					<h3>주문상품정보</h3>
				</div>
					<table border="1" class="boardList ">
			
				
					
				<thead>
					<tr>
						<th>이미지</th>
						<th>상품정보</th>
						<th>수량</th>
						<th>상품구매금액</th>
						
						

					</tr>
				</thead>
				<c:forEach items="${orderProList }" var="orderProList">
				<tbody>
					<tr>
						<td><img src="<%=request.getContextPath() %>/upload/${orderProList.pro_photo}" class="thumb"></td>
						
						<td><strong>${orderProList.pro_name}</strong></a>
							<div>[옵션: ${orderProList.color}]</div>
						<td>${orderProList.pro_qnt}</td>
						<td><strong>${orderProList.pro_price}</strong></td>		
					</tr>
				</tbody>
				</c:forEach>	
			</table>
			<br><br>
			
			
			주문처리상태  : 
		
			
		<strong><c:if test="${orderInfo.sel_status eq 'order_done' }">주문완료</c:if>
<c:if test="${orderInfo.sel_status eq 'check_paid' }">결제확인</c:if>
<c:if test="${orderInfo.sel_status eq 'send_pro' }">상품발송</c:if>
<c:if test="${orderInfo.sel_status eq 'deli_ing' }">배송중</c:if>
<c:if test="${orderInfo.sel_status eq 'deli_fin' }">배송완료</c:if>
<c:if test="${orderInfo.sel_status eq 'order_confirm' }">구매확정</c:if></strong><br><br>
				
								<a href="javascript:void(0);" name="orderCancel" id="orderCancel"
								class="btn"
								onclick="window.open('idCheckForm.mem?openInit=true','','width=300, height=200')">주문취소</a>
									<a href="javascript:void(0);" name="orderConfirm" id="orderConfirm"
								class="btn"
								onclick="window.open('idCheckForm.mem?openInit=true','','width=300, height=200')">구매확정</a>
							
						
				
				
				
				
				
				<br>
				<br>
			
			<table>
					<tr>
						<td colspan="7"><strong class="type">[기본배송]</strong> 상품구매금액
							 <strong></strong> + 배송비
							${orderInfo.deli_price}원 = 합계 : <strong class="total"><span>${orderInfo.final_price}</span></strong>
							<span class="displaynone"></span></td>
					</tr>
			</table>
			<div>
				<div class="title">
					<h3>최종 결제 정보</h3>
				</div>
				<table border="1" >
					
					<tbody>
						<th>총 주문금액</th>
						<td><strong>${orderInfo.final_price}원</strong></td>
						
						<th>결제 수단</th>
						<td><c:if test="${payInfo.pay_type eq 'mutong'}">무통장입금</c:if>
			<c:if test="${payInfo.pay_type eq 'silsi'}">실시간계좌이체</c:if>
			<c:if test="${payInfo.pay_type eq 'credit'}">카드결제</c:if></td>
						<td> 입금자:    ${payInfo.acc_name }           
						
						
						
						
						 계좌정보 : <td>
			<c:if test="${payInfo.acc_bank eq 'kookmin'}">국민은행:0000000000000(주)희희</c:if>
			<c:if test="${payInfo.acc_bank eq 'daegu'}">대구은행:0000000000000(주)희희</c:if>
			<c:if test="${payInfo.acc_bank eq 'nonghyeop'}">농협:000000000000(주)희희</c:if>
		</td>      </td>
						
					</tbody>
				</table>
			</div>
			
			<div class="title">
				<h3>배송지정보</h3>
			</div>
			<div class="boardView">
				<table border="1" summary="">
					<caption>배송지정보</caption>
					<tbody>
						<tr>
							<th >받으시는분</th>
							<td> ${orderInfo.user_name} </td>
						</tr>
						<tr>
							<th >우편번호</th>
							<td> ${deliInfo.deli_postcode} </td>
						</tr>
						<tr>
							<th >주소1</th>
							<td> ${deliInfo.deli_addr1} </td>
						</tr>
						<tr >
							<th >주소2</th>
							<td>${deliInfo.deli_addr2}</td>
						</tr>
						<tr>
							<th >휴대전화</th>
							<td> ${deliInfo.rec_tel} </td>
						</tr>

					</tbody>
				</table>
			</div>

</body>
</html>