<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<jsp:include page="/common/loginCheck.jsp"/>
<div class="mypage_top">
	<h2>[ MY SHOPPING ]</h2>
	<div class="user_info">
		<ul>
			<li>
				<strong>${id }</strong>
				님은
				<strong>
					[${grade eq 'N'.charAt(0) ? '일반회원' : '관리자' }]
				</strong>
				회원입니다.
			</li>
		</ul>
	</div>
	<div class="user_info2">
		<ul>
			<li><a href="#">POINT : </a></li>
			<li><a href="#">CART 0개(0)</a></li>
		</ul>
	</div>
</div>

<div class="mypage_wrap">
	<div class="mypage_inner">
		<ul>
			<li>
				<h1><a href="#">ORDER</a></h1>
				고객님께서 주문하신 상품의 주문내역을 확인하실 수 있습니다.<br>
				비회원의 경우, 주문서의 주문번호와 비밀번호로 주문조회가 가능합니다.
			</li>
			<li>
				<h1><a href="myinfo.mem">MY INFO</a></h1>
				회원이신 고객님의 개인정보를 관리하는 공간입니다.<br>
				개인정보를 최신 정보로 유지하시면 보다 간편히 쇼핑을 즐기실 수 있습니다.
			</li>
			<li>
				<h1><a href="#">POINT</a></h1>
				적립금은 상품 구매 시 사용하실 수 있습니다.
			</li>
			<li>
				<h1><a href="myboard.mem">MY BOARD</a></h1>
				고객님께서 작성하신 게시물을 관리하는 공간입니다.<br>
				고객님께서 작성하신 글을 한눈에 관리하실 수 있습니다.
			</li>
			<li>
				<h1><a href="#">ADDRESS</a></h1>
				자주 사용하는 배송지를 등록하고 관리하실 수 있습니다.
			</li>
		</ul>
	</div>
</div>
</body>
</html>