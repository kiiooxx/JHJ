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
		
		<!-- 리뷰 내용  -->
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
				<c:if test="${board.board_writer == id }">
					<a href="boardViewAction.bo?board_type=review&board_num=${board.board_num }&pro_num=${prd.pro_num}&path=modify_form" class="b">MODIFY</a>
					<a href="#" class="b" onclick="del()">DELETE</a>
				</c:if>
				<a href="boardListAction.bo?board_type=review" class="w">LIST</a>
			</p>
		</div>
</div>