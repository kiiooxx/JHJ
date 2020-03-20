<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!-- summernote -->
<jsp:include page="/resources/summernote.jsp"/>

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
	$('#board_title').on('keyup', function () {
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
<jsp:include page="/common/loginCheck.jsp"/>
<div id="join_form">
	<form action="boardModifyAction.bo" name="f" method="post" enctype="multipart/form-data">
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
			<input type="hidden" name="board_num" value="${board.board_num }"/>
			<input type="hidden" name="board_type" value="${board.board_type }"/>
			<table>
				<tr>
					<th>SUBJECT</th>
					<td><input type="text" name="board_title" id="board_title" style="width:60%" value="${board.board_title }">(<span id="lengthCheck">0</span>/100)</td>
				</tr>
				<tr>
					<th>WRITER</th>
					<td><input type="text" name="board_writer" value="${board.board_writer }" readonly></td>
				</tr>
				<tr>
					<th>SCORE</th>
					<td>
						<div class="starRev">
						  <span class="starR ${board.review_score >= 1 ? 'on' : ''}">1</span>
						  <span class="starR ${board.review_score >= 2 ? 'on' : ''}">2</span>
						  <span class="starR ${board.review_score >= 3 ? 'on' : ''}">3</span>
						  <span class="starR ${board.review_score >= 4 ? 'on' : ''}">4</span>
						  <span class="starR ${board.review_score >= 5 ? 'on' : ''}">5</span>
						  (<span id="star_text">${board.review_score }</span>)
						  <input type="hidden" name="review_score" id="score" value="${board.review_score }"/>
						</div>
					</td>
				</tr>
				<tr class="editor">
					<th colspan="2" style="padding:0px;">
						<textarea name="board_content" id="summernote">${board.board_content }</textarea>
					</th>
				</tr>
				
				<tr>
					<th>ATTACH FILE</th>
					<td>
						<p id="file_info">
							${board.board_photo }
							<input type="hidden" name="board_photo2" value="${board.board_photo }"/>
							<input type="button" value="수정" onclick="file_modify()"/>
						</p>
						<p id="file_add">
							<input type="file" name="board_photo" accept="image/gif, image/jpeg, image/png">
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
