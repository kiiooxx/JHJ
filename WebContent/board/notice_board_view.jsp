<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="nowPage" value="${page }"/>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>MVC게시판</title>
<style type="text/css">
	#articleForm {
		width : 700px;
		height : 500px;
		border : 1px solid #6c7a89;
		margin : auto;
		background : #6c7a89;
		color : #f3f1ef;
	}
	
	h2 {
		text-align : center;
	}
	
	#basicInfoArea {
		height : 40px;
	}
	
	#articleContentArea {
		background : #f3f1ef;
		margin-top : 20px;
		height : 350px;
		text-align : center;
		overflow : auto;
		color : #6c7a89;
	}
	
	#commandList {
		margin : auto;
		width : 500px;
		text-align : center;
	}
</style>
</head>
<body>
<!-- 게시판 수정 -->
<section id="articleForm">
	<h2>글 내용 상세보기</h2>
	<section id="basicInfoArea">
		제 &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;목 :
	${notice.notice_title}<br>
		
	</section>
	<section id="articleContentArea">
		${notice.notice_content}
	</section>
</section>
<section id="commandList">
	<a href="notice_boardModifyForm.bo?board_num=${notice.notice_num}&page=${nowPage}">[수정]</a>
	<a href="notice_boardDeletePro.bo?board_num=${notice.notice_num}&page=${nowPage}">[삭제]</a>
	
	<a href="notice_boardList.bo?page=${nowPage}">[목록]</a>
	&nbsp;&nbsp;	
</section>

</body>
</html>



