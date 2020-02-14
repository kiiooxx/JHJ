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
</style>
</head>
<body>
<!-- Page Heading -->
 <div class="d-sm-flex align-items-center justify-content-between mb-4">
 	<h1 class="h3 mb-0 text-gray-800">상품 목록</h1>
 </div>
 <!-- Content Row -->
 <div class="row">
 	<div class="col">
		<form action="productListManagement.ad" name="productListSearch" method="post">
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
						<a href="javascript:productListSearch.submit()" class="btn btn-primary">검색</a>
						<a href="javascript:productListSearch.reset()" class="btn btn-primary">초기화</a>
					</div>
				</div>
			</div>
		</form>
	</div>
</div>
 <div class="row">
 	<div class="col">
		<form action="" name="" method="post">
			<div class="card card-default">
				<div class="card-body">
					<table class="table table-bordered">
						<tr>
							<th><input type="checkbox" id="checkall"/></th>
							<th>상품명</th>
							<th>판매가</th>
							<th>진열상태</th>
							<th>메인진열</th>
							<th>상품분류</th>
							<th>상품 등록일</th>	
						</tr>
			
						<!-- 상품 리스트 -->
						<c:forEach var="list" items="${prdList }" varStatus="i">
							<tr>
								<td><input type="checkbox" name="chk" value="${list.pro_num }"/></td>
								<td><a href="javascript:void(0);" 
										onclick="window.open('productModifyForm.ad?pro_num=${list.pro_num }','상품수정','width=1000, height=700')">
										${list.pro_name }
									</a>
								</td>
					
								<!-- 가격 형식 -->
								<fmt:formatNumber var="price" value="${list.pro_price}" pattern="#,###"/>
								<td>${price }</td>
								<td>
									<c:choose>
										<c:when test="${list.active == 'Y'.charAt(0)}">
											진열함
										</c:when>
										<c:otherwise>
											진열안함
										</c:otherwise>
									</c:choose>
								</td>
								<td>
									<c:choose>
										<c:when test="${list.main_nb == 'X'.charAt(0)}">
											메인진열안함
										</c:when>
										<c:when test="${list.main_nb == 'B'.charAt(0) }">
											베스트
										</c:when>
										<c:otherwise>
											신상품
										</c:otherwise>
									</c:choose>
								</td>
								<td>
									<c:forEach var="clist" items="${categoryList }" varStatus="j">
										<c:if test="${list.cate_num == clist.cate_num }">
											<c:choose>
												<c:when test="${clist.ca_lev == 0}">
													[대분류]${clist.category }
												</c:when>
												<c:otherwise>
													[대분류]
													<c:forEach var="dlist" items="${categoryList }" varStatus="d">
														<c:if test="${clist.ca_ref == dlist.cate_num}">
															${dlist.category }
														</c:if>
													</c:forEach>
													 > [소분류]${clist.category }
												</c:otherwise>
											</c:choose>
										</c:if>
									</c:forEach>
								</td>
								<td>${list.pro_date }</td>
							</tr>
						</c:forEach>
					</table>
					
					<div id="pageList">
						<c:choose>
							<c:when test="${pageInfo.page <= 1 }">
								<ion-icon name="chevron-back-outline"></ion-icon>
							</c:when>
							<c:otherwise>
								<a href="productList.pro?page=${pageInfo.page-1 }"><ion-icon name="chevron-back-outline"></ion-icon></a>
							</c:otherwise>
						</c:choose>
							
							
						<c:forEach var="pglist" begin="${pageInfo.startPage }" end="${pageInfo.endPage }" step="1" varStatus="a">
							<c:choose>
								<c:when test="${a.count == pageInfo.page }">
									[${a.count }]
								</c:when>
								<c:otherwise>
									<a href="productList.pro?page=${a.count }">[${a.count }]</a>
								</c:otherwise>
							</c:choose>
						</c:forEach>
							
							
						<c:choose>
							<c:when test="${pageInfo.page>=pageInfo.maxPage }">
								<ion-icon name="chevron-forward-outline"></ion-icon>
							</c:when>
							<c:otherwise>
								<a href="productList.pro?page=${pageInfo.page+1 }"><ion-icon name="chevron-forward-outline"></ion-icon></li>
							</c:otherwise>
						</c:choose>
					</div>
					
					<div id="bottom">
						<a href="#" id="del">삭제</a>
						<select id="activeBox" name="activeBox">
							<option value="none" selected disabled hidden>--선택--</option>
							<option value="Y">진열함</option>
							<option value="N">진열안함</option>
						</select>
						<select id="main_nb" name="main_nb">
							<option value="none" selected disabled hidden>--선택--</option>
							<option value="X">메인진열안함</option>
							<option value="B">베스트</option>
							<option value="N">신상품</option>
						</select>
					</div>
				</div>
			</div>
		</form>
	</div>
</div>
</body>
</html>