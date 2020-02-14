<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>

<script>
function sample6_execDaumPostcode() {
	new daum.Postcode(
		{
			oncomplete : function(data) {
				// 팝업에서 검색결과 항목을 클릭했을때 실행할 코드를 작성하는 부분.

					// 각 주소의 노출 규칙에 따라 주소를 조합한다.
					// 내려오는 변수가 값이 없는 경우엔 공백('')값을 가지므로, 이를 참고하여 분기 한다.
			var fullAddr = ''; // 최종 주소 변수
			var extraAddr = ''; // 조합형 주소 변수

					// 사용자가 선택한 주소 타입에 따라 해당 주소 값을 가져온다.
			if (data.userSelectedType === 'R') { // 사용자가 도로명 주소를 선택했을 경우
				fullAddr = data.roadAddress;

			} else { // 사용자가 지번 주소를 선택했을 경우(J)
				fullAddr = data.jibunAddress;
			}

			// 사용자가 선택한 주소가 도로명 타입일때 조합한다.
			if (data.userSelectedType === 'R') {
				//법정동명이 있을 경우 추가한다.
				if (data.bname !== '') {
					extraAddr += data.bname;
				}
				// 건물명이 있을 경우 추가한다.
				if (data.buildingName !== '') {
					extraAddr += (extraAddr !== '' ? ', '
							+ data.buildingName : data.buildingName);
				}
				// 조합형주소의 유무에 따라 양쪽에 괄호를 추가하여 최종 주소를 만든다.
				fullAddr += (extraAddr !== '' ? ' (' + extraAddr
						+ ')' : '');
			}

			// 우편번호와 주소 정보를 해당 필드에 넣는다.
			document.getElementById('postcode').value = data.zonecode; //5자리 새우편번호 사용
			document.getElementById('addr1').value = fullAddr;

			// 커서를 상세주소 필드로 이동한다.
			document.getElementById('addr2').focus();
		}
	}).open();
}

</script>
</head>
<body>


	<div class="order_list">
		<table border="1">
			<h3 class="order_list">주문내역</h3>
			<tbody>
				<tr>
					<td><input type="checkbox" /></td>
					<th>상품이미지</th>
					<th>상품정보</th>
					<th>판매가</th>
					<th>수량</th>
					<th>적립금</th>
					<th>배송비</th>
					<th>합계</th>
				</tr>
				<!-- 여기서부터 상품 리스트 출력 -->
				<c:choose>
					<c:when test="${totalMoney < 50000 }">
						<c:set var="deliPrice" value="2500"/>
					</c:when>
					<c:otherwise>
						<c:set var="deliPrice" value="0"/>
					</c:otherwise>
				</c:choose>
				
				<c:forEach var="list" items="${cartList }">
				<tr>
					<td><input type="checkbox" name="chk" value="${list.pro_det_num }" /></td>
					<td><img src="<%=request.getContextPath() %>/upload/${list.pro_photo }"></td>
					<td>${list.pro_name }<br>[옵션 : ${list.color } / ${list.pro_size }]</td>
					<fmt:formatNumber var="price" value="${list.pro_price}" pattern="#,###"/>
					<td>${price }</td>
					<td>${list.bas_pro_qnt }</td>
					<td>적립금</td>
					<td>${deliPrice}원</td>
					<fmt:formatNumber var="total2" value="${list.pro_price * list.bas_pro_qnt }" pattern="#,###"/>
					<td>${total2 }</td>
				</tr>
				</c:forEach>
				<!-- 여기까지 상품 리스트 출력 -->


				<tr>
					<td colspan="8" style="text-align: left;"><input type="button"
						value="선택삭제">
				</tr>
				
				
				
				
				<tr>
					<fmt:formatNumber var="total3" value="${totalMoney }" pattern="#,###"/>
					<td colspan="8" style="text-align: right;">상품구매금액 (${totalMoney }) + 배송비 (${deliPrice })
						= 합계: ${totalMoney }원</td>
				</tr>

			</tbody>
		</table>
	</div>

	<br><br>

	<div class="order_table">
		<table border="1">
			<h3 class="order_title">주문정보</h3>
			<tbody>
				<tr>
					<th><label for="name">주문하시는 분</label><b class="req">*</b></th>
					<td><input type="text" name="user_name" id="name" required />
					</td>
				</tr>
				<tr>
					<th>주소<b class="req">*</b></th>
					<td><input type="text" name="postcode" id="postcode" size="6"
						readonly /> <a href="javascript:void(0);" name="zipSearch"
						id="zipSearch" class="btn" onclick="sample6_execDaumPostcode()">우편번호</a><br>
						<input type="text" name="addr1" id="addr1"
						value="<%//=rs.getString("addr1")%>" size=50 readonly /> 기본주소<br>
						<input type="text" name="addr2" id="addr2"
						value="<%//=rs.getString("addr2")%>" size=50 /> 상세주소</td>
				</tr>
				<tr>
					<th><label for="tel">휴대전화</label><b class="req">*</b></th>
					<td><input type="tel" name="tel" id="tel" required /></td>
				</tr>
				<tr>
					<th><label for="email">이메일 주소</label><b class="req">*</b></th>
					<td><input type="text" name="email" id="email" required /></td>
				</tr>
			</tbody>
		</table>
	</div>


<br><br>

<div class="deli_table">
		<table border="1">
			<h3 class="deli_title">배송정보</h3>
			<tbody>
				<tr>
					<th><label for="deli_add">배송지 선택</label></th>
					<td>
						<label><input type="radio" name="deli_add" value="same">주문자 정보와 동일</label>
						<label><input type="radio" name="deli_add" value="new">새로운 배송지</label>
						<label><input type="button" value="주소록" name="addrBook" onClick="window.open('addrBook.jsp?openInit=true','','width=500, height=300')"></label>
					</td>
				<tr>
					<th><label for="name">받으시는 분</label><b class="req">*</b></th>
					<td><input type="text" name="user_name" id="name" required />
					</td>
				</tr>
				<tr>
					<th>주소<b class="req">*</b></th>
					<td><input type="text" name="postcode" id="postcode" size="6"
						readonly /> <a href="javascript:void(0);" name="zipSearch"
						id="zipSearch" class="btn" onclick="sample6_execDaumPostcode()">우편번호</a><br>
						<input type="text" name="addr1" id="addr1"
						value="<%//=rs.getString("addr1")%>" size=50 readonly /> 기본주소<br>
						<input type="text" name="addr2" id="addr2"
						value="<%//=rs.getString("addr2")%>" size=50 /> 상세주소</td>
				</tr>
				<tr>
					<th><label for="tel">휴대전화</label><b class="req">*</b></th>
					<td><input type="tel" name="tel" id="tel" required /></td>
				</tr>
				<tr>
					<th><label for="deli_content">배송메시지</label><b class="req"></b></th>
					<td><input type="text" name="deli_content" id="deli_content" required /></td>
				</tr>
			</tbody>
		</table>
	</div>
	
	<div class="term">
		<table border="1">
			<h3 class="term">추가 정보</h3>
			<tbody>
				<tr>
					<th><label for="deli_add">구매 전 참고사항</label></th>
					<td>
						<label><input type="checkbox" name="term_agree1" value="term_agree1">동의합니다</label><br>
						(① 배송 전 변경/취소는 오전 11시 이전 요청 건에 한해서 가능합니다. ② 주문 후 입금확인이 완료되면 상품 준비기간은 평균 2-5일(공휴일, 주말 제외) 정도 소요됩니다. ③ 당일발송 상품은 오후 3시 이전까지 결제 완료된 주문건에 한해 출고됩니다.)
					</td>
				<tr>
					<th><label for="deli_add">구매 전 참고사항</label></th>
					<td>
						<label><input type="checkbox" name="term_agree2" value="term_agree2">동의합니다</label><br>
						(④ 적립금은 취소/반품 시 자동 소멸되오니, 신중한 구매 부탁드립니다:-) ⑤ 해외배송의 경우 주문 후 꼭! 1시간 이내로 게시판에 글 남겨주셔야 신속한 처리가 가능합니다. ⑥ 무통장 입금의 경우 주문일로부터 3일이내 입금해주셔야하며, 이후엔 주문건이 자동 취소됩니다.)
					</td>
				</tr>
			</tbody>
		</table>
	</div>
	
	<div class="payment">
		<table border="1">
			<h3 class="term">결제 예정 금액</h3>
			<tbody>
				<tr>
					<th>총 주문 금액</th>
					<th>사용 적립금</th>
					<th>총 결제예정 금액</th>
				</tr>
				<tr>
					<td>${totalMoney }원</td>
					<td>-0원</td>
					<td>=${totalMoney }원</td>
			</tbody>
		</table>
		<table>
			<tr>
				<th>적립금</th>
				<td><input type="text" name="point" id="point">원 (현재 가용 적립금:()원)</td>
			</tr>
		</table>
	</div>
	
	<div class="pay_info">
		<table border="1">
			<h3 class="pay_info">결제정보</h3>
			<tbody>
				<tr>
					
					<td colspan="2">
						<label><input type="radio" name="payment" value="noneAccount" selected>무통장입금
						<input type="radio" name="payment" value="transfer">실시간 계좌이체
						<input type="radio" name="payment" value="credit">카드결제</label>
					</td>
					<td rowspan="3" style="text-align: right;">			
						최종결제금액<br>
						${totalMoney }원<br>
						<input type="checkbox" name="termCheck"/>결제정보를 확인하였으며, 구매진행에 동의합니다.<br>
						<input type="button" name="pay" value="결제하기"/><br>
						적립예정금액:()원
					</td>	
					
				<tr>
					<th><label for="name">입금자명</label><b class="req">*</b></th>
					<td><input type="text" name="account_name" id="name" required />
					</td>
				</tr>
				<tr>
				
					<th><label for="bank">입금은행</label><b class="req">*</b></th>
					<td><select name="bank">
						    <option value="">입금은행 선택</option>
						    <option name="kookmin" value="kookmin">국민은행:0000000000000(주)희희</option>
						    <option name="daegu" value="daegu">대구은행:0000000000000(주)희희</option>
						    <option name="nonghyeop" value="nonghyeop">농협:000000000000(주)희희</option>
						</select>
					</td>
				</tr>
				
				
			</tbody>
		</table>
	</div>
					
</body>
</html>