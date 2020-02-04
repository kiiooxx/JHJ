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
<script src="http://code.jquery.com/jquery-latest.js"></script>
<script>
// html dom 이 다 로딩된 후 실행된다.
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
	});
	
	//카테고리 이름 클릭했을때
	$("[id^=index]").on('click', function(event){
		var id = $(this).attr("id")
		var num = id.replace("index", "");
		
		//클릭한 소분류의 값..
		var txt = $('#'+id).text().trim();
		$('#cate_name_info').val(txt);
		
		$('#large_hidden').hide(); 
		$('#sub_hidden').hide();
		$('#info_hidden').show();
		
		//클릭한 대분류의 값
		var lg = $('#'+id+'>#lg').val();
		alert(lg);
		$('#select_large_info').val(lg).prop("selected", true);
		
	});
	
	
	//'대분류 추가' 클릭할시에
	$('.large_add').click(function(){
		var state = $('#large_hidden').css('display');
		if(state == 'none'){ 
			$('#large_hidden').show(); 
			$('#sub_hidden').hide();
			$('#info_hidden').hide();
		}
	});
	
	//'소분류 추가' 클릭할시에
	$('.sub_add').click(function(){
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
.menu a{cursor:pointer;}
[class*='hide']{display:none; margin-left:15px;}
#large_hidden {display :none;}
#sub_hidden {display :none;}
#info_hidden {display:none;}
ul {
	list-style : none;
}
</style>
<title>Insert title here</title>
</head>
<body>

<div id="categoryArea">
	
	<div id="categoryList">
		<h3>카테고리 목록</h3>
		<form name="categorylistForm" action="categoryManagement.pro" method="post">
		<!-- 목록 누르면 옆에 분류명에 이름 뜸 -->
		<div class="cate_list">
			<ul>
				<!-- 대분류 -->
				<!-- 버튼 누르면 옆에 추가버튼 생성 됨 -->
				<c:forEach var="list" items="${categoryList }" varStatus="i">
					<c:choose>
						<c:when test="${list.ca_lev == 0}">
							<!-- 대분류 -->
							<li class="menu">
								<span>
									<a href="#" id="img${i.index }"><img src="layout_image/cate_default.png"></a>
									<a href="#" class="large" id="index${i.index }">
										<!-- 대분류 값을 저장한다.. -->
										<c:set var="large" value="${i.index }"/>
										${list.category }
									</a>
								</span>			
							</li>
						</c:when>
						
						<c:otherwise>
							<!-- 소분류 -->
							<!-- 안보이다가 대분류 클릭하면 보여지게/ 저장한 대분류 index값 쓴다 -->
							<ul class="hide${large }">
								<li>
									<span>
										<img src="layout_image/cate_folder.png">
										<a href="#" class="sub" id="index${i.index }">
											<!-- 저장한 대분류값을 불러온다 -->
											<input type="hidden" id="lg" value="${large+1 }"/>
											<input type="hidden" id="sb" value="${list.category }"/>
											${list.category }
										</a>
									</span>
								</li>
							</ul>
						</c:otherwise>
					</c:choose>
				</c:forEach>
			</ul>
		</div>
		<div class="cate_bottom">
			<a href="#" class="large_add">대분류 추가</a>
			<a href="#" class="sub_add">소분류 추가</a>
		</div>
		</form>
	</div>
	
	<div id="setTable">
		<div id="large_hidden">
			<h3>대분류추가</h3>
			<form name="categorySetForm1" action="categoryAddAction.pro" method="post">
				<table>
					<tr>
						<th>카테고리명</th>
						<td>
							<input type="hidden" name="index" id="index" value="${index }"/>
							<input type="text" name="cate_name" id="cate_name" required/>
							<a href="javascript:categorySetForm1.submit()" class="add_btn">추가</a>
						</td>
					</tr>
				</table>
			</form>
		</div>
		
		<div id="sub_hidden">
			<h3>소분류 추가</h3>
			<form name="categorySetForm2" action="categoryAddAction.pro" method="post">
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
							<a href="javascript:categorySetForm2.submit()" class="add_btn">추가</a>
						</td>
					</tr>
				</table>
			</form>
		</div>
		
		
		<!-- 카테고리 이름 누르면 옆에 정보가 뜨는 창 -->
		<!-- 수정, 삭제 기능도 추가할것 (삭제 누르면 카테고리 내용도 전부 삭제되야하고, 수정누르면 상품 카테고리 이름 전부 변경되어야함...) -->
		<div id="info_hidden">
			<h3>분류 정보</h3>
			<form name="categorySetForm" action="categoryAddAction.pro" method="post">
				<table>
					<tr>
						<th>대분류명</th>
						<td>		
							<select name="cate_large" id="select_large_info">
								<c:forEach var="list" items="${categoryList }" varStatus="i">
									<c:if test="${list.ca_lev == 0}">
										<option value="${i.index+1 }">${list.category }</option>
									</c:if>
								</c:forEach>
							</select>
						</td>
					</tr>
					<tr>
						<th>카테고리명</th>
						<td><input type="text" name="cate_name" id="cate_name_info" required/></td>
					</tr>
					<tr>
						<td><a href="javascript:categorySetForm2.submit()" class="add_btn">수정</a></td>
						<td><a href="javascript:categorySetForm2.submit()" class="add_btn">삭제</a></td>
					</tr>
				</table>
			</form>
		</div>
	</div>
</div>
</body>
</html>