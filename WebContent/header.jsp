<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="sql" uri="http://java.sun.com/jsp/jstl/sql"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<div class="top">
	<ul>
		<li>
			<c:choose>
				<c:when test="${id != null && id != '' }">
					<a href="logout.log">LOGOUT</a>
				</c:when>
				<c:otherwise>
					<a href="loginForm.log">LOGIN</a>
				</c:otherwise>
			</c:choose>
		</li>
		<li>
			<a href="joinForm.mem">JOIN</a>
		</li>
		<li>
			<a href="account.mem">ACCOUNT</a>
		</li>
		<li>
			<a href="#">ORDER</a>
		</li>
		<li>
			<a href="cartList.pro">CART</a>
		</li>
		<!-- 관리자일때만 admin메뉴 보이게 -->
		<li>
			<a href="adminPage.ad">ADMIN</a>
		</li>
	</ul>
</div>