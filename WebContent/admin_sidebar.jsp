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
	<h1 class="main_title"><a href="#">관리자</a></h1>
	
	<div class="category_clothes">
		<ul>
			<li><a href="categoryManagement.ad">카테고리관리</a></li>
			<li><a href="productManagement.ad">상품등록</a></li>
			<li><a href="#">상품관리</a></li>
			<li><a href="#">재고관리</a></li>
		</ul>
	</div>
</div>
</body>
</html>