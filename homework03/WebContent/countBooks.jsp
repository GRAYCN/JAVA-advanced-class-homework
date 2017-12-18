<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page import="wgh.dao.BookInfoDao"%>
<%@ page import="wgh.util.*,wgh.model.*"%>
<%@ page import="org.springframework.beans.factory.BeanFactory"%>
<%
	//1-通过服务层获得数据
	//因为业务比较简单,跳过了service层,直接使用在control层使用dao
	BeanFactory factory = SpringUtils.getBeanFactory();
	BookInfoDao dao = factory.getBean(BookInfoDao.class);

	 int count = dao.countRows();
		response.setCharacterEncoding("utf-8");
		response.getWriter().write(""+count);
%>