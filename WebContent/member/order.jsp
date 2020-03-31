<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<div class="orderArea">
	<h3>ORDER</h3>
	
	<div class="orderList_table">
		<table>
			<colgroup>
				<col style="width:150px;">
				<col style="width:92px;">	
				<col style="width:auto;">
				<col style="width:100px;">
				<col style="width:120px;">
				<col style="width:100px;">
				<col style="width:100px;">
			</colgroup>

			<tr>
				<th scope="col">주문일자<br>(주문번호)</th>
				<th scope="col">이미지</th>
				<th scope="col">상품정보</th>
				<th scope="col">총 금액</th>
				<th scope="col">상태</th>
				<th scope="col">문의</th>
			</tr>
			
			<c:set var="size" value="${fn:length(order) }"/>
			<c:if test="${size>0 }">
				<c:forEach var="list" items="${order }" varStatus="i">
					
					<tr>
						<td rowspan="${row_cnt }">
							<!-- 날짜 형식 바꿔주기 (yyyy-MM-dd) -->
							<fmt:parseDate value="${list.sel_date }" var="sel_date" pattern="yyyy-MM-dd HH:mm:ss"/>
							<fmt:formatDate var="dateFmt" value="${sel_date}" pattern="yyyy-MM-dd"/>
							${dateFmt}<br>
							<a href="orderdetail.mem?sel_num=${list.sel_num}">[${list.sel_num }]</a>
						</td>
						
						<td>
							<a href="productDetail.pro?pro_num=${list.pro_num}">
							<img src="<%=request.getContextPath() %>/upload/${list.pro_photo}" class="cartImage"></a>
						</td>
		
						<td class="item_info">
							&nbsp;${list.pro_name }
							<br>
							<!-- 가격 형식 -->
							<fmt:formatNumber var="price" value="${list.pro_price}" pattern="#,###"/>
							&nbsp;${price} / ${list.pro_qnt}개
						</td>
						
						<!-- 가격 형식 -->
						<fmt:formatNumber var="final_price" value="${list.final_price}" pattern="#,###"/>
						<td>${final_price}</td>
							
							
						<td rowspan="${row_cnt }">
							<c:if test="${list.cancel_req == 'N'.charAt(0) }">
								<c:if test="${list.sel_status eq 'order_done' }">주문완료</c:if>
								<c:if test="${list.sel_status eq 'check_paid' }">결제확인</c:if>
								<c:if test="${list.sel_status eq 'send_pro' }">상품발송</c:if>
								<c:if test="${list.sel_status eq 'deli_ing' }">배송중</c:if> 
								<c:if test="${list.sel_status eq 'deli_fin' }">배송완료</c:if> 
								<c:if test="${list.sel_status eq 'order_confirm' }">구매확정</c:if>
							</c:if>
							<c:if test="${list.cancel_req == 'Y'.charAt(0) }">주문취소 요청중</c:if>
							<c:if test="${list.cancel_req == 'C'.charAt(0) }">주문취소 완료</c:if>
							
							<c:choose>
								<c:when test="${list.sel_status eq 'deli_ing' || list.sel_status eq 'deli_fin'}">
									<br><a href="ordercheck.mem?sel_num=${list.sel_num }" class="small_btn" onclick="">구매확정</a>
								</c:when>
								
								<c:when test="${list.sel_status eq 'order_done' }">
									<br><a href="ordercancel.mem?sel_num=${list.sel_num }" class="small_btn" onclick="">주문취소</a>
								</c:when>
							</c:choose>
						</td>
								
						<td>
							<a href="boardWriteForm.bo?board_type=qna&pro_num=${list.pro_num }&sel_num=${list.sel_num}" class="small_btn">문의작성</a>
						</td>
						
					</tr>
		
					
				</c:forEach>
			</c:if>

			<c:if test="${size==0 }">
				<tr>
					<td colspan="6" class="empty">주문 내역이 없습니다</td>
				</tr>
			</c:if>
		</table>
	</div>


		<!-- 페이지 리스트 -->
		<div id="pageList">
			<c:if test="${pageInfo.endPage > 0}">
				<ol>
				<c:choose>
					<c:when test="${pageInfo.page <= 1 }">
						<li> < </li>
					</c:when>
					<c:otherwise>
						<li><a href="order.mem?page=${pageInfo.page-1 }"> < </a></li>
					</c:otherwise>
				</c:choose>
				
				
				<c:forEach var="a" begin="${pageInfo.startPage }" end="${pageInfo.endPage }" step="1">
					<c:choose>
						<c:when test="${a == pageInfo.page }">
							<li>[${a}]</li>
						</c:when>
						<c:otherwise>
							<li><a href="order.mem?page=${a}">[${a}]</a></li>
						</c:otherwise>
					</c:choose>
				</c:forEach>
				
				
				<c:choose>
					<c:when test="${pageInfo.page>=pageInfo.maxPage }">
						<li> > </li>
					</c:when>
					<c:otherwise>
						<li><a href="order.mem?page=${pageInfo.page+1 }"> > </a></li>
					</c:otherwise>
				</c:choose>
				</ol>
			</c:if>
		</div>
</div>