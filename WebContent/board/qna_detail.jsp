<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>


<script type="text/javascript">
//삭제 버튼 눌렀을 때
function del() {
	if(confirm('정말 삭제하시겠습니까?')) {
		location.href='boardDeleteAction.bo?board_num='+${board.board_num};
	}else {
		return false;
	}
}

</script>



<div class="blank">
</div>

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
				<c:if test="${board.board_writer == id }">
					<a href="boardViewAction.bo?board_type=qna&board_num=${board.board_num }&pro_num=${prd.pro_num}&path=modify_form" class="b">MODIFY</a>
					<a href="#" class="b" onclick="del()">DELETE</a>
				</c:if>
				<a href="boardListAction.bo?board_type=qna" class="w">LIST</a>
			</p>
		</div>
</div>
