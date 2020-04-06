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


<!-- css -->
<jsp:include page="/resources/css.jsp"/>

<!-- js -->
<jsp:include page="/resources/js.jsp"/>

<title>purple line</title>
</head>
<body>
<jsp:include page="/common/categoryCheck.jsp"/>
<jsp:include page="/common/pagefileCheck.jsp"/>

<div id="template">	
	<!-- 헤더 -->
	<div id="header">
		<jsp:include page="header.jsp"></jsp:include>
	</div>
	<div id="wrap">
	
		<!-- 왼쪽 사이드 바 -->
		<div id="wleft">
			<jsp:include page="sidebar.jsp"></jsp:include>
		</div>
		
		<!-- 내용 -->
		<div id="container">
			<div id="contents">
				<jsp:include page='${pagefile }'></jsp:include>
			</div>
		</div>
		
		<!-- 푸터 -->
		<div id="footer">
			<jsp:include page="footer.jsp"></jsp:include>
		</div>
	</div>
</div>



</body>
</html>
