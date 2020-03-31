<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="sql" uri="http://java.sun.com/jsp/jstl/sql"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<!-- 게시판 리스트 -->
<div id="boardList">
	<div class="board">
		
		<!-- 번호 역순으로 매기기위해  -->
		<c:set var="size" value="${fn:length(boardList) }"/>
		
		<!-- 공지사항 -->
		<c:if test="${board_type == 'notice' }">
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
					<td scope="col">NO</td>
					<td scope="col">SUBJECT</td>
					<td scope="col">WRITER</td>
					<td scope="col">DATE</td>
				</tr>
				
				<c:forEach var="list" items="${boardList }" varStatus="i">
					<c:if test="${list.board_notice == 'Y' }">
						<c:set var="cnt" value="${cnt + 1 }"/>
					</c:if>
					
					<tr>
						<td>${list.board_notice == 'Y' ? '공지' : size}</td>
						<td style="text-align:left;">
							<a href="boardViewAction.bo?&board_num=${list.board_num}&page=${pageInfo.page}&path=/board/board_detail">
								${list.board_title }
							</a>
						</td>
						<td>관리자</td>
						<!-- 날짜 형식 바꿔주기 (yyyy-MM-dd) -->
						<fmt:parseDate value="${list.board_date}" var="board_date" pattern="yyyy-MM-dd HH:mm:ss"/>
						<fmt:formatDate var="dateFmt" value="${board_date}" pattern="yyyy-MM-dd"/>
						<td>${dateFmt }</td>
					</tr>
					
					<c:set var="size" value="${size - 1 }"/>
				</c:forEach>
			</table>
			<c:if test="${grade == 'A'.charAt(0) }">
				<div class="order_button_area">
					<p>
						<a href="boardWriteForm.bo?board_type=${board_type }">WRITE</a>
					</p>
				</div>
			</c:if>
		</div>
		</c:if>
		
		<c:if test="${board_type == 'qna' }">
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
					<c:forEach var="list" items="${boardList }" varStatus="i">
						<c:if test="${list.board_notice == 'Y' }">
							<c:set var="cnt" value="${cnt + 1 }"/>
						</c:if>
						
						<tr>
							<td>${list.board_notice == 'Y' ? '공지' : size}</td>
							<td>
								<c:if test="${list.board_notice == 'N' }">
									<c:choose>
										<c:when test="${list.qna_type == 'product_qna'}">
											[상품문의]
										</c:when>
										<c:when test="${list.qna_type == 'delivery_qna' }">
											[배송문의]
										</c:when>
										<c:when test="${list.qna_type == 'etc_qna' }">
											[기타문의]
										</c:when>
									</c:choose>
								</c:if>
							</td>
							<td style="text-align:left;">
								<c:choose>
									<c:when test="${list.qna_open != 'N' || grade == 'A'.charAt(0) || list.board_writer == id }">
										<a href="boardViewAction.bo?&board_num=${list.board_num}&pro_num=${list.pro_num}&path=/board/board_detail">
											${list.board_title}
										</a>
									</c:when>
									<c:otherwise>
										${list.board_title}					
									</c:otherwise>
								</c:choose>
									${list.board_step == 'Y' ? '[1]' : '' }	<!-- 답글 여부 -->
								<c:if test="${list.qna_open == 'N' }">
									<img src="<%= request.getContextPath() %>/layout_image/lock_icon.png"/>
								</c:if>
							</td>
							<td>
								<c:if test="${list.board_notice == 'N' }">
									${list.board_step }
								</c:if>
							</td>
							<td>${list.board_writer }</td>
							<!-- 날짜 형식 바꿔주기 (yyyy-MM-dd) -->
							<fmt:parseDate value="${list.board_date}" var="board_date" pattern="yyyy-MM-dd HH:mm:ss"/>
							<fmt:formatDate var="dateFmt" value="${board_date}" pattern="yyyy-MM-dd"/>
							<td>${dateFmt }</td>
					</tr>
					
					<c:set var="size" value="${size - 1 }"/>
					</c:forEach>
				</table>
				<div class="order_button_area">
					<p>
						<a href="boardWriteForm.bo?board_type=${board_type }" class="b">WRITE</a>
					</p>
				</div>
			</div>
		</c:if>
		
		
		<c:if test="${board_type == 'review' }">
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
						<c:if test="${list.board_notice == 'Y' }">
							<c:set var="cnt" value="${cnt + 1 }"/>
						</c:if>
						
						<tr>
							<td>${list.board_notice == 'Y' ? '공지' : size}</td>
							<td>
								<c:if test="${list.board_notice == 'N' }">						
									<a href="productDetail.pro?pro_num=${list.pro_num }">
										<img src="<%= request.getContextPath() %>/upload/${prdList[i.index].pro_photo }" class="rev_thumb"/>
									</a>
								</c:if>
							</td>
							<td style="text-align:left;">
								<a href="boardViewAction.bo?&board_num=${list.board_num}&pro_num=${list.pro_num}&page=${pageInfo.page}&path=/board/board_detail">
									${list.board_title }
									${list.board_step == 'Y' ? '[1]' : '' }
								</a>
								<!-- 조회수 10 넘으면 Hit 아이콘 -->
								<c:if test="${list.board_hits > 10}">
									<img src="<%= request.getContextPath() %>/layout_image/hit_icon.png">
								</c:if>
								<!-- 사진 있으면 사진 아이콘 -->
								<c:if test="${!(list.board_photo == null || list.board_photo == '')}">
									<img src="<%= request.getContextPath() %>/layout_image/pic_icon.gif"/>
								</c:if>
							</td>
							<td>
								<c:if test="${list.board_notice == 'N' }">
									<div class="starRev">
									  <span class="starR ${list.review_score >= 1 ? 'on' : ''}">1</span>
									  <span class="starR ${list.review_score >= 2 ? 'on' : ''}">2</span>
									  <span class="starR ${list.review_score >= 3 ? 'on' : ''}">3</span>
									  <span class="starR ${list.review_score >= 4 ? 'on' : ''}">4</span>
									  <span class="starR ${list.review_score >= 5 ? 'on' : ''}">5</span>
									</div>
								</c:if>
							</td>
							<td>${list.board_writer }</td>
							<!-- 날짜 형식 바꿔주기 (yyyy-MM-dd) -->
							<fmt:parseDate value="${list.board_date}" var="board_date" pattern="yyyy-MM-dd HH:mm:ss"/>
							<fmt:formatDate var="dateFmt" value="${board_date}" pattern="yyyy-MM-dd"/>
							<td>${dateFmt }</td>
						</tr>
						<c:set var="size" value="${size - 1 }"/>
					</c:forEach>
				</table>
				<c:if test="${grade == 'A'.charAt(0) }">
					<div class="order_button_area">
						<p>
							<a href="boardWriteForm.bo?board_type=${board_type }">WRITE</a>
						</p>
					</div>
				</c:if>
			</div>
		</c:if>
		
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
				
				
				<c:forEach var="a" begin="${pageInfo.startPage }" end="${pageInfo.endPage }" step="1">
					<c:choose>
						<c:when test="${a == pageInfo.page }">
							<li>[${a}]</li>
						</c:when>
						<c:otherwise>
							<li><a href="boardListAction.bo?board_type=notice&page=${a}">[${a}]</a></li>
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