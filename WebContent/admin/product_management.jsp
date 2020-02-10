<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="sql" uri="http://java.sun.com/jsp/jstl/sql"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="vo.CategoryBean" %>


<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">

<link href="http://netdna.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.css" rel="stylesheet">
<script src="http://cdnjs.cloudflare.com/ajax/libs/jquery/2.1.4/jquery.js"></script> 
<script src="http://netdna.bootstrapcdn.com/bootstrap/3.3.5/js/bootstrap.js"></script> 
	
<link href="dist/summernote.css" rel="stylesheet">
<script src="dist/summernote.js"></script>
    
<script type="text/javascript">
$(document).ready(function() {
	$('#summernote').summernote({ // summernote를 사용하기 위한 선언
		width : 900,
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
      alert(file.name);
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
</script>

<script>
$(document).ready(function(){
	
	//대분류 이미지 클릭했을때
	$("[id^=img]").on('click', function(event){
		var id = $(this).attr("id")
		var num = id.replace("img", "");
		
		var src = ($('#'+id+'>img').attr('src') == 'layout_image/cate_default.png') ? 'layout_image/cate_click.png' : 'layout_image/cate_default.png';
		$('#'+id+'>img').attr('src', src);
		
		var id2 = '.hide' + num;
		var submenu = $(id2);
			
		// submenu 가 화면상에 보일때는 위로 보드랍게 접고 아니면 아래로 보드랍게 펼치기
		if( submenu.is(":visible") ){
			submenu.slideUp();
		}else{
			submenu.slideDown();
		}
		
		//화면이 위로 가는 현상 막아줌.
		return false;
	});
	
	//카테고리 이름 클릭했을때
	$("[id^=index]").on('click', function(event){
		var id = $(this).attr("id")
		var num = id.replace("index", "");
		
		//클릭한 소분류의 값..
		var txt = $('#'+id).text().trim();
		
		var ref = $('#ref'+num).val();
		
		//클릭한 카테고리의 카테고리 번호 값.
		var index = $('#'+id+'>#cate_index').val();
		
		//대분류 값을 클릭한 경우,,
		if(ref == "" || ref == null || ref == undefined) {
			$('#cate_large').val(txt);
			$('#cate_sub').val('');
		}else {
			$('#cate_sub').val(txt);
			$('#cate_large').val(ref);
		}
		
		$('#cate_num').val(num);
		return false;
	});
	
	//옵션품목 만들기 버튼 누르면
	$('#addTable').on('click', function(event){
		if(f.color.value.trim() == ""){
	    	alert("색상을 입력하세요.");
	    	f.color.focus();
	    	return false;
	    }
	    
	    if(f.size.value.trim() == ""){
	    	alert("색상을 입력하세요.");
	    	f.size.focus();
	    	return false;
	    }
	    
	    $('#optionChk').val('1');
	    
		// 입력한 컬러값 받아와서  콤마(,)로 구분해서 배열 'c'로 저장
		var colorStr = $('#color').val();
		var c = colorStr.split(',');
		
		// 입력한 사이즈값 받아와서 콤마(,)로 구분해서 배열 's'로 저장
		var sizeStr = $('#size').val();
		var s = sizeStr.split(',');
		
		// 새로운 list를 만들어서 list값에다 c, s값 저장
		var list = new Array();
		for (var i in c) {
			for(var j in s) {
				list.push({color : c[i], size : s[j]});
			}
		}
		
		//배열의 크기만큼 테이블 동적으로 추가한다.
		var html = '';
		var html2 = '';
		for(key in list) {
			html += '<tr>';
			html += '<td>' + list[key].color + '</td>';
			html += '<td>' + list[key].size + '</td>';
			html += '<td>' + '<input type="number" min="0" name="stock" id="stock" required/></td>';
			html += '</tr>';
			
			html2 += '<input type="hidden" name="pro_color" id="pro_color" value="'+ list[key].color +'"/>';
			html2 += '<input type="hidden" name="pro_size" id="pro_size" value="'+ list[key].size +'"/>';
		}
		
		$('#optionTable').empty();
		$('#optionTable').append(html);
		$('#optionTable').append(html2);
		return false;
	});
});


//입력한 값 없을때 안넘어가게 체크
var chkId = false;
function chkForm(f) {
	
    if (f.pro_name.value.trim() == "") {
        alert("상품이름을 입력해주세요.");
        f.pro_name.focus();
        return false;
    }
    
    if (f.pro_price.value.trim() == ""){
    	alert("상품가격을 입력해 주세요.");
    	f.pro_price.focus();
    	return false;
    	
    }
    
    if(f.pro_detail.value.trim() == ""){
    	alert("상품 간단 설명을 입력해주세요.");
    	f.pro_detail.focus();
    	return false;
    }
    
    if(f.pro_content.value.trim() == "" ){
    	alert("상품 상세 설명을 입력해주세요.");
    	f.pro_content.focus();
    	return false;
    }
    
    var fileCheck = document.getElementById("photo").value;
    if(!fileCheck){
        alert("이미지를 첨부해 주세요");
        return false;
    }
    
    if(f.cate_large.value.trim() == ""){
    	alert("카테고리를 선택해주세요.");
    	f.cate_large.focus();
    	return false;
    }

    if(f.optionChk.value.trim() != "1"){
    	alert("옵션품목 만들기 버튼을 눌러주세요");
    	return false;
    }
	f.submit();
	
}
</script>
<style>
.menu a{cursor:pointer;}
[class*='hide']{display:none; margin-left:15px;}
#large_hidden {display :none;}
#sub_hidden {display :none;}
#info_hidden {display:none;}
ul {
	list-style : none;
}
#optionChk {display:none;}
</style>
<title>Insert title here</title>
</head>
<body>
<div class="headArea">
	<h1>상품 등록</h1>
</div>

<form action="productRegistAction.ad" name="f" method="post" enctype="multipart/form-data">
<div class="contentArea">
	<div class="prdAddForm">
		
		<h3 class="formTitle">기본 정보</h3>
		
			<table>
				<tr>
					<th><label for="pro_name">상품명</label></th>
					<td><input type="text" name="pro_name" id="pro_name" required/></td>
				</tr>
				<tr>
					<th><label for="pro_price">판매가</label></th>
					<td><input type="text" name="pro_price" id="pro_price" numberOnly/></td>
				</tr>
				<tr>
					<th><label for="pro_detail">상품 간단 설명</label></th>
					<td><input type="text" name="pro_detail" id="pro_detail"/></td>
				</tr>
				<tr>
					<th><label for="pro_content">상품 상세 설명</label></th>
					<td>
						<textarea name="pro_content" id="summernote"></textarea>
					</td>
				</tr>
				<tr>
					<th><label for="photo">대표 이미지</label></th>
					<td>
						<input type="file" name="photo" id="photo"/>
					</td>
				</tr>
			</table>
	</div>
	
	<div class="prdAddForm">
		<h3 class="formTitle">쇼핑몰 진열설정</h3>
			<table>
				<tr>
					<th><label for="active">진열상태</label></th>
					<td>
						<input type="radio" name="active" id="active" value="Y" checked/>진열
						<input type="radio" name="active" id="active" value="N"/>진열안함
					</td>
				</tr>
				<tr>
					<th><label for="main_nb">메인진열</label></th>
					<td>
						<input type="radio" name="main_nb" id="main_nb" value="B"/>베스트
						<input type="radio" name="main_nb" id="main_nb" value="N" checked/>신상품
					</td>
				</tr>
				<tr>
					<th>상품 분류</th>
					<td>				
						<!-- 카테고리 -->
						<ul>
							<!-- 대분류 -->
							<!-- 버튼 누르면 옆에 추가버튼 생성 됨 -->
							<c:forEach var="list" items="${categoryList }" varStatus="i">
								<c:choose>
									<c:when test="${list.ca_lev == 0}">
										<!-- 대분류 -->
										<li class="menu">
											<span>
												<a href="#" id="img${list.cate_num }"><img src="layout_image/cate_default.png"></a>
												<a href="#" class="large" id="index${list.cate_num }">${list.category }</a>
												<c:set var="ref" value="${list.category }"/>
											</span>			
										</li>
									</c:when>
									
									<c:otherwise>
										<!-- 소분류 -->
										<ul class="hide${list.ca_ref }">
											<li>
												<span>
													<img src="layout_image/cate_folder.png">
													<a href="#" class="sub" id="index${list.cate_num }">
														<input type="hidden" id="ref${list.cate_num }" value="${ref }"/>
														${list.category }
													</a>
												</span>
											</li>
										</ul>
									</c:otherwise>
								</c:choose>
							</c:forEach>
					</ul>
					대분류 <input type="text" name="cate_large" id="cate_large" readonly/>
					소분류 <input type="text" name="cate_sub" id="cate_sub" readonly/>
					<input type="hidden" name="cate_num" id="cate_num"/>
					</td>
				</tr>
				
			</table>
		
	</div>
	
	<div class="prdAddForm">
		<h3 class="formTitle">옵션 설정</h3>
		<table>
			<tr>
				<th>색상</th>
				<td>
					<!-- 추가버튼 누르면 텍스트 입력 추가되도록....여러개들어가니까 배열.. -->
					<input type="text" name="color" id="color"/>
					(콤마로 구분)
				</td>
			</tr>
			<tr>
				<th>사이즈</th>
				<td>
					<!-- 추가버튼 누르면 텍스트 입력 추가되도록...... -->
					<input type="text" name="size" id="size"/>
					(콤마로 구분)
					&nbsp;
					
					<!-- 누르면 밑에 재고 설정 테이블 생성 -->
					<a href="#" id="addTable">옵션품목 만들기
						<!-- 옵션품목 만들기 버튼 눌렀는지 확인하기 위해 (누르면 value값 0->1로바뀜) -->
						<input type="text" name="optionChk" id="optionChk" required/>
					</a>
				</td>
			</tr>
			<tr>
				<th>재고설정</th>
				<td>
					<!-- 색상, 사이즈 입력한 값 불러와서 테이블로 만들고... 거기에 각 각 재고 입력할 수 있도록... -->
					<div>
						<table>
							<tr>
								<th>색상</th>
								<th>사이즈</th>
								<th>재고</th>
							</tr>
							<tbody id="optionTable">
								
							</tbody>
						</table>
					</div>
				</td>
			</tr>
		</table>
		
	</div>
	
	<div class="jo_btn">
		<a href="javascript:chkForm(document.f);">등록</a>&nbsp;&nbsp;
	</div>
</div>
</form>
</body>
</html>