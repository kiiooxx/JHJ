<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>


<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" href="common.css" type="text/css">
<link rel="stylesheet" href="style.css" type="text/css">
</head>
<body>
<jsp:include page="/common/categoryCheck.jsp"/>
<jsp:include page="/common/pagefileCheck.jsp"/>
<div id="template">
	<!-- 헤더 -->
	<div id="header">
		<jsp:include page="top_menu.jsp"></jsp:include>
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
			
				<%-- <jsp:include page="admin/category_management.jsp"></jsp:include> --%>
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
