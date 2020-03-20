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
<title>Insert title here</title>
<script src="http://code.jquery.com/jquery-latest.js"></script>
<script src="https://unpkg.com/ionicons@5.0.0/dist/ionicons.js"></script>

<script>
window.name="openPage";
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

$(document).ready(function() {
	//전체 선택 체크박스 눌렀을 때
	$('#checkall').click(function() {
		if($('#checkall').prop("checked")) {
			$('input[name=chk]').prop('checked', true);
		}else {
			$('input[name=chk]').prop('checked', false);
		}
	});
	
	
	//삭제 버튼 눌렀을 때
	$('#del').click(function() {
		if($('input[name=chk]').is(":checked") == false){
			alert("상품번호를 선택하세요.");
			return false;
		}else {
			if(confirm('정말 삭제하시겠습니까?')) {
				var items=[];
				$('input[name=chk]:checkbox:checked').each(function(){items.push($(this).val());});
				var tmp = items.join(',');
				location.href='boardDeleteAction.bo?board_num='+tmp;
			}else {
				return false;
			}
		}
	});
	
	//진열 select 변경했을 때
	$('#activeBox').change(function() {
		if($('input[name=chk]').is(":checked") == false){
			alert("상품번호를 선택하세요.");
			return false;
		}else {
			var items=[];
			$('input[name=chk]:checkbox:checked').each(function(){items.push($(this).val());});
			var tmp = items.join(',');
			var select = $('select[name=activeBox]').val();
			location.href='productListUpdate.ad?pro_num='+tmp+'&active='+select;
		}
	});
	
	//메인상품진열 수정
	$('#main_nb').change(function() {
		if($('input[name=chk]').is(":checked") == false){
			alert("상품번호를 선택하세요.");
			return false;
		}else {
			var items=[];
			$('input[name=chk]:checkbox:checked').each(function(){items.push($(this).val());});
			var tmp = items.join(',');
			var select = $('select[name=main_nb]').val();
			location.href='productListUpdate.ad?pro_num='+tmp+'&main_nb='+select;
		}
	});
	
});
</script>
<style>
	th {background : #F6F6F6;}
	.col {margin-bottom : 40px;}
	#pageList {text-align : center;}
</style>
</head>
<body>
<!-- Page Heading -->
 <div class="d-sm-flex align-items-center justify-content-between mb-4">
 	<h1 class="h3 mb-0 text-gray-800">상품 목록</h1>
 </div>
 <!-- Content Row -->
 <div class="row">
 	<div class="col">
		<form action="boardManagementForm.ad" name="boardSearch" method="post">
			<div class="card card-default">
				<div class="card-body">
					<table class="table table-bordered">
						<tr>
							<th>기간</th>
							<td>
								<input type="radio" name="board_date" value="-0"/>오늘
								<input type="radio" name="board_date" value="-3"/>3일
								<input type="radio" name="board_date" value="-7"/>7일
								<input type="radio" name="board_date" value="-30"/>1개월
								<input type="radio" name="board_date" value="all" checked/>전체
							</td>
						</tr>
						<tr>
							<th>게시판 선택</th>
							<td>
								<select name="board_type">
									<option value="all" selected>전체 게시물</option>
									<option value="notice">공지사항</option>
									<option value="review">리뷰게시판</option>
									<option value="qna">QnA게시판</option>
								</select>
							</td>
						</tr>
						<tr>
							<th>게시글 찾기</th>
							<td>
								<select name="search_type">
									<option value="board_title">제목</option>
									<option value="board_content">내용</option>
									<option value="board_writer">작성자</option>
									<option value="pro_num">상품명</option>
								</select>
								<input type="text" name="search_text"/>
							</td>
						</tr>
						<tr>
							<th>답변상태</th>
							<td>
								<input type="radio" name="board_step" value="all" checked/>전체보기&nbsp;&nbsp;
								<input type="radio" name="board_step" value="N"/>답변 전&nbsp;&nbsp;
								<input type="radio" name="board_step" value="Y"/>답변 완료
							</td>
						</tr>
						<tr>
							<th>첨부파일 여부</th>
							<td>
								<input type="radio" name="board_photo" value="all" checked/>전체보기&nbsp;&nbsp;
								<input type="radio" name="board_photo" value="Y"/>있음&nbsp;&nbsp;
								<input type="radio" name="board_photo" value="N"/>없음
							</td>
						</tr>
					</table>
					
					<div align="center">
						<a href="javascript:boardSearch.submit()" class="btn btn-primary">검색</a>
						<a href="javascript:boardSearch.reset()" class="btn btn-primary">초기화</a>
					</div>
				</div>
			</div>
		</form>
	</div>
</div>
 <div class="row">
 	<div class="col">
		<form action="" name="" method="post">
			<div class="card card-default">
				<div class="card-body">
					<table class="table table-bordered">
						<tr>
							<th><input type="checkbox" id="checkall"/></th>
							<th>번호</th>
							<th>분류</th>
							<th>제목</th>
							<th>답변상태</th>
							<th>답변하기</th>
							<th>작성자</th>
							<th>작성일</th>
						</tr>
			
						<!-- 상품 리스트 -->
						<c:forEach var="list" items="${boardList }" varStatus="i">
							<tr>
								<td><input type="checkbox" name="chk" value="${list.board_num }"/></td>
								<td>${list.board_num }</td>
								<td>
										<c:if test="${list.board_type == 'notice'}">
											공지사항
										</c:if>
										<c:if test="${list.board_type == 'review' }">
											리뷰게시판
										</c:if>
										<c:if test="${list.board_type == 'qna' }">
											문의게시판
										</c:if>
								</td>
								
								<td><a href="javascript:void(0);" onclick="window.open('boardView.ad?path=board_view&board_num=${list.board_num}&pro_num=${list.pro_num }', ' ', 'width=700, height=500')">
										${list.board_title }
									</a>
								</td>
								<td>
									<c:if test="${list.board_type == 'notice' }">
										-
									</c:if>
									${list.board_step }
								</td>
								<td>
									<c:if test="${list.board_type != 'notice' }">
										<a href="javascript:void(0);" onclick="window.open('boardView.ad?path=board_answer_form&board_num=${list.board_num}&pro_num=${list.pro_num }', ' ', 'width=600, height=800')">답변하기</a>
									</c:if>
								</td>
								<td>${list.board_writer }</td>
								<td>${list.board_date }</td>
							</tr>
						</c:forEach>
					</table>
					
					<div id="pageList">
						<c:choose>
							<c:when test="${pageInfo.page <= 1 }">
								<ion-icon name="chevron-back-outline"></ion-icon>
							</c:when>
							<c:otherwise>
								<a href="boardManagementForm.ad?board_date=${board_date }&board_type=${board_type }&search_type=${search_type }&search_text=${search_text }&board_step=${board_step }&board_photo=${board_photo }&page=${pageInfo.page-1 }"><ion-icon name="chevron-back-outline"></ion-icon></a>
							</c:otherwise>
						</c:choose>
							
							
						<c:forEach var="pglist" begin="${pageInfo.startPage }" end="${pageInfo.endPage }" step="1" varStatus="a">
							<c:choose>
								<c:when test="${a.count == pageInfo.page }">
									[${a.count }]
								</c:when>
								<c:otherwise>
									<a href="boardManagementForm.ad?board_date=${board_date }&board_type=${board_type }&search_type=${search_type }&search_text=${search_text }&board_step=${board_step }&board_photo=${board_photo }&page=${a.count }">[${a.count }]</a>
								</c:otherwise>
							</c:choose>
						</c:forEach>
							
							
						<c:choose>
							<c:when test="${pageInfo.page>=pageInfo.maxPage }">
								<ion-icon name="chevron-forward-outline"></ion-icon>
							</c:when>
							<c:otherwise>
								<a href="boardManagementForm.ad?board_date=${board_date }&board_type=${board_type }&search_type=${search_type }&search_text=${search_text }&board_step=${board_step }&board_photo=${board_photo }&page=${pageInfo.page+1 }"><ion-icon name="chevron-forward-outline"></ion-icon></li>
							</c:otherwise>
						</c:choose>
					</div>
					
					<div id="bottom">
						<a href="#" id="del">삭제</a>
					</div>
				</div>
			</div>
		</form>
	</div>
</div>
</body>
</html>