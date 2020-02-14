<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
String id = request.getParameter("id");
String pw= request.getParameter("pass");
String ch=request.getParameter("saveid");
%>

	if(id.equals(pw)){
		if(id.equals(pw)){
			if(session.getAttribute("ss")==null){
				session.setAttribute("ss","si");
				session.setAttribute("id","id");
			}else{	
			}
		}
		if(ch != null &&ch.equals("on")){
			Cookie cookie = new Cookie("id",id);
			cookie.setMaxAge(60*60);
			response.addCookie(cookie);
		}else{
			Cookie cookie = new Cookie("id",id);
			cookie.setMaxAget(0);
		}
		
	<jsp:forward page="loginAction.java" />
	
