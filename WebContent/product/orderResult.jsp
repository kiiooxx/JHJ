<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<div id="join_form">

	<div class="orderInfo">
	<fmt:formatNumber var="reviewPoint" value="${reviewPoint}" pattern="#,###"/>
		<h1>주문이 완료되었습니다!<br>
			구매확정 시,
			<c:choose>
				<c:when test="${pointMan.p_mark eq 'per'}">구매금액의 ${pointMan.p_rate }</c:when>
				<c:when test="${pointMan.p_mark eq 'won'}">${confrimPoint}</c:when>
				<c:when test="${pointMan.p_mark eq 'double'}">${confrimPoint}</c:when>
			</c:choose>
			원 적립예정<br>
			리뷰 작성 시, ${reviewPoint }원 추가 적립
		</h1>
	</div>

	<div class="join_table">
		<table>
			<h3 class="join_title">주문 정보</h3>
			<tr>
				<th>주문번호</th>
				<td>${order.sel_num }</td>
			</tr>
			<tr>
				<th>주문일자</th>
				<td>${order.sel_date }</td>
			</tr>
			<tr>
				<th>주문자</th>
				<td>${order.user_id }</td>
			</tr>
		</table>
	</div>
	
	<div class="cartList">
		<table class="cartTable">
			<h3 class="join_title">주문 상품 정보</h3>
			<colgroup>
					<col style="width:92px"/>
					<col style="width:auto"/>
					<col style="width:100px"/>
					<col style="width:100px"/>
					<col style="width:100px"/>
			</colgroup>
			<tr>
				<th scope="col">IMAGE</th>
				<th scope="col">INFO</th>
				<th scope="col">PRICE</th>
				<th scope="col">QTY</th>
				<th scope="col">TOTAL</th>
			</tr>
			
			<c:forEach var="list" items="${cartList }">
				<tr>
					<td>
						<a href="productDetail.pro?pro_num=${orderProList.pro_num }" >
							<img src="<%=request.getContextPath() %>/upload/${list.pro_photo }" class="cartImage">
						</a>
					</td>
					<td class="left">
						${list.pro_name }<br>[옵션 : ${list.color } / ${list.pro_size }]</td>
						<fmt:formatNumber var="price" value="${list.pro_price}" pattern="#,###"/>
					<td>${price }</td>
					<td>${list.bas_pro_qnt }</td>
					<fmt:formatNumber var="total2" value="${list.pro_price * list.bas_pro_qnt }" pattern="#,###"/>
					<td>${total2 }</td>
				</tr>
			</c:forEach>
		</table>
	</div>
	
	<div class="join_table">
		<table>
			<h3 class="join_title">배송지 정보</h3>
			<tr>
				<th>받는 사람</th>
				<td>${deliInfo.rec_name }</td>
			</tr>
			
			<tr>
				<th>전화번호</th>
				<td>${deliInfo.rec_tel}</td>
			</tr>
			
			<tr>
				<th>배송주소</th>
				<td>${deliInfo.deli_postcode } <br>
					${deliInfo.deli_addr1 } ${deliInfo.deli_addr2 }
				</td>
			</tr>
		</table>
	</div>
	
	<div class="join_table">
		<table>
			<h3 class="join_title">결제 정보</h3>
				<c:if test="${payInfo.pay_type eq 'mutong'}">
					<tr>
						<th>결제정보 </th>
						<td>무통장입금<br>
							입금자명 : ${payInfo.acc_name }, 계좌번호 : 
							<c:if test="${payInfo.acc_bank eq 'kookmin' }">국민은행:0000000000000(주)희희 </c:if>
							<c:if test="${payInfo.acc_bank eq 'daegu' }">대구은행:0000000000000(주)희희</c:if>
							<c:if test="${payInfo.acc_bank eq 'nonghyeop' }">농협:000000000000(주)희희</c:if>
						</td>
					</tr>
					<tr>
						<th>입금기한</th>
						<td>${payInfo.pay_exp }</td>
				</c:if>
				
				<c:if test="${payInfo.pay_type eq 'silsi'}">
					<tr>
						<th>결제정보</th>
						<td>실시간 계좌이체<br>
							예금주명 : ${payInfo.acc_name }, 입금은행 : ${payInfo.acc_bank }
						</td>
					</tr>
				</c:if>
				<c:if test="${payInfo.pay_type eq 'credit'}">
					<tr>
						<th>결제정보</th>
						<td>카드결제<br>
							현대(3210-****-****-****) - 일시불
						</td>
					</tr>
				</c:if>
				
			<tr>
				<th>총 상품금액</th>
				<%-- <fmt:formatNumber var="total" value="${totalMoney}" pattern="#,###"/> --%>
				<td>${total1 }원</td>
			</tr>
			
			<tr>
				<th>배송비 (+)</th>
				<fmt:formatNumber var="deli_price" value="${order.deli_price}" pattern="#,###"/>
				<td>${deli_price }원 </td>
			</tr>
			<tr>
				<th>적립금 (-)</th>
				<fmt:formatNumber var="point_use" value="${order.point_use}" pattern="#,###"/>
				<td>${point_use }원</td>
			</tr>
			<tr>
				<th>결제금액</th>
				<td>${result2}원</td>
			</tr>
		</table>
	</div>
	
	<div class="order_button_area">
		<p>
			<a href="main.pro" class="b">메인으로</a>
		</p>
	</div>

</div>