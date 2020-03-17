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
<link href="https://stackpath.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" rel="stylesheet">
<script src="https://code.jquery.com/jquery-3.4.1.min.js"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
<script src="dist/summernote.js"></script>
<link href="dist/summernote.css" rel="stylesheet">

<script>
$(document).ready(function() {
	$('#summernote').summernote({ // summernote를 사용하기 위한 선언
        height: 400,
        callbacks : {
			onImageUpload: function(files, editor, welEditable) {
				for (var i = files.length - 1; i >= 0; i--) {
					sendFile(files[i], this);
		        }
	        }
        }
	});
});

function sendFile(file, editor) {
      var data = new FormData();
      data.append("file", file, file.name);

      $.ajax({
    	dataType : 'jSON',
        data: data,
        type: "POST",
        url: '<%=request.getContextPath()%>/summernotePhotoUpload',
        cache: false,
        contentType: false,
        enctype: 'multipart/form-data',
        processData: false,
        success: function(data) {
        	$('#summernote').summernote('insertImage', data.url, file.name);
        	$('#summernote').summernote('pasteHTML', '<img src="' + data.url + file.name + '"/>');
        },
        error: function() {
        	alert('error');
        }
      });
}

var chkId = false;
function chkForm(f) {
	if (f.notice_title.value.trim() == "") {
		alert("제목을 입력하세요.");
		f.pass.focus();
		return false;
	}
    
    if (f.notice_content.value.trim() == "") {
        alert("내용을 입력해 주세요.");
        f.name.focus();
        return false;
    }
	
	f.submit();
	
}
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
	<form action="notice_boardWritePro.bo" method="post" name="f">
		<div class="join_table">
			<h1>NOTICE WRITE</h1>
			<table>
				<tr>
					<th><label for="notice_title">제 목</label></th>
					<td><input type="text" name="notice_title" id="notice_title"/></td>
				</tr>
				<tr class="editor">
					<th colspan="2" style="padding:0px;">
						<textarea name="notice_content" id="summernote"></textarea>
					</th>
				</tr>
			</table>
			<div class="jo_btn">
				<a href="javascript:chkForm(document.f);">등록</a>
			</div>
		</div>
	</form>
</div>
</body>
</html>