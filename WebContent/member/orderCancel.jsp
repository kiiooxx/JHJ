<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<script type="text/javascript">

//설렉트뱍스틑 기타사유로 뒀을때
$(document).ready(function(){
	
	$('#reqselect').on('change',function() {
		var reason = $('#reqselect option:selected').val();
		
		if(reason == 'etc') {
			$('#reasontext').show();
		}else {
			$('#reasontext').hide();
		}
	});

});	
</script>

<style>
	#reasontext {display:none;}
</style>
	<h2>취소요청처리</h2>
	
	<!-- 주문취소정보 -->
	<div class="cartList">
		<table class="cartTable">
			<h3 class="join_title">상품주문번호 : ${orderInfo.sel_num }</h3>
			<colgroup>
				<col style="width:92px"/>
				<col style="width:auto"/>
				<col style="width:100px"/>
				<col style="width:100px"/>
			</colgroup>
				
			<tr>
				<th scope="col">IMAGE</th>
				<th scope="col">INFO</th>
				<th scope="col">QTY</th>
				<th scope="col">PRICE</th>
			</tr>

		<c:forEach var="orderProList" items="${orderProList}" varStatus="i">
			<tr>
				<td>
					<a href="productDetail.pro?pro_num=${orderProList.pro_num }" >
						<img src="<%=request.getContextPath() %>/upload/${orderProList.pro_photo}" class="cartImage">
					</a>
				</td>
				<td class="left">${orderProList.pro_name}
					<div>[옵션: ${orderProList.color} / ${orderProList.pro_size }]</div>
				</td>
				<td>${orderProList.pro_qnt}</td>
				<fmt:formatNumber var="pro_price" value="${orderProList.pro_price}" pattern="#,###"/>
				<td><strong>${pro_price}</strong></td>
			</tr>
			
		</c:forEach>
			<fmt:formatNumber var="deli" value="${orderInfo.deli_price}" pattern="#,###"/>
			<fmt:formatNumber var="total3" value="${orderInfo.final_price - orderInfo.deli_price}" pattern="#,###"/>
			<fmt:formatNumber var="total4" value="${orderInfo.final_price}" pattern="#,###"/>
			<tr>
				<td colspan="6" class="right">
					상품 구매 금액(적립금 사용 포함) <b>${total3 }</b> + 배송비 = ${deli } ${deli == '0' ? '(무료)' : '' } 합계 = <strong>${total4 }</strong>
				</td>
			</tr>
	</table>
	
	<div class="join_table">
		<form action="ordercancelpro.mem" id="cancel_req" name="q" method="post">
			<table>
				<input type="hidden" name="sel_num" value="${orderInfo.sel_num }"/>
				<tr>
					<th>사유 선택<b class="req">*</b></th>
					<td>
						<select class="사유선택" name="reqselect" id="reqselect">
							<option value="구메의사없음"selected="selected">구매의사없음</option>
							<option value="색상 및 사이즈 변경">색상 및 사이즈 변경</option>
							<option value="다른 상품 잘못 주문">다른 상품 잘못 주문</option>
							<option value="서비스 정보 불만족">서비스 정보 불만족</option>
							<option value="상품 정보 상이">상품 정보 상이</option>
							<option value="etc" id="id">기타 사유</option>
						</select>	
					</td>
				</tr>
				
	
				<tr id="reasontext">
					<th>사유 입력<b class="req"></b></th>
					<td>
						<textarea name="cancel_reason" width="120" height="60" ></textarea>
					</td>
			  	</tr>
			</table>
			
			<div class="jo_btn">
				<a href="javascript:q.submit();">취소요청</a>&nbsp;&nbsp;
			</div>
		</form>
	</div>
