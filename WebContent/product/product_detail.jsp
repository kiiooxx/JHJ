<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="sql" uri="http://java.sun.com/jsp/jstl/sql"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<!-- 가격 형식 -->
<fmt:formatNumber var="price" value="${prd.pro_price}" pattern="#,###"/>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<script src="http://code.jquery.com/jquery-latest.js"></script>
<script>
$(document).ready(function(){
	var index1;
	var index2;
	
	$('#colorSelect').on('change', function() {
		//선택된 color, size값 받아오기
		var color = $(this).val();
		var size = $('#sizeSelect option:selected').val();
		
		//colorSelect에서 선택된 index 값 저장
		index1 = $("#colorSelect option").index($("#colorSelect option:selected"));
		
		if(!(index2 == "" || index2 == null || index2 == undefined)) {
			var index = Number(index1) * Number(index2);
			
			if($('.option_product'+index).is(":visible")){
				alert("이미 선택 된 상품입니다.");
			}else {
				$('#qnt_'+index).val('1');
				$('#price_'+index).text(${prd.pro_price});
				$('.option_product'+index).show();
				var prodetnum = $('#pdn'+index).val();
				var html = '';
				html += '<input type="hidden" id="size' + index + '" name="size" value="' + size + '"/>';
				html += '<input type="hidden" id="color' + index + '" name="color" value="' + color + '"/>';
				html += '<input type="hidden" name="pro_det_num" value="'+ prodetnum +'"/>';
				$("#totalPrice").append(html);
				
				var price = $('.total_price').text();
				$('.total_price').text(Number(price) + Number(${prd.pro_price}));

				var qnt = $('.total_qnt').text();
				$('.total_qnt').text(Number(qnt) + 1);
				
				$('#total_price').val(Number(price) + Number(${prd.pro_price}));
				$('#total_qnt').val(Number(qnt) + 1);
				
				index1 = '';
				index2 = '';
				
				$(this).val('none').prop('selected', true);
			}
		}	 
		return false;
	});
	
	$('#sizeSelect').on('change', function() {
		var size = $(this).val();
		var color = $('#colorSelect option:selected').val();
		index2 = $("#sizeSelect option").index($("#sizeSelect option:selected"));

		if(!(index1 == "" || index1 == null || index1 == undefined)) {
			var index = Number(index1) * Number(index2);
			
			if($('.option_product'+index).is(":visible")){
				alert("이미 선택 된 상품입니다.");
			}else {
				$('#qnt_'+index).val('1');
				$('#price_'+index).text(${prd.pro_price});
				$('.option_product'+index).show();
				var prodetnum = $('#pdn'+index).val();
				var html = '';
				html += '<input type="hidden" id="size' + index + '" name="size" value="' + size + '"/>';
				html += '<input type="hidden" id="color' + index + '" name="color" value="' + color + '"/>';
				html += '<input type="hidden" name="pro_det_num" value="'+ prodetnum +'"/>';
				$("#totalPrice").append(html);
				
				var price = $('.total_price').text();
				$('.total_price').text(Number(price) + Number(${prd.pro_price}));
				
				var qnt = $('.total_qnt').text();
				$('.total_qnt').text(Number(qnt) + 1);
				
				$('#total_price').val(Number(price) + Number(${prd.pro_price}));
				$('#total_qnt').val(Number(qnt) + 1);
				
				index1 = '';
				index2 = '';
				$(this).val('none').prop('selected', true);
			}
		}
		return false;
	});
	
	$('[id^=qnt_]').change(function(){
		var q = $(this).val();
		var id = $(this).attr("id")
		var num = id.replace("qnt_", "");
		
		//해당 옵션의 가격 바꾸기
		var pr = Number(${prd.pro_price}*q);
		$('#price_'+num).text(pr);
		
		//총금액 구하기
		var total_p = '0';
		var total_q = '0';
		var cnt = $('#cnt').val();
		
		for(var i=1; i<=cnt; i++) {
			total_p = Number(total_p) + Number($('#price_'+i).text());
			total_q = Number(total_q) + Number($('#qnt_'+i).val());
		}
		
		$('.total_price').text(total_p);
		$('.total_qnt').text(total_q);
		$('#total_price').val(total_p);
		$('#total_qnt').val(total_q);
		
	});
	
	//삭제
	$('[id^=delItem]').on('click', function() {
		var id = $(this).attr("id")
		var num = id.replace("delItem", "");
		
		$('.option_product'+num).hide();
		$('#size'+num).remove();
		$('#color'+num).remove();
		$('#price_'+num).text(0);
		$('#qnt_'+num).val(0);
		
		//총금액 구하기
		var total_p = '0';
		var total_q = '0';
		var cnt = $('#cnt').val();
		
		for(var i=1; i<=cnt; i++) {
			total_p = Number(total_p) + Number($('#price_'+i).text());
			total_q = Number(total_q) + Number($('#qnt_'+i).val());
		}
		
		$('.total_price').text(total_p);
		$('.total_qnt').text(total_q);
		$('#total_price').val(total_p);
		$('#total_qnt').val(total_q);
		
	});
});
</script>

<style>
[class^=option_product]{display:none;}
</style>
</head>
<body>
<div class="blank">
</div>

<form action="" name="f" method="post">
<div id="product_detail">
	<div class="detail_Area">
	 	<div class="img_Area">
			<img src="<%= request.getContextPath() %>/upload/${prd.pro_photo }">
		</div>
		
		<div class="info_Area">
			<div>
				<table>
					<tr>
						<td>
							<!-- 상품 이름 -->
							<p>${prd.pro_name }</p>
						</td>
					</tr>
					
					<tr>
						<td>
							<!-- 상품 가격 -->
							<p>${price }</p>
						</td>
					</tr>
				
					<tr>
						<td>
							<!-- 상품 소개 -->
							<p>${prd.pro_detail }</p>
						</td>
					</tr>
				
				</table>
			</div>
			
			<table class="info_table">
			
			<!-- 색상 -->
			<tr>
				<th>COLOR</th>
				<td>
				<select id="colorSelect">
					<option value="none" selected disabled hidden>-[필수]옵션을 선택해주세요-</option>
					<c:forEach var="clist" items="${prdDetList }" varStatus="i">
						<c:if test="${i.index == 0 || cl != clist.color}">
							<option value="${clist.color }">${clist.color }</option>
							<c:set var="cl" value="${clist.color }"/>		
						</c:if>
					</c:forEach>
			</select>
			</td>
			</tr>
			<!-- 사이즈 -->
			<tr>
				<th>SIZE</th>
				<td>
			<select id="sizeSelect">
				<option value="none" selected disabled hidden>-[필수]옵션을 선택해주세요-</option>
				<c:forEach var="slist" items="${prdDetList }" varStatus="i">
					<c:if test="${i.index == 0 || sz != slist.pro_size}">
						<option value="${slist.pro_size }">${slist.pro_size }</option>
						<c:set var="sz" value="${slist.pro_size }"/>
					</c:if>
				</c:forEach>
			</select>
			</td>
			</tr>
			</table>
			
			<div id="totalPrice">
				<table>
					<tbody id="optionProduct">
					
					<c:set var="count" value="0"/>
					
					<c:forEach var="list" items="${prdDetList }" varStatus="i">
						
						<c:if test="${i.index==0 || cl != list.color || sz != list.pro_size}">
							<c:set var="cl" value="${list.color }"/>
							<c:set var="sz" value="${list.pro_size }"/>
							<c:set var="count" value="${count + 1}" />
							
							<tr class="option_product${count }">
								<td>
									<p class="product">
										<input type="hidden" id="pdn${count }" value="${list.pro_det_num }"/>
										<span id="color_${count }">${list.color } / </span>
										<span id="size_${count }">${list.pro_size }</span>
									</p>
								</td>
							
								<td>
									<span class="qnt">
										<input type="number" value="0" min="1" name="qnt" id="qnt_${count }"/>
										<a href="#" id="delItem${count }">X</a>
									</span>
								</td>
								
								<td class="right">
									<span class="price" id="price_${count }">0</span>
								</td>
							</tr>
						</c:if>

					</c:forEach>
					
					<input type="hidden" id="cnt" value="${count }"/>
					</tbody>
					<tr>
						<td><span>TOTAL(QUANTITY) : </span>
							<span class="total_price">0</span>
							(<span class="total_qnt">0</span>)
							<input type="hidden" name="total_price" id="total_price"/>
							<input type="hidden" name="total_qnt" id="total_qnt"/>
						</td>
					</tr>
				</table>
			</div>
			
			<!-- 주문, 장바구니 보낼때 
				getParameterValues : 사이즈(size), 색상(color), 수량(qnt) (금액은 수량 곱하기)
				getParameter : 총금액(total_price), 총 수량(total_qnt)
			-->
			
			<!-- 주문, 장바구니버튼 -->
			<div id="btnArea">
				<ul>
					<li><a href="#" class="btn_b">BUY</a></li>
					<li><a href="#" class="btn_w">SHOPPING CART</a></li>
				</ul>
			</div>
		</div>
	</div>
	
	<div id="prdContent">
		<div id="con">
			<div class="cont">
				${prd.pro_content }
			</div>
		</div>
	</div>
	
	<div id="prdReview">
	
	</div>
	
	<div id="prdQnA">
	
	</div>
</div>
</form>
</body>
</html>