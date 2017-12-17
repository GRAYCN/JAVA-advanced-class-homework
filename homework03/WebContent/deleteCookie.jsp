<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	String cookieName = request.getParameter("cookieName");
	//获取Cookies数组
	Cookie[] cookies = request.getCookies();
	// 迭代查找并清除Cookie
	for (Cookie cookie : cookies) {
		if (cookieName.equals(cookie.getName())) {
			cookie.setMaxAge(0);
			response.addCookie(cookie);
		}
	}
%>