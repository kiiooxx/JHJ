<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="vo.OrderProView" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<meta charset="UTF-8" name="viewport" content="width=device-width, initial-scale=1">
<script src="http://code.jquery.com/jquery-latest.js"></script>
<script src="https://unpkg.com/ionicons@5.0.0/dist/ionicons.js"></script>
<script type="text/javascript" src="https://code.jquery.com/jquery-2.1.0.min.js" ></script>
<style>
img{
	width: 92px;
}
#progress{
	text-align: center;
	word-spacing: 10em;
}
.modal-title{
	font-weight: bold;
}
#progressCard{
	text-align: center;
}
</style>
<script>

//배송상태변경
function changeBtn(f){
	if(confirm("배송상태를 변경하시겠습니까?") == true ){
		f.submit();
	}else{
		return;
	}
}

//주문취소요청 팝업
function showPopup() { 
	window.open("acceptCancelForm.ad?openInit=true", "a", "width=500, height=300, left=100, top=50"); 
}

//배송상태 progress bar
$(function(){
	var status = "${orderInfo.sel_status}";
	if(status == 'order_done'){
		$('#progress-bar').css('width', '20%');
		$('#a').css('font-weight', 'bold');
	}else if(status == 'check_paid'){
		$('#progress-bar').css('width', '35%');
		$('#b').css('font-weight', 'bold');
	}else if(status == 'send_pro'){
		$('#progress-bar').css('width', '50%');
		$('#c').css('font-weight', 'bold');
	}else if(status == 'deli_ing'){
		$('#progress-bar').css('width', '65%');
		$('#d').css('font-weight', 'bold');
	}else if(status == 'deli_fin'){
		$('#progress-bar').css('width', '78%');
		$('#e').css('font-weight', 'bold');
	}else if(status == 'order_confirm'){
		$('#progress-bar').css('width', '100%');
		$('#f').css('font-weight', 'bold');
	}
	
});

</script>
<body>
<div class="card shadow mb-4">
	<div class="card-header py-3">
		<h6 class="m-0 font-weight-bold text-primary">주문 상세내역</h6>
    </div>
    <div class="card-body">
     		<table class="table table-bordered">
				<tr>
					<th>주문번호</th>
					<td colspan="5">${orderInfo.sel_num }</td>
				</tr>
				<tr>
					<th>주문일</th>
					<td colspan="5">${orderInfo.sel_date }</td>
				</tr>
				<tr>	
					<th>주문자ID</th><td colspan="5">${orderInfo.user_id }</td>
				</tr>
				<tr>
					<th>주문자 이름</th><td colspan="5">${orderInfo.user_name }</td>
				</tr>
				<tr>	
					<th colspan="6">주문상품</th>
				</tr>

				<c:choose>
					<c:when test="${orderProList ne null }">
						<c:forEach items="${orderProList }" var="orderProList">
							<tr>
								<td><img src="${pageContext.request.contextPath}/upload/${orderProList.pro_photo }" class="proImage"/></td>
								<td>상품번호:${orderProList.pro_det_num }<br>상품명:${orderProList.pro_name }<br>옵션:${orderProList.pro_size }[${orderProList.color }]</td>
								<fmt:formatNumber var="pro_price" value="${orderProList.pro_price }" pattern="#,###"/>
								<td>${pro_price }원</td>
								<td>${orderProList.pro_qnt }개</td>
								<fmt:formatNumber var="price" value="${orderProList.pro_price*orderProList.pro_qnt}" pattern="#,###"/>
								<td>${price }원</td>
							</tr>
						</c:forEach>
					</c:when>
				</c:choose>
				<tr>
					<th>배송비</th>
					<fmt:formatNumber var="deli_price" value="${orderInfo.deli_price }" pattern="#,###"/>
					<td colspan="5">${deli_price }원</td>
				</tr>	
				<tr>
					<th>사용적립금</th>
					<fmt:formatNumber var="point_use" value="${orderInfo.point_use }" pattern="#,###"/>
					<td colspan="5">${point_use }원</td>
				</tr>
				<tr>
					<fmt:formatNumber var="total" value="${orderInfo.final_price }" pattern="#,###"/>
					<th>최종결제금액</th><td colspan="5">${total}원</td>
				</tr>
			</table>
		</div>
	</div>


<div class="card shadow mb-4">
	<div class="card-header py-3">
		<h6 class="m-0 font-weight-bold text-primary">배송정보</h6>
    </div>
    <div class="card-body">
		<table class="table table-bordered">
			<c:if test="${deliInfo ne null }">
				<tr>
					<th>배송번호</th>
					<td>${deliInfo.deli_num }</td>
				</tr>
				<tr>
					<th>수령인 이름</th>
					<td>${deliInfo.rec_name }</td>
				</tr>
				<tr>
					<th>수령인 연락처</th>
					<td>${deliInfo.rec_tel }</td>
				</tr>
				<tr>
					<th>배송주소</th>
					<td>
						${deliInfo.deli_addr1 } ${deliInfo.deli_addr2 }<br>
						우편번호: ${deliInfo.deli_postcode }
					</td>
				</tr>
				<tr>
					<th>배송메시지</th>
					<td>
						<c:choose>
							<c:when test="${deliInfo.deli_content ne '' }">
							${deliInfo.deli_content }
							</c:when>
							<c:otherwise>
								없음
							</c:otherwise>
						</c:choose>
					</td>
				</tr>
			</c:if>
		</table>
	</div>
</div>

<div class="card shadow mb-4">
	<div class="card-header py-3">
		<h6 class="m-0 font-weight-bold text-primary">결제정보</h6>
    </div>
    <div class="card-body">
		<table class="table table-bordered">
			<tr>
				<th>결제방식</th>
				<td>
				<c:if test="${payInfo.pay_type eq 'mutong'}">무통장입금</c:if>
				<c:if test="${payInfo.pay_type eq 'silsi'}">실시간계좌이체</c:if>
				<c:if test="${payInfo.pay_type eq 'credit'}">카드결제</c:if>
				</td>
			</tr>
			<tr>
				<th>입금자 성명</th>
				<td>${payInfo.acc_name }</td>
			</tr>
			<tr>
				<th>입금은행</th>
				<td>
					<c:if test="${payInfo.acc_bank eq 'kookmin'}">국민은행:0000000000000(주)희희</c:if>
					<c:if test="${payInfo.acc_bank eq 'daegu'}">대구은행:0000000000000(주)희희</c:if>
					<c:if test="${payInfo.acc_bank eq 'nonghyeop'}">농협:000000000000(주)희희</c:if>
				</td>
			</tr>
			<tr>
				<th>결제기한</th>
				<td>${payInfo.pay_exp }</td>
			</tr>
			<tr>
				<th>결제일</th>
				<td>
					<c:choose>
						<c:when test="${payInfo.pay_date ne null }">
							${payInfo.pay_date }
						</c:when>
						<c:otherwise>
							미결제
						</c:otherwise>
					</c:choose>
				</td>
			</tr>
			</table>
		</div>
	</div>
	

<form action="changeStatus.ad" method="post" name="f">
<input type="hidden" value="${orderInfo.sel_num }" name="sel_num"/>
<input type="hidden" value="${orderInfo.user_id }" name="user_id"/>
	<div class="card mb-4">
		<div class="card-header py-3">
			<h6 class="m-0 font-weight-bold text-primary">배송상태</h6>
        </div>
        <div id="progressCard" class="card-body">
        <div class="mb-1 big" id="progress">
			<span id="a">주문완료</span>
            <span id="b">결제확인</span>
            <span id="c">상품발송</span>
            <span id="d">배송중</span>
            <span id="e">배송완료</span>
            <span id="f">구매확정</span>
            </div>
            <div class="progress mb-4">
            <div class="progress-bar" id="progress-bar" role="progressbar" aria-valuenow="75" aria-valuemin="0" aria-valuemax="100"></div>
            </div>
				<input type="hidden" name="id" value="${orderInfo.user_id }">                  
            	<input type="radio" id="deliStatus" name="deliStatus" value="order_done">주문완료 
				<input type="radio" id="deliStatus" name="deliStatus" value="check_paid">결제확인
				<input type="radio" id="deliStatus" name="deliStatus" value="send_pro">상품발송 
				<input type="radio" id="deliStatus" name="deliStatus" value="deli_ing">배송중
				<input type="radio" id="deliStatus" name="deliStatus" value="deli_fin">배송완료
				<input type="radio" id="deliStatus" name="deliStatus" value="order_confirm">구매확정&nbsp;&nbsp;으로&nbsp;
             	<a href="#" class="btn btn-secondary btn-icon-split" onclick="javascript:changeBtn(document.f);">
     			<span class="icon text-white-50">
       				<i class="fas fa-arrow-right"></i>
     			</span>
     			<span class="text">배송상태 변경하기</span>
   				</a>
			</div>  	
	</div>
</form>

<div class="card shadow mb-4">
	<div class="card-header py-3">
		<h6 class="m-0 font-weight-bold text-primary">주문취소 신청여부</h6>
    </div>
    <div class="card-body">
     	<table class="table table-bordered">		
			<c:choose>
				<c:when test="${fn:contains(orderInfo.cancel_req,'N') }">
					<span style="font-weight:bold;">
	                	취소요청이 없습니다.
	                </span><br>
				</c:when>
				<c:when test="${fn:contains(orderInfo.cancel_req,'C') }">
					<span class="btn btn-success btn-circle">
                    	<i class="fas fa-check"></i>
                  	</span>&nbsp;
					<span style="font-weight:bold;">
	                	주문취소가 완료되었습니다.
	                </span><br><br>&nbsp;&nbsp;&nbsp;
					<button type="button" class="btn btn-primary" id="readCancelContent" data-toggle="modal" data-target="#exampleModalCenter">
	  					주문취소사유 내용보기
					</button>
				</c:when>
				<c:otherwise>
					<span class="btn btn-warning btn-circle">
	                	<i class="fas fa-exclamation-triangle"></i>
	                </span>&nbsp;
	                <span style="font-weight:bold;">
	                	주문 취소 요청이 있습니다.
	                </span><br><br>&nbsp;&nbsp;&nbsp;
					<button type="button" class="btn btn-primary" id="readCancelContent" data-toggle="modal" data-target="#exampleModalCenter">
	  					주문취소사유 내용보기
					</button>
				</c:otherwise>
			</c:choose>		
		</table>
	</div>
</div>

<button type="button" class="btn btn-primary btn-lg btn-block" onclick="location.href='orderManageList.ad';">목록보기</button>
<br><br><br>
<!-- 주문취소 내용보기 모달창 -->
<form action="acceptCancel.ad" method="post" name="g">
	<div class="modal fade" id="exampleModalCenter" tabindex="-1" role="dialog" aria-labelledby="exampleModalCenterTitle" aria-hidden="true">
		<div class="modal-dialog modal-dialog-centered" role="document">
			<div class="modal-content">
				<div class="modal-header">
					<h5 class="modal-title" id="exampleModalCenterTitle">취소사유 조회</h5>
					<button type="button" class="close" data-dismiss="modal" aria-label="Close">
	        			<span aria-hidden="true">&times;</span>
	        		</button>
	      		</div>
      			<div id="cancelContent" class="modal-body">      
					<input type="hidden" value="${orderInfo.sel_num }" id="sel_num" name="sel_num">
					<input type="hidden" value="${orderInfo.user_id }" id="user_id" name="user_id">
					
      					${orderInfo.cancel_reason}
      			</div>
      			<div class="modal-footer">
        			<button type="button" class="btn btn-secondary" data-dismiss="modal">닫기</button>
        			<c:if test="${fn:contains(orderInfo.cancel_req,'Y') }">
        			<button type="button" class="btn btn-primary" onclick="g.submit();">취소 승인</button>
      				</c:if>
      			</div>
    		</div>
  		</div>
	</div>	
</form>
</body>

</html>