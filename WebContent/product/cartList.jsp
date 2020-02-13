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
<style>
.cartImage {
	width : 50px;
	
}
</style>
</head>
<body>
	<table>
		<tr>
			<th><input type="checkbox"/></th>
			<th>IMAGE</th>
			<th>INFO</th>
			<th>PRICE</th>
			<th>QTY</th>
			<th>TOTAL</th>
		</tr>
		<c:forEach var="list" items="${cartList }" varStatus="i">
			<tr>
				<td><input type="checkbox"/></td>
				<td><img src="<%= request.getContextPath() %>/upload/${list.pro_photo }" class="cartImage"/></td>
				<td>
					<div>
						${list.pro_name }
						${list.color } / ${list.pro_size }
					</div>
				</td>
				<td>${list.pro_price }</td>
				<td>${totalPrice }</td>
				<td></td>
			</tr>
		</c:forEach>
	</table>
</body>
</html>