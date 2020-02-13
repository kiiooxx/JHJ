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
<script src="https://code.jquery.com/jquery-3.4.1.min.js"></script>
<script src="https://unpkg.com/ionicons@5.0.0/dist/ionicons.js"></script>
    
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
</script>

<script>
var color_array = [];	//컬러 저장하는 배열
var size_array = [];	//사이즈 저장하는 배열

function keyEvent_color() {
	var flug = true;
	var keycode = event.keyCode;
	var cnt = 0;
	//세미콜론(;) , 엔터, tab 키 눌렀을 때 반응
	if(keycode == 186 || keycode == 13 || keycode == 9) {
		if($('#color').val()=='') {
			return false;
		}
		//중복확인
		for(var i in color_array) {
			if(color_array[i] == $('#color').val()) {
				flug = false;
			}
			cnt = i+1;
		}
		
		//중복 값 없으면
		if(flug) {
			color_array = color_array.concat($('#color').val());
			var html = '';
			html += '<span id="color_' + cnt + '" class="bg-warning"><a href="#" id="color' + cnt + '">' + $('#color').val() + '</a><a href="#" id="del_color'+ cnt +'">[X]</a></span> &nbsp;';		
			$('#color_append').append(html);
		}
		
		$('#color').val('');
	}
}

function keyEvent_size() {
	var flug = true;
	var cnt = 0;
	var keycode = event.keyCode;
	if(keycode == 186 || keycode == 13 || keycode == 9) {
		if($('#size').val()=='') {
			return false;
		}
		for(var i in size_array) {
			if(size_array[i] == $('#size').val()) {
				flug = false;
			}
			var cnt = i+1;
		}
		if(flug) {
			size_array = size_array.concat($('#size').val());
			var html = '';
			html += '<span id="size_' + cnt + '" class="bg-warning"><a href="#" id="size' + cnt +'">' + $('#size').val() + '</a><a href="#" id="del_size'+ cnt +'">[X]</a></span> &nbsp;';	
			$('#size_append').append(html);
		}
		
		$('#size').val('');
	}
}

$(document).on("click", '[id^=del_color]', function() {
	var id = $(this).attr("id")
	var num = id.replace("del_color", "");
	
	var txt = $('#color' + num).text();
	color_array.splice(color_array.indexOf(txt),1);
	$('#color_' + num).remove();
	
	return false;
});

$(document).on("click", '[id^=del_size]', function() {
	var id = $(this).attr("id")
	var num = id.replace("del_size", "");
	
	var txt = $('#size' + num).text();
	size_array.splice(size_array.indexOf(txt),1);
	$('#size_' + num).remove();
	
	return false;
});

$(document).on("click", '[id^=del_option]', function() {
	var id = $(this).attr("id")
	var num = id.replace("del_option", "");
	
	$('#option' + num).remove();
	
	return false;
});

$(document).ready(function(){
	//옵션품목 만들기 버튼 누르면
	$('#addTable').on('click', function(event){
		alert(color_array);
		if(color_array == null || color_array == '') {
			alert("색상을 입력해주세요.");
			return false;
		}
		
		if(size_array == null || size_array == '') {
			alert("사이즈를 입력해주세요.")
			return false;
		}
		
	    $('#optionChk').val('1');
	
		
		// 새로운 list를 만들어서 list값에다 c, s값 저장
		var list = new Array();
		for (var i in color_array) {
			for(var j in size_array) {
				list.push({color : color_array[i], size : size_array[j]});
			}
		}
		
		//배열의 크기만큼 테이블 동적으로 추가한다.
		var html = '';
		var html2 = '';
		for(key in list) {
			html += '<tr id="option'+ key +'">';
			html += '<td>' + list[key].color + '</td>';
			html += '<td>' + list[key].size + '</td>';
			html += '<td>' + '<input type="number" min="0" name="stock" id="stock" required/>';
			html += '<a href="#" id="del_option' + key + '">삭제</a>';
			html += '</td>';
			
			html += '<input type="hidden" name="pro_color" id="pro_color" value="'+ list[key].color +'"/>';
			html += '<input type="hidden" name="pro_size" id="pro_size" value="'+ list[key].size +'"/>';
			html += '</tr>';
		}
		
		$('#optionTable').empty();
		$('#optionTable').append(html);
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
    
    if(f.ca_ref.value.trim() == "none"){
    	alert("카테고리 대분류를 선택해주세요.");
    	f.ca_ref.focus();
    	return false;
    }
    
    if(f.cate_sub.value.trim() == "none"){
    	if($("#cate_sub option").size() > 1) {
    		alert("카테고리 소분류를 선택해주세요.");
        	f.cate_sub.focus();
        	return false;
    	}
    }

    if(f.optionChk.value.trim() != "1"){
    	alert("옵션품목 만들기 버튼을 눌러주세요");
    	return false;
    }
	f.submit();
	
}

//카테고리
$(function() {
	$('#setSelectBox').change(function() {
		var param = 'ca_ref='+$(this).val();
		$.ajax({
			url : '<%=request.getContextPath()%>/subCategoryList',
			dataType : 'json',
			type : 'POST',
			data : param,
			cache: false,
			contentType : 'application/x-www-form-urlencoded; charset=UTF-8',
	        processData: false,
			success : function(data) {
				var html = '<option value="none" selected disabled hidden>--[소분류]--</option>';
				$.each(data, function(index, item){
					var result = '';
					result += index + " : " + item.cate_num;
					html += '<option value="' + item.cate_num + '">' + item.category + '</option>'; 
				})
				$('#cate_sub').empty();
				$('#cate_sub').append(html);
			},
			error : function() {
				console.log("에러");
			}
		});
	});
});

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
#pro_detail {width : 100%;}
table {float : left; width : 100%;}
table th {width : 20%; background : #F6F6F6;}
</style>
<title>Insert title here</title>
</head>
<body>
 <!-- Page Heading -->
 <div class="d-sm-flex align-items-center justify-content-between mb-4">
 	<h1 class="h3 mb-0 text-gray-800">상품 등록</h1>
 </div>
 <!-- Content Row -->
 <div class="row">
 	<div class="col">
		<form action="productRegistAction.ad" name="f" method="post" enctype="multipart/form-data">
			<div class="card shadow mb-4">
                <div class="card-header py-3">
                  <h6 class="m-0 font-weight-bold text-primary">기본 정보</h6>
                </div>
                <div class="card-body">
	                <table class="table table-bordered">
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
              </div>
              
              <div class="card shadow mb-4">
                <div class="card-header py-3">
                  <h6 class="m-0 font-weight-bold text-primary">쇼핑몰 진열 설정</h6>
                </div>
                <div class="card-body">
                	<table class="table table-bordered">
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
								<input type="radio" name="main_nb" id="main_nb" value="X"/>메인진열X
								<input type="radio" name="main_nb" id="main_nb" value="B"/>베스트
								<input type="radio" name="main_nb" id="main_nb" value="N" checked/>신상품
							</td>
						</tr>
						<tr>
							<th>상품 분류</th>
							<td>
								<div class="row">
								<div class="col-sm-5">			
									<!-- 카테고리... -->
									<select name="ca_ref" id="setSelectBox" class="form-control">
										<option value="none" selected disabled hidden>--[대분류]--</option>
										<c:forEach var="clist" items="${categoryList }" varStatus="i">
											<c:if test="${clist.ca_lev == 0 }">
												<option value="${clist.cate_num }">${clist.category }</option>
											</c:if>
										</c:forEach>
									</select>
									</div>
									<div class="col-sm-5">	
									<select name="cate_sub" id="cate_sub" class="form-control">
										<option value="none" selected disabled hidden>--[소분류]--</option>
									</select>
									</div>
									</div>
							</td>
						</tr>	
					</table>
                </div>
              </div>
              
              <div class="card shadow mb-4">
                <div class="card-header py-3">
                  <h6 class="m-0 font-weight-bold text-primary">옵션 설정</h6>
                </div>
                <div class="card-body">
                	<table class="table table-bordered">
						<tr>
							<th>색상</th>
							<td>
								<!-- 추가버튼 누르면 텍스트 입력 추가되도록....여러개들어가니까 배열.. -->
								<input type="text" name="color" id="color" onkeydown="javascript:keyEvent_color(this);"/>
								<span id="color_append">
								
								</span>
								<br>(세미콜론(;),엔터,탭키로 구분)
							</td>
						</tr>
						<tr>
							<th>사이즈</th>
							<td>
								<!-- 추가버튼 누르면 텍스트 입력 추가되도록...... -->
								<input type="text" name="size" id="size" onkeydown="javascript:keyEvent_size(this);"/>
								
								<span id="size_append">
									
								</span>
								<br>(세미콜론(;),엔터,탭키로 구분)
								<br>
								<!-- 누르면 밑에 재고 설정 테이블 생성 -->
								<a href="#" id="addTable" class="btn btn-primary">옵션품목 만들기
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
									<table class="table table-bordered">
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
             </div>
			<div>
				<a href="javascript:chkForm(document.f);" class="btn btn-primary btn-lg btn-block">등록</a>&nbsp;&nbsp;
			</div>
		</form>
	</div>
</div>
</body>
</html>