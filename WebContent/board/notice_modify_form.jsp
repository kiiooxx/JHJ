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
	if (f.board_title.value.trim() == "") {
		alert("제목을 입력하세요.");
		f.board_title.focus();
		return false;
	}
    
    if (f.board_content.value.trim() == "") {
        alert("내용을 입력해 주세요.");
        f.board_content.focus();
        return false;
    }
	
	f.submit();
	
}

//파일 수정 버튼을 누르면
function file_modify() {
	$('#file_add').show();
}
</script>

<div class="blank">
</div>

<div id="join_form">
	<form action="boardModifyAction.bo" method="post" name="f" enctype="multipart/form-data">
		<input type="hidden" name="board_num" value="${board.board_num }"/>
		<input type="hidden" name="board_type" value="${board.board_type }"/>
		<div class="join_table">
			<h1>NOTICE MODIFY</h1>
			<table>
				<tr>
					<th>SUBJECT</th>
					<td><input type="text" name="board_title" value="${board.board_title }"/></td>
				</tr>
				<tr>
					<th>WRITER</th>
					<td>관리자</td>
				</tr>
				<tr class="editor">
					<th colspan="2" style="padding:0px;">
						<textarea name="board_content" id="summernote">
							${board.board_content}
						</textarea>
					</th>
				</tr>
				<tr>
					<th>ATTACH FILE</th>
					<td>
						<p id="file_info">
							${board.board_photo }
							<input type="hidden" name="qna_file2" value="${board.board_photo }"/>
							<input type="button" value="수정" onclick="file_modify()"/>
						</p>
						<p id="file_add">
							<input type="file" name="board_photo" accept="image/gif, image/jpeg, image/png">
						</p>
					</td>
				</tr>
			</table>
			<div class="jo_btn">
				<a href="javascript:chkForm(document.f);">등록</a>
			</div>
		</div>
	</form>
</div>