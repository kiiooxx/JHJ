<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<body>
	<!-- 게시판 리스트 -->
<div id="noticeList">
	<div class="board">
		<h3>NOTICE</h3>
		<div>
			<table class="board_table">
				<colgroup>
					<col style="width:70px;">	
					<col style="width:auto">
					<col style="width:80px;">
					<col style="width:80px;">
				</colgroup>
				
				<tr>
					<td>NO</td>
					<td>SUBJECT</td>
					<td>WRITER</td>
					<td>DATE</td>
				</tr>
				
				<c:forEach var="list" items="${boardList }" varStatus="i">
					<tr>
						<td>${i.count}</td>
						<td style="text-align:left;">
							<a href="boardViewAction.bo?&board_num=${list.board_num}&page=${pageInfo.page}&path=detail">
								${list.board_title }
							</a>
						</td>
						<td>관리자</td>
						<!-- 날짜 형식 바꿔주기 (yyyy-MM-dd) -->
						<fmt:parseDate value="${list.board_date}" var="board_date" pattern="yyyy-MM-dd HH:mm:ss"/>
						<fmt:formatDate var="dateFmt" value="${board_date}" pattern="yyyy-MM-dd"/>
						<td>${dateFmt }</td>
					</tr>
				</c:forEach>
			</table>
			<c:if test="${grade == 'A'.charAt(0) }">
				<div class="order_button_area">
					<p>
						<a href="noticeWriteForm.bo">WRITE</a>
					</p>
				</div>
			</c:if>
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
							<li><a href="boardListAction.bo?board_type=notice&page=${pageInfo.page-1 }"> < </a></li>
						</c:otherwise>
					</c:choose>
					
					
					<c:forEach var="pglist" begin="${pageInfo.startPage }" end="${pageInfo.endPage }" step="1" varStatus="a">
						<c:choose>
							<c:when test="${a.count == pageInfo.page }">
								<li>[${a.count }]</li>
							</c:when>
							<c:otherwise>
								<li><a href="boardListAction.bo?board_type=notice&page=${a.count }">[${a.count }]</a></li>
							</c:otherwise>
						</c:choose>
					</c:forEach>
					
					
					<c:choose>
						<c:when test="${pageInfo.page>=pageInfo.maxPage }">
							<li> > </li>
						</c:when>
						<c:otherwise>
							<li><a href="boardListAction.bo?board_type=notice&page=${pageInfo.page+1 }"> > </a></li>
						</c:otherwise>
					</c:choose>
					</ol>
				</c:if>
			</div>
	</div>
</div>