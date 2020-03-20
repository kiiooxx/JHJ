<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<style>
.thumb {
	width: 50px;
}


var windowWidth = 200;
var windowHeight = 200;
var windowLeft = parseInt((screen.availWidth/2) - (windowWidth/2));
var windowTop = parseInt((screen.availHeight/2) - (windowHeight/2));
var windowSize = "width=" + windowWidth + ",height=" + windowHeight + "left=" + windowLeft + ",top=" + windowTop + "screenX=" + windowLeft + ",screenY=" + windowTop;



</style>
</head>
<body>



	<div class="titleArea">
		<h2>ORDER</h2>
	</div>

	<div class="orderhistorytab ">
		<ul>
			<li class="tab_class"><a href="##">주문내역조회 (0)</a></li>
			<li class="tab_class_cs"><a href="##">취소/반품/교환 내역 (0)</a></li>
			<li class="tab_class_old displaynone"><a href="##">이전 주문내역
					(0)</a></li>
		</ul>
	</div>

	<form action="order.mem" method="post" id="OrderHistoryForm"
		name="OrderHistoryForm">
		<div class="xans-element- xans-myshop xans-myshop-orderhistoryhead ">
			<fieldset>
				<legend>검색기간설정</legend>
				<span> <a href="#none" class="btnNormal" days="00"><img
						src="http://img.echosting.cafe24.com/skin/base_ko_KR/myshop/btn_date1.gif"
						offimage="http://img.echosting.cafe24.com/skin/base_ko_KR/myshop/btn_date1.gif"
						onimage="http://img.echosting.cafe24.com/skin/base_ko_KR/myshop/btn_date1_on.gif"
						alt="오늘" /></a> <a href="#none" class="btnNormal" days="07"><img
						src="http://img.echosting.cafe24.com/skin/base_ko_KR/myshop/btn_date2.gif"
						offimage="http://img.echosting.cafe24.com/skin/base_ko_KR/myshop/btn_date2.gif"
						onimage="http://img.echosting.cafe24.com/skin/base_ko_KR/myshop/btn_date2_on.gif"
						alt="1주일" /></a> <a href="#none" class="btnNormal" days="30"><img
						src="http://img.echosting.cafe24.com/skin/base_ko_KR/myshop/btn_date3.gif"
						offimage="http://img.echosting.cafe24.com/skin/base_ko_KR/myshop/btn_date3.gif"
						onimage="http://img.echosting.cafe24.com/skin/base_ko_KR/myshop/btn_date3_on.gif"
						alt="1개월" /></a> <a href="#none" class="btnNormal" days="90"><img
						src="http://img.echosting.cafe24.com/skin/base_ko_KR/myshop/btn_date4.gif"
						offimage="http://img.echosting.cafe24.com/skin/base_ko_KR/myshop/btn_date4.gif"
						onimage="http://img.echosting.cafe24.com/skin/base_ko_KR/myshop/btn_date4_on.gif"
						alt="3개월" /></a> <a href="#none" class="btnNormal" days="180"><img
						src="http://img.echosting.cafe24.com/skin/base_ko_KR/myshop/btn_date5.gif"
						offimage="http://img.echosting.cafe24.com/skin/base_ko_KR/myshop/btn_date5.gif"
						onimage="http://img.echosting.cafe24.com/skin/base_ko_KR/myshop/btn_date5_on.gif"
						alt="6개월" /></a>
				</span> <input id="history_start_date" name="history_start_date"
					class="fText" readonly="readonly" size="10" value="2019-12-18"
					type="text" /> ~ <input id="history_end_date"
					name="history_end_date" class="fText" readonly="readonly" size="10"
					value="2020-03-17" type="text" /> <input alt="조회"
					id="order_search_btn" type="image"
					src="//img.echosting.cafe24.com/skin/admin_ko_KR/myshop/btn_search.gif" />
			</fieldset>
		</div>
		<input id="mode" name="mode" value="" type="hidden" /> <input
			id="term" name="term" value="" type="hidden" />
		<!-- </form> -->
	
			<!--
        $login_url = /member/login.html
    -->
			<table border="1" summary="">
				<caption>주문주문 상품 정보 상품 정보 목록</caption>
				<thead>
					<tr>
						<th>ORDER NO</th>
						<th>-</th>
						<th>ITEM</th>
						<th>TOTAL PRICE</th>
						<th>상태</th>
						<th>주문취소</th>
						
						
						
						
						
					</tr>
				</thead>

				<c:forEach var="orderlist" items="${orderList }" varStatus="i">
					<c:if test="${orderlist.sel_num != null }">
						<tr>

							<c:choose>
								<c:when test="${i.index == 0 || sel_num != orderlist.sel_num }">
									<c:set var="sel_num" value="${orderlist.sel_num }" />
									<td><a href="orderdetail.mem?sel_num=${orderlist.sel_num}">
									${orderlist.sel_num }</td>
								</c:when>
								<c:otherwise>
									<td></td>
								</c:otherwise>
							</c:choose>

							<td><a href="productDetail.pro?pro_num=${orderlist.pro_num}"><img
								src="<%=request.getContextPath() %>/upload/${orderlist.pro_photo}"
								class="thumb"></a>
								
							</td>

							<td>
								${orderlist.pro_name }
								<br>
								${orderlist.pro_price} / ${orderlist.pro_qnt}개
							</td>
							
							<td>${orderlist.final_price}</td>
							
							<td><c:if test="${orderlist.sel_status eq 'order_done' }">주문완료</c:if>
								<c:if test="${orderlist.sel_status eq 'check_paid' }">결제확인</c:if>
								<c:if test="${orderlist.sel_status eq 'send_pro' }">상품발송</c:if>
								<c:if test="${orderlist.sel_status eq 'deli_ing' }">배송중</c:if> <c:if
									test="${orderlist.sel_status eq 'deli_fin' }">배송완료</c:if> <c:if
									test="${orderlist.sel_status eq 'order_confirm' }">구매확정</c:if></td>
							<td>${orderlist.cancel_req}
							<a href="#none" class="displaynone" onclick=""><img src="http://img.echosting.cafe24.com/skin/base_ko_KR/myshop/btn_order_cancel.gif" alt="주문취소"/></a></td>
						</tr>
					</c:if>
				</c:forEach>



				
				<tbody class="">
					<tr>
						<td colspan="7" class="empty">주문 내역이 없습니다</td>
					</tr>
				</tbody>
			</table>
		</div>


		<div id="Page_Nav"
			class="xans-element- xans-myshop xans-myshop-orderhistorypaging">
			<span><a
				href="?page=1&history_start_date=2019-12-18&history_end_date=2020-03-17&past_year=2019">&lt;&lt;</a></span>
			<span><a href="">&lt;</a></span>
			<ol>
				<li class="xans-record-"><a
					href="?page=1&history_start_date=2019-12-18&history_end_date=2020-03-17&past_year=2019"
					class="this">1</a></li>
			</ol>
			<span><a
				href="?page=1&history_start_date=2019-12-18&history_end_date=2020-03-17&past_year=2019">&gt;</a></span>
			<span><a
				href="?page=1&history_start_date=2019-12-18&history_end_date=2020-03-17&past_year=2019">&gt;&gt;</a></span>
		</div>
		</div>
		</div>
		<div id="footer" class="xans-element- xans-layout xans-layout-footer ">
			<ul class="xans-element- xans-layout xans-layout-info left ">
				C/S
				<br />





			</ul>
		</div>
	</form>

</body>
</html>