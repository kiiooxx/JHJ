<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="memberList" value="${requestScope.memberList }"/>
<c:set var="pageInfo" value="${requestScope.pageInfo }"/>

<c:set var="searchType"/>
<c:set var="searchText"/>
<c:set var="searchGrade"/>
<c:set var="startPrice"/>
<c:set var="endPrice"/>
<c:set var="startDate"/>
<c:set var="endDate"/>

<c:if test="${searchType ne null }">
   <c:set var="searchType" value="${requestScope.searchType }"/>
</c:if>
<c:if test="${searchText ne null }">
   <c:set var="searchText" value="${requestScope.searchText }"/>
</c:if>
<c:if test="${searchGrade ne null }">
   <c:set var="searchGrade" value="${requestScope.searchGrade }"/>
</c:if>
<c:if test="${startPrice ne null }">
   <c:set var="startPrice" value="${requestScope.startPrice }"/>
</c:if>
<c:if test="${endPrice ne null }">
   <c:set var="endPrice" value="${requestScope.endPrice }"/>
</c:if>
<c:if test="${startDate ne null }">
   <c:set var="startDate" value="${requestScope.startDate }"/>
</c:if>
<c:if test="${endDate ne null }">
   <c:set var="endDate" value="${requestScope.endDate }"/>
</c:if>



<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>

<script src="https://code.jquery.com/jquery-3.1.1.min.js"></script>
<script type="text/javascript" src="//code.jquery.com/jquery-2.1.1.min.js"></script>
<script src="https://unpkg.com/ionicons@5.0.0/dist/ionicons.js"></script>
<script src="https://code.jquery.com/jquery-1.11.0.min.js"></script>
<script src="http://maxcdn.bootstrapcdn.com/bootstrap/3.2.0/js/bootstrap.min.js"></script>
<script src="http://cdnjs.cloudflare.com/ajax/libs/bootstrap-datepicker/1.3.0/js/bootstrap-datepicker.js"></script>
<script>
//숫자만 입력가능
function onlyNumber(){
    if((event.keyCode<48)||(event.keyCode>57))
       event.returnValue=false;
}

//주문 시작일 종료일 체크
function inputDateComparison(obj){
	var startDate = inputDateSplit(document.getElementById("startDate").value);
	var endDate = inputDateSplit(document.getElementById("endDate").value);
	
	var objDate = inputDateSplit(obj.value);
	
	if(startDate == objDate && startDate > endDate){
		alert("시작일이 종료일보다 이후일 수는 없습니다. \n 다시 선택하여 주시기 바랍니다.");
		obj.value = document.getElementById("endDate").value;
		obj.focus();
	}else if(endDate == objDate && endDate < startDate){
		alert("종료일이 시작일보다 이전일 수는 없습니다. \n 다시 선택하여 주시기 바랍니다.");
		obj.value = document.getElementById("startDate").value;
		obj.focus();
	}else{
		return false;
	}
}

function inputDateSplit(obj){
	var dateArray = obj.split("-");
	return dateArray[0] + dateArray[1] + dateArray[2];
}

//주문 시작금액 종료금액 체크
function inputPriceComparison(obj){
	var startPrice = document.getElementById("startPrice").value;
	var endPrice = document.getElementById("endPrice").value;
	var objPrice = obj.value;
	
	if(endPrice != "" && startPrice == objPrice && startPrice > endPrice){
		alert("시작 금액은 종료 금액보다 클 수 없습니다. \n 다시 선택하여 주시기 바랍니다.");
		obj.value = document.getElementById("endPrice").value;
		obj.focus();
	}else if(endPrice == objPrice && endPrice < startPrice){
		alert("종료 금액은 시작 금액보다 작을 수 없습니다. \n 다시 선택하여 주시기 바랍니다.");
		obj.value = document.getElementById("startPrice").value;
		obj.focus();
	}else{
		return false;
	}	

}


$(document).ready(function() {
   $('#checkall').click(function() {
      if($('#checkall').prop("checked")) {
         $('input[name=chk]').prop('checked', true);
      }else {
         $('input[name=chk]').prop('checked', false);
      }
   });
   
 	//등급 변경 버튼 눌렀을때
	$('#gradebtn').on('click', function() {
		if($('input[name=chk]').is(":checked") == false){
			alert("멤버를 선택하세요.");
			return false;
		}else {
			if($('#membergrade option:selected').val() == 'none') {
				alert('등급을 선택하세요!');
				return false;
			}else {
				if(confirm('정말 변경하시겠습니까?')) {
					var items=[];
					$('input[name=chk]:checkbox:checked').each(function(){items.push($(this).val());});
					var tmp = items.join(',');
					location.href='memberGradeManagement.ad?user_id='+tmp+'&membergrade='+$('#membergrade option:selected').val();
				}else {
					return false;
				}	
			}
			
		}
	});
});  



</script>
<style>
   th {background-color : #F6F6F6;}
   .col {margin-bottom : 40px;}
   #pageList {text-align : center;}
</style>
</head>
<body>
<!-- Page Heading -->
 <div class="d-sm-flex align-items-center justify-content-between mb-4">
    <h1 class="h3 mb-0 text-gray-800">회원 관리</h1>
 </div>
 <!-- Content Row -->
 <div class="row">
    <div class="col">
   <form action="memberList.ad" method="post">
      <div class="card card-default">
            <div class="card-body">
               <table class="table table-bordered">
                  <tr>
                     <th>개인정보</th>
                     <td>
                        <select id="searchType" name="searchType">
                           <option value="">선택</option>
                           <option value="user_id">아이디</option>
                           <option value="user_name">이름</option>
                           <option value="tel">휴대전화</option>
                           <option value="addr1">주소</option>
                        </select>
                        <input type="text" name="searchText" >
                     </td>
                  </tr>
                  <tr>
                     <th>회원등급</th>
                     <td>
                        <select id="searchGrade" name="searchGrade">
                           <option value="">선택</option>
                           <option value="N">일반회원</option>
                           <option value="A">관리자</option>
                        </select>
                     </td>
                  </tr>
                  <tr>
                     <th>구매금액</th>
                     <td>
                        <input type="text" id="startPrice" name="startPrice" onkeypress="onlyNumber();" onChange="inputPriceComparison(this);" size="5">원  ~ 
                        <input type="text" id="endPrice" name="endPrice" onkeypress="onlyNumber();" onChange="inputPriceComparison(this);" size="5">원
                     </td>
                  </tr>
                  <tr>
                          <th>주문일</th>
                          <td>
                                  <!-- 시작일 -->
                                      <input type="date" name="startDate" id="startDate" onChange="inputDateComparison(this);">
                                  <span class="demi">~</span>
                                  <!-- 종료일 -->
                                      <input type="date" name="endDate" id="endDate" onChange="inputDateComparison(this);">
                          </td>
                      </tr>   
               </table>
               <input type="submit" value="검색" id="search_btn" name="search_btn">
            </div>
         </div>
      </form>
   </div>
</div>

<div class="row">
    <div class="col">
      <div class="card card-default">
            <div class="card-body">
               <table class="table table-striped">
                  
                  <tr>
                     <th class="list_top"><input type="checkbox" name="checkall" id="checkall"></th>
                     <th>회원가입일</th>
                     <th>아이디</th>
                     <th>이름</th>
                     <th>등급</th>
                     <th>휴대전화</th>
                     <th>성별</th>
                     <th>주소</th>
                     <th>이메일</th>
                  </tr>
   
                  <!-- 여기서부터 검색결과 -->
                  
                  <c:choose>
                     <c:when test="${memberList ne null }">
                     
                        <c:forEach items="${memberList }" var="member">
                           <tr>
                              <td><input type="checkbox" name="chk" id="chk" value="${member.user_id } "></td>
                              <td><a href="memberInfo.ad?user_id=${member.user_id }">${member.joindate }</a></td>
                              <td><a href="memberInfo.ad?user_id=${member.user_id }">${member.user_id }</a></td>
                              <td><a href="memberInfo.ad?user_id=${member.user_id }">${member.user_name }</a></td>
                              <td><a href="memberInfo.ad?user_id=${member.user_id }">${member.grade }</a></td>
                              <td><a href="memberInfo.ad?user_id=${member.user_id }">${member.tel }</a></td>
                              <td><a href="memberInfo.ad?user_id=${member.user_id }">${member.sex }</a></td>
                              <td><a href="memberInfo.ad?user_id=${member.user_id }">${member.addr1 }</a></td>
                              <td><a href="memberInfo.ad?user_id=${member.user_id }">${member.email}</a></td>
                           </tr>      
                        </c:forEach>
                  
                     </c:when>
                     <c:otherwise>
                        <tr>
                           <td colspan="10">
                            검색된 회원이 없습니다.
                           </td>
                        </tr>
                     </c:otherwise>
                     
                  </c:choose>
                  <!-- 여기까지 검색결과 -->
                  
               </table>

      <!-- 여기서부터 페이징 -->
         <section id="pageList">
               <c:choose>
                  <c:when test="${pageInfo.page <= 1 }">
                     <ion-icon name="chevron-back-outline"></ion-icon>  
                  </c:when>
                  <c:otherwise>
                     <a href="memberList.ad?page=${pageInfo.page-1 }&searchType=${searchType}&searchText=${searchText}&searchGrade=${searchGrade}&startPrice=${startPrice}&endPrice=${endPrice}&startDate=${startDate}&endDate=${endDate}"><ion-icon name="chevron-back-outline"></ion-icon></a>  
                  </c:otherwise>
               </c:choose>
         
               <c:forEach var="a" begin="${pageInfo.startPage }" end="${pageInfo.endPage }" step="1">
               
                  <c:choose>
                     <c:when test="${a eq pageInfo.page }"> 
                        [${a}]            <!-- 현재페이지는 링크 안걸어도 되니까. -->
                     </c:when>
                     <c:otherwise>
                        <a href="memberList.ad?page=${a}&searchType=${searchType}&searchText=${searchText}&searchGrade=${searchGrade}&startPrice=${startPrice}&endPrice=${endPrice}&startDate=${startDate}&endDate=${endDate}">[${a}]</a>
                     </c:otherwise>
                  </c:choose>
               </c:forEach>
               <c:choose>
                  <c:when test="${pageInfo.page >= pageInfo.maxPage }">
                     <ion-icon name="chevron-forward-outline"></ion-icon>
                  </c:when>
                  <c:otherwise>
                     <a href="memberList.ad?page=${pageInfo.page+1 }&searchType=${searchType}&searchText=${searchText}&searchGrade=${searchGrade}&startPrice=${startPrice}&endPrice=${endPrice}&startDate=${startDate}&endDate=${endDate}"><ion-icon name="chevron-forward-outline"></ion-icon></a>   
                  </c:otherwise>
               </c:choose>
            </section>
         </div>
      </div>
   </div>
</div>

<!-- 여기까지 페이징 -->

<div class="d-sm-flex align-items-center justify-content-between mb-4">
	<h1 class="h3 mb-0 text-gray-800">회원 등급관리</h1>
</div>
 <!-- Content Row -->
<div class="row">
	<div class="col">
		<form action="memberList.ad" method="post">
			<div class="card card-default">
				<div class="card-body">
               		<table class="table table-bordered">
                    	<tr>
                     		<td>
                     			선택된 회원을
	                    		<select id="membergrade" name="membergrade">
		                    		<option value="none" selected disabled hidden>선택</option>
		                    		<option value="N">일반회원(N)</option>
		                    		<option value="A">관리자(A)</option>
	                    		</select>으로
	                    		
	         					<a href="#" id="gradebtn" class="btn btn-light btn-icon-split">
                    				<span class="icon text-gray-600">
                      					<i class="fas fa-arrow-right"></i>
                    				</span>
                    				<span class="text">등급변경</span>
               					</a>합니다.
	         	  			</td>
						</tr>
               		</table>
            	</div>
         	</div>
      </form>
   </div>
</div>
</body>
</html>