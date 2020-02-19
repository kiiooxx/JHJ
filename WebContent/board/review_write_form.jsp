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
	<form action="reviewRegist.bo" name="f" method="post" enctype="multipart/form-data">
		<input type="hidden" name="pro_num" value="${prd.pro_num }"/>
		<!-- 상품 정보 -->
		<div class="prd_info">
			<p class="prdThumb">
				<a href="#"><img src="<%= request.getContextPath() %>/upload/${prd.pro_photo }"></a>
			</p>
			<div class="prd_name">
				<h3><a href="productDetail.pro?pro_num=${prd.pro_num }">${prd.pro_name }</a></h3>
				<fmt:formatNumber var="price" value="${prd.pro_price}" pattern="#,###"/>
				<p>${price }</p>
			</div>
		</div>
		
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
					<th>SCORE</th>
					<td>
						<div class="starRev">
						  <span class="starR on">1</span>
						  <span class="starR on">2</span>
						  <span class="starR on">3</span>
						  <span class="starR on">4</span>
						  <span class="starR on">5</span>
						  (<span id="star_text">5</span>)
						  <input type="hidden" name="score" id="score" value="5"/>
						</div>
					</td>
				</tr>
				<tr class="editor">
					<th colspan="2" style="padding:0px;">
						<textarea name="content" id="summernote"></textarea>
					</th>
				</tr>
				
				<tr>
					<th>ATTACH FILE</th>
					<td><input type="file" name="rev_photo" accept="image/gif, image/jpeg, image/png"></td>
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