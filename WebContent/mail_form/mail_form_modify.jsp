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
function acceptBtn(f){
	if(confirm("저장하시겠습니까?")==false){
		return false;
	}else{
		f.submit;
	}
}
</script>
</head>
<style>
table {
	margin: auto;
	width: 300px;
	text-align: center;
}
th{
	height: 50px;
}
</style>

<body>
<form action="mailModify.ad" method="post" name="f">
<input type="hidden" name="col_title" id="col_title" value="${param.col_title }">
<input type="hidden" name="col_content" id="col_content" value="${param.col_content }">

<table>
	<tr>
		<th>메일 제목</th>
	</tr>
	<tr>
		<td><input type="text" name="title" id="title" size="100" value="${mailOption.title }"/></td>
	</tr>
	<tr>
		<th>메일 내용</th>
	</tr>
	<tr>
		<td>
			<textarea name="content" id="content">
			${mailOption.content}
			</textarea>
		</td>
	</tr>
	<tr>
		<td>
			<input type="submit" id="save" value="저장" onclick="javascript:acceptBtn(document.f);">
			<input type="button" id="close" value="닫기" onclick="self.close();">
		</td>
	</tr>
</table>

</form>                
</body>

</html>