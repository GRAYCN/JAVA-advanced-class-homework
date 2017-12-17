<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<%@page import="com.google.gson.Gson,com.google.gson.reflect.TypeToken"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page import="wgh.dao.BookInfoDao"%>
<%@ page import="wgh.util.*,wgh.model.*"%>
<%@ page import="org.springframework.beans.factory.BeanFactory"%>


 
<%
	String bookId = request.getParameter("bookId");
	String orderQuantity = request.getParameter("orderQuantity");
	String strOut = null;
	//获取存储订单明细的Cookie信息
	Cookie[] cookies = request.getCookies();
	Gson gson = new Gson();
	if (null != cookies && cookies.length > 0) {
		boolean flag = false;
		for (Cookie c : cookies) {
			if (c.getName().equals("cartList")) {

				String strJSON = c.getValue();
				//    		OrderDetail[] orderDetails= (OrderDetail[])JsonUtils.jsonToObject(StrJSON, OrderDetail.class);
				List<OrderDetail> orderDetailList = gson.fromJson(strJSON, new TypeToken<List<OrderDetail>>() {
				}.getType());
				for (OrderDetail orderDetail : orderDetailList) {
					if (orderDetail.getBookId().equals(Integer.parseInt(bookId))) {
						flag = true;
/* 						if ("1".equals(operation)) {
							int newQuantity = orderDetail.getOrderQuantity() + 1;
							orderDetail.setOrderQuantity(newQuantity);
						} else if ("2".equals(operation)) {
							int newQuantity = orderDetail.getOrderQuantity() - 1;
							if (newQuantity != 0)
								orderDetail.setOrderQuantity(newQuantity);
							else
								orderDetailList.remove(orderDetail);
						} else if ("3".equals(operation)) {
							orderDetailList.remove(orderDetail);
						} */

						if("0".equals(orderQuantity)){
							orderDetailList.remove(orderDetail);
						}
						else{
							int newQuantity = Integer.parseInt(orderQuantity);
							orderDetail.setOrderQuantity(newQuantity);
						}
						break;
					}
				}
				if (flag == true) {
					strJSON = JsonUtils.objectToJson(orderDetailList);
					Cookie cookie = new Cookie("cartList", strJSON);
					response.addCookie(cookie); //新增 cartList cookie
					strOut = strJSON;
					out.write(strOut);
					break;
				}
			}
		}
	}
%>