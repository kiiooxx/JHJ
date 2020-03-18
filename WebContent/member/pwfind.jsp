<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<script>
	function domainCheck(value) {
		if (value == 0) {
			document.getElementById("emailInput").value = "";
		} else {
			document.getElementById("emailInput").value = value;
		}
	}
</script>


<div class="login">
	<h1 align=center style="height:30px;">FIND PASSWORD</h1>
	<fieldset>
		<div class="loginBx">
			<ul class="forget">
			</ul>
			
			<form action="pwfindAction.log" name="pwfindscreen" method="post">
				<p class="nna">ID</p>
				<label class="id"><input type="text" name="id"></label>
				
				<p class="nna">E-MAIL</p>
				<label class="pass">
					<input type="text" name="email" style="width: 80px" onblur="checkid()">
					@
					<input type="text" id="emailInput" name="e_domain" style="width: 80px">
					<select id="emailDomain" name="domain" onchange="domainCheck(this.value);">
						<option value="0" selected="selected">직접입력</option>
						<option value="naver.com">naver.com</option>
						<option value="hanmail.net">hanmail.net</option>
						<option value="nate.com">nate.com</option>
						<option value="yahoo.com">yahoo.com</option>
					</select>
				</label>
				<p class="btx">
					<a href="javascript:pwfindscreen.submit()" class="login_btn">임시비밀번호보내기</a>
				</p>
			</form>
		</div>
	</fieldset>
</div>