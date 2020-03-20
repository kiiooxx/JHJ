<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="sql" uri="http://java.sun.com/jsp/jstl/sql"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>


<!-- 리뷰 게시판 -->
<div id="prdReview">
	<div class="board">
		<h3>review</h3>
		<div>
			<table class="board_table">
				<colgroup>
					<col style="width:70px;">
					<col style="width:80px;">	
					<col style="width:auto">
					<col style="width:120px;">
					<col style="width:120px;">
					<col style="width:80px;">
				</colgroup>
				<tr>
					<th scope="col">no</th>
					<th scope="col">image</th>
					<th scope="col">subject</th>
					<th scope="col">score</th>
					<th scope="col">writer</th>
					<th scope="col">date</th>
				</tr>
				<c:forEach var="list" items="${boardList }" varStatus="i">
				<tr>
					<td>${i.count }</td>
					<td>
						<a href="productDetail.pro?pro_num=${list.pro_num }">
							<img src="<%= request.getContextPath() %>/upload/${prdList[i.index].pro_photo }" class="rev_thumb"/>
						</a>
					</td>
					<td style="text-align:left;">
						<a href="boardViewAction.bo?&board_num=${list.board_num}&pro_num=${list.pro_num}&page=${pageInfo.page}&path=detail">
							${list.board_title }
						</a>
						<c:if test="${!(list.board_photo == null || list.board_photo == '')}">
							<img src="<%= request.getContextPath() %>/layout_image/pic_icon.gif"/>
						</c:if>
					</td>
					<td>
						<div class="starRev">
						  <span class="starR ${list.review_score >= 1 ? 'on' : ''}">1</span>
						  <span class="starR ${list.review_score >= 2 ? 'on' : ''}">2</span>
						  <span class="starR ${list.review_score >= 3 ? 'on' : ''}">3</span>
						  <span class="starR ${list.review_score >= 4 ? 'on' : ''}">4</span>
						  <span class="starR ${list.review_score >= 5 ? 'on' : ''}">5</span>
						</div>
					</td>
					<td>${list.board_writer }</td>
					<!-- 날짜 형식 바꿔주기 (yyyy-MM-dd) -->
					<fmt:parseDate value="${list.board_date}" var="board_date" pattern="yyyy-MM-dd HH:mm:ss"/>
					<fmt:formatDate var="dateFmt" value="${board_date}" pattern="yyyy-MM-dd"/>
					<td>${dateFmt }</td>
				</tr>
				</c:forEach>
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
							<li><a href="boardListAction.bo?board_type=review&page=${pageInfo.page-1 }"> < </a></li>
						</c:otherwise>
					</c:choose>
					
					
					<c:forEach var="pglist" begin="${pageInfo.startPage }" end="${pageInfo.endPage }" step="1" varStatus="a">
						<c:choose>
							<c:when test="${a.count == pageInfo.page }">
								<li>[${a.count }]</li>
							</c:when>
							<c:otherwise>
								<li><a href="boardListAction.bo?board_type=review&page=${a.count }">[${a.count }]</a></li>
							</c:otherwise>
						</c:choose>
					</c:forEach>
					
					
					<c:choose>
						<c:when test="${pageInfo.page>=pageInfo.maxPage }">
							<li> > </li>
						</c:when>
						<c:otherwise>
							<li><a href="boardListAction.bo?board_type=review&page=${pageInfo.page+1 }"> > </a></li>
						</c:otherwise>
					</c:choose>
					</ol>
				</c:if>
			</div>
	</div>
</div>