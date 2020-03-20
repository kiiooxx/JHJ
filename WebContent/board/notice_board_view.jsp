<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="nowPage" value="${page }"/>

<script>
//삭제 버튼 눌렀을 때
function del() {
	if(confirm('정말 삭제하시겠습니까?')) {
		location.href="notice_boardDeletePro.bo?board_num=${notice.notice_num}&page=${nowPage}";
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
				<td>${notice.notice_title}</td>
			</tr>
			<tr>
				<th>WRITER</th>
				<td>관리자</td>
			</tr>
			<tr>
				<th>DATE</th>
				<td>${notice.notice_date }</td>
			</tr>
			<tr>
				<th colspan="2">
					${notice.notice_content}
				</th>
			</tr>
		</table>
		
		<div class="order_button_area">
			<p>
				<c:if test="${grade == 'A'.charAt(0) }">
					<a href="notice_boardModifyForm.bo?board_num=${notice.notice_num}&page=${nowPage}" class="b">MODIFY</a>
					<a href="#" class="b" onclick="del()">DELETE</a>
				</c:if>
				<a href="notice_boardList.bo?page=${nowPage}" class="w">LIST</a>
			</p>
		</div>
	</div>
</div>



