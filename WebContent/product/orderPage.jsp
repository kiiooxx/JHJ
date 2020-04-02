<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:choose>
	<c:when test="${totalMoney < 50000 }">
		<c:set var="deliPrice" value="2500"/>
	</c:when>
	<c:otherwise>
		<c:set var="deliPrice" value="0"/>
	</c:otherwise>
</c:choose>

<script>
function openPostcode(){
	new daum.Postcode({
	    oncomplete: function(data) {
	    	 // 각 주소의 노출 규칙에 따라 주소를 조합한다.
	        // 내려오는 변수가 값이 없는 경우엔 공백('')값을 가지므로, 이를 참고하여 분기 한다.
	        var addr = ''; // 주소 변수
	        var extraAddr = ''; // 참고항목 변수

	        //사용자가 선택한 주소 타입에 따라 해당 주소 값을 가져온다.
	        if (data.userSelectedType === 'R') { // 사용자가 도로명 주소를 선택했을 경우
	            addr = data.roadAddress;
	        } else { // 사용자가 지번 주소를 선택했을 경우(J)
	            addr = data.jibunAddress;
	        }

	        // 사용자가 선택한 주소가 도로명 타입일때 참고항목을 조합한다.
	        if(data.userSelectedType === 'R'){
	            // 법정동명이 있을 경우 추가한다. (법정리는 제외)
	            // 법정동의 경우 마지막 문자가 "동/로/가"로 끝난다.
	            if(data.bname !== '' && /[동|로|가]$/g.test(data.bname)){
	                extraAddr += data.bname;
	            }
	            // 건물명이 있고, 공동주택일 경우 추가한다.
	            if(data.buildingName !== '' && data.apartment === 'Y'){
	                extraAddr += (extraAddr !== '' ? ', ' + data.buildingName : data.buildingName);
	            }
	            // 표시할 참고항목이 있을 경우, 괄호까지 추가한 최종 문자열을 만든다.
	            if(extraAddr !== ''){
	                extraAddr = ' (' + extraAddr + ')';
	            }
	        } 

	        // 우편번호와 주소 정보를 해당 필드에 넣는다.
	        document.getElementById('recPostcode').value = data.zonecode;
	        document.getElementById("recAddr1").value = addr + extraAddr;
	        // 커서를 상세주소 필드로 이동한다.
	        document.getElementById("recAddr2").focus();
	    }
	}).open();
}

function numberFormat(inputNumber) {
	   return inputNumber.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",");
	}
	
 function inqPoint(){
	 
	 
	if(${memberPoint.point_final} != null){
		
		var priceLimit = ${pointMan.p_pricelimit };/* 적립금 사용가능 최소상품 구매 합계액 */
		var pointLimit = ${pointMan.p_pointlimit };/* 적립금 사용가능 최소누적 적립금액 */
		
		var po = document.getElementById('usePoint').value; /* 적립금 사용자입력 */
		var memPoint = ${memberPoint.point_final}; /* 사용자의 보유 적립금액 */
		var total = ${totalMoney + deliPrice} - po;	/* 총액+배송비-적립금 //최종걸제액 */
		
		var totalAct = ${totalMoney - deliPrice} - po;	/* 실결제액기준 적립계산과정 (총액 - 배송비 - 적립금) */
		var totalAs = ${totalMoney - deliPrice}; /* 적립금포함 계산금액기준 적립계산과정(총액 - 배송비) */
		
		var totalNo = 0; /* 적립금사용시 적립안함 옵션 */
		
		var rate = ${pointMan.p_rate }; /* 적립비율 */
		var totalAct_point = Math.floor(totalAct*(rate/100));	/* 실결제액기준 적립금 계산결과 (총액 - 배송비 - 적립금)*적립비율 */
		var totalAs_point = Math.floor(totalAs*(rate/100));	/* 적립금포함기준 적립금 계산결과 (총액 - 배송비)*적립비율 */
		
		totalAs_point = numberFormat(totalAs_point);/* 천단위 콤마 */
		totalAct_point = numberFormat(totalAct_point);/* 천단위 콤마 */
		po = numberFormat(po);/* 천단위 콤마 */
		total = numberFormat(total);/* 천단위 콤마 */
		rate = rate.toFixed(1);/* 퍼센트 소수점 하나 찍기 */
		
		if(${totalMoney} < priceLimit){
			alert('구매 합계액이 '+priceLimit+ '원 이상일 경우 적립금 사용이 가능합니다.');
			document.getElementById('usePoint').value = 0;
			return false;
		}
		if(memPoint < pointLimit){
			alert('누적 적립금액이 ' + pointLimit + '원 이상일 경우 적립금 사용이 가능합니다.');
			document.getElementById('usePoint').value = 0;
			return false;
		}
		if(po > ${memberPoint.point_final}){ 
			alert('보유 적립금 이상 사용은 불가능 합니다.');
			return false;
		}else{
			document.getElementById('point').value = po;/* 적립금 */
			document.getElementById('result').value = total; /* 계산금액(=최종결제액) */
			document.getElementById('result2').value = total; /* 최종결제액(=계산금액) */
			
			/* 적립금 사용 시 - 적립금 포함 계산일 경우 */
			if(${pointMan.p_stand == 'as'}){
				if(${pointMan.p_mark == 'won'}){
					$("#r3").text(totalAs_point+"원");
					$("#confrimPoint").val(totalAs_point+"원");
				}else if(${pointMan.p_mark == 'per'}){
					$("#r3").text("구매금액의 "+rate+"%");
					$("#confrimPoint").val("구매금액의 "+rate+"%");
				}else if(${pointMan.p_mark == 'double'}){
					$("#r3").text(totalAs_point + "원(" + rate +  "%)");
					$("#confrimPoint").val(totalAs_point + "원(" + rate +  "%)");
				}
			} 
			/* 적립금 사용 시 - 적립금 미포함 계산(실결제액기준)일 경우 */
			else if(${pointMan.p_stand == 'act'}){
				if(${pointMan.p_mark == 'won'}){
					$("#r3").text(totalAct_point+"원");
					$("#confirmPoint").val(totalAct_point+"원");
				}else if(${pointMan.p_mark == 'per'}){
					$("#r3").text("구매금액의 "+rate+"%");
					$("#confirmPoint").val("구매금액의 "+rate+"%");
				}else if(${pointMan.p_mark == 'double'}){
					$("#r3").text(totalAct_point + "원(" + rate +  "%)");
					$("#confirmPoint").val(totalAct_point + "원(" + rate +  "%)");
				}
				
			}
			/* 적립금 사용 시 - 적립안할 경우 */
			else if(${pointMan.p_stand == 'no'}){
				$("#r3").text(totalNo+"원");
				$("#confrimPoint").val(totalNo+"원");
				
			}
					
		}
		 
	}

} 


//적립금 입력칸에 숫자만 입력되도록
function onlyNumber(){
    if((event.keyCode<48)||(event.keyCode>57))
       event.returnValue=false;
}


$(document).ready(function() {
	sameAddr();
});

//주문자 정보와 배송지 정보가 동일한 경우
function sameAddr(){
	if($("#sameaddr").is(":checked")){
		$("input[name=recName]").val($("input[name=user_name]").val());
		$("input[name=recPostcode]").val($("input[name=postcode]").val());
		$("input[name=recAddr1]").val($("input[name=addr1]").val());
		$("input[name=recAddr2]").val($("input[name=addr2]").val());
		$("input[name=recTel]").val($("input[name=tel]").val());
	
	}else{
		$("input[name=recName]").val('');
		$("input[name=recPostcode]").val('');
		$("input[name=recAddr1]").val('');
		$("input[name=recAddr2]").val('');
		$("input[name=recTel]").val('');
	}
}

//필수항목 입력여부 체크

function chkForm(f){
	//주문자 정보
	if(f.user_name.value.trim() == ""){
		alert("주문하시는 분의 성함을 입력해 주세요.");
		f.user_name.focus();
		return false;
	}
	if(f.postcode.value.trim() == ""){
		alert("주소는 필수 입력사항입니다.");
		f.addr1.focus();
		return false;
	}
	if(f.addr2.value.trim() == ""){
		alert("주소는 필수 입력사항입니다.");
		f.addr2.focus();
		return false;
	}
	if(f.tel.value.trim == ""){
		alert("주문하시는 분의 연락처를 입력해주세요.");
		f.tel.focus();
		return false;
	}
	if(f.email.value.trim == ""){
		alert("주문 및 배송정보를 수신할 이메일 주소를 입력해주세요.");
		f.email.focus();
		return false;
	}
	
	//배송지 정보
	if(f.recName.value.trim() == ""){
		alert("받으시는 분의 성함을 입력해주세요.");
		f.recName.focus();
		return false;
	}
	if(f.recPostcode.value.trim() == ""){
		alert("상품을 받으실 주소를 입력해주세요.");
		f.recAddr1.focus();
		return false;
	}
	if(f.recAddr2.value.trim() == ""){
		alert("상품을 받으실 주소를 입력해주세요.");
		f.recAddr.focus();
		return false;		
	}
	if(f.recTel.value.trim() == ""){
		alert("배송받으실 분의 연락처를 입력해주세요.");
		f.recTel.focus();
		return false;
	}
	
	//결제 정보 - 무통장 입금
	
	if(f.account_name.value.trim() == ""){
		alert("입금하실 예금주명을 입력해주세요");
		return false;
	}
	if(f.bank.value.trim() == ""){
		alert("입금하실 은행을 선택해주세요.");
		return false;
	}
	//결제 정보 - 실시간 계좌이체
	
	
	//결제 정보 - 카드결제
	
	
	//약관동의체크 3개
	if(f.termCheck1.checked == false){
		alert("구매 전 참고사항을 확인 후 체크해주세요.");
		return false;
	}
	if(f.termCheck2.checked == false){
		alert("구매 전 참고사항을 확인 후 체크해주세요.");
		return false;
	}
	
	if(f.termCheck3.checked == false){
		alert("결제정보 확인 및 구매진행 동의에 체크하셔야 결제가 진행됩니다.");
		return false;
	}
	
	f.submit();
	
}

</script>
<jsp:include page="/common/loginCheck.jsp"/>
<div class="cartPage">
	<form action="payProcess.pro" name="f" method="post">
	<div class="cartList">
		<table class="cartTable">
			<colgroup>
				<col style="width:92px"/>
				<col style="width:auto"/>
				<col style="width:100px"/>
				<col style="width:100px"/>
				<col style="width:100px"/>
				<col style="width:100px"/>
			</colgroup>
			<tr>
				<th scope="col">IMAGE</th>
				<th scope="col">INFO</th>
				<th scope="col">PRICE</th>
				<th scope="col">QTY</th>
				<th scope="col">DELIVERY</th>
				<th scope="col">TOTAL</th>
			</tr>

				<!-- 여기서부터 상품 리스트 출력 -->	
				<c:choose>
					<c:when test="${cartList2 ne null }">
						<c:forEach var="list" items="${cartList2 }">
						<tr>
						<input type="hidden" name="directOrder" value="true"/>
						<input type="hidden" name="pro_det_num" value="${list.pro_det_num }" />
						<input type="hidden" name="bas_pro_qnt" value="${list.bas_pro_qnt }"/>
						<input type="hidden" name="pro_price" value="${list.pro_price }"/>
						<input type="hidden" name="color" value="${list.color }"/>
						<input type="hidden" name="pro_size" value="${list.pro_size }"/>
						<input type="hidden" name="pro_num" value="${list.pro_num }"/>
						<input type="hidden" name="pro_name" value="${list.pro_name }"/>
						<input type="hidden" name="pro_photo" value="${list.pro_photo }"/>
							<td><img src="<%=request.getContextPath() %>/upload/${list.pro_photo }" class="cartImage"></td>
							<td>${list.pro_name }<br>[옵션 : ${list.color } / ${list.pro_size }]</td>
							<fmt:formatNumber var="price" value="${list.pro_price}" pattern="#,###"/>
							<td>${price }</td>
							<td>${list.bas_pro_qnt }</td>
							<td>${deliPrice}원</td>
							<fmt:formatNumber var="total2" value="${list.pro_price * list.bas_pro_qnt }" pattern="#,###"/>
							<td>${total2 }</td>
						</tr>
						</c:forEach>
					</c:when>
					
					<c:otherwise>
						<c:forEach var="list" items="${cartList }">
						<tr>
							<%-- <td><input type="checkbox" name="chk" value="${list.pro_det_num }" /></td> --%>
							<input type="hidden" name="directOrder" value="false"/>
							<input type="hidden" name="pro_det_num" value="${list.pro_det_num }" />
							<td><img src="<%=request.getContextPath() %>/upload/${list.pro_photo }"  class="cartImage"></td>
							<td>${list.pro_name }<br>[옵션 : ${list.color } / ${list.pro_size }]</td>
							<fmt:formatNumber var="price" value="${list.pro_price}" pattern="#,###"/>
							<td>${price }</td>
							<td>${list.bas_pro_qnt }</td>
							<td>${deliPrice}원</td>
							<fmt:formatNumber var="total2" value="${list.pro_price * list.bas_pro_qnt }" pattern="#,###"/>
							<td>${total2 }</td>
						</tr>
						</c:forEach>
					</c:otherwise>
				</c:choose>			
				<!-- 여기까지 상품 리스트 출력 -->
				
				<tr>
					<fmt:formatNumber var="total3" value="${totalMoney }" pattern="#,###"/>
					<fmt:formatNumber var="deli_price" value="${deliPrice }" pattern="#,###"/>
					<fmt:formatNumber var="total4" value="${totalMoney+deliPrice }" pattern="#,###"/>
					<td colspan="8" style="text-align: right;">상품구매금액 (${total3 }) + 배송비 (${deli_price })
						= 합계: ${total4}원</td>
				</tr>

			</tbody>
		</table>
	</div>

	<br><br>

	<div class="join_table">
		<table border="1">
			<h3 class="join_title">주문정보</h3>
			<tbody>
				<tr>
					<th><label for="name">주문하시는 분</label><b class="req">*</b></th>
					<td><input type="text" value="${member.user_name }" name="user_name" id="name" required/>
					</td>
				</tr>
				<tr>
					<th>주소<b class="req">*</b></th>
					<td><input type="text" value="${member.postcode }" name="postcode" id="postcode" size="6" readonly /> 
					<a href="javascript:void(0);" id="zipSearch" class="small_btn" >우편번호</a><br>
						<input type="text" value="${member.addr1 }" name="addr1" id="addr1" size=50 readonly />기본주소<br>
						<input type="text" value="${member.addr2 }" name="addr2" id="addr2" size=50 required/> 상세주소</td>
				</tr>
				<tr>
					<th><label for="tel">휴대전화</label><b class="req">*</b></th>
					<td><input type="tel" value="${member.tel }"name="tel" id="tel" required /></td>
				</tr>
				<tr>
					<th><label for="email">이메일 주소</label><b class="req">*</b></th>
					<td><input type="text" value="${member.email }" name="email" id="email" required /></td>
				</tr>
			</tbody>
		</table>
	</div>

	<div class="join_table">
		<table border="1">
			<h3 class="join_title">배송정보</h3>
			<tbody>
				<tr>
					<th><label for="deli_add">배송지 선택</label></th>
					<td>
						<label><input type="checkbox" name="sameaddr" id="sameaddr" value="ok" onClick="sameAddr();">주문자 정보와 동일</label>
						<!-- <label><input type="radio" name="deli_add" value="new">새로운 배송지</label> -->
						<!-- <label><input type="button" value="주소록" name="addrBook" onClick="window.open('addrBook.jsp?openInit=true','','width=500, height=300')"></label> -->
					</td>
				<tr>
					<th><label for="name">받으시는 분</label><b class="req">*</b></th>
					<td><input type="text" name="recName" id="recName" required/>
					</td>
				</tr>
				<tr>
					<th>주소<b class="req">*</b></th>
					<td><input type="text" name="recPostcode" id="recPostcode" value="" size="6" readonly/> 
					<a href="javascript:void(0);" name="zipSearch" id="zipSearch" class="small_btn" onClick="openPostcode()">우편번호</a><br>
						<input type="text" name="recAddr1" id="recAddr1" size=50 readonly /> 기본주소<br>
						<input type="text" name="recAddr2" id="redAddr2" size=50 required/>상세주소</td>
				</tr>
				<tr>
					<th><label for="tel">휴대전화</label><b class="req">*</b></th>
					<td><input type="tel" name="recTel" id="recTel" required/></td>
				</tr>
				<tr>
					<th><label for="deli_content">배송메시지</label><b class="req"></b></th>
					<td><input type="text" name="deli_content" id="deli_content" /></td>
				</tr>
			</tbody>
		</table>
	</div>
	
	<div class="join_table">
		
		<table border="1">
			<h3 class="join_title">추가 정보</h3>
			<tbody>
				<tr>
					<th><label for="deli_add">구매 전 참고사항</label></th>
					<td>
						<label><input type="checkbox" name="termCheck1" id="termCheck1" value="termCheck1">동의합니다</label><br>
						<p>① 배송 전 변경/취소는 오전 11시 이전 요청 건에 한해서 가능합니다.</p>
						<p>② 주문 후 입금확인이 완료되면 상품 준비기간은 평균 2-5일(공휴일, 주말 제외) 정도 소요됩니다.</p>
						<p>③ 당일발송 상품은 오후 3시 이전까지 결제 완료된 주문건에 한해 출고됩니다.</p>
					</td>
				<tr>
					<th><label for="deli_add">구매 전 참고사항</label></th>
					<td>
						<label><input type="checkbox" name="termCheck2" id="termCheck2" value="termCheck2">동의합니다</label><br>
						<p>④ 적립금은 취소/반품 시 자동 소멸되오니, 신중한 구매 부탁드립니다:-)</p>
						<p>⑤ 해외배송의 경우 주문 후 꼭! 1시간 이내로 게시판에 글 남겨주셔야 신속한 처리가 가능합니다.</p>
						<p>⑥ 무통장 입금의 경우 주문일로부터 3일이내 입금해주셔야하며, 이후엔 주문건이 자동 취소됩니다.</p>
					</td>
				</tr>
			</tbody>
		</table>
	</div>
	
	<div class="join_table">
		<table border="1">
			<h3 class="join_title">결제 예정 금액</h3>
			<tbody>
				<colgroup>
					<col style="width:33%"/>
					<col style="width:33%"/>
					<col style="width:33%"/>
				</colgroup>
				<tr>
					<th scope="col">총 주문 금액</th>
					<th scope="col">사용 적립금</th>
					<th scope="col">총 결제예정 금액</th>
				</tr>
				<tr>
					<th>${total4 }원</th>
					<th>-&nbsp;<input type="text" id="point" value="0" size="3" class="no-line" readonly>원</th>
					<fmt:formatNumber var="result" value="${totalMoney + deliPrice}" pattern="#,###"/>
					<th>=<input type="text" id="result" name="result" value="${result}" class="no-line" readonly>원</th>
				</tr>
			</tbody>
		</table>
		<table>
			<tr>
				<th>적립금 사용</th>
				<td><input type="text" name="usePoint" id="usePoint" value="0" size="5" onkeypress="onlyNumber();" >원 
				<fmt:formatNumber var="point" value="${memberPoint.point_final == null ? '0' : memberPoint.point_final }" pattern="#,###"/>
				<a href="javascript:void(0);" onclick="inqPoint();" class="small_btn">사용하기</a>(현재 적립금:${point}원)
				<br>※(적립금 포함으로 결제하실 경우, 해당 주문건은 적립금이 지급되지 않습니다.)
				</td>
			</tr>
		</table>
	</div>
	
	<div class="join_table">
		<table border="1">
			<h3 class="join_title">결제정보</h3>
			<tbody>
				<tr>
					
					<td colspan="2" style="border-right:1px solid #d7d5d5;">
						<label><input type="radio" name="payment" value="mutong" checked>무통장입금
						<input type="radio" name="payment" value="silsi">실시간 계좌이체
						<input type="radio" name="payment" value="credit">카드결제</label>
					</td>
					<td rowspan="3" style="text-align: right;">			
						최종결제금액<br>
						<fmt:formatNumber var="result2" value="${totalMoney + deliPrice}" pattern="#,###"/>
						<input type="text" id="result2" name="result2" value="${result2}" class="no-line" readonly>원<br>
						<input type="checkbox" id="termCheck3" name="termCheck3"/>결제정보를 확인하였으며, 구매진행에 동의합니다.<br>
						<a href="javascript:chkForm(document.f);" class="order_button">결제하기</a>
						<fmt:formatNumber var="applyRate" value="${totalMoney * (pointMan.p_rate*0.01)}" pattern="#,###"/>
						<br>구매확정 시, 
						
						<span id="r3">
						<c:choose>
							<c:when test="${pointMan.p_mark eq 'per'}">구매금액의 ${pointMan.p_rate }</c:when>
							<c:when test="${pointMan.p_mark eq 'won'}">${applyRate}원</c:when>
							<c:when test="${pointMan.p_mark eq 'double'}">${applyRate}원(${pointMan.p_rate }%)</c:when>
						</c:choose>
						</span>
						&nbsp;적립 예정
						
					</td>
						
				
				<div id="mutong" style="display: none;">	
				
					<tr>
						<th><label for="name">입금자명</label><b class="req">*</b></th>
						<td><input type="text" name="account_name" id="account_name" required>
						</td>
					</tr>
					<tr>				
						<th><label for="bank">입금은행</label><b class="req">*</b></th>
						<td><select id="bank" name="bank">
							    <option value="">입금은행 선택</option>
							    <option value="kookmin">국민은행:0000000000000(주)희희</option>
							    <option value="daegu">대구은행:0000000000000(주)희희</option>
							    <option value="nonghyeop">농협:000000000000(주)희희</option>
							</select>
						</td>
					</tr>
					
				</div>
				
				<div id="sil" style="display: none;">
				실시간 계좌이체
				
				</div>
				
				<div id="card" style='display:none'>
				카드결제
				</div>
				
			</tbody>
		</table>
		<input type="hidden" name="total1" value="${total3 }"><!-- orderResult.jsp 총 상품금액 -->
		<!--name=result2 orderResult.jsp 최종결제금액 -->
		<input type="hidden" name="reviewPoint" id="reviewPoint" value="${pointMan.p_review }">
		<input type="hidden" name="confrimPoint" id="confrimPoint" value="${applyRate }">
	
	</div>
	</form>
</div>
