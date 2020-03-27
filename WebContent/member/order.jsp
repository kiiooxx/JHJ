<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<style>
	.board_table tr, .board_table th {
		 border : 1px solid #ddd;
	}

</style>

<div class="orderArea">
	<h3>ORDER</h3>

	<div class="orderhistorytab">
		<ul>
			<li class="tab_class"><a href="##">주문내역조회 (0)</a></li>
			<li class="tab_class_cs"><a href="##">취소내역 (0)</a></li>
		</ul>
	</div>

	<!-- 날짜 검색 -->
	<div id="OrderHistoryForm">
		<form action="order.mem" method="post" name="OrderHistoryForm">
		
			<fieldset>
				<span>
					<a href="#" class="small_btn">오늘</a>
					<a href="#" class="small_btn">1주일</a>
					<a href="#" class="small_btn">1개월</a>
					<a href="#" class="small_btn">3개월</a>
					<a href="#" class="small_btn">6개월</a>
				</span>
				<input id="history_start_date" name="history_start_date" class="fText" readonly="readonly" size="10" value="2019-12-18" type="text" />
					~ 
				<input id="history_end_date" name="history_end_date" class="fText" readonly="readonly" size="10" value="2020-03-17" type="text" />
				<a href="#" class="small_btn">조회</a>
			</fieldset>	
		</form>
	</div>
	
	
	<div class="board_table">
		<table>
			<colgroup>
				<col style="width:90px;">
				<col style="width:92px;">	
				<col style="width:auto;">
				<col style="width:100px;">
				<col style="width:100px;">
				<col style="width:100px;">
				<col style="width:100px;">
			</colgroup>

			<tr>
				<th scope="col">ORDER NO</th>
				<th scope="col">IMAGE</th>
				<th scope="col">ITEM</th>
				<th scope="col">TOTAL PRICE</th>
				<th scope="col">상태</th>
				<th scope="col">주문취소</th>
				<th scope="col">문의/리뷰</th>
			</tr>
			
			<c:if test="${orderInfo != null }">
				<c:forEach var="orderlist" items="${orderList }" varStatus="i">
					<c:set var="new_sel_num" value="false"/>
					
					
					<c:if test="${i.index == 0 || sel_num != orderlist.sel_num }">
						<c:set var="new_sel_num" value="true"/>
					</c:if>
					<c:set var="sel_num" value="${orderlist.sel_num }" />
					<c:set var="row_cnt" value="${0 }"/>
					<c:forEach var="orderlist2" items="${orderList }" varStatus="j">
						<c:if test="${orderlist2.sel_num == sel_num }">
							<c:set var="row_cnt" value="${row_cnt + 1 }"/>
						</c:if>
					</c:forEach>
					
					
					<tr>
						<c:if test="${new_sel_num == true }">
							<th rowspan="${row_cnt }">
								<a href="orderdetail.mem?sel_num=${orderlist.sel_num}">${orderlist.sel_num }</a>
							</th>
						</c:if>
						
						<th>
							<a href="producthetail.pro?pro_num=${orderlist.pro_num}">
							<img src="<%=request.getContextPath() %>/upload/${orderlist.pro_photo}" class="cartImage"></a>
						</th>
		
						<th class="item_info">
							&nbsp;${orderlist.pro_name }
							<br>
							<!-- 가격 형식 -->
							<fmt:formatNumber var="price" value="${orderlist.pro_price}" pattern="#,###"/>
							&nbsp;${price} / ${orderlist.pro_qnt}개
						</th>
						
							<!-- 가격 형식 -->
							<fmt:formatNumber var="final_price" value="${orderlist.final_price}" pattern="#,###"/>
						<th>${final_price}</th>
							
							
						<c:if test="${new_sel_num == true }">
							<th rowspan="${row_cnt }">
								<c:if test="${orderlist.cancel_req == 'N'.charAt(0) }">
									<c:if test="${orderlist.sel_status eq 'order_done' }">주문완료</c:if>
									<c:if test="${orderlist.sel_status eq 'check_paid' }">결제확인</c:if>
									<c:if test="${orderlist.sel_status eq 'send_pro' }">상품발송</c:if>
									<c:if test="${orderlist.sel_status eq 'deli_ing' }">배송중</c:if> 
									<c:if test="${orderlist.sel_status eq 'deli_fin' }">배송완료</c:if> 
									<c:if test="${orderlist.sel_status eq 'order_confirm' }">구매확정</c:if>
								</c:if>
								<c:if test="${orderlist.cancel_req == 'Y'.charAt(0) }">주문취소 요청중</c:if>
								<c:if test="${orderlist.cancel_req == 'C'.charAt(0) }">주문취소 완료</c:if>
							</th>

							<th rowspan="${row_cnt }">
								<c:choose>
									<c:when test="${orderlist.sel_status eq 'deli_ing' || orderlist.sel_status eq 'deli_fin'}">
										<a href="ordercheck.mem?sel_num=${orderlist.sel_num }" class="small_btn" onclick="">구매확정</a>
									</c:when>
									
									<c:when test="${orderlist.cancel_req == 'N'.charAt(0) }">
										<a href="ordercancel.mem?sel_num=${orderlist.sel_num }" class="small_btn" onclick="">주문취소</a>
									</c:when>
								</c:choose>
							</th>
						</c:if>
								
						<th>
							<c:if test="${orderlist.sel_status eq 'order_confirm' }">
								<a href="boardWriteForm.bo?board_type=review&pro_num=${orderlist.pro_num }" class="small_btn">리뷰작성</a><br><br>
							</c:if>
							<a href="boardWriteForm.bo?board_type=qna&pro_num=${orderlist.pro_num }&sel_num=${orderlist.sel_num}" class="small_btn">문의작성</a>
						</th>
					</tr>
		
					
				</c:forEach>
			</c:if>

			<c:if test="${orderInfo == null }">
				<tr>
					<td colspan="7" class="empty">주문 내역이 없습니다</td>
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
				
				
				<c:forEach var="pglist" begin="${pageInfo.startPage }" end="${pageInfo.endPage }" step="1" varStatus="a">
					<c:choose>
						<c:when test="${a.count == pageInfo.page }">
							<li>[${a.count }]</li>
						</c:when>
						<c:otherwise>
							<li><a href="order.mem?page=${a.count }">[${a.count }]</a></li>
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