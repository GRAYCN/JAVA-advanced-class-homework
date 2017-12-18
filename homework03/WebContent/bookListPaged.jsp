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

	int pageInex = 0;
	try {
		pageInex = Integer.parseInt(request.getParameter("pageIndex"));
	} catch (Exception e) {
	}
	
	int count=dao.countRows();
	int pageCount=0;
	if(count%DbUtils.PAGE_SIZE==0)
		pageCount=count/DbUtils.PAGE_SIZE;
	else
	       pageCount=count/DbUtils.PAGE_SIZE+1;
	//3-如果page index>=总行数，pagex index=总行数-1
	if(pageInex>=pageCount)
	pageInex=pageCount-1;
	

	BookInfo[] books = dao.findPagedBookinfosByPageindex(pageInex);

	//5-将对象数组转换为json字符串
	response.setCharacterEncoding("utf-8");
	String strJson=JsonUtils.objectToJson(books);
	out.write( strJson );

%>
