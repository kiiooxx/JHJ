<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="vo.PageInfo"%>
<%@ page import="vo.Notice_BoardBean"%>
<%@ page import="java.util.*"%>
<%@ page import="java.text.SimpleDateFormat"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>MVC 게시판</title>
<style type="text/css">
#listForm {
	width: 700px;
	height: 300px;
	border: 1px solid #2e3131;
	margin: auto;
	background: #f3f1ef;
}

h2 {
	text-align: center;
	color: #6c7a89;
}

table {
	margin: auto;
	width: 550px;
}

#tr_top {
	background: #6c7a89;
	text-align: center;
	color: #f3f1ef;
}

#tr_top td {
	overflow: hidden;
}

#tr_del {
	color: #bfbfbf;
}

#pageList {
	margin: auto;
	width: 500px;
	text-align: center;
}

#emptyArea {
	margin: auto;
	width: 500px;
	text-align: center;
}
</style>
</head>
<body>
	<!-- 게시판 리스트 -->
	<section id="listForm">
		<h2>글 목록</h2>
		<table>
			<tr>
				<td colspan="5" align="right"><a href="notice_boardWriteForm.bo">글쓰기</a></td>
			<tr id="tr_top">
				<td>번호</td>
				<td>제목</td>
				<td>작성자</td>
				<td>날짜</td>
			</tr>

			
					<c:forEach var="list" items="${articleList }" varStatus="i">

						<tr>
							<td>${list.notice_num}</td>
							<td><a href="notice_boardDetail.bo?board_num=${list.notice_num}&page=${pageInfo.page}">${list.notice_title }</a></td>
							<td>관리자</td>
							<td>${list.notice_date}</td>
						</tr>
					</c:forEach>
		</table>
		
		
		<div id="pageList">
			<ol>
			<c:choose>
				<c:when test="${pageInfo.page <= 1 }">
					<li> < </li>
				</c:when>
				<c:otherwise>
					<li><a href="productList.pro?page=${pageInfo.page-1 }"> < </a></li>
				</c:otherwise>
			</c:choose>
			
			
			<c:forEach var="pglist" begin="${pageInfo.startPage }" end="${pageInfo.endPage }" step="1" varStatus="a">
				<c:choose>
					<c:when test="${a.count == pageInfo.page }">
						<li>[${a.count }]</li>
					</c:when>
					<c:otherwise>
						<li><a href="productList.pro?page=${a.count }">[${a.count }]</a></li>
					</c:otherwise>
				</c:choose>
			</c:forEach>
			
			
			<c:choose>
				<c:when test="${pageInfo.page>=pageInfo.maxPage }">
					<li> > </li>
				</c:when>
				<c:otherwise>
					<li><a href="productList.pro?page=${pageInfo.page+1 }"> > </a></li>
				</c:otherwise>
			</c:choose>
			</ol>
		</div>
	</section>

	
	
</body>
</html>