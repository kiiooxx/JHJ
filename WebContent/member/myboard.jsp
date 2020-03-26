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
					<c:forEach var="list" items="${reviewList }" varStatus="i">
					<tr>
						<td>${i.count }</td>
						<td>
							<c:if test="${list.pro_num != 0 }">
								<a href="productDetail.pro?pro_num=${list.pro_num }">
									<img src="<%= request.getContextPath() %>/upload/${prdList[i.index].pro_photo }" class="rev_thumb"/>
								</a>
							</c:if>
						</td>
						<td style="text-align:left;">
							<a href="boardViewAction.bo?&board_num=${list.board_num}&pro_num=${list.pro_num}&page=${reviewPageInfo.page}&path=/board/board_detail">
								${list.board_title }
								${list.board_step == 'Y' ? '[1]' : '' }	<!-- 답글 여부 -->
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
						<fmt:parseDate value="${list.board_date}" var="rev_date" pattern="yyyy-MM-dd HH:mm:ss"/>
						<fmt:formatDate var="dateFmt" value="${rev_date}" pattern="yyyy-MM-dd"/>
						<td>${dateFmt }</td>
					</tr>
					</c:forEach>
				</table>
			</div>

			
			<!-- 페이지 리스트 -->
			<div id="pageList">
				<c:if test="${reviewPageInfo.endPage > 0}">
					<ol>
					<c:choose>
						<c:when test="${reviewPageInfo.page <= 1 }">
							<li> < </li>
						</c:when>
						<c:otherwise>
							<li><a href="boardListAction.bo?board_type=review&page=${reviewPageInfo.page-1 }"> < </a></li>
						</c:otherwise>
					</c:choose>
					
					
					<c:forEach var="pglist" begin="${reviewPageInfo.startPage }" end="${reviewPageInfo.endPage }" step="1" varStatus="a">
						<c:choose>
							<c:when test="${a.count == reviewPageInfo.page }">
								<li>[${a.count }]</li>
							</c:when>
							<c:otherwise>
								<li><a href="boardListAction.bo?board_type=review&page=${a.count }">[${a.count }]</a></li>
							</c:otherwise>
						</c:choose>
					</c:forEach>
					
					
					<c:choose>
						<c:when test="${reviewPageInfo.page>=reviewPageInfo.maxPage }">
							<li> > </li>
						</c:when>
						<c:otherwise>
							<li><a href="boardListAction.bo?board_type=review&page=${reviewPageInfo.page+1 }"> > </a></li>
						</c:otherwise>
					</c:choose>
					</ol>
				</c:if>
			</div>
		</div>
	</div>
	
	<div id="prdQnA">
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
						<th scope="col">answer</th>
						<th scope="col">writer</th>
						<th scope="col">date</th>
					</tr>
					<c:set var="size" value="${fn:length(qnaList) }"/>
					<c:forEach var="qna_list" items="${qnaList }" varStatus="i">
					<tr>
						<td>${i.count }</td>
						<td>
							<c:choose>
								<c:when test="${qna_list.qna_type == 'product_qna'}">
									[상품문의]
								</c:when>
								<c:when test="${qna_list.qna_type == 'delivery_qna' }">
									[배송문의]
								</c:when>
								<c:otherwise>
									[기타문의]
								</c:otherwise>
							</c:choose>
						</td>
						<td style="text-align:left;">
							<a href="boardViewAction.bo?&board_num=${qna_list.board_num}&pro_num=${qna_list.pro_num}&page=${qnaPageInfo.page}&path=/board/board_detail">
								${qna_list.board_title}
								${qna_list.board_step == 'Y' ? '[1]' : '' }	<!-- 답글 여부 -->
							</a>
							
							<c:if test="${qna_list.qna_open == 'N' }">
								<img src="<%= request.getContextPath() %>/layout_image/lock_icon.png"/>
							</c:if>
						</td>
						<td>
							${qna_list.board_step }
						</td>
						<td>${qna_list.board_writer }</td>
						<!-- 날짜 형식 바꿔주기 (yyyy-MM-dd) -->
						<fmt:parseDate value="${qna_list.board_date}" var="qna_date" pattern="yyyy-MM-dd HH:mm:ss"/>
						<fmt:formatDate var="dateFmt" value="${qna_date}" pattern="yyyy-MM-dd"/>
						<td>${dateFmt }</td>
					</tr>
					</c:forEach>
				</table>
			</div>
			
			<!-- 페이지 리스트 -->
			<div id="pageList">
				<c:if test="${qnaPageInfo.endPage > 0}">
					<ol>
					<c:choose>
						<c:when test="${qnaPageInfo.page <= 1 }">
							<li> < </li>
						</c:when>
						<c:otherwise>
							<li><a href="boardListAction.bo?board_type=qna&page=${qnaPageInfo.page-1 }"> < </a></li>
						</c:otherwise>
					</c:choose>
					
					
					<c:forEach var="pglist" begin="${qnaPageInfo.startPage }" end="${qnaPageInfo.endPage }" step="1" varStatus="a">
						<c:choose>
							<c:when test="${a.count == qnaPageInfo.page }">
								<li>[${a.count }]</li>
							</c:when>
							<c:otherwise>
								<li><a href="boardListAction.bo?board_type=qna&page=${a.count }">[${a.count }]</a></li>
							</c:otherwise>
						</c:choose>
					</c:forEach>
					
					
					<c:choose>
						<c:when test="${qnaPageInfo.page>=qnaPageInfo.maxPage }">
							<li> > </li>
						</c:when>
						<c:otherwise>
							<li><a href="boardListAction.bo?board_type=qna&page=${qnaPageInfo.page+1 }"> > </a></li>
						</c:otherwise>
					</c:choose>
					</ol>
				</c:if>
			</div>
		</div>
	</div>
