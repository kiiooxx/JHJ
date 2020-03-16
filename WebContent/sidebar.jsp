<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="sql" uri="http://java.sun.com/jsp/jstl/sql"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="vo.CategoryBean" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>사이드바</title>
</head>
<body>
<!-- 메인 사진 -->
<p class="main_photo">
	<a href="#"><img src="main.jpg"></a>
</p>

<div class="wleft">
	<!-- 쇼핑몰 이름 -->
	<h1 class="main_title"><a href="main.pro">JHJ</a></h1>
	
	<div class="category_clothes">
		<ul>
			<li><a href="productListBestNew.pro?main_nb=B">BEST</a></li>
			<li><a href="productListBestNew.pro?main_nb=N">NEW</a></li>
		<c:forEach var="list" items="${categoryList }" varStatus="i">
			<c:if test="${list.ca_lev == 0}">
			<li><a href="productList.pro?cate_num=${list.cate_num }&category=${list.category}">${list.category }</a></li>
			</c:if>
		</c:forEach>
		</ul>
	</div>
	
	<div class="category_board">
		<ul>
			<li><a href="notice_boardList.bo">NOTICE</a></li>
			<li><a href="qnaWriteForm.bo">Q & A</a></li>
			<li><a href="reviewList.bo">REVIEW</a></li>
		</ul>
	</div>
	
	<div class="category_call">
		<ul>
			<li>010.XXXX.XXXX</li>
			<li>AM 10:00 ~ PM 05:00</li>
			<li>SAT, SUN, HOLIDAY OFF</li>
		</ul>
	</div>
	
</div>
</body>
</html>