<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<script>
//삭제 버튼 눌렀을 때
function del() {
	if(confirm('정말 삭제하시겠습니까?')) {
		location.href="boardDeleteAction.bo?board_num=${board.board_num}";
	}else {
		return false;
	}
}
</script>

<div class="blank">
</div>
<div id="join_form">
	<div class="join_table">
		<table>
			<tr>
				<th>SUBJECT</th>
				<td>${board.board_title}</td>
			</tr>
			<tr>
				<th>WRITER</th>
				<td>관리자</td>
			</tr>
			<tr>
				<th>DATE</th>
				<td>${board.board_date }</td>
			</tr>
			<tr>
				<th colspan="2">
					<c:if test="${!(board.board_photo eq null || board.board_photo eq '' )}">
						<img src="<%= request.getContextPath() %>/upload/${board.board_photo }"><br>
					</c:if>
					${board.board_content }
				</th>
			</tr>
		</table>
		
		<div class="order_button_area">
			<p>
				<c:if test="${grade == 'A'.charAt(0) }">
					<a href="boardViewAction.bo?board_type=notice&board_num=${board.board_num }&pro_num=${prd.pro_num}&path=modify_form" class="b">MODIFY</a>
					<a href="#" class="b" onclick="del()">DELETE</a>
				</c:if>
				<a href="boardListAction.bo?board_type=notice" class="w">LIST</a>
			</p>
		</div>
	</div>
</div>



