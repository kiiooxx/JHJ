<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<script src="https://code.jquery.com/jquery-3.2.1.js"></script>
<link rel="stylesheet" href="/css/jquery-ui.min.css">
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/css/bootstrap.min.css"/>
<script src="http://netdna.bootstrapcdn.com/bootstrap/3.0.3/js/bootstrap.min.js"></script>
<script type='text/javascript' src='//code.jquery.com/jquery-1.8.3.js'></script>
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-datepicker/1.5.0/css/bootstrap-datepicker3.min.css">
<script type='text/javascript' src="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-datepicker/1.5.0/js/bootstrap-datepicker.min.js"></script>
<script src="/js/bootstrap-datepicker.kr.js" charset="UTF-8"></script>
<script>
$(function(){
    $('.input-group.date').datepicker({
        calendarWeeks: false,
        todayHighlight: true,

        autoclose: true,

        format: "yyyy-mm-dd",

        language: "kr"

    });

});


</script>
<style>


</style>
</head>
<body>

<h2>주문관리 페이지</h2>

<section id="order_search">

<form action="orderList.ad" method="post">
	<table>
		<tr>
			<th>검색어</th>
			<td>
				<select id="searchType" name="searchType">
					<option value="order_num" >주문번호</option>
					<option value="deli_num" >배송번호</option>
					<option value="pro_num" >상품번호</option>
					<option value="user_id" >주문자 아이디</option>
					<option value="user_name" >주문자 이름</option>
					<option value="rec_name" >수령인 이름</option>
				</select>
				<input type="text" name="searchText">
			</td>
		</tr>
		<tr>
			<th>기간</th>
			<td>
				<select id="searchDate" name="searchDate">
					<option value="orderDate">주문일</option>
					<option value="payDate">결제일</option>
				</select>
                <div class="container">

        

        <div class="input-group date">

            <input type="text" class="form-control">
            <span class="input-group-addon">
            <i class="glyphicon glyphicon-calendar"></i></span>

        </div>

    </div>

			</td>
		</tr>
		<tr>
			<th>주문상태</th>
			<td>
				<input type="checkbox">주문완료
				<input type="checkbox">입금확인
				<input type="checkbox">배송중
				<input type="checkbox">배송완료
				<input type="checkbox">구매확정
			</td>
		</tr>	
	
	</table>



</form>


</section>

</body>
</html>