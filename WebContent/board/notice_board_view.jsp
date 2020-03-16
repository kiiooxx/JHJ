<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page import="vo.Notice_BoardBean" %>
<%
Notice_BoardBean selectArticle = (Notice_BoardBean)request.getAttribute("selectArticle");
	String nowPage = (String)request.getAttribute("page");
%>
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
	${selectArticle.notice_title}<br>
		
	</section>
	<section id="articleContentArea">
		${selectArticle.Notice_content1}
	</section>
</section>
<section id="commandList">
	<a href="boardModifyForm.bo?board_num=${selectArticle.Notice_num()}&page=&{nowPage}">[수정]</a>
	<a href="boardDeleteForm.bo?board_num=${selectArticle.Notice_num()}&page=&{nowPage}">[삭제]</a>
	<%System.out.println("현재페이지" + nowPage); %>
	<a href="boardList.bo?page=${nowPage}">[목록]</a>
	&nbsp;&nbsp;	
</section>

</body>
</html>



