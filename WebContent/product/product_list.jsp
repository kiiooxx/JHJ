<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="sql" uri="http://java.sun.com/jsp/jstl/sql"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="vo.ProductBean" %>

<c:if test="${pageInfo != null }">
	<c:set var="nowPage" value="${pageInfo.page }"/>
	<c:set var="maxPage" value="${pageInfo.maxPage }"/>
	<c:set var="startPage" value="${pageInfo.startPage }"/>
	<c:set var="endPage" value="${pageInfo.endPage }"/>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link rel="stylesheet" href="common.css" type="text/css">
<link rel="stylesheet" href="style.css" type="text/css">
<title>Insert title here</title>
<style>

</style>
</head>
<body>
	<div class="blank">
	</div>
	
	<!-- 상품 목록 폼 -->
	<div class="product_list">
		<!-- 카테고리 이름 -->
		<h2>${category }</h2>
		
		<div class="sub_category">
			<ul>
				<li><a href="#">ALL</a></li>
				
				<!-- 서브 카테고리 이름 넣기 -->
				<c:forEach var="list" items="${categoryList }" varStatus="i">
					<c:if test="${cate_num == list.ca_ref && list.ca_lev != 0 }">
						<li><a href="#">${list.category }</a></li>
					</c:if>
				</c:forEach>
			</ul>
		</div>
		
		<!-- 진열 순서 -->
		<div class="pro_display_seq">
			<ul>
				<li><a href="#">신상품</a></li>
				<li><a href="#">상품명</a></li>
				<li><a href="#">낮은가격</a></li>
				<li><a href="#">높은가격</a></li>
			</ul>
		</div>

		<ul class="prdList">
			<c:forEach var="plist" items="${prdList }" varStatus="i">
				<!-- 상품 진열 활성화 된것만-->
				<c:if test="${plist.active eq 'Y'.charAt(0) }">
					<li>
						<div class="thumbnail">
							<a href="productDetail.pro?pro_num=${plist.pro_num }&page="${nowPage }"><img src="<%= request.getContextPath() %>/upload/${plist.pro_photo }"/></a>
						</div>
						
						<div class="description">
							<p class="name">${plist.pro_name }</p>
							<p class="price">${plist.pro_price }</p>
						</div>
					</li>
					
					<c:if test="${i.count % 3 == 0 }">
						</ul>
						<ul class="prdList">
					</c:if>
				</c:if>
			</c:forEach>	
		</ul>
		
		<div id="pageList">
			<ol>
			<c:choose>
				<c:when test="${nowPage <= 1 }">
					<li> < </li>
				</c:when>
				<c:otherwise>
					<li><a href="productList.pro?page=${nowPage-1 }"> < </a></li>
				</c:otherwise>
			</c:choose>
			
			
			<c:forEach var="pglist" begin="${startPage }" end="${endPage }" step="1" varStatus="a">
				<c:choose>
					<c:when test="${a.count == nowPage }">
						<li>[${a.count }]</li>
					</c:when>
					<c:otherwise>
						<li><a href="productList.pro?page=${a.count }">[${a.count }]</a></li>
					</c:otherwise>
				</c:choose>
			</c:forEach>
			
			
			<c:choose>
				<c:when test="${nowPage>=maxPage }">
					<li> > </li>
				</c:when>
				<c:otherwise>
					<li><a href="productList.pro?page=${nowPage+1 }"> > </a></li>
				</c:otherwise>
			</c:choose>
			</ol>
		</div>
	</div>
</c:if>
</body>
</html>