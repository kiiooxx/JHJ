<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>

<script type="text/javascript">


</script>
<style>
	.editor th{
		margin : 0px;
		width : 100%;
		padding : 0px;
	}
	

</style>
</head>
<body>
<div class="blank">
</div>

<div id="join_form">
	<form action="reviewRegist.bo" name="f" method="post" enctype="multipart/form-data">
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
					<td>${qna.qna_title }</td>
				</tr>
				<tr>
					<th>WRITER</th>
					<td>${qna.user_id }</td>
				</tr>
				<tr>
					<th>문의구분</th>
					<td>
						<c:choose>
								<c:when test="${qna.qna_type == 'product_qna'}">
									[상품문의]
								</c:when>
								<c:when test="${qna.qna_type == 'delivery_qna' }">
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
							<c:when test="${qna.qna_open == 'Y'.charAt(0) } ">
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
					<td>${qna.qna_email }</td>
				</tr>
				<tr>
					<th>답변여부</th>
					<td>${qna.qna_step }</td>
				</tr>
				<tr class="editor">
					<th colspan="2">
						<c:if test="${!(qna.qna_file eq null || qna.qna_file eq '' )}">
							<img src="<%= request.getContextPath() %>/upload/${qna.qna_file }"><br>
						</c:if>
						${qna.qna_content }
					</th>
				</tr>
				
			</table>
		</div>
		<div class="order_button_area">
			<p>
				<a href="qnaList.bo" class="w">LIST</a>
			</p>
		</div>
	</form>
</div>
</body>
</html>