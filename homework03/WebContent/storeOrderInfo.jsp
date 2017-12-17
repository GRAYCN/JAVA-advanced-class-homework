<%@page import="java.util.Date"%>
<%@page import="wgh.dao.OrderInfoDao"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page import="wgh.model.OrderInfo"%>
<%@ page import="wgh.util.*,wgh.model.*"%>
<%@ page import="org.springframework.beans.factory.BeanFactory"%>
<%
	//1-通过服务层获得数据
	//因为业务比较简单,跳过了service层,直接使用在control层使用dao
	BeanFactory factory = SpringUtils.getBeanFactory();
	OrderInfoDao dao = factory.getBean(OrderInfoDao.class);

	String orderDiscount = request.getParameter("orderDiscount");
	
	OrderInfo orderInfo = new OrderInfo(new Date(), Double.parseDouble(orderDiscount));
	
	int rowId = dao.insertOrderInfo(orderInfo);
	out.write(Integer.toString(rowId));
%>