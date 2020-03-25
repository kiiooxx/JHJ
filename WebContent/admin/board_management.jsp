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
	$('#del').on('click', function() {
		if($('input[name=chk]').is(":checked") == false){
			alert("게시글번호를 선택하세요.");
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
	
	//공지 등록 버튼 눌렀을 때
	$('#notice_add').on('click', function() {
		if($('input[name=chk]').is(":checked") == false){
			alert("게시글번호를 선택하세요.");
			return false;
		}else {
			var items=[];
			$('input[name=chk]:checkbox:checked').each(function(){items.push($(this).val());});
			var tmp = items.join(',');
			location.href='boardNoticeAction.bo?board_num='+tmp+"&board_notice=Y";
		}
	});

});
</script>
<style>
	th {background : #F6F6F6;}
	.col {margin-bottom : 40px;}
	#pageList {text-align : center;}
	#bottom>a {
		margin-right : 20px;
	}
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
						<tr>
							<th>공지등록 여부</th>
							<td>
								<input type="radio" name="board_notice" value="all" checked/>전체보기&nbsp;&nbsp;
								<input type="radio" name="board_notice" value="Y"/>등록&nbsp;&nbsp;
								<input type="radio" name="board_notice" value="N"/>미등록
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
							<th>조회수</th>
							<th>작성일</th>
						</tr>
			
						<!-- 상품 리스트 -->
						<c:forEach var="list" items="${boardList }" varStatus="i">
							<tr>
								<td><input type="checkbox" name="chk" value="${list.board_num }"/></td>
								<td>
									${list.board_num }
									${list.board_notice == 'Y' ? '[공지]' : '' }
								</td>
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
								
								<td>
									<c:if test="${list.board_num != list.board_ref }">
										└>[답변] : 
									</c:if>
									<a href="javascript:void(0);" onclick="window.name='opener'; window.open('boardViewAction.bo?path=/admin/board_detail&board_num=${list.board_num}&pro_num=${list.pro_num }', 'view', 'width=700, height=500')">
										${list.board_title }
									</a>
								</td>
								<td>
									<c:choose>
										<c:when test="${list.board_step eq '' || list.board_notice == 'Y'}">
											-
										</c:when>
										<c:otherwise>
										${list.board_step }
										</c:otherwise>
									</c:choose>
								</td>
								<td>
									<c:if test="${list.board_step == 'N' && list.board_notice == 'N'}">
										<a href="javascript:void(0);" onclick="window.open('boardViewAction.bo?path=/admin/board_answer_form&board_num=${list.board_num}&pro_num=${list.pro_num }', ' ', 'width=800, height=800')">답변하기</a>
									</c:if>
								</td>
								<td>${list.board_writer }</td>
								<td>${list.board_hits }</td>
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
						<a href="#" id="notice_add">공지등록</a>
						<a href="#" id="notice_del">공지해제</a>
					</div>
				</div>
			</div>
		</form>
	</div>
</div>
</body>
</html>