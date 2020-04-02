<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<style>
.thumb {
	width: 90px;
}
</style>

<div id="join_form">

		<div align ="center" class="titleArea">
			<h2>주문상세조회</h2>
		</div>
		<br><br>
		
		
		<!-- 주문정보 -->
		<div class="join_table">
			<table>
				<h3 class="join_title">주문 정보</h3>
					<tr>
						<th>주문번호</th>
						<td>${orderInfo.sel_num}</td>
					</tr>
					<tr>
						<th>주문일자</th>
						<td>${orderInfo.sel_date}</td>
					</tr>
					<tr>
						<th>주문자</th>
						<td>${orderInfo.user_name}</td>
					</tr>
					<tr>
						<th>주문처리상태</th>
						<td>
							<c:if test="${orderInfo.cancel_req == 'N'.charAt(0) }">
								<c:if test="${orderInfo.sel_status eq 'order_done' }">주문완료</c:if>
								<c:if test="${orderInfo.sel_status eq 'check_paid' }">결제확인</c:if>
								<c:if test="${orderInfo.sel_status eq 'send_pro' }">상품발송</c:if>
								<c:if test="${orderInfo.sel_status eq 'deli_ing' }">배송중</c:if>
								<c:if test="${orderInfo.sel_status eq 'deli_fin' }">배송완료</c:if>
								<c:if test="${orderInfo.sel_status eq 'order_confirm' }">구매확정</c:if>	
							</c:if>
							<c:if test="${orderInfo.cancel_req =='Y'.charAt(0) }">주문취소 요청중</c:if>
							<c:if test="${orderInfo.cancel_req =='C'.charAt(0) }">주문취소 완료</c:if>
							
							<c:if test="${orderInfo.sel_status eq 'deli_ing' || orderInfo.sel_status eq 'deli_fin'}">
								&nbsp;<a href="ordercheck.mem?sel_num=${orderInfo.sel_num }" class="displaynone" onclick="">구매확정</a>
							</c:if>
							<c:if test="${orderInfo.sel_status eq 'order_done' }">
								&nbsp;<a href="ordercancel.mem?sel_num=${orderInfo.sel_num }" class="displaynone" onclick=""><img src="http://img.echosting.cafe24.com/skin/base_ko_KR/myshop/btn_order_cancel.gif" alt="주문취소"/></a>
							</c:if>
						</td>
					</tr>
			</table>
		</div>
		
		
		<div class="cartList">
			<table class="cartTable">
				<h3 class="join_title">주문 상품 정보</h3>
					<colgroup>
						<col style="width:92px"/>
						<col style="width:auto"/>
						<col style="width:100px"/>
						<col style="width:100px"/>
						<col style="width:100px"/>
					</colgroup>
					
					<tr>
						<th scope="col">IMAGE</th>
						<th scope="col">INFO</th>
						<th scope="col">QTY</th>
						<th scope="col">PRICE</th>
						<th scope="col">REVIEW</th>
					</tr>

				<c:forEach items="${orderProList }" var="orderProList">
					<tr>
						<td>
							<a href="productDetail.pro?pro_num=${orderProList.pro_num }" >
								<img src="<%=request.getContextPath() %>/upload/${orderProList.pro_photo}" class="cartImage">
							</a>
						</td>
						<td class="left"><strong>${orderProList.pro_name}</strong>
							<div>[옵션: ${orderProList.color} / ${orderProList.pro_size }]</div>
						<td>${orderProList.pro_qnt}</td>
						<fmt:formatNumber var="pro_price" value="${orderProList.pro_price}" pattern="#,###"/>
						<td><strong>${pro_price}</strong></td>		
						<td>
							<c:choose>
							<c:when test="${orderInfo.sel_status eq 'order_confirm' }">
								<c:set var="review_cnt" value="0"/>
								<c:forEach var="reviewList" items="${reviewList }" varStatus="i">
									<c:choose>
										<c:when test="${reviewList.sel_num == orderInfo.sel_num && reviewList.pro_num == orderProList.pro_num}">
											<c:if test="${review_cnt == 0 }">
												리뷰작성 완료
												<c:set var="review_cnt" value="${review_cnt + 1 }"/>
											</c:if>
											
										</c:when>
										<c:otherwise>
											<c:if test="${review_cnt == 0 }">
												<a href="boardWriteForm.bo?board_type=review&pro_num=${orderProList.pro_num }&sel_num=${orderInfo.sel_num}" class="small_btn">리뷰작성</a>
												<c:set var="review_cnt" value="${review_cnt + 1 }"/>
											</c:if>
										</c:otherwise>
									</c:choose>
								</c:forEach>
							</c:when>
							<c:otherwise>
								-
							</c:otherwise>
							</c:choose>
						</td>
					</tr>
				</c:forEach>
				
				<fmt:formatNumber var="deli" value="${orderInfo.deli_price}" pattern="#,###"/>
				<fmt:formatNumber var="total3" value="${orderInfo.final_price - orderInfo.deli_price}" pattern="#,###"/>
				<fmt:formatNumber var="total4" value="${orderInfo.final_price}" pattern="#,###"/>
				<tr>
					<td colspan="6" class="right">
						상품 구매 금액(적립금 사용 포함) <b>${total3 }</b> + 배송비 = ${deli } ${deli == '0' ? '(무료)' : '' } 합계 = <strong>${total4 }</strong>
					</td>
				</tr>
			</table>
		</div>
		
		<div class="join_table">
			<table>
				<h3 class="join_title">배송지 정보</h3>
				<tr>
					<th>받는 사람</th>
					<td>${deliInfo.rec_name }</td>
				</tr>
				
				<tr>
					<th>전화번호</th>
					<td>${deliInfo.rec_tel}</td>
				</tr>
				
				<tr>
					<th>배송주소</th>
					<td>${deliInfo.deli_postcode } <br>
						${deliInfo.deli_addr1 } ${deliInfo.deli_addr2 }
					</td>
				</tr>
			</table>
		</div>
		
		<div class="join_table">
			<table>
				<h3 class="join_title">결제 정보</h3>
					<c:if test="${payInfo.pay_type eq 'mutong'}">
						<tr>
							<th>결제정보 </th>
							<td>무통장입금<br>
								입금자명 : ${payInfo.acc_name }, 계좌번호 : 
								<c:if test="${payInfo.acc_bank eq 'kookmin' }">국민은행:0000000000000(주)희희 </c:if>
								<c:if test="${payInfo.acc_bank eq 'daegu' }">대구은행:0000000000000(주)희희</c:if>
								<c:if test="${payInfo.acc_bank eq 'nonghyeop' }">농협:000000000000(주)희희</c:if>
							</td>
						</tr>
						<tr>
							<th>입금기한</th>
							<td>${payInfo.pay_exp }</td>
					</c:if>
					
					<c:if test="${payInfo.pay_type eq 'silsi'}">
						<tr>
							<th>결제정보</th>
							<td>실시간 계좌이체<br>
								예금주명 : ${payInfo.acc_name }, 입금은행 : ${payInfo.acc_bank }
							</td>
						</tr>
					</c:if>
					<c:if test="${payInfo.pay_type eq 'credit'}">
						<tr>
							<th>결제정보</th>
							<td>카드결제<br>
								현대(3210-****-****-****) - 일시불
							</td>
						</tr>
					</c:if>
					
				<tr>
					<th>총 주문금액</th>
					<fmt:formatNumber var="total5" value="${orderInfo.final_price}" pattern="#,###"/>
					<td>${total5 }원</td>
				</tr>
			</table>
		</div>
</div>