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

<!-- include libraries(jQuery, bootstrap) -->
<script src="https://code.jquery.com/jquery-3.4.1.min.js"></script>

    
<style type="text/css">
	#writeForm {
		width : 500px;
		height : 500px;
		border : 1px solid #f3f1ef;
		margin : auto;
		background : #f3f1ef;
		color : #6c7a89;
	}
	
	h2 {
		text-align : center;
	}
	
	table {
		margin : auto;
		width : 450px;
	}
	
	.td_left {
		width : 150px;
		background : #6c7a89;
		color : #f3f1ef;
	}
	
	.td_right {
		width : 300px;
		background : white;
	}
	
	#commandCell {
		text-align : center;
	}
</style>
</head>
<body>
	<!-- 게시판 등록 -->
	<section id="writeForm">
		<h2>공지 게시판 글 등록</h2>
		<form action="notice_boardWritePro.bo" method="post" name="boardform">
			<table>
				
				<tr>
					<td class="td_left"><label for="notice_title">제 목</label></td>
					<td class="td_right"><input type="text" name="notice_title" id="notice_title" required="required"/></td>
				</tr>
				<tr>
					<td class="td_left"><label for="notice_content">내 용</label></td>
					<td class="td_right"><textarea name="notice_content" id="notice_content" cols="40" rows="15" required="required"></textarea></td>
				</tr>
				
			</table>
			<section id="commandCell">
				<input type="submit" value="등록">&nbsp;&nbsp;
				<input type="reset" value="다시쓰기"/>
			</section>
		</form>
	</section>