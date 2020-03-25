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
<title>게시글</title>
<!-- css -->
<jsp:include page="/resources/css.jsp"/>

<!-- js -->
<jsp:include page="/resources/js.jsp"/>

<script type="text/javascript">
//삭제 버튼 눌렀을 때
function del() {
	if(confirm('정말 삭제하시겠습니까?')) {
		location.href='boardDeleteAction.bo?board_num=${board.board_num}';
		alert('삭제되었습니다! 창을 닫습니다.');
		opener.parent.location.reload();
		self.close();
	}else {
		return false;
	}
}


</script>

</head>
<body>
<div id="join_form">
	<c:if test="${prd != null }">
		<input type="hidden" name="pro_num" value="${prd.pro_num }"/>
		<!-- 상품 정보 -->
		<div class="prd_info">
			<p class="prdThumb">
				<a href="productDetail.pro?pro_num=${prd.pro_num }"><img src="<%= request.getContextPath() %>/upload/${prd.pro_photo }"></a>
			</p>
			<div class="prd_name">
				<h3><a href="productDetail.pro?pro_num=${prd.pro_num }">${prd.pro_name }</a></h3>
				<fmt:formatNumber var="price" value="${prd.pro_price}" pattern="#,###"/>
				<p>${price }</p>
			</div>
		</div>
	</c:if>
		
	<!-- QnA게시판  -->
	<c:if test="${board.board_type == 'qna' }">	
		<!-- 문의 내용  -->
		<div class="join_table">
			<table>
				<tr>
					<th>SUBJECT</th>
					<td>${board.board_title }</td>
				</tr>
				<tr>
					<th>WRITER</th>
					<td>${board.board_writer }</td>
				</tr>
				<tr>
					<th>문의구분</th>
					<td>
						<c:choose>
								<c:when test="${board.qna_type == 'product_qna'}">
									[상품문의]
								</c:when>
								<c:when test="${board.qna_type == 'delivery_qna' }">
									[배송문의]
								</c:when>
								<c:otherwise>
									[기타문의]
								</c:otherwise>
							</c:choose>
					</td>
				</tr>
				<tr>
					<th>공개여부</th>
					<td>
						<c:choose>
							<c:when test="${board.qna_open == 'Y' } ">
								공개
							</c:when>
							<c:otherwise>
								비공개
							</c:otherwise>
						</c:choose>
					</td>
				</tr>
				<tr>
					<th>E-MAIL</th>
					<td>${board.qna_email }</td>
				</tr>
				<tr>
					<th>답변여부</th>
					<td>${board.board_step }</td>
				</tr>
				<tr class="editor">
					<th colspan="2">
						<c:if test="${!(board.board_photo eq null || board.board_photo eq '' )}">
							<img src="<%= request.getContextPath() %>/upload/${board.board_photo }"><br>
						</c:if>
						${board.board_content }
					</th>
				</tr>
				
			</table>
		</div>
		<div class="order_button_area">
			<p>
				<c:if test="${board.board_writer == id || grade == 'A'.charAt(0) }">
					<a href="boardViewAction.bo?board_type=qna&board_num=${board.board_num }&pro_num=${prd.pro_num}&path=/admin/board_modify_form" class="b">MODIFY</a>
					<a href="#" class="b" onclick="del()">DELETE</a>
				</c:if>
			</p>
		</div>
	</c:if>
	
	
	<!-- 리뷰게시판 -->
	<c:if test="${board.board_type=='review' }">
		<div class="join_table">
			<table>
				<tr>
					<th>SUBJECT</th>
					<td>${board.board_title }</td>
				</tr>
				<tr>
					<th>WRITER</th>
					<td>${board.board_writer }</td>
				</tr>
				<tr>
					<th>SCORE</th>
					<td>
						<div class="starRev">
						  <span class="starR ${board.review_score >= 1 ? 'on' : ''}">1</span>
						  <span class="starR ${board.review_score >= 2 ? 'on' : ''}">2</span>
						  <span class="starR ${board.review_score >= 3 ? 'on' : ''}">3</span>
						  <span class="starR ${board.review_score >= 4 ? 'on' : ''}">4</span>
						  <span class="starR ${board.review_score >= 5 ? 'on' : ''}">5</span>
						</div>
					</td>
				</tr>
				<tr class="editor">
					<th colspan="2">
						<c:if test="${!(board.board_photo eq null || board.board_photo eq '' )}">
							<img src="<%= request.getContextPath() %>/upload/${board.board_photo }"><br>
						</c:if>
						${board.board_content }
					</th>
				</tr>
				
			</table>
		</div>
		<div class="order_button_area">
			<p>
				<c:if test="${board.board_writer == id || grade == 'A'.charAt(0) }">
					<a href="boardViewAction.bo?board_type=review&board_num=${board.board_num }&pro_num=${prd.pro_num}&path=/admin/board_modify_form" class="b">MODIFY</a>
					<a href="#" class="b" onclick="del()">DELETE</a>
				</c:if>
			</p>
		</div>
	</c:if>
	
	<c:if test="${board.board_type == 'notice' }">
		<div class="join_table">
		<table>
			<tr>
				<th>SUBJECT</th>
				<td>${board.board_title}</td>
			</tr>
			<tr>
				<th>WRITER</th>
				<td>관리자</td>
			</tr>
			<tr>
				<th>DATE</th>
				<td>${board.board_date }</td>
			</tr>
			<tr>
				<th colspan="2">
					<c:if test="${!(board.board_photo eq null || board.board_photo eq '' )}">
						<img src="<%= request.getContextPath() %>/upload/${board.board_photo }"><br>
					</c:if>
					${board.board_content }
				</th>
			</tr>
		</table>
		
		<div class="order_button_area">
			<p>
				<c:if test="${grade == 'A'.charAt(0) }">
					<a href="boardViewAction.bo?board_type=notice&board_num=${board.board_num }&pro_num=${prd.pro_num}&path=/admin/board_modify_form" class="b">MODIFY</a>
					<a href="#" class="b" onclick="del()">DELETE</a>
				</c:if>
			</p>
		</div>
		</div>
	</c:if>
	
	<!-- 답글있을때 -->
	<c:if test="${board.board_step == 'Y' || board.board_type == 'answer' }">
		<div class="comment_table">
			<table>
				<tr>
					<th><strong class="name">관리자</strong></th>
					<th><span class="comment_top_right">${board.board_type=='answer' ? board.board_date : board_answer.board_date}</span></th>
				</tr>
				
				<tr>
					<th colspan="2">${board.board_type=='answer' ? board.board_title : board_answer.board_title }</th>
				</tr>
				
				<tr>
					<th colspan="2">
						<c:choose>
							<c:when test="${board.board_type == 'answer' }">
								<c:if test="${!(board.board_photo eq null || board.board_photo eq '' )}">
									<img src="<%= request.getContextPath() %>/upload/${board.board_photo }"><br>
								</c:if>
							</c:when>
							<c:otherwise>
								<c:if test="${!(board_answer.board_photo eq null || board_answer.board_photo eq '' )}">
									<img src="<%= request.getContextPath() %>/upload/${board_answer.board_photo }"><br>
								</c:if>
							</c:otherwise>
						</c:choose>
							${board.board_type=='answer' ? board.board_content : board_answer.board_content }
					</th>
				</tr>
			</table>
		</div>
		
		<c:if test="${board.board_type == 'answer' }">
			<div class="order_button_area">
				<p>
					<c:if test="${board.board_writer == id || grade == 'A'.charAt(0) }">
						<a href="boardViewAction.bo?board_type=answer&board_num=${board.board_num }&pro_num=${prd.pro_num}&path=/admin/board_modify_form" class="b">MODIFY</a>
						<a href="#" class="b" onclick="del()">DELETE</a>
					</c:if>
				</p>
			</div>
		</c:if>
	</c:if>
</div>
</body>
</html>