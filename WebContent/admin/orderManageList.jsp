<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<c:set var="pageInfo" value="${requestScope.pageInfo }" />
<c:set var="orderList" value="${requestScope.orderList }" />

<c:set var="searchType" />
<c:set var="searchText" />
<c:set var="orderDate" />
<c:set var="deliStatus" />
<c:set var="cancelReq"/>

<c:if test="${searchType ne null }">
	<c:set var="searchType" value="${requestScope.searchType }" />
</c:if>
<c:if test="${searchText ne null }">
	<c:set var="searchText" value="${requestScope.searchText }" />
</c:if>
<c:if test="${orderDate ne null }">
	<c:set var="orderDate" value="${requestScope.orderDate }" />
</c:if>
<c:if test="${deliStatus ne null }">
	<c:set var="deliStatus" value="${requestScope.deliStatus }" />
</c:if>
<c:if test="${cancelReq ne null }">
	<c:set var="cancelReq" value="${requestScope.cancelReq }"/>
</c:if>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<script src="https://code.jquery.com/jquery-3.2.1.js"></script>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
<script>
$(function(){
	$("#exceptCancel").change(function(){
		if($("#exceptCancel").prop("checked")){
			$(".cancel").prop("checked", false);
			$(".cancel").attr("disabled", true);
		}else{
			$(".cancel").attr("disabled", false);
		}
	});
});

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

</script>
<style>

th {
	background: #F6F6F6;
}

.col {
	margin-bottom: 40px;
}

#page {
	text-align: center;
}
</style>

</head>
<body>
	<div class="d-sm-flex align-items-center justify-content-between mb-4">
		<h1 class="h3 mb-0 text-gray-800">주문관리 페이지</h1>
	</div>
	<!-- Content Row -->
	<div class="row">
		<div class="col">
			<form action="orderManageList.ad" method="post">
				<div class="card card-default">
					<div class="card-body">
						<table class="table table-bordered">
							<tr>
								<th>검색어</th>
								<td><select id="searchType" name="searchType">
										<option value="user_id">주문자 아이디</option>
										<option value="user_name">주문자 이름</option>
										<option value="rec_name">수령인 이름</option>
										<option value="sel_num">주문번호</option>
										<option value="deli_num">배송번호</option>
										<option value="pro_num">상품번호</option>
								</select> <input type="text" name="searchText"></td>
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
                                <input type="date" name="startDate" id="startDate" onChange="inputDateComparison(this);" >
                                <span class="demi">~</span>
                                  <!-- 종료일 -->
                                 <input type="date" name="endDate" id="endDate" onChange="inputDateComparison(this);" >
                      			</td>
                      		</tr>
							<tr>
								<th>주문상태</th>
								<td><input type="checkbox" name="deliStatus" value="order_done">주문완료 &nbsp;
									<input type="checkbox" name="deliStatus" value="check_paid">입금확인 &nbsp;
									<input type="checkbox" name="deliStatus" value="send_pro">상품발송 &nbsp;
									<input type="checkbox" name="deliStatus" value="deli_ing">배송중 &nbsp;
									<input type="checkbox" name="deliStatus" value="deli_fin">배송완료 &nbsp;
									<input type="checkbox" name="deliStatus" value="order_confirm">구매확정
								</td>
							</tr>
							<tr>
								<th>주문취소신청</th>
								<td>
									<input type="checkbox" name="cancel_req" class="cancel" value="Y">취소요청 &nbsp;
									<input type="checkbox" name="cancel_req" class="cancel" value="C">취소완료 &nbsp;
									<input type="checkbox" name="cancel_req" id="exceptCancel" value="N">취소요청/완료상태 제외하고 보기
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
						<th>주문일</th>
						<th>주문번호</th>
						<th>주문자</th>
						<th>상품명</th>
						<th>결제금액</th>
						<th>상태</th>
						<th>취소요청</th>
					</tr>
						<!-- 여기서부터 검색결과 -->

						<c:choose>
						<c:when test="${orderList ne null }">
							<c:forEach items="${orderList }" var="order">
								<tr>
									<td><a href="orderManageDetail.ad?sel_num=${order.sel_num }&user_id=${order.user_id}">${order.sel_date }</a></td>
									<td><a href="orderManageDetail.ad?sel_num=${order.sel_num }&user_id=${order.user_id}">${order.sel_num }</a></td>
									<td><a href="orderManageDetail.ad?sel_num=${order.sel_num }&user_id=${order.user_id}">${order.user_id }</a></td>
									
									<c:choose>
									<c:when test="${order.pro_count > 1 }">
									<td>${order.pro_name } 외 ${order.pro_count }개</td>
									</c:when>
									<c:otherwise>
									<td>${order.pro_name }</td>
									</c:otherwise>
									</c:choose>
									
									
									<td>${order.final_price }원</td>
									<td>
									<c:if test="${order.sel_status eq 'order_done' }">
										주문완료
									</c:if>
									<c:if test="${order.sel_status eq 'check_paid' }">
										입금확인
									</c:if>
									<c:if test="${order.sel_status eq 'send_pro' }">
										상품발송
									</c:if>
									<c:if test="${order.sel_status eq 'deli_ing' }">
										배송중
									</c:if>
									<c:if test="${order.sel_status eq 'deli_fin' }">
										배송완료
									</c:if>
									<c:if test="${order.sel_status eq 'order_confirm' }">
										구매확정
									</c:if>
									</td>
									<td>
									<c:if test="${fn:contains(order.cancel_req,'Y')}">
										<span class="badge badge-pill badge-danger">취소요청</span>
									</c:if>
									<c:if test="${fn:contains(order.cancel_req,'C')}">
										<span class="badge badge-pill badge-success">취소완료</span>
									</c:if>
									</td>
								</tr>
							</c:forEach>
						</c:when>
						<c:otherwise>
							 검색된 주문내역이 없습니다.
						</c:otherwise>
					</c:choose>
					     
               </table>
            </div>
               <!-- 여기까지 검색결과 -->

     	<!-- 여기서부터 페이징 -->
				<section id="page">
					<c:choose>
						<c:when test="${pageInfo.page <= 1 }">
							[이전]&nbsp;	
						</c:when>
						<c:otherwise>
							<a href="orderManageList.ad?page=${pageInfo.page-1 }&searchType=${searchType}&searchText=${searchText}&startDate=${startDate}&endDate=${endDate}&startPrice=${startPrice}&endPrice=${endPrice}
							&deliStatus=
							<c:if test="${deliStatus ne null }">
								<c:forEach items="${deliStatus }" var="deliStatus">
								${deliStatus}
								</c:forEach>
							</c:if>
							&cancel_req=${cancel_req}">[이전]</a>&nbsp;	
						</c:otherwise>
					</c:choose>

					<c:forEach var="a" begin="${pageInfo.startPage }" end="${pageInfo.endPage }" step="1">

						<c:choose>
							<c:when test="${a eq pageInfo.page }"> 
								[${a}]				<!-- 현재페이지는 링크 안걸어도 되니까. -->
							</c:when>
							<c:otherwise>
								<a href="orderManageList.ad?page=${a}&searchType=${searchType}&searchText=${searchText}&startDate=${startDate}&endDate=${endDate}&startPrice=${startPrice}&endPrice=${endPrice}
								&deliStatus=
									<c:if test="${deliStatus ne null }">
										<c:forEach items="${deliStatus }" var="deliStatus">
										${deliStatus }
										</c:forEach>
									</c:if>
								&cancel_req=
									<c:if test="${cancelReq ne null }">
										<c:forEach items="${cancelReq }" var="cancelReq">
										${cancelReq }
										</c:forEach>
									</c:if>
								">[${a}]</a>
							</c:otherwise>
						</c:choose>
					</c:forEach>
					<c:choose>
						<c:when test="${pageInfo.page >= pageInfo.maxPage }">
							&nbsp;[다음]
						</c:when>
						<c:otherwise>
							<a href="orderManageList.ad?page=${pageInfo.page+1 }&searchType=${searchType}&searchText=${searchText}&startDate=${startDate}&endDate=${endDate}&startPrice=${startPrice}&endPrice=${endPrice}
							&deliStatus=
							<c:if test="${deliStatus ne null }">
								<c:forEach items="${deliStatus }" var="deliStatus">
									${deliStatus}
								</c:forEach>
							</c:if>
							&cancel_req=
								<c:if test="${cancelReq ne null }">
										<c:forEach items="${cancelReq }" var="cancelReq">
										${cancelReq }
										</c:forEach>
									</c:if>
							">[다음]</a>
						</c:otherwise>
					</c:choose>
				</section>
			</div>
		</div>
	</div>


	<!-- 여기까지 페이징 -->



			

</body>
</html>