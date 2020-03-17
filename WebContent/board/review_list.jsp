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

</head>
<body>

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
					<c:forEach var="list" items="${reviewList }" varStatus="i">
					<tr>
						<td>${list.rev_num }</td>
						<td>
							<a href="productDetail.pro?pro_num=${list.pro_num }">
								<img src="<%= request.getContextPath() %>/upload/${list.pro_photo }" class="rev_thumb"/>
							</a>
						</td>
						<td style="text-align:left;">
							<a href="reviewDetail.bo?&rev_num=${list.rev_num}&pro_num=${list.pro_num}">
								${list.rev_subject }
							</a>
							<c:if test="${!(list.rev_photo == null || list.rev_photo == '')}">
								<img src="<%= request.getContextPath() %>/layout_image/pic_icon.gif"/>
							</c:if>
						</td>
						<td>
							<div class="starRev">
							  <span class="starR ${list.score >= 1 ? 'on' : ''}">1</span>
							  <span class="starR ${list.score >= 2 ? 'on' : ''}">2</span>
							  <span class="starR ${list.score >= 3 ? 'on' : ''}">3</span>
							  <span class="starR ${list.score >= 4 ? 'on' : ''}">4</span>
							  <span class="starR ${list.score >= 5 ? 'on' : ''}">5</span>
							</div>
						</td>
						<td>${list.user_id }</td>
						<!-- 날짜 형식 바꿔주기 (yyyy-MM-dd) -->
						<fmt:parseDate value="${list.rev_date}" var="rev_date" pattern="yyyy-MM-dd HH:mm:ss"/>
						<fmt:formatDate var="dateFmt" value="${rev_date}" pattern="yyyy-MM-dd"/>
						<td>${dateFmt }</td>
					</tr>
					</c:forEach>
				</table>
			</div>
		</div>
	</div>
</body>
</html>