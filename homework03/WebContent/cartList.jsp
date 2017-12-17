<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
Cookie[] cookies = request.getCookies();
if (null != cookies && cookies.length > 0) {
	for (Cookie c : cookies) {					//遍历cookie寻找是否有购物车的cookie
		if (c.getName().equals("cartList")) {	
			String strJSON = c.getValue();
			out.write(strJSON);
			break;
		}
	}
}

%>