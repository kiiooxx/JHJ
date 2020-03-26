<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>주문취소창</title>
</head>
<style>
.thumb {
	width: 90px;
}


</style>
<script type="text/javascript">

//설렉트뱍스틑 기타사유로 뒀을때
$(document).ready(function(){
	
	$('#reqselect').on('change',function() {
		var reason = $('#reqselect option:selected').val();
		
		if(reason == 'etc') {
			$('#reasontext').show();
		}else {
			$('#reasontext').hide();
		}
	});

});	
</script>

<style>
	#reasontext {display:none;}
</style>
<body>
	<h2>취소요청처리</h2>
	
	<!-- 주문취소정보 -->
	
	<table border="1" >
	<thead>
		<tr>
			<th>상품주문번호</th>
			<th>-</th>
			<th>상품명</th>
			<th>상품구매금액/수량</th>
			<th>배송비</th>
			<th>총 환불 금액</th>
		</tr>
	</thead>
		
	<tbody>
	<c:forEach var="orderProList" items="${orderProList}" varStatus="i">
		<tr>
				<c:choose>
					<c:when test="${i.index == 0 || sel_num != orderProList.sel_num }">
						<c:set var="sel_num" value="${ orderProList.sel_num}"/>
						<td>${orderProList.sel_num} </td>
					</c:when>
					<c:otherwise>
						<td></td>
					</c:otherwise>
				</c:choose>	
				<td><img src="<%=request.getContextPath() %>/upload/${orderProList.pro_photo}" class="thumb"></td>
				<td>${orderProList.pro_name}</td>
				<td> ${orderProList.pro_price}원/${orderProList.pro_qnt}개</td>
				<td>${orderInfo.deli_price} 원</td>
				<td>${orderInfo.final_price} 원</td>
	
		</tr>
		
	</c:forEach>
	</table>
		
		<form action="ordercancelpro.mem" id="cancel_req" name="q" method="post">
		<table>
				<input type="hidden" name="sel_num" value="${orderInfo.sel_num }"/>
			<tr>
				<th>사유 선택<b class="req">*</b></th>
				<td>
					<select class="사유선택" name="reqselect" id="reqselect">
						<option value="orderReqCancel"selected="selected">구매의사없음</option>
						<option value="changeCoSi">색상 및 사이즈 변경</option>
						<option value="wrnogChoice">다른 상품 잘못 주문</option>
						<option value="svcInfoDissa=">서비스 정보 불만족</option>
						<option value="wrongProInfo">상품 정보 상이</option>
						<option value="etc" id="id">기타 사유</option>
					</select>	
				</td>
			</tr>
			

			<tr id="reasontext">
				<th>사유 입력<b class="req"></b></th>
				<td>
					<textarea name="cancel_reason" width="120" height="60" ></textarea>
				</td>
		  </tr>
	</table>
	</form>
					<a href="javascript:q.submit();">취소요청</a>

</body>
</html>

