<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:set var="pageInfo" value="${requestScope.pageInfo }" />
<c:set var="orderList" value="${requestScope.orderList }" />

<c:set var="searchType" />
<c:set var="searchText" />
<c:set var="orderDate" />
<c:set var="deliStatus" />

<c:if test="${searchType ne null }">
	<c:set var="searchType" value="${requestScope.searchType }" />
</c:if>
<c:if test="${searchText ne null }">
	<c:set var="searchText" value="${requestScope.searchText }" />
</c:if>
<c:if test="${orderDate ne null }">
	<c:set var="orderDate" value="${requestScope.orderDate }" />
</c:if>
<c:if test="${deliStatus ne null }">
	<c:set var="deliStatus" value="${requestScope.deliStatus }" />
</c:if>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<script src="https://code.jquery.com/jquery-3.2.1.js"></script>
<!-- <link rel="stylesheet" href="/css/jquery-ui.min.css">
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/css/bootstrap.min.css" />
<script
	src="http://netdna.bootstrapcdn.com/bootstrap/3.0.3/js/bootstrap.min.js"></script>
<script type='text/javascript' src='//code.jquery.com/jquery-1.8.3.js'></script>
<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-datepicker/1.5.0/css/bootstrap-datepicker3.min.css">
<script type='text/javascript'
	src="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-datepicker/1.5.0/js/bootstrap-datepicker.min.js"></script>
<script src="/js/bootstrap-datepicker.kr.js" charset="UTF-8"></script> -->
<script>
	$(function() {
		$('.input-group.date').datepicker({
			calendarWeeks : false,
			todayHighlight : true,

			autoclose : true,

			format : "yyyy-mm-dd",

			language : "kr"

		});

	});
</script>
<style>
<
style>th {
	background: #F6F6F6;
}

.col {
	margin-bottom: 40px;
}

#pageList {
	text-align: center;
}
</style>

</style>
</head>
<body>
	<div class="d-sm-flex align-items-center justify-content-between mb-4">
		<h1 class="h3 mb-0 text-gray-800">주문관리 페이지</h1>
	</div>
	<!-- Content Row -->
	<div class="row">
		<div class="col">


			<form action="orderManageList.ad" method="post">
				<div class="card card-default">
					<div class="card-body">
						<table class="table table-bordered">
							<tr>
								<th>검색어</th>
								<td><select id="searchType" name="searchType">
										<option value="user_id">주문자 아이디</option>
										<option value="user_name">주문자 이름</option>
										<option value="rec_name">수령인 이름</option>
										<option value="order_num">주문번호</option>
										<option value="deli_num">배송번호</option>
										<option value="pro_num">상품번호</option>
								</select> <input type="text" name="searchText"></td>
							</tr>
							<tr>
								<th>주문일</th>
								<td>
									<div class="container">
										<div class="input-group date">
											<input type="text" name="orderDate" class="form-control">
											<span class="input-group-addon"> <i
												class="glyphicon glyphicon-calendar"></i></span>
										</div>
									</div>
								</td>
							</tr>
							<tr>
								<th>주문상태</th>
								<td><input type="checkbox" name="deliStatus"
									value="order_done">주문완료 <input type="checkbox"
									name="deliStatus" value="check_paid">입금확인 <input
									type="checkbox" name="deliStatus" value="deli_ing">배송중
									<input type="checkbox" name="deliStatus" value="deli_fin">배송완료
									<input type="checkbox" name="deliStatus" value="order_confirm">구매확정
								</td>
							</tr>
						</table>
						<input type="submit" value="검색" id="search_btn" name="search_btn">
					</div>
				</div>
			</form>
		</div>
	</div>

	<div class="row">
		<div class="col">
			<div class="card-body">

				<table class="table table-bordered">
					<tr>
						<td>주문일</td>
						<td>주문번호</td>
						<td>주문자</td>
						<td>상품명</td>
						<td>결제금액</td>
						<td>상태</td>
					</tr>

					<!-- 여기서부터 검색결과 -->

					<c:choose>
						<c:when test="${orderList ne null }">
							<c:forEach items="${orderList }" var="order">
								<tr>
									<td>${order.sel_date }</td>
									<td>${order.sel_num }</td>
									<td>${order.user_id }</td>
									<td>상품명</td>
									<td>${order.final_price }원</td>
									<td>
									<c:if test="${order.sel_status eq 'order_done' }">
										주문완료
									</c:if>
									<c:if test="${order.sel_status eq 'check_paid' }">
										입금확인
									</c:if>
									<c:if test="${order.sel_status eq 'deli_ing' }">
										배송중
									</c:if>
									<c:if test="${order.sel_status eq 'deli_fin' }">
										배송완료
									</c:if>
									<c:if test="${order.sel_status eq 'order_confirm' }">
										구매확정
									</c:if>
									</td>
								</tr>
							</c:forEach>
						</c:when>
						<c:otherwise>
		 검색된 주문내역이 없습니다.
		</c:otherwise>
					</c:choose>
					<!-- 여기까지 검색결과 -->

				</table>

				<!-- 여기서부터 페이징 -->
				<section id="page">
					<c:choose>
						<c:when test="${pageInfo.page <= 1 }">
							[이전]&nbsp;	
						</c:when>
						<c:otherwise>
							<a
								href="orderList.ad?page=${pageInfo.page-1 }&searchType=${searchType}&searchText=${searchText}&orderDate=${orderDate}&deliStatus=${deliStatus}">[이전]</a>&nbsp;	
						</c:otherwise>
					</c:choose>

					<c:forEach var="a" begin="${pageInfo.startPage }"
						end="${pageInfo.endPage }" step="1">

						<c:choose>
							<c:when test="${a eq pageInfo.page }"> 
								[${a}]				<!-- 현재페이지는 링크 안걸어도 되니까. -->
							</c:when>
							<c:otherwise>
								<a
									href="orderList.ad?page=${a}&searchType=${searchType}&searchText=${searchText}&orderDate=${orderDate}&deliStatus=${deliStatus}">[${a}]</a>
							</c:otherwise>
						</c:choose>
					</c:forEach>
					<c:choose>
						<c:when test="${pageInfo.page >= pageInfo.maxPage }">
							&nbsp;[다음]
						</c:when>
						<c:otherwise>
							<a
								href="orderList.ad?page=${pageInfo.page+1 }&searchType=${searchType}&searchText=${searchText}&orderDate=${orderDate}&deliStatus=${deliStatus}">[다음]</a>
						</c:otherwise>
					</c:choose>
				</section>
			</div>
		</div>
	</div>
	</div>

	<!-- 여기까지 페이징 -->

</body>
</html>