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
		<input type="hidden" name="pro_num" value="${prd.pro_num }"/>
		<!-- 상품 정보 -->
		<div class="prd_info">
			<p class="prdThumb">
				<a href="#"><img src="<%= request.getContextPath() %>/upload/${prd.pro_photo }"></a>
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
					<td>${review.rev_subject }</td>
				</tr>
				<tr>
					<th>WRITER</th>
					<td>${review.user_id }</td>
				</tr>
				<tr>
					<th>SCORE</th>
					<td>
						<div class="starRev">
						  <span class="starR ${review.score >= 1 ? 'on' : ''}">1</span>
						  <span class="starR ${review.score >= 2 ? 'on' : ''}">2</span>
						  <span class="starR ${review.score >= 3 ? 'on' : ''}">3</span>
						  <span class="starR ${review.score >= 4 ? 'on' : ''}">4</span>
						  <span class="starR ${review.score >= 5 ? 'on' : ''}">5</span>
						</div>
					</td>
				</tr>
				<tr class="editor">
					<th colspan="2">
						<c:if test="${!(review.rev_photo eq null || review.rev_photo eq '' )}">
							<img src="<%= request.getContextPath() %>/upload/${review.rev_photo }"><br>
						</c:if>
						${review.rev_content }
					</th>
				</tr>
				
			</table>
		</div>
		<div class="order_button_area">
			<p>
				<a href="reviewList.bo" class="w">LIST</a>
			</p>
		</div>
	</form>
</div>
</body>
</html>