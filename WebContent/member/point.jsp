<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<div class="pointArea">
	<h3>POINT</h3>
	
	<div class="pointInfo">
		<uL>
			<li>
				<strong class="title">총 적립금</strong>
				<!-- 가격 형식 -->
				<fmt:formatNumber var="point_final" value="${memberPoint.point_final}" pattern="#,###"/>
				<span class="data"><strong>${point_final}</strong>원</span>
			</li>
		</uL>
	</div>

	
	<div class="orderList_table">
		<table>
			<colgroup>
				<col style="width:150px;">
				<col style="width:auto;">
				<col style="width:120px;">
			</colgroup>
			<tr>
				<th>적립날짜</th>
				<th>적립내용</th>
				<th>적립금</th>
			</tr>

			<c:set var="size" value="${fn:length(myPointList) }"/>
			<c:if test="${size > 0 }">
				<c:forEach var="myPoint2" items="${myPointList}" varStatus="i">
					<tr>
						<td>
							<fmt:parseDate value="${myPoint2.point_date}" var="date" pattern="yyyy-MM-dd HH:mm:ss"/>
							<fmt:formatDate var="dateFmt" value="${date}" pattern="yyyy-MM-dd"/>
							${dateFmt}
						</td>
						<td style="text-align:left">${myPoint2.point_reason}</td>	
						<td style="text-align:right">
							${myPoint2.increase }
							<fmt:formatNumber var="point_price" value="${myPoint2.point_price}" pattern="#,###"/>
							${point_price}원
						</td>
					</tr>
				</c:forEach>
			</c:if>
			
			<c:if test="${size==0 }">
				<tr>
					<td colspan="4">적립 내역이 없습니다</td>
				</tr>
			</c:if>
		</table>
	</div>
	
	
	
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
				
				
				<c:forEach var="a" begin="${pageInfo.startPage }" end="${pageInfo.endPage }" step="1">
					<c:choose>
						<c:when test="${a == pageInfo.page }">
							<li>[${a}]</li>
						</c:when>
						<c:otherwise>
							<li><a href="mypoint.mem?page=${a}">[${a}]</a></li>
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