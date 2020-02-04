<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<div class="headArea">
	<h1>상품 등록</h1>
</div>
<div class="prdAddForm">
	<form action="" name="" method="post" enctype="multipart/form-data">
	<h3>기본 정보</h3>
	
		<table>
			<tr>
				<th>상품명</th>
				<td><input type="text"/></td>
			</tr>
			<tr>
				<th>판매가</th>
				<td><input type="text"/></td>
			</tr>
			<tr>
				<th>상품 설명</th>
				<td><input type="text"/></td>
			</tr>
			<tr>
				<th>이미지</th>
				<td></td>
			</tr>
		</table>
</div>

<div>
	<h3>쇼핑몰 진열설정</h3>
		<table>
			<tr>
				<th>진열상태</th>
				<td>
					<input type="radio" name="active" value=""/>진열
					<input type="radio" name="active" value=""/>진열안함
				</td>
			</tr>
			<tr>
				<th>메인진열</th>
				<td>
					<input type="radio" name="nb" value=""/>베스트
					<input type="radio" name="nb" value=""/>신상품
				</td>
			</tr>
			<tr>
				<th>상품 분류</th>
				<td><!-- 카테고리 --></td>
			</tr>
			
		</table>
	</form>
</div>

<div>
	<h3>옵션 설정</h3>
	<table>
		<tr>
			<th>색상</th>
			<td>
				<select>
					<option></option>
				</select>
			</td>
		</tr>
		<tr>
			<th>사이즈</th>
			<td>
				<select>
					<option></option>
				</select>
			</td>
		</tr>
	</table>
</div>
</body>
</html>