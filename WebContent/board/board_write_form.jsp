<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="sql" uri="http://java.sun.com/jsp/jstl/sql"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

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
	
	//리뷰 평점
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
</script>

<jsp:include page="/common/loginCheck.jsp"/>
<div class="blank">
</div>

<div id="join_form">
	
	<form action="boardRegistAction.bo" method="post" name="f" enctype="multipart/form-data">
	<c:if test="${prd != null }">
		<input type="hidden" name="pro_num" value="${prd.pro_num }"/>
		<!-- 상품 정보 -->
		<div class="prd_info">
			<p class="prdThumb">
				<a href="productDetail.pro?pro_num=${prd.pro_num }"><img src="<%= request.getContextPath() %>/upload/${prd.pro_photo }"></a>
			</p>
			<div class="prd_name">
				<h3><a href="productDetail.pro?pro_num=${prd.pro_num }">${prd.pro_name }</a></h3>
				<fmt:formatNumber var="price" value="${prd.pro_price}" pattern="#,###"/>
				<p>${price }</p>
			</div>
		</div>
	</c:if>
		<input type="hidden" name="sel_num" value="${sel_num }"/>
		<input type="hidden" name="board_type" value="${board_type }"/>
		<input type="hidden" name="board_writer" value="${id }"/>
		<div class="join_table">
			<table>
				<!-- 공지사항 -->
				<c:if test="${board_type == 'notice' }">
				<h1>NOTICE WRITE</h1>
					<tr>
						<th>SUBJECT</th>
						<td><input type="text" name="board_title" id="board_title" style="width:60%">(<span id="lengthCheck">0</span>/100)</td>
					</tr>
				</c:if>
				
				<!-- QnA -->
				<c:if test="${board_type == 'qna' }">
				<h1>QnA WRITE</h1>
					<tr>
						<th>SUBJECT</th>
						<td><input type="text" name="board_title" id="board_title" style="width:60%">(<span id="lengthCheck">0</span>/100)</td>
					</tr>
					<tr>
						<th>WRITER</th>
						<td><input type="text" name="board_writer" value="${id }" readonly></td>
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
							<input type="radio" name="qna_open" value="Y" checked>공개&nbsp;&nbsp;
							<input type="radio" name="qna_open" value="N">비공개
						</td>
					</tr>
					<tr>
						<th>E-MAIL</th>
						<td><input type="text" name="qna_email" id="qna_email" required /></td>
					</tr>
				</c:if>
				
				<!-- 리뷰게시판 -->
				<c:if test="${board_type == 'review' }">
				<h1>REVIEW WRITE</h1>
					<tr>
						<th>SUBJECT</th>
						<td><input type="text" name="board_title" id="board_title" style="width:60%">(<span id="lengthCheck">0</span>/100)</td>
					</tr>
					<tr>
						<th>WRITER</th>
						<td><input type="text" name="board_writer" value="${id }" readonly></td>
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
							  <input type="hidden" name="review_score" id="score" value="5"/>
							</div>
						</td>
					</tr>
				</c:if>
				
				<!-- 공지등록 여부 -->
				<c:if test="${grade == 'A'.charAt(0) }">
					<tr>
						<th>공지사항</th>
						<td><input type="radio" name="board_notice" value="Y">등록&nbsp;&nbsp;
							<input type="radio" name="board_notice" value="N" checked>미등록
						</td>
					</tr>
				</c:if>
				
					<tr class="editor">
						<th colspan="2" style="padding:0px;">
							<textarea name="board_content" id="summernote">
								<c:if test="${board_type == 'qna' }">
									성함 : <br>
									연락처 : <br>
									문의 내용 : <br>
								</c:if>
							</textarea>
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