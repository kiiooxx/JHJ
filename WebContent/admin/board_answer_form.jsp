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
<title>게시판 답글</title>

<!-- css -->
<jsp:include page="/resources/css.jsp"/>

<!-- js -->
<jsp:include page="/resources/js.jsp"/>

<!-- summernote -->
<jsp:include page="/resources/summernote.jsp"/>

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

	//제목 글자수 제한
	$('#board_re_title').on('keyup', function () {
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
	alert('답변이 등록되었습니다!');
	opener.parent.location.reload();
	self.close();
}
</script>
</head>
<body>

<div id="join_form">

	<!-- QnA게시판  -->
	<c:if test="${board.board_type == 'qna' }">	
		<div class="join_table">
			<table>
				<tr>
					<th>SUBJECT</th>
					<td>${board.board_title }</td>
				</tr>
				<tr>
					<th>WRITER</th>
					<td>${board.board_writer }</td>
				</tr>
				<tr>
					<th>문의구분</th>
					<td>
						<c:choose>
								<c:when test="${board.qna_type == 'product_qna'}">
									[상품문의]
								</c:when>
								<c:when test="${board.qna_type == 'delivery_qna' }">
									[배송문의]
								</c:when>
								<c:otherwise>
									[기타문의]
								</c:otherwise>
							</c:choose>
					</td>
				</tr>
				<tr>
					<th>공개여부</th>
					<td>
						<c:choose>
							<c:when test="${board.qna_open == 'Y' } ">
								공개
							</c:when>
							<c:otherwise>
								비공개
							</c:otherwise>
						</c:choose>
					</td>
				</tr>
				<tr>
					<th>E-MAIL</th>
					<td>${board.qna_email }</td>
				</tr>
				<tr>
					<th>답변여부</th>
					<td>${board.board_step }</td>
				</tr>
				<tr class="editor">
					<th colspan="2">
						<c:if test="${!(board.board_photo eq null || board.board_photo eq '' )}">
							<img src="<%= request.getContextPath() %>/upload/${board.board_photo }"><br>
						</c:if>
						${board.board_content }
					</th>
				</tr>
				
			</table>
		</div>
	</c:if>
	
	<!-- 리뷰게시판 -->
	<c:if test="${board.board_type=='review' }">
		<div class="join_table">
			<table>
				<tr>
					<th>SUBJECT</th>
					<td>${board.board_title }</td>
				</tr>
				<tr>
					<th>WRITER</th>
					<td>${board.board_writer }</td>
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
						</div>
					</td>
				</tr>
				<tr class="editor">
					<th colspan="2">
						<c:if test="${!(board.board_photo eq null || board.board_photo eq '' )}">
							<img src="<%= request.getContextPath() %>/upload/${board.board_photo }"><br>
						</c:if>
						${board.board_content }
					</th>
				</tr>
				
			</table>
		</div>
	</c:if>
	
	<div style="margin-bottom:30px"></div>
	
	<form action="boardRegistAction.bo" method="post" name="f" enctype="multipart/form-data">
		<!-- 로그인한 아이디, 관련글 번호와 게시판 번호를 넘긴다. -->
		<input type="hidden" name="board_type" value="answer"/>
		<input type="hidden" name="board_num" value="${board.board_num }"/>
		<input type="hidden" name="board_ref" value="${board.board_ref }"/>
		<input type="hidden" name="board_writer" value="${id }"/>
		<div class="join_table">
			<h1>답글</h1>
			<table>
				<tr>
					<th>SUBJECT</th>
					<td><input type="text" name="board_title" id="board_title" style="width:60%">(<span id="lengthCheck">0</span>/100)</td>
				</tr>
				<tr class="editor">
					<th colspan="2" style="padding:0px;">
						<textarea name="board_content" id="summernote" style="width:100%; height:200px;"></textarea>
					</th>
				</tr>
				<tr>
					<th>ATTACH FILE</th>
					<td><input type="file" name="board_photo" accept="image/gif, image/jpeg, image/png"></td>
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