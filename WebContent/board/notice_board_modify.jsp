<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="sql" uri="http://java.sun.com/jsp/jstl/sql"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<!-- summernote -->
<jsp:include page="/resources/summernote.jsp"/>

<script language="javascript">
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

<div class="blank">
</div>

<div id="join_form">
	<form action="notice_boardModify.bo" method="post" name="f">
		<input type="hidden" name="board_num" value="${notice.notice_num}"/>
		<div class="join_table">
			<h1>NOTICE MODIFY</h1>
			<table>
				<tr>
					<th>SUBJECT</th>
					<td><input type="text" name="notice_title" value="${notice.notice_title }"/></td>
				</tr>
				<tr>
					<th>WRITER</th>
					<td>관리자</td>
				</tr>
				<tr class="editor">
					<th colspan="2" style="padding:0px;">
						<textarea name="notice_content" id="summernote">${notice.notice_content}</textarea>
					</th>
				</tr>
			</table>
			<div class="jo_btn">
				<a href="javascript:chkForm(document.f);">등록</a>
			</div>
		</div>
	</form>
</div>