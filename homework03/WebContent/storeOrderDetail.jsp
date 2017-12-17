<%@page import="wgh.dao.OrderDetailDao"%>
<%@page import="java.util.Date"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="wgh.util.*,wgh.model.*"%>
<%@ page import="org.springframework.beans.factory.BeanFactory"%>
<%
	//1-通过服务层获得数据
	//因为业务比较简单,跳过了service层,直接使用在control层使用dao
	BeanFactory factory = SpringUtils.getBeanFactory();
	OrderDetailDao dao = factory.getBean(OrderDetailDao.class);

	String orderId = request.getParameter("orderId");
	String bookId = request.getParameter("bookId");
	String orderQuantity = request.getParameter("orderQuantity");

	OrderDetail orderDetail = new OrderDetail(Integer.parseInt(orderId), Integer.parseInt(bookId),
			Integer.parseInt(orderQuantity));
	
	dao.insertOrderDetail(orderDetail);
%>