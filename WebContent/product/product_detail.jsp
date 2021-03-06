<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="sql" uri="http://java.sun.com/jsp/jstl/sql"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<!-- 가격 형식 -->
<fmt:formatNumber var="price" value="${prd.pro_price}" pattern="#,###"/>


<script>

//숫자 콤마 추가
function numberFormat(inputNumber) {
	return inputNumber.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",");
}

//숫자 콤마 제거
function removeComma(inputNumber) {
	return parseInt(inputNumber.replace(/,/g,""));
}

$(document).ready(function(){
	var index1;
	var index2;
	var len = "${fn:length(prdDetList) }";
	
	$('#colorSelect').change(function() {
		var pro_num = "${prd.pro_num}";
		var color = $(this).val();
		
		//사이즈 셀렉트박스 옵션 변경
		var html = '<option value="none" selected disabled hidden>-[필수]옵션을 선택해주세요-</option>';
		<c:forEach var="list" items="${prdDetList }" varStatus="i">
			if("${list.color}" == color) {
				html += '<option value=${list.pro_size}>${list.pro_size}</option>';
			}
		</c:forEach>

		$('#sizeSelect').empty();
		$('#sizeSelect').append(html);
				
	});
	
	
	$('#sizeSelect').on('change', function() {
		var size = $(this).val();
		var color = $('#colorSelect option:selected').val();

		var h = '';
		<c:forEach var="list" items="${prdDetList }" varStatus="i">
			if(size == "${list.pro_size}" && color == "${list.color}") {
				if($('.option_product${i.count}').is(":visible")){
					alert("이미 선택 된 상품입니다.");
				}else if("${list.stock_qnt}" <= 0) {
					swal("재고가 없습니다!", "다른 상품을 선택해주세요.", "warning");
				}else {
					h += '<tr class="option_product${i.count}">';
					h += '<td><p class="product">';
					h += '<input type="hidden" name="pro_det_num" value="${list.pro_det_num}"/>';
					h += '<input type="hidden" name="color" value="' + color + '"/>';
					h += '<input type="hidden" name="size" value="' + size + '"/>';
					h += '<span>' + color + '/</span>';
					h += '<span>' + size + '</span></p></td>';
					h += '<td><span class="qnt">';
					h += '<input type="number" value="1" min="1" name="qnt" id="qnt_${i.count}"/>';
					h += '<a href="#" id="delItem${i.count}">X</a></span></td>';
					h += '<td class="right"><span class="price" id="price_${i.count}">' + numberFormat("${prd.pro_price}") + '</span></td></tr>';
					$('#optionProduct').append(h);
					
					var price = removeComma($('.total_price').text());
					$('.total_price').text(numberFormat(Number(price) + Number(removeComma("${prd.pro_price}"))));
					
					var qnt = $('.total_qnt').text();
					$('.total_qnt').text(Number(qnt) + 1);
					
					$(this).val('none').prop('selected', true);	
				}
			}
		</c:forEach>

		return false;
	});
	
	
	$('body').on('change', '[id^=qnt_]', function() {
		//수량 구하기
		var q = $(this).val();
		var id = $(this).attr("id")
		var num = id.replace("qnt_", "");	//인덱스번호
		
		//해당 옵션의 가격 바꾸기
		var pr = Number("${prd.pro_price}"*q);	//상품가격*수량
		
		$('#price_'+num).text(numberFormat(pr));

		//총금액 구하기
		var total_p = '0';
		var total_q = '0';
		
		//선택한 상품의 금액을 더한다
		for(var i=1; i<=len; i++) {
			if($('.option_product' + i).is(":visible")) {
				total_p = Number(total_p) + Number(removeComma($('#price_'+i).text()));
				total_q = Number(total_q) + Number($('#qnt_'+i).val());
			}
		}
		
		$('.total_price').text(numberFormat(total_p));
		$('.total_qnt').text(total_q);
	});
	
	//삭제
	$('body').on('click', '[id^=delItem]', function() {
		var id = $(this).attr("id")
		var num = id.replace("delItem", "");
		
		$('.option_product'+num).remove();
		
		//총금액 구하기
		var total_p = '0';
		var total_q = '0';
		
		for(var i=1; i<=len; i++) {
			if($('.option_product' + i).is(":visible")) {
				total_p = Number(total_p) + Number(removeComma($('#price_'+i).text()));
				total_q = Number(total_q) + Number($('#qnt_'+i).val());
			}
		}
		
		$('.total_price').text(numberFormat(total_p));
		$('.total_qnt').text(total_q);
		
	});
	
	$('#cart').on('click', function() {
		 var pro_num = "${prd.pro_num}";
		 var photo = "${prd.pro_photo}";
		 var pro_name = "${prd.pro_name}";
		 var pro_price = "${prd.pro_price}";

		 var size = $("input[name='pro_det_num']").length;

		
		 
		 if(size == 0) {
			 swal({
				  title: "주문 실패!",
				  text: "상품을 선택하세요!",
				  icon: "error",
				});
			 return false;
		 }else {
			 var prodetnum = new Array(size);
			 for(var i=0; i<size; i++){                          
				 prodetnum[i] = $("input[name='pro_det_num']")[i].value;
			 }
			
			 var size2 = $("input[name='qnt']").length;
			 var qnt = new Array(size2);
			 for(var i=0; i<size2; i++){                          
				 qnt[i] = $("input[name='qnt']")[i].value;
			 }
			 
			 var size3 = $("input[name='color']").length;
			 var color = new Array(size3);
			 for(var i=0; i<size3; i++){                          
				 color[i] = $("input[name='color']")[i].value;
			 }
			 
			 var size4 = $("input[name='size']").length;
			 var pro_size = new Array(size4);
			 for(var i=0; i<size4; i++){                          
				 pro_size[i] = $("input[name='size']")[i].value;
			 }

			 for(var i=0; i<size; i++) {
				 <c:forEach var="list" items="${prdDetList }" varStatus="i">
				 	if(prodetnum[i] == "${list.pro_det_num}") {
				 		if(Number("${list.stock_qnt}")-Number(qnt[i]) < 0) {
				 			swal("재고가 부족합니다!", "다른 상품을 선택해주세요.", "warning");
				 			return false;
				 		}
				 	}
				 </c:forEach>
			 }
			$.ajax({
				url : '<%=request.getContextPath()%>/cartAdd.pro',
				type : 'POST',
				data : 'pro_det_num='+prodetnum+'&qnt='+qnt+'&pro_num='+pro_num
					+'&pro_photo='+photo+'&pro_name='+pro_name+'&pro_price='+pro_price
					+'&color='+color+'&pro_size='+pro_size,
				cache: false,
				contentType : 'application/x-www-form-urlencoded; charset=UTF-8',
		        processData: false,
				success : function() {
					swal({
						  title: "장바구니로 이동하시겠습니까?",
						  text: "장바구니에 상품을 담았습니다!",
						  icon: "success",
						  buttons: true,
						  dangerMode: false,
					})
						.then((willCart) => {
						  if (willCart) {
						    location.href="cartList.pro";
						  }else {
							  $('[class^=option_product]').remove();
							  $('.total_price').text('0');
							  $('.total_qnt').text('0');
						  }
					});
				},
				error : function() {
					console.log("에러");
				}
			});
		 }
		 
		
	});
	
	//바로주문
	$('#buy').on('click', function() {
		 var pro_num = "${prd.pro_num}";
		 var photo = "${prd.pro_photo}";
		 var pro_name = "${prd.pro_name}";
		 var pro_price = "${prd.pro_price}";
		 
		 var size = $("input[name='pro_det_num']").length;
		 
		 if(size == 0) {
			 swal({
				  title: "장바구니 담기 실패!",
				  text: "상품을 선택하세요!",
				  icon: "error",
				});
			 return false;
		 }else {
			 var prodetnum = new Array(size);
			 for(var i=0; i<size; i++){                          
				 prodetnum[i] = $("input[name='pro_det_num']")[i].value;
			 }

			 var size2 = $("input[name='qnt']").length;
			 var qnt = new Array(size2);
			 for(var i=0; i<size2; i++){                          
				 qnt[i] = $("input[name='qnt']")[i].value;
			 }
			 
			 var size3 = $("input[name='color']").length;
			 var color = new Array(size3);
			 for(var i=0; i<size3; i++){                          
				 color[i] = $("input[name='color']")[i].value;
			 }
			 
			 var size4 = $("input[name='size']").length;
			 var pro_size = new Array(size4);
			 for(var i=0; i<size4; i++){                          
				 pro_size[i] = $("input[name='size']")[i].value;
			 }
			 
			 for(var i=0; i<size; i++) {
				 <c:forEach var="list" items="${prdDetList }" varStatus="i">
				 	if(prodetnum[i] == "${list.pro_det_num}") {
				 		if(Number("${list.stock_qnt}")-Number(qnt[i]) < 0) {
				 			swal("재고가 부족합니다!", "다른 상품을 선택해주세요.", "warning");
				 			return false;
				 		}
				 	}
				 </c:forEach>
			 }
			 
			 location.href='directOrderPage.pro?' + 
			 		'pro_det_num='+prodetnum+'&qnt='+qnt+'&pro_num='+pro_num
					+'&pro_photo='+photo+'&pro_name='+pro_name+'&pro_price='+pro_price
					+'&color='+color+'&pro_size='+pro_size;
		 }
	});
	
	//리뷰 제목 클릭했을 때
	$("[id^=rev_subject]").on('click', function(event){
		var id = $(this).attr("id")
		var num = id.replace("rev_subject", "");
		
		var id2 = '#rev_content' + num;
		
		var content = $(id2);
		
		// rev_content 가 화면상에 보일때는 위로 보드랍게 접고 아니면 아래로 보드랍게 펼치기
		if( content.is(":visible") ){
			content.slideUp();
		}else{
			content.slideDown();
		}
		
		return false;
	});
		
		
});
</script>

<style>
[id*='rev_content'] {display:none;}
</style>
</head>
<body>
<div class="blank">
</div>

<form action="" name="f" method="post">
<div id="product_detail">
	<div class="detail_Area">
	 	<div class="img_Area">
			<img src="<c:url value='/upload/${prd.pro_photo }'/>">
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
							<option value="${clist.color }" >${clist.color }</option>
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
				
				<c:set var="sizeArr" value=""/>
				<c:forEach var="sizeList" items="${prdDetList }" varStatus="i">
					<c:set var="dup" value="true"/>
					<c:forEach var="dupCheck" items="${sizeArr }" varStatus="j">
						<c:if test="${sizeList.pro_size == dupCheck }">
							<c:set var="dup" value="false"/>
						</c:if>
					</c:forEach>
					
					<c:choose>
						<c:when test="${i.index == 0 }">
							<c:set var="sizeArr" value="${sizeList.pro_size }"/>
						</c:when>
						<c:otherwise>
							<c:if test="${dup == true }">
								<c:set var="sizeArr" value="${sizeArr},${sizeList.pro_size }"/>
							</c:if>
						</c:otherwise>
					</c:choose>
				</c:forEach>
			<select id="sizeSelect">
				<option value="none" selected disabled hidden>-[필수]옵션을 선택해주세요-</option>
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
					<li><a href="#" class="btn_b" id="buy">BUY</a></li>
					<li><a href="#" class="btn_w" id="cart">SHOPPING CART</a></li>
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
	
	<!-- 리뷰 게시판 -->
	<div id="prdReview">
		<div class="board">
			<h3>review</h3>
			<div>
				<table class="board_table">
					<colgroup>
						<col style="width:70px;">	
						<col style="width:auto">
						<col style="width:120px;">
						<col style="width:120px;">
						<col style="width:80px;">
						<col style="width:70px;">
					</colgroup>
					<tr>
						<th scope="col">no</th>
						<th scope="col">subject</th>
						<th scope="col">score</th>
						<th scope="col">writer</th>
						<th scope="col">date</th>
					</tr>
					<c:set var="size" value="5"/>
					<c:forEach var="review_list" items="${reviewList }" varStatus="i">
						<tr>
							<td>${review_listCount - ((reviewPageInfo.page * 5) - size)}</td>
							<td style="text-align:left;">
								<a href="#" id="rev_subject${i.count }">
									${review_list.board_title }
									${review_list.board_step == 'Y' ? '[1]' : '' }	<!-- 답글 여부 -->
								</a>
									<!-- 조회수 10 넘으면 Hit 아이콘 -->
								<c:if test="${review_list.board_hits > 10}">
									<img src="<%= request.getContextPath() %>/layout_image/hit_icon.png">
								</c:if>
								<!-- 사진 있으면 사진 아이콘 -->
								<c:if test="${!(review_list.board_photo == null || review_list.board_photo == '')}">
									<img src="<%= request.getContextPath() %>/layout_image/pic_icon.gif"/>
								</c:if>
							</td>
							<td>
								<div class="starRev">
								  <span class="starR ${review_list.review_score >= 1 ? 'on' : ''}">1</span>
								  <span class="starR ${review_list.review_score >= 2 ? 'on' : ''}">2</span>
								  <span class="starR ${review_list.review_score >= 3 ? 'on' : ''}">3</span>
								  <span class="starR ${review_list.review_score >= 4 ? 'on' : ''}">4</span>
								  <span class="starR ${review_list.review_score >= 5 ? 'on' : ''}">5</span>
								</div>
							</td>
							<td>${review_list.board_writer}</td>
							<!-- 날짜 형식 바꿔주기 (yyyy-MM-dd) -->
							<fmt:parseDate value="${review_list.board_date}" var="board_date" pattern="yyyy-MM-dd HH:mm:ss"/>
							<fmt:formatDate var="dateFmt" value="${board_date}" pattern="yyyy-MM-dd"/>
							<td>${dateFmt }</td>
							<c:set var="size" value="${size - 1 }"/>
						</tr>
						<tr class="editor" id="rev_content${i.count }">
							<th colspan="5">
								<c:if test="${!(review_list.board_photo eq null || review_list.board_photo eq '' )}">
									<img src="<%= request.getContextPath() %>/upload/${review_list.board_photo }"><br>
								</c:if>
								${review_list.board_content }
								
								
								<!-- 답글있을때 -->
								<c:if test="${review_list.board_step == 'Y'}">
									<br><br><br>
									<div class="comment_table">
										<table>
											<tr>
												<th><strong class="name">관리자</strong></th>
												<th><span class="comment_top_right">${reviewList_answer[i.index].board_date}</span></th>
											</tr>
											
											<tr>
												<th colspan="2">${reviewList_answer[i.index].board_title }</th>
											</tr>
											
											<tr>
												<th colspan="2">
													<c:if test="${!(reviewList_answer[i.index].board_photo eq null || reviewList_answer[i.index].board_photo eq '' )}">
														<img src="<%= request.getContextPath() %>/upload/${reviewList_answer[i.index].board_photo }"><br>
													</c:if>
														${reviewList_answer[i.index].board_content }
												</th>
											</tr>
										</table>
									</div>
								</c:if>
								
							</th>
						</tr>
						
						
					</c:forEach>
				</table>
			</div>
			
			<div class="order_button_area">
				<p>
					<a href="boardListAction.bo?board_type=review" class="w">LIST</a>
				</p>
			</div>
			
			<!-- 페이지 리스트 -->
			<div id="pageList">
				<c:if test="${reviewPageInfo.endPage > 0}">
					<ol>
					<c:choose>
						<c:when test="${reviewPageInfo.page <= 1 }">
							<li> < </li>
						</c:when>
						<c:otherwise>
							<li><a href="boardListAction.bo?board_type=review&page=${reviewPageInfo.page-1 }"> < </a></li>
						</c:otherwise>
					</c:choose>
					
					
					<c:forEach var="a" begin="${reviewPageInfo.startPage }" end="${reviewPageInfo.endPage }" step="1">
						<c:choose>
							<c:when test="${a == reviewPageInfo.page }">
								<li>[${a}]</li>
							</c:when>
							<c:otherwise>
								<li><a href="boardListAction.bo?board_type=review&page=${a}">[${a}]</a></li>
							</c:otherwise>
						</c:choose>
					</c:forEach>
					
					
					<c:choose>
						<c:when test="${reviewPageInfo.page>=reviewPageInfo.maxPage }">
							<li> > </li>
						</c:when>
						<c:otherwise>
							<li><a href="boardListAction.bo?board_type=review&page=${reviewPageInfo.page+1 }"> > </a></li>
						</c:otherwise>
					</c:choose>
					</ol>
				</c:if>
			</div>
		</div>
	</div>
	
	<div id="prdQnA">
		<div class="board">
			<h3>QnA</h3>
			<div>
				<table class="board_table">
					<colgroup>
						<col style="width:70px;">
						<col style="width:100px;">	
						<col style="width:auto">
						<col style="width:80px;">
						<col style="width:120px;">
						<col style="width:80px;">
					</colgroup>
					<tr>
						<th scope="col">no</th>
						<th scope="col">type</th>
						<th scope="col">subject</th>
						<th scope="col">answer</th>
						<th scope="col">writer</th>
						<th scope="col">date</th>
					</tr>
					<c:set var="size" value="5"/>
					<c:forEach var="qna_list" items="${qnaList }" varStatus="i">
					<tr>
						<td>${qna_listCount - ((qnaPageInfo.page * 5) - size)}</td>
						<td>
							<c:choose>
								<c:when test="${qna_list.qna_type == 'product_qna'}">
									[상품문의]
								</c:when>
								<c:when test="${qna_list.qna_type == 'delivery_qna' }">
									[배송문의]
								</c:when>
								<c:otherwise>
									[기타문의]
								</c:otherwise>
							</c:choose>
						</td>
						<td style="text-align:left;">
							<c:choose>
								<c:when test="${qna_list.qna_open != 'N' || grade=='A'.charAt(0)}">
									<a href="boardViewAction.bo?&board_num=${qna_list.board_num}&pro_num=${qna_list.pro_num}&path=/board/board_detail">
										${qna_list.board_title}
									</a>
								</c:when>
								<c:otherwise>
									${qna_list.board_title}
								</c:otherwise>
							</c:choose>
							${qna_list.board_step == 'Y' ? '[1]' : '' }	<!-- 답글 여부 -->
							<c:if test="${qna_list.qna_open == 'N' }">
								<img src="<%= request.getContextPath() %>/layout_image/lock_icon.png"/>
							</c:if>
						</td>
						<td>
							${qna_list.board_step }
						</td>
						<td>${qna_list.board_writer }</td>
						<!-- 날짜 형식 바꿔주기 (yyyy-MM-dd) -->
						<fmt:parseDate value="${qna_list.board_date}" var="board_date" pattern="yyyy-MM-dd HH:mm:ss"/>
						<fmt:formatDate var="dateFmt" value="${board_date}" pattern="yyyy-MM-dd"/>
						<td>${dateFmt }</td>
						<c:set var="size" value="${size - 1 }"/>
					</tr>
					</c:forEach>
				</table>
			</div>
			
			<div class="order_button_area">
				<p>
					<a href="boardListAction.bo?board_type=qna" class="w">LIST</a>
					<a href="boardWriteForm.bo?board_type=qna&pro_num=${prd.pro_num }" class="b">WRITE</a>
				</p>
			</div>
			<!-- 페이지 리스트 -->
			<div id="pageList">
				<c:if test="${qnaPageInfo.endPage > 0}">
					<ol>
					<c:choose>
						<c:when test="${qnaPageInfo.page <= 1 }">
							<li> < </li>
						</c:when>
						<c:otherwise>
							<li><a href="boardListAction.bo?board_type=qna&page=${qnaPageInfo.page-1 }"> < </a></li>
						</c:otherwise>
					</c:choose>
					
					
					<c:forEach var="a" begin="${qnaPageInfo.startPage }" end="${qnaPageInfo.endPage }" step="1">
						<c:choose>
							<c:when test="${a == qnaPageInfo.page }">
								<li>[${a}]</li>
							</c:when>
							<c:otherwise>
								<li><a href="boardListAction.bo?board_type=qna&page=${a}">[${a}]</a></li>
							</c:otherwise>
						</c:choose>
					</c:forEach>
					
					
					<c:choose>
						<c:when test="${qnaPageInfo.page>=qnaPageInfo.maxPage }">
							<li> > </li>
						</c:when>
						<c:otherwise>
							<li><a href="boardListAction.bo?board_type=qna&page=${qnaPageInfo.page+1 }"> > </a></li>
						</c:otherwise>
					</c:choose>
					</ol>
				</c:if>
			</div>
		</div>
	</div>
</div>
</form>
