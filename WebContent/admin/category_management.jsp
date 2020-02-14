<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="sql" uri="http://java.sun.com/jsp/jstl/sql"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8" name="viewport" content="width=device-width, initial-scale=1">
<script src="http://code.jquery.com/jquery-latest.js"></script>
<script src="https://unpkg.com/ionicons@5.0.0/dist/ionicons.js"></script>

<script>
// html dom 이 다 로딩된 후 실행된다.
$(document).ready(function(){
	
	//대분류 이미지 클릭했을때
	$("[id^=img]").on('click', function(event){
		var id = $(this).attr("id")
		var num = id.replace("img", "");
		
		var id2 = '.hide' + num;
		var submenu = $(id2);
		//소분류값 있을때만
		if(!(submenu.text() == "" || submenu.text() == null || submenu.text() == undefined)) {
			
			var src = ($('#'+id+'>ion-icon').attr('name') == 'folder-outline') ? 'folder-open-outline' : 'folder-outline';
			$('#'+id+'>ion-icon').attr('name', src);
			
			// submenu 가 화면상에 보일때는 위로 보드랍게 접고 아니면 아래로 보드랍게 펼치기
			if( submenu.is(":visible") ){
				submenu.slideUp();
			}else{
				submenu.slideDown();
			}
		}
		
		
	});
	
	//카테고리 이름 클릭했을때
	$("[id^=index]").on('click', function(event){
		var id = $(this).attr("id");	//index + num
		var num = id.replace("index", "");
		
		var txt = $('#'+id).text().trim();	//클릭한 카테고리 이름의 값

		var ref = $('#ref'+num).val();
		if(!(ref == "" || ref == null || ref == undefined)) {
			$('#info_select_large').val(ref).prop("selected", true);
		}else {
			$('#info_select_large').val(num).prop("selected", true);
		}
		
		/* $('#info_select_large').val(num).prop("selected", true);
		alert($("#info_select_large option:selected").text()); */

		$('#info_cate_name').val(txt);	//카테고리명 text값
		$('#info_cate_num').val(num);	//카테고리번호
		
		$('#large_hidden').hide(); 
		$('#sub_hidden').hide();
		$('#info_hidden').show();
		
		$('[id^=del_icon]').hide();
		$('#del_icon'+num).show();
		
	});
	
	//삭제 아이콘 눌렀을때
	$("[id^=del_icon]").click(function(){
		var id = $(this).attr("id");
		var num = id.replace("del_icon", "");

		if(confirm('삭제 시 하위 카테고리 내용도 전부 삭제됩니다. 정말 삭제하시겠습니까?')) {
			location.href='categoryDelAction.ad?cate_num='+num;
		}else {
			return false;
		}
	});
	
	//'대분류 추가' 클릭할시에
	$('#large_add').click(function(){
		var state = $('#large_hidden').css('display');
		if(state == 'none'){ 
			$('#large_hidden').show(); 
			$('#sub_hidden').hide();
			$('#info_hidden').hide();
		}
	});
	
	//'소분류 추가' 클릭할시에
	$('#sub_add').click(function(){
		var state = $('#sub_hidden').css('display');
		if(state == 'none'){ 
			$('#large_hidden').hide(); 
			$('#sub_hidden').show();
			$('#info_hidden').hide();
		}
	});
});
</script>
<style>
a {
color : #343a40;
}
.menu a{cursor:pointer;}
[class*='hide']{display:none;}
#large_hidden {display :none;}
#sub_hidden {display :none;}
#info_hidden {display:none;}
[id*='del_icon']{display:none;}
ul {
	list-style : none;
}
</style>
<title>Insert title here</title>
</head>
<body>
 <!-- Page Heading -->
 <div class="d-sm-flex align-items-center justify-content-between mb-4">
 	<h1 class="h3 mb-0 text-gray-800">카테고리 관리</h1>
 </div>
 <!-- Content Row -->
 <div class="row">
 	<div class="col">
		<div class="card card-default">
			<div class="card-header">
            	카테고리 관리
          	</div>
			
			<div class="card-body">
				<div class="row">
	              <div class="col-md-6">
	                <div class="form-group">
	                	<label>카테고리 목록</label>
						<form name="categorylistForm" action="categoryManagement.ad" method="post">
						<!-- 목록 누르면 옆에 분류명에 이름 뜸 -->
						<ul>
							<!-- 대분류 -->
							<!-- 버튼 누르면 옆에 추가버튼 생성 됨 -->
							<c:forEach var="list" items="${categoryList }" varStatus="i">
								<c:choose>
									<c:when test="${list.ca_lev == 0}">
										<!-- 대분류 -->
										<li class="menu">
											<span>
												<a href="#" id="img${list.cate_num }"><ion-icon name="folder-outline"></ion-icon></a>
												<a href="#" class="large" id="index${list.cate_num }">${list.category }</a>
												<a href="#" id="del_icon${list.cate_num }"><ion-icon name="close-circle-outline"></ion-icon></a>
											</span>			
										</li>
									</c:when>
										
									<c:otherwise>
										<!-- 소분류 -->
										<ul class="hide${list.ca_ref }">
											<li>
												<span>
													<ion-icon name="folder-outline"></ion-icon>
													<a href="#" class="sub" id="index${list.cate_num }">
														<input type="hidden" id="ref${list.cate_num }" value="${list.ca_ref }"/>
														${list.category }
													</a>
													<a href="#" id="del_icon${list.cate_num }"><ion-icon name="close-circle-outline"></ion-icon></a>
												</span>
											</li>
										</ul>
									</c:otherwise>
								</c:choose>
							</c:forEach>
						</ul>
						</form>
						<div class="cate_bottom">
							<a class="btn btn-default" href="#" role="button" id="large_add">대분류 추가</a>
							<a class="btn btn-default" href="#" role="button" id="sub_add">소분류 추가</a>
						</div>
					</div>
					
				</div>
				
				
				<div class="form-group" id="large_hidden">
					<label>대분류 추가</label>
					<form name="categorySetForm1" action="categoryAddAction.ad" method="post">
						<table>
							<tr>
								<th>카테고리명</th>
								<td>
									<input type="hidden" name="index" id="index" value="${index }"/>
									<input type="text" name="cate_name" id="cate_name" required/>
									<a href="javascript:categorySetForm1.submit()" class="btn btn-default" role="button">추가</a>
								</td>
							</tr>
						</table>
					</form>
				</div>
			
			
				<div class="form-group" id="sub_hidden">
					<label>소분류 추가</label>
					<form name="categorySetForm2" action="categoryAddAction.ad" method="post">
						<table>
							<tr>
								<th>대분류명</th>
								<td>		
									<select name="cate_large">
										<c:forEach var="list" items="${categoryList }" varStatus="i">
											<c:if test="${list.ca_lev == 0}">
												<option value="${list.cate_num }">${list.category }</option>
											</c:if>
										</c:forEach>
									</select>
								</td>
							</tr>
							
							<tr>
								<th>카테고리명</th>
								<td>
									<input type="text" name="cate_name" id="cate_name" required/>
									<a href="javascript:categorySetForm2.submit()" class="btn btn-default" role="button">추가</a>
								</td>
							</tr>
						</table>
					</form>
				</div>
				
				<!-- 카테고리 이름 누르면 옆에 정보가 뜨는 창 -->
				<!-- 수정, 삭제 기능도 추가할것 (삭제 누르면 카테고리 내용도 전부 삭제되야하고, 수정누르면 상품 카테고리 이름 전부 변경되어야함...) -->
				<div class="form-group" id="info_hidden">
					<p>분류 정보</p>
					<form name="categorySetForm3" action="categoryUpdateAction.ad" method="post">
						<table>
							<tr>
								<th>대분류명</th>
								<td>		
									<select name="cate_large" id="info_select_large">
										<c:forEach var="list" items="${categoryList }" varStatus="i">
											<c:if test="${list.ca_lev == 0}">
												<option value="${list.cate_num }">${list.category }</option>
											</c:if>
										</c:forEach>
									</select>
								</td>
							</tr>
							<tr>
								<th>카테고리명</th>
								<td>
									<input type="text" name="cate_name" id="info_cate_name" required/>
									<input type="hidden" name="cate_num" id="info_cate_num"/>
								</td>
								
							</tr>
							<tr>
								<td><a href="javascript:categorySetForm3.submit()" class="btn btn-default" role="button">수정</a></td>
							</tr>
						</table>
					</form>
				</div>
			</div>
		</div>
	</div>
</div>
</div>
</body>
</html>