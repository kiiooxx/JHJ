<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ page import="vo.Member" %>    
<%@ page import="vo.Order" %>
<c:set var="pageInfo" value="${requestScope.pageInfo }"/>
<c:set var="member" value="${requestScope.member }"/>
<c:set var="order" value="${requestScope.order }"/>

<!-- ionicons -->
<script src="https://unpkg.com/ionicons@5.0.0/dist/ionicons.js"></script>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<style>
table{
   border: 1px solid;
   width: 70%;
}
th, td{
   border: 1px solid;
}
</style>
<body>
<form action="memberInfo.ad" method="post" name="b">
<div class="card shadow mb-4">
	<div class="card-header py-3">
		<h6 class="m-0 font-weight-bold text-primary">회원 정보</h6>
    </div>
    <div class="card-body">
     		<table class="table table-bordered">
				<tr>
         			<th>아이디</th>
         			<td>${member.user_id }</td>
      			</tr>
      			<tr>
         			<th>회원등급</th>
         			<td>${member.grade }</td>
      			</tr>
				<tr>
				   <th>이름</th>
				   <td>${member.user_name }</td>
				</tr>
				<tr>
				   <th>연락처</th>
				   <td>${member.tel }</td>
				</tr>
				<tr>
				   <th>주소</th>
				   <td>${member.addr1} ${member.addr2}</td>
				</tr>
				<tr>
				   <th>우편번호</th>
				   <td>${member.postcode }</td>
				</tr>
				<tr>
				   <th>이메일</th>
				   <td>${member.email }</td>
				</tr>
				<tr>
				   <th>성별</th>
				   <td>
				   <c:choose>
				      <c:when test="${member.sex eq null || member.sex eq ''} ">
				         체크안함
				      </c:when>
				      <c:otherwise>
				         ${member.sex }
				      </c:otherwise>
				   </c:choose>
				   </td>
				</tr>
				<tr>
				   <th>생년월일</th>
				   <td>
				   <c:choose>
				      <c:when test="${member.birth eq null || member.birth eq ''} ">
				         기입안함
				      </c:when>
				      <c:otherwise>
				         ${member.birth }
				      </c:otherwise>
				   </c:choose>
				   </td>
				</tr>
				<tr>
				   <th>가입일</th>
				   <td>${member.joindate }</td>
				</tr>
			</table>
		</div>
	</div>

</form>

<form action="orderList.ad" method="post">
<div class="card shadow mb-4">
	<div class="card-header py-3">
		<h6 class="m-0 font-weight-bold text-primary">회원 주문내역</h6>
    </div>
    <div class="card-body">
     		<table class="table table-bordered">
				<tr>
         			<th>주문번호 </th>
         			<th>주문일자</th>
         			<th>주문상품정보</th>
         			<th>결제금액</th>
         			<th>상태</th>
         			<th>비고</th>
      			</tr>
			   <!-- 여기서부터 데이터 뿌려주기 -->
			      <c:choose>
			      <c:when test="${!empty order}">
			      
			         <c:forEach items="${order }" var="order">
			            <tr>
			               <td><a href="orderManageDetail.ad?sel_num=${order.sel_num }&user_id=${member.user_id}">${order.sel_num }&nbsp;[상세내역보기]</a></td>
			               <td>${order.sel_date }</td>
			               <td>${order.pro_name }</td>
			               <td>${order.final_price }원</td>
			               <td>
			               <c:if test="${order.sel_status eq 'order_done' }">주문완료</c:if>
			               <c:if test="${order.sel_status eq 'check_paid' }">입금확인</c:if>
			               <c:if test="${order.sel_status eq 'send_pro' }">상품발송</c:if>
			               <c:if test="${order.sel_status eq 'deli_ing' }">배송중</c:if>
			               <c:if test="${order.sel_status eq 'deli_fin' }">배송완료</c:if>
			               <c:if test="${order.sel_status eq 'order_confirm' }">구매확정</c:if>
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
         		<tr>
	            	<td colspan="6">
	             		주문 내역이 없습니다.
	            	</td>
         		</tr>
				</c:otherwise>
   				</c:choose>
			</table>
		</div>
		
		<!-- 여기서부터 페이징 -->
   <div id="pageList">
      <c:choose>
         <c:when test="${pageInfo.page <= 1 }">
            <ion-icon name="chevron-back-outline"></ion-icon>
         </c:when>
         <c:otherwise>
            <a href="memberInfo.ad?&user_id=${user_id }&page=${pageInfo.page-1 }"><ion-icon name="chevron-back-outline"></ion-icon></a>
         </c:otherwise>
      </c:choose>
         
         
      <c:forEach var="a" begin="${pageInfo.startPage }" end="${pageInfo.endPage }" step="1">
         <c:choose>
            <c:when test="${a == pageInfo.page }">
               [${a}]
            </c:when>
            <c:otherwise>
               <a href="memberInfo.ad?&user_id=${user_id }&page=${a}">[${a}]</a>
            </c:otherwise>
         </c:choose>
      </c:forEach>
         
         
      <c:choose>
         <c:when test="${pageInfo.page>=pageInfo.maxPage }">
            <ion-icon name="chevron-forward-outline"></ion-icon>
         </c:when>
         <c:otherwise>
            <a href="memberInfo.ad?&user_id=${user_id }&page=${pageInfo.page+1 }"><ion-icon name="chevron-forward-outline"></ion-icon></a>
         </c:otherwise>
      </c:choose>
   </div>
<!-- 여기까지 페이징 -->
		
	</div>

<button type="button" class="btn btn-primary btn-lg btn-block" onclick="location.href='memberList.ad';">목록보기</button>
<br><br><br>

	         
   


</form>

</body>
</html>