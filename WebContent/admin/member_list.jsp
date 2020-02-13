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



<script>

$(document).ready(function() {

    //datepicker 한국어로 사용하기 위한 언어설정
    $.datepicker.setDefaults($.datepicker.regional['ko']);     

    // Datepicker            
    $(".datepicker").datepicker({
        showButtonPanel: true,
        dateFormat: "yy-mm-dd",
        onClose : function ( selectedDate ) {
        
            var eleId = $(this).attr("id");
            var optionName = "";

            if(eleId.indexOf("startDate") > 0) {
                eleId = eleId.replace("startDate", "endDate");
                optionName = "minDate";
            } else {
                eleId = eleId.replace("endDate", "startDate");
                optionName = "maxDate";
            }

            $("#"+eleId).datepicker( "option", optionName, selectedDate );        
            $(".searchDate").find(".chkbox2").removeClass("on"); 
        }
    }); 

    //시작일.
    /*$('#searchStartDate').datepicker("option","onClose", function( selectedDate ) {    
        // 시작일 datepicker가 닫힐때
        // 종료일의 선택할수있는 최소 날짜(minDate)를 선택한 시작일로 지정
        $("#searchEndDate").datepicker( "option", "minDate", selectedDate );
        $(".searchDate").find(".chkbox2").removeClass("on");
    });
    */

    //종료일.
    /*$('#searchEndDate').datepicker("option","onClose", function( selectedDate ) {    
        // 종료일 datepicker가 닫힐때
        // 시작일의 선택할수있는 최대 날짜(maxDate)를 선택한 종료일로 지정 
        $("#searchStartDate").datepicker( "option", "maxDate", selectedDate );
        $(".searchDate").find(".chkbox2").removeClass("on");
    });
    */

    $(".dateclick").dateclick();    // DateClick
    $(".searchDate").schDate();        // searchDate
    
});

// Search Date
jQuery.fn.schDate = function(){
    var $obj = $(this);
    var $chk = $obj.find("input[type=radio]");
    $chk.click(function(){                
        $('input:not(:checked)').parent(".chkbox2").removeClass("on");
        $('input:checked').parent(".chkbox2").addClass("on");                    
    });
};

// DateClick
jQuery.fn.dateclick = function(){
    var $obj = $(this);
    $obj.click(function(){
        $(this).parent().find("input").focus();
    });
}    


function setSearchDate(start){

    var num = start.substring(0,1);
    var str = start.substring(1,2);

    var today = new Date();

    //var year = today.getFullYear();
    //var month = today.getMonth() + 1;
    //var day = today.getDate();
    
    var endDate = $.datepicker.formatDate('yy-mm-dd', today);
    $('#searchEndDate').val(endDate);
    
    if(str == 'd'){
        today.setDate(today.getDate() - num);
    }else if (str == 'w'){
        today.setDate(today.getDate() - (num*7));
    }else if (str == 'm'){
        today.setMonth(today.getMonth() - num);
        today.setDate(today.getDate() + 1);
    }

    var startDate = $.datepicker.formatDate('yy-mm-dd', today);
    $('#startDate').val(startDate);
            
    // 종료일은 시작일 이전 날짜 선택하지 못하도록 비활성화
    $("#endDate").datepicker( "option", "minDate", startDate );
    
    // 시작일은 종료일 이후 날짜 선택하지 못하도록 비활성화
    $("#startDate").datepicker( "option", "maxDate", endDate );
    
    

}

function checkAll(theForm){
	if(theForm.remove.length == undefined){ //체크박스에 선택된 항목이 없을 때 전체선택
		theForm.remove.checked = theForm.allCheck.checked;  //오른쪽에 체크된 항목을 왼쪽에 넣음.
	}else{
		for(var i = 0; i < theForm.remove.length; i++){
			theForm.remove[i].checked = theForm.allCheck.checked;
		}		
	}	
}
	
	


/* function inputPrice(obj){
	var priceTmp = 
	return 
} */

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
								<input type="text" id="startPrice" name="startPrice" onChange="priceComp(this);" size="5">원  ~ 
								<input type="text" id="endPrice" name="endPrice" onChange="priceComp(this);" size="5">원
							</td>
						</tr>
						<tr>
		                    <th>주문일</th>
		                    <td>
		                        <ul class="searchDate">
		                            <li>
		                                <span class="chkbox2">
		                                    <input type="radio" name="dateType" id="dateType1" onclick="setSearchDate('0d')"/>
		                                    <label for="dateType1">당일</label>
		                                </span>
		                            </li>
		                            <li>
		                                <span class="chkbox2">
		                                    <input type="radio" name="dateType" id="dateType2" onclick="setSearchDate('3d')"/>
		                                    <label for="dateType2">3일</label>
		                                </span>
		                            </li>
		                            <li>
		                                <span class="chkbox2">
		                                    <input type="radio" name="dateType" id="dateType3" onclick="setSearchDate('1w')"/>
		                                    <label for="dateType3">1주</label>
		                                </span>
		                            </li>
		                            <li>
		                                <span class="chkbox2">
		                                    <input type="radio" name="dateType" id="dateType4" onclick="setSearchDate('2w')"/>
		                                    <label for="dateType4">2주</label>
		                                </span>
		                            </li>
		                            <li>
		                                <span class="chkbox2">
		                                    <input type="radio" name="dateType" id="dateType5" onclick="setSearchDate('1m')"/>
		                                    <label for="dateType5">1개월</label>
		                                </span>
		                            </li>
		                            <li>
		                                <span class="chkbox2">
		                                    <input type="radio" name="dateType" id="dateType6" onclick="setSearchDate('3m')"/>
		                                    <label for="dateType6">3개월</label>
		                                </span>
		                            </li>
		                            <li>
		                                <span class="chkbox2">
		                                    <input type="radio" name="dateType" id="dateType7" onclick="setSearchDate('6m')"/>
		                                    <label for="dateType7">6개월</label>
		                                </span>
		                            </li>
		                        </ul>
		                        
		                        <div class="clearfix">
		                            <!-- 시작일 -->
		                            <span class="dset">
		                                <input type="text" class="datepicker inpType" name="startDate" id="startDate" >
		                                <a href="#none" class="btncalendar dateclick">달력</a>
		                            </span>
		                            <span class="demi">~</span>
		                            <!-- 종료일 -->
		                            <span class="dset">
		                                <input type="text" class="datepicker inpType" name="endDate" id="endDate" >
		                                <a href="#none" class="btncalendar dateclick">달력</a>
		                            </span>
		                        </div>    
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
					<table class="table table-bordered">
						<tr>
							<td colspan="10"><input type="submit" value="선택삭제" name="remove" id="remove" style="align:left;"></td>
						</tr>
	
						<tr>
							<td class="list_top"><input type="checkbox" name="remove" id="remove"></td>
							<td>회원가입일</td>
							<td>아이디</td>
							<td>이름</td>
							<td>등급</td>
							<td>휴대전화</td>
							<td>성별</td>
							<td>주소</td>
							<td>이메일</td>
							<td>메모</td>
						</tr>
	
						<!-- 여기서부터 검색결과 -->
						
						<c:choose>
							<c:when test="${memberList ne null }">
							
								<c:forEach items="${memberList }" var="member">
									<tr>
										<td><input type="checkbox" name="remove" id="remove"></td>
										<td><a href="memberInfo.ad?user_id=${member.user_id }">${member.joindate }</a></td>
										<td><a href="memberInfo.ad?user_id=${member.user_id }">${member.user_id }</a></td>
										<td><a href="memberInfo.ad?user_id=${member.user_id }">${member.user_name }</a></td>
										<td><a href="memberInfo.ad?user_id=${member.user_id }">${member.grade }</a></td>
										<td><a href="memberInfo.ad?user_id=${member.user_id }">${member.tel }</a></td>
										<td><a href="memberInfo.ad?user_id=${member.user_id }">${member.sex }</a></td>
										<td><a href="memberInfo.ad?user_id=${member.user_id }">${member.addr1 }</a></td>
										<td><a href="memberInfo.ad?user_id=${member.user_id }">${member.email}</a></td>
										<td>메모</td>
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
						
						<tr>
							<td colspan="10"><input type="submit" value="선택삭제" name="remove" id="remove" style="align:left;"></td>
						</tr>
					</table>

		<!-- 여기서부터 페이징 -->
			<section id="page">
					<c:choose>
						<c:when test="${pageInfo.page <= 1 }">
							[이전]&nbsp;	
						</c:when>
						<c:otherwise>
							<a href="memberList.ad?page=${pageInfo.page-1 }&searchType=${searchType}&searchText=${searchText}&searchGrade=${searchGrade}&startPrice=${startPrice}&endPrice=${endPrice}&startDate=${startDate}&endDate=${endDate}">[이전]</a>&nbsp;	
						</c:otherwise>
					</c:choose>
			
					<c:forEach var="a" begin="${pageInfo.startPage }" end="${pageInfo.endPage }" step="1">
					
						<c:choose>
							<c:when test="${a eq pageInfo.page }"> 
								[${a}]				<!-- 현재페이지는 링크 안걸어도 되니까. -->
							</c:when>
							<c:otherwise>
								<a href="memberList.ad?page=${a}&searchType=${searchType}&searchText=${searchText}&searchGrade=${searchGrade}&startPrice=${startPrice}&endPrice=${endPrice}&startDate=${startDate}&endDate=${endDate}">[${a}]</a>
							</c:otherwise>
						</c:choose>
					</c:forEach>
					<c:choose>
						<c:when test="${pageInfo.page >= pageInfo.maxPage }">
							&nbsp;[다음]
						</c:when>
						<c:otherwise>
							<a href="memberList.ad?page=${pageInfo.page+1 }&searchType=${searchType}&searchText=${searchText}&searchGrade=${searchGrade}&startPrice=${startPrice}&endPrice=${endPrice}&startDate=${startDate}&endDate=${endDate}">[다음]</a>	
						</c:otherwise>
					</c:choose>
				</section>
			</div>
		</div>
	</div>
</div>

<!-- 여기까지 페이징 -->
</body>
</html>