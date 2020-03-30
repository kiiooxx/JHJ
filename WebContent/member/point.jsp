<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>적립금내역확인</title>
</head>
<body>

<table border = "1">

	<th>총 적립금</th>
		

	
	<td>총 ${memberPoint.point_final}원</td>

	
</tr>
</table>

	<br><br>

	<thead>
	<tr>
	<td>적립날짜</td>
	<td>적립내용</td>
	<td>증감여부</td>
	<td>적립금</td>
	</tr>
	</thead>


<table border = "1">
		

	<tbody>
	<c:forEach var="myPoint2" items="${myPointList}" varStatus="i">
		<tr>
			<td>${myPoint2.point_date}</td>
			<td>${myPoint2.point_reason}</td>	
			<td>${myPoint2.increase }</td>
			<td>${myPoint2.point_price}원</td>
		</tr>
	</tbody>
	<br>
	
	<c:if test="${myPoint2.point_num == null }">
				<tr>
				
			
					<td>적립 내역이 없습니다</td>
				</tr>
			</c:if>
		
</table>

	</c:forEach>


	
	
	
<!-- 페이지 리스트 -->
		<div id="pageList">
			<c:if test="${pageInfo.endPage > 0}">
				<ol>
				<c:choose>
					<c:when test="${pageInfo.page <= 1 }">
						<li> < </li>
					</c:when>
					<c:otherwise>
						<li><a href="mypoint.mem?page=${pageInfo.page-1 }"> < </a></li>
					</c:otherwise>
				</c:choose>
				
				
				<c:forEach var="pglist" begin="${pageInfo.startPage }" end="${pageInfo.endPage }" step="1" varStatus="a">
					<c:choose>
						<c:when test="${a.count == pageInfo.page }">
							<li>[${a.count }]</li>
						</c:when>
						<c:otherwise>
							<li><a href="mypoint.mem?page=${a.count }">[${a.count }]</a></li>
						</c:otherwise>
					</c:choose>
				</c:forEach>
				
				
				<c:choose>
					<c:when test="${pageInfo.page>=pageInfo.maxPage }">
						<li> > </li>
					</c:when>
					<c:otherwise>
						<li><a href="mypoint.mem?page=${pageInfo.page+1 }"> > </a></li>
					</c:otherwise>
				</c:choose>
				</ol>
			</c:if>
		</div>
</div>	
	
</body>
</html>