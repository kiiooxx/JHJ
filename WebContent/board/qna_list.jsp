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
	.rev_thumb {
		width : 50px;
	}
</style>
</head>
<body>
<div class="blank">
</div>

<!-- 문의 게시판 -->
	<div id="prdReview">
		<div class="board">
			<h3>QnA</h3>
			<div>
				<table class="board_table">
					<colgroup>
						<col style="width:70px;">
						<col style="width:100px;">	
						<col style="width:auto">
						<col style="width:80px;">
						<col style="width:120px;">
						<col style="width:80px;">
					</colgroup>
					<tr>
						<th scope="col">no</th>
						<th scope="col">type</th>
						<th scope="col">subject</th>
						<th scope="col">step</th>
						<th scope="col">writer</th>
						<th scope="col">date</th>
					</tr>
					<c:forEach var="list" items="${qnaList }" varStatus="i">
					<tr>
						<td>${list.qna_num }</td>
						<td>
							<c:choose>
								<c:when test="${list.qna_type == 'product_qna'}">
									[상품문의]
								</c:when>
								<c:when test="${list.qna_type == 'delivery_qna' }">
									[배송문의]
								</c:when>
								<c:otherwise>
									[기타문의]
								</c:otherwise>
							</c:choose>
						</td>
						<td style="text-align:left;">
							<c:choose>
								<c:when test="${list.qna_open != 'N'.charAt(0) || grade=='A'.charAt(0)}">
									<a href="qnaDetail.bo?&qna_num=${list.qna_num}&pro_num=${list.pro_num}">
										${list.qna_title}
									</a>
								</c:when>
								<c:otherwise>
									${list.qna_title}
								</c:otherwise>
							</c:choose>
							<c:if test="${list.qna_open == 'N'.charAt(0) }">
								<img src="<%= request.getContextPath() %>/layout_image/lock_icon.png"/>
							</c:if>
						</td>
						<td>
							${list.qna_step }
						</td>
						<td>${list.user_id }</td>
						<!-- 날짜 형식 바꿔주기 (yyyy-MM-dd) -->
						<fmt:parseDate value="${list.qna_date}" var="qna_date" pattern="yyyy-MM-dd HH:mm:ss"/>
						<fmt:formatDate var="dateFmt" value="${qna_date}" pattern="yyyy-MM-dd"/>
						<td>${dateFmt }</td>
					</tr>
					</c:forEach>
				</table>
				<div class="order_button_area">
					<p>
						<a href="qnaWriteForm.bo" class="b">WRITE</a>
					</p>
				</div>
			</div>
		</div>
	</div>
</body>
</html>