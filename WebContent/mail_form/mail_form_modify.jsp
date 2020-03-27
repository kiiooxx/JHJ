<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link href="http://netdna.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.css" rel="stylesheet">
<script src="http://cdnjs.cloudflare.com/ajax/libs/jquery/3.2.1/jquery.js"></script>
<script src="http://netdna.bootstrapcdn.com/bootstrap/3.3.5/js/bootstrap.js"></script>
<!-- include summernote css/js-->
<link href="http://cdnjs.cloudflare.com/ajax/libs/summernote/0.8.8/summernote.css" rel="stylesheet">
<script src="http://cdnjs.cloudflare.com/ajax/libs/summernote/0.8.8/summernote.js"></script>
<script>
$(document).ready(function() 
		{ $('#content').summernote({
			height: 400
		});

});

/* function init() {
	   if("${param.openInit != null && param.openInit eq 'true'}") {
		   if("${param.preview eq 'true'}"){
			   document.getElementById('title').readOnly = true;
			   document.getElementById('content').readOnly = true;
			   
		   }else{
			   if("${param.col_title == 'new_mem_title'}"){
				   document.getElementById("col_title").value = "${param.col_title }";
				   document.getElementById("col_content").value = "${param.col_content }";
			   }
			   else if("${param.col_title == 'quit_mem_title'}"){
				   document.getElementById("col_title").value = "${param.col_title }";
				   document.getElementById("col_content").value = "${param.col_content }";
			   }
			   else if("${param.col_title == 'order_info_title'}"){
				   document.getElementById("col_title").value = "${param.col_title }";
				   document.getElementById("col_content").value = "${param.col_content }";
			   }   
		   }
		   
	   }
	   
	} */
	
function acceptBtn(f){
	if(confirm("저장하시겠습니까?")==true){
		f.submit;
	}else{
		return;
	}
}
</script>
</head>
<style>

</style>

<!-- <body onload="init()"> -->
<body>
<form action="mailModify.ad" method="post" name="f">
param.col_title : ${param.col_title }<br>
param.col_content : ${param.col_content }<br>
param.preview : ${param.preview }
<input type="hidden" name="col_title" id="col_title" value="${param.col_title }">
<input type="hidden" name="col_content" id="col_content" value="${param.col_content }">

<table>
	<tr>
		<th>메일 제목</th>
		<td><input type="text" name="title" id="title" size="100" value="${mailOption.title }"/></td>
	</tr>
	<tr>
		<th>메일 내용</th>
		<td>
			<textarea name="content" id="content">
			${mailOption.content}
			</textarea>
			<br><input type="button" value="변수보기">
		</td>
	</tr>
</table>
<input type="submit" value="저장" onclick="javascript:acceptBtn(document.f);">
<input type="button" value="취소" onclick="self.close();">

</form>                
</body>

</html>