<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="sql" uri="http://java.sun.com/jsp/jstl/sql"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<!-- 가격 형식 -->
<fmt:formatNumber var="total_price" value="${totalPrice}" pattern="#,###"/>

<script type="text/javascript">
$(document).ready(function() {
	
	//전체 선택 체크박스 눌렀을 때
	$('#checkall').click(function() {
		if($('#checkall').prop("checked")) {
			$('input[name=chk]').prop('checked', true);
		}else {
			$('input[name=chk]').prop('checked', false);
		}
	});
	
	
	//삭제 버튼 눌렀을 때
	$('#del').click(function() {
		if($('input[name=chk]').is(":checked") == false){
			alert("상품을 선택하세요.");
			return false;
		}else {
			if(confirm('정말 삭제하시겠습니까?')) {
				var items=[];
				$('input[name=chk]:checkbox:checked').each(function(){items.push($(this).val());});
				var tmp = items.join(',');
				location.href='cartDelete.pro?pro_det_num='+tmp;
			}else {
				return false;
			}
		}
	});
	
	//select order 버튼 눌렀을 때
	$('#selectOrder').click(function() {
		if($('input[name=chk]').is(":checked") == false){
			alert("상품을 선택하세요.");
			return false;
		}else {
			var items=[];
			
			//체크한 상품상세코드만 배열로 만들어서 pro_det_num 값으로 보내고, 주문 페이지로 이동
			$('input[name=chk]:checkbox:checked').each(function(){items.push($(this).val());});
			var tmp = items.join(',');
			location.href='orderPage.pro?pro_det_num='+tmp;
		}
	});
	
	
	//all order 버튼 눌렀을 때
	$('#allOrder').click(function() {
		location.href='orderPage.pro';
	});
});
</script>

<div class="cartPage">
	<div class="cartList">
		<table class="cartTable">
			<colgroup>
				<col style="width:27px"/>
				<col style="width:92px"/>
				<col style="width:auto"/>
				<col style="width:80px"/>
				<col style="width:60px"/>
				<col style="width:80px"/>
			</colgroup>
			<tr>
				<th scope="col"><input type="checkbox" id="checkall"/></th>
				<th scope="col">IMAGE</th>
				<th scope="col">INFO</th>
				<th scope="col">PRICE</th>
				<th scope="col">QTY</th>
				<th scope="col">TOTAL</th>
			</tr>
			
			<c:forEach var="list" items="${cartList }" varStatus="i">
				<tr>
					<td><input type="checkbox" name="chk" value="${list.pro_det_num }"/></td>
					<td><img src="<%= request.getContextPath() %>/upload/${list.pro_photo }" class="cartImage"/></td>
					<td class="left">
						<a href="productDetail.pro?pro_num=${list.pro_num }" >${list.pro_name }</a>
						<ul class="cart_option">
							<li>[옵션 : ${list.color } / ${list.pro_size }]</li>
						</ul>
					</td>
					<fmt:formatNumber var="price" value="${list.pro_price}" pattern="#,###"/>
					<td>${price }</td>
					<td>
						<span class="qnt_area">
							<input type="text" name="qnt" value="${list.bas_pro_qnt }" readonly>
							<a href="cartQtyUp.pro?pro_det_num=${list.pro_det_num }" id="up"><ion-icon name="chevron-up-outline"></ion-icon></a>
							<a href="cartQtyDown.pro?pro_det_num=${list.pro_det_num }" id="down"><ion-icon name="chevron-down-outline"></ion-icon></a>
						</span>
					</td>
					<fmt:formatNumber var="total2" value="${list.pro_price * list.bas_pro_qnt }" pattern="#,###"/>
					<td>${total2 }</td>
				</tr>
			</c:forEach>
			
			<c:choose>
				<c:when test="${totalPrice < 50000 }">
					<c:set var="deliPrice" value="2500"/>
				</c:when>
				<c:otherwise>
					<c:set var="deliPrice" value="0"/>
				</c:otherwise>
			</c:choose>
			
			
			<fmt:formatNumber var="deli" value="${deliPrice}" pattern="#,###"/>
			<fmt:formatNumber var="total" value="${totalPrice+deliPrice}" pattern="#,###"/>
			<tr>
				<td colspan="6" class="right">
					상품 구매 금액 <b>${total_price }</b> + 배송비 = ${deli } ${deli == '0' ? '(무료)' : '' } 합계 = <b>${total }</b>
				</td>
			</tr>
		</table>
	</div>
	<div class="button_area">
		<span>
			선택한 상품을
		<a href="#" id="del" class="small_btn">DELETE</a>
		</span>
	</div>
	
	<div class="total">
		<table>
			<tr>
				<th>총 상품금액</th>
				<th>총 배송비</th>
				<th>결제예정금액</th>
			</tr>
			<tr>
				<td>${total_price }</td>
				<td>${deli }</td>
				<td>${total}</td>
			</tr>
		</table>
	</div>
	
	<div class="order_button_area">
		<p>
			<a href="#" id="selectOrder" class="w">SELECT ORDER</a>
			<a href="#" id="allOrder" class="b">ALL ORDER</a>
		</p>
	</div>
</div>
