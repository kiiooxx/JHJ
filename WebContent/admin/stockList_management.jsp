<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="sql" uri="http://java.sun.com/jsp/jstl/sql"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<script src="http://code.jquery.com/jquery-latest.js"></script>
<script src="https://unpkg.com/ionicons@5.0.0/dist/ionicons.js"></script>

<script>
window.name="openPage";
$(function() {
	$('#setSelectBox').change(function() {
		var param = 'ca_ref='+$(this).val();
		$.ajax({
			url : '<%=request.getContextPath()%>/subCategoryList',
			dataType : 'json',
			type : 'POST',
			data : param,
			cache: false,
			contentType : 'application/x-www-form-urlencoded; charset=UTF-8',
	        processData: false,
			success : function(data) {
				var html = '<option value="none" selected disabled hidden>--[소분류]--</option>';
				$.each(data, function(index, item){
					var result = '';
					result += index + " : " + item.cate_num;
					html += '<option value="' + item.cate_num + '">' + item.category + '</option>'; 
				})
				$('#cate_sub').empty();
				$('#cate_sub').append(html);
			},
			error : function() {
				console.log("에러");
			}
		});
	});
});

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
			alert("상품번호를 선택하세요.");
			return false;
		}else {
			if(confirm('정말 삭제하시겠습니까?')) {
				var items=[];
				$('input[name=chk]:checkbox:checked').each(function(){items.push($(this).val());});
				var tmp = items.join(',');
				location.href='productDelAction.ad?pro_num='+tmp;
			}else {
				return false;
			}
		}
	});
	
	//진열 select 변경했을 때
	$('#activeBox').change(function() {
		if($('input[name=chk]').is(":checked") == false){
			alert("상품번호를 선택하세요.");
			return false;
		}else {
			var items=[];
			$('input[name=chk]:checkbox:checked').each(function(){items.push($(this).val());});
			var tmp = items.join(',');
			var select = $('select[name=activeBox]').val();
			location.href='productListUpdate.ad?pro_num='+tmp+'&active='+select;
		}
	});
	
	//메인상품진열 수정
	$('#main_nb').change(function() {
		if($('input[name=chk]').is(":checked") == false){
			alert("상품번호를 선택하세요.");
			return false;
		}else {
			var items=[];
			$('input[name=chk]:checkbox:checked').each(function(){items.push($(this).val());});
			var tmp = items.join(',');
			var select = $('select[name=main_nb]').val();
			location.href='productListUpdate.ad?pro_num='+tmp+'&main_nb='+select;
		}
	});
	
});
</script>
<style>
	th {background : #F6F6F6;}
	.col {margin-bottom : 40px;}
	#pageList {text-align : center;}
	.thumb {width:80px;}
</style>
</head>
<body>
<!-- Page Heading -->
 <div class="d-sm-flex align-items-center justify-content-between mb-4">
 	<h1 class="h3 mb-0 text-gray-800">재고 목록</h1>
 </div>
 <!-- Content Row -->
 <div class="row">
 	<div class="col">
		<form action="stockListManagement.ad" name="stockListSearch" method="post">
			<div class="card card-default">
				<div class="card-body">
					<table class="table table-bordered">
						<tr>
							<th>검색분류</th>
							<td>
								<select name="search_type">
									<option value="pro_name">상품명</option>
									<option value="pro_num">상품번호</option>
								</select>
								<input type="text" name="search_text"/>
							</td>
						</tr>
						<tr>
							<th>상품분류</th>
							<td>
								<!-- 카테고리... -->
								<select name="ca_ref" id="setSelectBox">
									<option value="none" selected disabled hidden>--[대분류]--</option>
									<c:forEach var="clist" items="${categoryList }" varStatus="i">
										<c:if test="${clist.ca_lev == 0 }">
											<option value="${clist.cate_num }">${clist.category }</option>
										</c:if>
									</c:forEach>
								</select>
								
								<select name="cate_sub" id="cate_sub">
									<option value="none" selected disabled hidden>--[소분류]--</option>
								</select>	
							</td>
						</tr>
						<tr>
							<th>상품등록일</th>
							<td>
								<input type="radio" name="pro_date" value="-0"/>오늘
								<input type="radio" name="pro_date" value="-3"/>3일
								<input type="radio" name="pro_date" value="-7"/>7일
								<input type="radio" name="pro_date" value="-30"/>1개월
								<input type="radio" name="pro_date" value="-90"/>3개월
								<input type="radio" name="pro_date" value="-365"/>1년
								<input type="radio" name="pro_date" value="all" checked/>전체
							</td>
						</tr>
						<tr>
							<th>진열상태</th>
							<td>
								<input type="radio" name="active" value="all" checked/>전체
								<input type="radio" name="active" value="Y"/>진열함
								<input type="radio" name="active" value="N"/>진열안함
							</td>
						</tr>
					</table>
					<div align="center">
						<a href="javascript:stockListSearch.submit()" class="btn btn-primary">검색</a>
						<a href="javascript:stockListSearch.reset()" class="btn btn-primary">초기화</a>
					</div>
				</div>
			</div>
		</form>
	</div>
</div>

 <div class="row">
 	<div class="col">
		<form action="stockListModify.ad" name="stockListModifyForm" method="post">
			<div class="card card-default">
				<div class="card-body">
					<table class="table table-bordered">
						<colgroup>
							<col style="width:70px;">
							<col style="width:auto">
							<col style="width:200px;">
							<col style="width:150px;">
							<col style="width:100px">
							<col style="width:140px;">
						</colgroup>
						<tr style="text-align:center">
							<th>번호</th>
							<th>상품명</th>
							<th>품목명</th>
							<th>재고수량</th>
							<th>진열상태</th>
							<th>총 누적 판매량</th>
						</tr>
			
						
						<!-- 상품 리스트 -->
						<c:forEach var="list" items="${prdList }" varStatus="i">
							<c:set var="cnt" value="0"/>
							<c:forEach var="stock_list" items="${stockList }" varStatus="j">
								<c:if test="${list.pro_num == stock_list.pro_num }">
									<c:set var="cnt" value="${cnt + 1 }"/>
								</c:if>
							</c:forEach>
							<tr>
								<td rowspan="${cnt }">${list.pro_num }</td>
								<td rowspan="${cnt }">
									<img src="<%= request.getContextPath() %>/upload/${list.pro_photo }" class="thumb">
									<br>
									${list.pro_name }
								</td>
								
							<c:forEach var="stock_list" items="${stockList }" varStatus="s">
								<c:if test="${list.pro_num == stock_list.pro_num}">
									<td>
										${stock_list.color } / ${stock_list.pro_size }<br>
										(${stock_list.pro_det_num })
									</td>
									<!-- 재고 테이블 : 상품 번호, 상세 제품 코드, 수량 -->
									<input type="hidden" name="pro_num" value="${stock_list.pro_num }"/>
									<input type="hidden" name="pro_det_num" value="${stock_list.pro_det_num }"/>
									<td><input type="text" name="stock_qnt" value="${stock_list.stock_qnt }" style="text-align:right"/></td>
									<td style="text-align:center;">
										<c:choose>
											<c:when test="${list.active == 'Y'.charAt(0)}">
												진열함
												<c:if test="${stock_list.stock_qnt == 0 }">
												(품절)
												</c:if>
											</c:when>
											<c:otherwise>
												진열안함
												<c:if test="${stock_list.stock_qnt == 0 }">
												(품절)
												</c:if>
											</c:otherwise>
											
										</c:choose>
									</td>
									<td style="text-align:right">${stock_list.out_stock_qnt }</td>
								</tr>
								<tr>
								</c:if>
							</c:forEach>
							</tr>
						</c:forEach>
					</table>
					<div align="right" style="margin-bottom:10px;">
						<a href="javascript:stockListModifyForm.submit()" class="btn btn-primary">저장</a>
					</div>
				</form>
					
					<div id="pageList">
						<c:choose>
							<c:when test="${pageInfo.page <= 1 }">
								<ion-icon name="chevron-back-outline"></ion-icon>
							</c:when>
							<c:otherwise>
								<a href="stockListManagement.ad?search_type=${search_type }&search_text=${search_text }&cate_type=${cate_type }&ca_ref=${ca_ref }&pro_date=${pro_date }&active=${active }&page=${pageInfo.page-1 }"><ion-icon name="chevron-back-outline"></ion-icon></a>
							</c:otherwise>
						</c:choose>
							
							
						<c:forEach var="a" begin="${pageInfo.startPage }" end="${pageInfo.endPage }" step="1">
							<c:choose>
								<c:when test="${a == pageInfo.page }">
									[${a}]
								</c:when>
								<c:otherwise>
									<a href="stockListManagement.ad?search_type=${search_type }&search_text=${search_text }&cate_type=${cate_type }&ca_ref=${ca_ref }&pro_date=${pro_date }&active=${active }&page=${a}">[${a}]</a>
								</c:otherwise>
							</c:choose>
						</c:forEach>
							
							
						<c:choose>
							<c:when test="${pageInfo.page>=pageInfo.maxPage }">
								<ion-icon name="chevron-forward-outline"></ion-icon>
							</c:when>
							<c:otherwise>
								<a href="stockListManagement.ad?search_type=${search_type }&search_text=${search_text }&cate_type=${cate_type }&ca_ref=${ca_ref }&pro_date=${pro_date }&active=${active }&page=${pageInfo.page+1 }"><ion-icon name="chevron-forward-outline"></ion-icon></li>
							</c:otherwise>
						</c:choose>
					</div>
					
				</div>
			</div>
		
	</div>
</div>
</body>
</html>