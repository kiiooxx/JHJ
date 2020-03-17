<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link href="https://stackpath.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" rel="stylesheet">
<script src="https://code.jquery.com/jquery-3.4.1.min.js"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
<script src="dist/summernote.js"></script>
<link href="dist/summernote.css" rel="stylesheet">
<script type="text/javascript">

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

$(document).ready(function() {
	$('.starRev span').click(function(){
		  $(this).parent().children('span').removeClass('on');
		  $(this).addClass('on').prevAll('span').addClass('on');
		  
		  var text = $(this).text();
		  $('#star_text').text(text);
		  $('#score').val(text);
		  return false;
	});
	
	//제목 글자수 제한
	$('#subject').on('keyup', function () {
	    var content = $(this).val();
	    
	    
	    var length = 0;
	    var len = 0;

	    for (var i = 0; i < content.length; i++) {
	    	if(len <= 98) {
			    if (escape(content.charAt(i)).length == 6) {
			    	len++;
			    }
			    	len++;
			    	length++;
	    	}else {
	    		alert('문자 수를 초과했습니다!');
	    		$(this).val(content.substring(0, length));
	    		$('#lengthCheck').text(100);
	    	}
	    }
	    
	    $('#lengthCheck').text(len);    //글자수 실시간 카운팅
	});
	
	
	
});

var chkId = false;
function chkForm(f) {
	if (f.subject.value.trim() == "") {
		alert("제목을 입력하세요.");
		f.pass.focus();
		return false;
	}
    
    if (f.content.value.trim() == "") {
        alert("내용을 입력해 주세요.");
        f.name.focus();
        return false;
    }
	
	f.submit();
	
}

//파일 수정 버튼을 누르면
function file_modify() {
	$('#file_add').show();
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
<jsp:include page="/common/loginCheck.jsp"/>
<div id="join_form">
	<form action="qnaModify.bo" name="f" method="post" enctype="multipart/form-data">
		<!-- 글 쓰기 폼 -->
		<div class="join_table">
			<input type="hidden" name="qna_num" value="${qna.qna_num }"/>
			<input type="hidden" name="pro_num" value="${qna.pro_num }"/>
			<input type="hidden" name="sel_num" value="${qna.sel_num }"/>
			<input type="hidden" name="qna_step"	value="${qna.qna_step }"/>
			<table>
				<tr>
					<th>SUBJECT</th>
					<td><input type="text" name="subject" id="subject" style="width:60%" value="${qna.qna_title }">(<span id="lengthCheck">0</span>/100)</td>
				</tr>
				<tr>
					<th>WRITER</th>
					<td><input type="text" name="user_id" value="${qna.user_id }" readonly></td>
				</tr>
				<tr>
					<th>문의구분</th>
					<td>
						<select name="qna_type" id="qna_type">
							<option value="product_qna" ${qna.qna_type == 'product_qna' ? 'selected' : '' }>상품문의</option>
							<option value="delivery_qna" ${qna.qna_type == 'delivery_qna' ? 'selected' : '' }>배송문의</option>
							<option value="etc_qna" ${qna.qna_type == 'etc_qna' ? 'selected' : '' }>기타문의</option>
						</select>
					</td>
				</tr>
				<tr>
					<th>공개여부</th>
					<td>
						<input type="radio" name="qna_open" value="Y" ${qna.qna_open == 'Y'.charAt(0) ? 'checked' : '' }>공개
						<input type="radio" name="qna_open" value="N" ${qna.qna_open == 'N'.charAt(0) ? 'checked' : '' }>비공개
					</td>
				</tr>
				<tr>
					<th>E-MAIL</th>
					<td><input type="text" name="email" id="email" value="${qna.qna_email }" required/></td>
				</tr>
				<tr class="editor">
					<th colspan="2" style="padding:0px;">
						<textarea name="content" id="summernote">
							${qna.qna_content }
						</textarea>
					</th>
				</tr>
				
				<tr>
					<th>ATTACH FILE</th>
					<td>
						<p id="file_info">
							${review.rev_photo }
							<input type="hidden" name="qna_file2" value="${qna.qna_file }"/>
							<input type="button" value="수정" onclick="file_modify()"/>
						</p>
						<p id="file_add">
							<input type="file" name="qna_file" accept="image/gif, image/jpeg, image/png">
						</p>
					</td>
				</tr>
			</table>
		</div>
		<div class="jo_btn">
			<a href="javascript:chkForm(document.f);">등록</a>&nbsp;&nbsp;
		</div>
	</form>
</div>
</body>
</html>