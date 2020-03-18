<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<script>
    window.onload = function() {
 
        if (getCookie("id")) { // getCookie함수로 id라는 이름의 쿠키를 불러와서 있을경우
            document.loginform.id.value = getCookie("id"); //input 이름이 id인곳에 getCookie("id")값을 넣어줌
            document.loginform.idsave.checked = true; // 체크는 체크됨으로
        }
 
    }
 
    function setCookie(name, value, expiredays) //쿠키 저장함수
    {
        var todayDate = new Date();
        todayDate.setDate(todayDate.getDate() + expiredays);
        document.cookie = name + "=" + escape(value) + "; path=/; expires="
                + todayDate.toGMTString() + ";"
    }
 
    function getCookie(Name) { // 쿠키 불러오는 함수
        var search = Name + "=";
        if (document.cookie.length > 0) { // if there are any cookies
            offset = document.cookie.indexOf(search);
            if (offset != -1) { // if cookie exists
                offset += search.length; // set index of beginning of value
                end = document.cookie.indexOf(";", offset); // set index of end of cookie value
                if (end == -1)
                    end = document.cookie.length;
                return unescape(document.cookie.substring(offset, end));
            }
        }
    }
 
    function sendit() {
        var frm = document.loginform;
        if (!frm.id.value) { //아이디를 입력하지 않으면.
            alert("아이디를 입력 해주세요!");
            frm.id.focus();
            return;
        }
        if (!frm.pass.value) { //패스워드를 입력하지 않으면.
            alert("패스워드를 입력 해주세요!");
            frm.pass.focus();
            return;
        }
 
        if (document.loginform.idsave.checked == true) { // 아이디 저장을 체크 하였을때
            setCookie("id", document.loginform.id.value, 7); //쿠키이름을 id로 아이디입력필드값을 7일동안 저장
        } else { // 아이디 저장을 체크 하지 않았을때
            setCookie("id", document.loginform.id.value, 0); //날짜를 0으로 저장하여 쿠키삭제
        }
        
        document.loginform.submit(); //유효성 검사가 통과되면 서버로 전송.
 
    }
</script>


<div class="login">
	<fieldset>
	<form action="login.log" name="loginform" method="post">
		
      <div class="loginBx">
      	<p class="nna">id</p>
      	<label class="id">
      		<input type="text" name="id" id="id" />
      	</label>
      	<p class="nna">password</p>
         <label class="pass">
            <input type="password" name="pass" id="pass"/>
         </label>
         <p class="idsave">
         	<input type="checkbox" name="idsave" value="saveOk"
         	/>SAVE ID
         </p>
         
         <p class="btx">
         	<a href="javascript:loginform.submit()" class="login_btn" onclick="sendit()">LOGIN</a>
         	<a href="joinForm.mem" class="join_btn">JOIN MEMBER</a>
         	<jsp:include page="/log/index.html"></jsp:include>
         </p>
        
         
        <ul class="forget">
           <li><a href="idfind.log" name="findid" method="post">FIND ID</a> &nbsp;/&nbsp;
      			<a href="pwfind.log" name="findpw" method="post">FIND PASSWORD</a></li>
        </ul>
      </div>
   </form>
   </fieldset>
</div>