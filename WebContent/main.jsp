<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="sql" uri="http://java.sun.com/jsp/jstl/sql"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<div class="blank">
</div>

<!-- 상품 목록 폼 -->
<div class="product_list">
	<!-- 카테고리 이름 -->
	<h2>BEST</h2>
	

	<div id="autoplay1">
		<c:forEach var="plist" items="${prdList }" varStatus="i">
			<!-- 상품 진열 활성화 된것만-->
			<c:if test="${plist.active eq 'Y'.charAt(0) && plist.main_nb eq 'B'.charAt(0)}">
				<div>
					<div>
						<a href="productDetail.pro?pro_num=${plist.pro_num }"><img src="<%= request.getContextPath() %>/upload/${plist.pro_photo }"/></a>
					</div>
						
					<div class="description2">
						<p>${plist.pro_name }</p>
						<fmt:formatNumber var="price" value="${plist.pro_price}" pattern="#,###"/>
						<p>${price }</p>
					</div>
				</div>
			</c:if>
		</c:forEach>

	</div>
</div>

	<!-- 상품 목록 폼 -->
<div class="product_list">
	<!-- 카테고리 이름 -->
	<h2>NEW</h2>
	

	<div id="autoplay2">
		
		<c:forEach var="plist" items="${prdList }" varStatus="i">
			<!-- 상품 진열 활성화 된것만-->
			<c:if test="${plist.active eq 'Y'.charAt(0) && plist.main_nb eq 'N'.charAt(0)}">
				<div>
					<div>
						<a href="productDetail.pro?pro_num=${plist.pro_num }"><img src="<%= request.getContextPath() %>/upload/${plist.pro_photo }"/></a>
					</div>
						
					<div class="description2">
						<p>${plist.pro_name }</p>
						<fmt:formatNumber var="price" value="${plist.pro_price}" pattern="#,###"/>
						<p>${price }</p>
					</div>
				</div>
			</c:if>
		</c:forEach>

	</div>
	
	<script type="text/javascript" src="//code.jquery.com/jquery-1.11.0.min.js"></script>
	<script type="text/javascript" src="//code.jquery.com/jquery-migrate-1.2.1.min.js"></script>
	<script type="text/javascript" src="slick/slick.min.js"></script>
	
	<script>
		$('#autoplay1').slick({
		  slidesToShow: 3,
		  slidesToScroll: 1,
		  autoplay: true,
		  autoplaySpeed: 2000,
		});
		
		$('#autoplay2').slick({
			  slidesToShow: 3,
			  slidesToScroll: 1,
			  autoplay: true,
			  autoplaySpeed: 2000,
			});
	</script>
</div>
