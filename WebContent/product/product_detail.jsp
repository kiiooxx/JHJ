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
		alert("dd");
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
				var h = '';
				<c:forEach var="list" items="${prdDetList }" varStatus="i">
					if(size == "${list.pro_size}" && color == "${list.color}") {
					alert(color);
						h += '<tr class="option_product${i.count}">';
						h += '<td><p class="product">';
						h += '<input type="hidden" name="pro_det_num" value="${list.pro_det_num}"/>';
						h += '<span>' + color + '/</span>';
						h += '<span>' + size + '</span></p></td>';
						h += '<td><span class="qnt">';
						h += '<input type="number" value="1" min="1" name="qnt" id="qnt_${i.count}"/>';
						h += '<a href="#" id="delItem${i.count}">X</a></span></td>';
						h += '<td class="right"><span class="price" id="price_${count}">${prd.pro_price}</span></td></tr>';
						alert(h);
						$('#optionProduct').append(h);
						
						var price = $('.total_price').text();
						$('.total_price').text(Number(price) + Number(${prd.pro_price}));
						
						var qnt = $('.total_qnt').text();
						$('.total_qnt').text(Number(qnt) + 1);
						
						$('#total_price').val(Number(price) + Number(${prd.pro_price}));
						$('#total_qnt').val(Number(qnt) + 1);
						
						$(this).val('none').prop('selected', true);	
					}
				</c:forEach>
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
				var h = '';
				<c:forEach var="list" items="${prdDetList }" varStatus="i">
					if(size == "${list.pro_size}" && color == "${list.color}") {
						alert(size);
						h += '<tr class="option_product${i.count}">';
						h += '<td><p class="product">';
						h += '<input type="hidden" name="pro_det_num" value="${list.pro_det_num}"/>';
						h += '<span>' + color + '/</span>';
						h += '<span>' + size + '</span></p></td>';
						h += '<td><span class="qnt">';
						h += '<input type="number" value="1" min="1" name="qnt" id="qnt_${i.count}"/>';
						h += '<a href="#" id="delItem${i.count}">X</a></span></td>';
						h += '<td class="right"><span class="price" id="price_${count}">${prd.pro_price}</span></td></tr>';
						alert(h);
						$('#optionProduct').append(h);
						
						var price = $('.total_price').text();
						$('.total_price').text(Number(price) + Number(${prd.pro_price}));
						
						var qnt = $('.total_qnt').text();
						$('.total_qnt').text(Number(qnt) + 1);
						
						$('#total_price').val(Number(price) + Number(${prd.pro_price}));
						$('#total_qnt').val(Number(qnt) + 1);
						
						$(this).val('none').prop('selected', true);	 
					}
				</c:forEach>
			}
		}
		return false;
	});
	
	$('input[name="qnt_"]').on('change', function() {
		alert('ㅇㅇㅇ');
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
		alert("삭제");
		var id = $(this).attr("id")
		var num = id.replace("delItem", "");
		
		$('.option_product'+num).remove();
		
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
	
	$('#cart').on('click', function() {
		 var pro_num = "${prd.pro_num}";
		 var size = $("input[name='pro_det_num']").length;
		 var prodetnum = new Array(size);
		 for(var i=0; i<size; i++){                          
			 prodetnum[i] = $("input[name='pro_det_num']")[i].value;
		 }
		 alert(prodetnum);
		 var size2 = $("input[name='qnt']").length;
		 var qnt = new Array(size2);
		 for(var i=0; i<size2; i++){                          
			 qnt[i] = $("input[name='qnt']")[i].value;
		 }
		 alert(qnt);
		$.ajax({
			url : '<%=request.getContextPath()%>/addCart',
			dataType : 'json',
			type : 'POST',
			data : 'pro_det_num='+prodetnum+'&qnt='+qnt+'&pro_num='+pro_num,
			cache: false,
			contentType : 'application/x-www-form-urlencoded; charset=UTF-8',
	        processData: false,
			success : function(data) {
					alert('선택한 상품을 장바구니에 담았습니다!');
			},
			error : function() {
				console.log("에러");
			}
		});
	});
});
</script>

<style>

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
					<li><a href="cartAdd.pro?pro_num=${prd.pro_num }&" class="btn_w" id="cart">SHOPPING CART</a></li>
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