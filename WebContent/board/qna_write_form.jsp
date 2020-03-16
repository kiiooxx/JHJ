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
	<form action="qnaRegist.bo" name="f" method="post" enctype="multipart/form-data">
		<input type="hidden" name="pro_num" value="${prd.pro_num }"/>
		
		<!-- 글 쓰기 폼 -->
		<div class="join_table">
			<table>
				<tr>
					<th>SUBJECT</th>
					<td><input type="text" name="subject" id="subject" style="width:60%">(<span id="lengthCheck">0</span>/100)</td>
				</tr>
				<tr>
					<th>WRITER</th>
					<td><input type="text" name="user_id" value="${id }" readonly></td>
				</tr>
				<tr>
					<th>문의구분</th>
					<td>
						<select name="qna_type" id="qna_type">
							<option value="product_qna">상품문의</option>
							<option value="delivery_qna">배송문의</option>
							<option value="etc_qna">기타문의</option>
						</select>
					</td>
				</tr>
				<tr>
					<th>공개여부</th>
					<td>
						<input type="radio" name="open" value="open" checked>공개
						<input type="radio" name="open" value="sercret">비공개
					</td>
				</tr>
				<tr>
					<th>E-MAIL</th>
					<td><input type="text" name="email" id="email" required /></td>
				</tr>
				<tr class="editor">
					<th colspan="2" style="padding:0px;">
						<textarea name="content" id="summernote">
							성함 : <br>
							연락처 : <br>
							문의 내용 : <br>
						</textarea>
					</th>
				</tr>
				
				<tr>
					<th>ATTACH FILE</th>
					<td><input type="file" name="rev_photo" accept="image/gif, image/jpeg, image/png"></td>
				</tr>
			</table>
			<!-- 상품번호 : 상품 상세 페이지에서 문의 글쓰기를 눌렀을 경우 -->
			<input type="hidden" name="pro_num" value="${pro_num }"/>
			<!-- 주문번호 : 마이페이지 -> 주문내역 -> 해당 주문건에 대한 문의하기를 눌렀을 경우 -->
			<input type="hidden" name="sel_num" value="${sel_num }"/>
		</div>
		<div class="jo_btn">
			<a href="javascript:chkForm(document.f);">등록</a>&nbsp;&nbsp;
		</div>
	</form>
</div>
</body>
</html>