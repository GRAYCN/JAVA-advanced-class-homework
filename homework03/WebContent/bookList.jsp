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

	BookInfo[] books = dao.findAllBooksInfos();
	//2-java对象(数组) 转换 json字符串
	String jsonStr = JsonUtils.objectToJson(books);
	//3-将Json字符串写出
	out.write(jsonStr);
%>
