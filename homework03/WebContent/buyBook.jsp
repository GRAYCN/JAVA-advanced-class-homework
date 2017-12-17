<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<%@page import="com.google.gson.Gson,com.google.gson.reflect.TypeToken"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page import="wgh.dao.BookInfoDao"%>
<%@ page import="wgh.util.*,wgh.model.*"%>
<%@ page import="org.springframework.beans.factory.BeanFactory"%>
<%
	//1-通过服务层获得数据
	//因为业务比较简单,跳过了service层,直接使用在control层使用dao
	BeanFactory factory = (BeanFactory) SpringUtils.getBeanFactory();
	BookInfoDao dao = ((org.springframework.beans.factory.BeanFactory) factory).getBean(BookInfoDao.class);
	//BookInfoDao dao = new BookInfoDao();

	Gson gson = new Gson();

	String bookId = request.getParameter("bookId");

	BookInfo book = dao.findBookById(Integer.parseInt(bookId));

	//获取存储订单明细的Cookie信息
	Cookie[] cookies = request.getCookies();
	String strOut = null;
	//遍历Cookie中的内容,找到存储订单明细的那一条,将其转为OrderDetail对象数组,
	//遍历OrderDetail,如果添加的书籍已经存在,就将OrderDetail的quality++,否则新建一个存储
	//待购书籍的OrderDetail,并将quality设置为1
	boolean hasCookie=false;
	if (null != cookies && cookies.length > 0) {
		for (Cookie c : cookies) {					//遍历cookie寻找是否有购物车的cookie
			if (c.getName().equals("cartList")) {	
				hasCookie=true;						//如果cookie中已经有购物车信息,将hasCookie置为true,并且后面只需要更新cookie即可
				String strJSON = c.getValue();
				//    		OrderDetail[] orderDetails= (OrderDetail[])JsonUtils.jsonToObject(StrJSON, OrderDetail.class);
				List<OrderDetail> orderDetailList = gson.fromJson(strJSON, new TypeToken<List<OrderDetail>>() {
				}.getType());
				//检查所选购的图书是否在列表中
				boolean flag = false;
				for (OrderDetail orderDetail : orderDetailList) {
					if (orderDetail.getBookId().equals(Integer.parseInt(bookId))) {
						flag = true;
						orderDetail.setOrderQuantity(orderDetail.getOrderQuantity() + 1);
						break;
					}
				}
				if (!flag) { //当该图书不在列表中,需要在列表中添加新的一行
					orderDetailList.add(new OrderDetail(Integer.parseInt(bookId), 1));
				}

				strJSON = JsonUtils.objectToJson(orderDetailList);
				Cookie cookie = new Cookie("cartList", strJSON);
				response.addCookie(cookie);				//新增 cartList cookie
				strOut = strJSON;
				
				if(hasCookie)
					break;			//当已经处理过cookie中已有的购物车信息,调出这段逻辑循环
			}
		}
		if(!hasCookie)
		{
			OrderDetail orderDetail = new OrderDetail(Integer.parseInt(bookId), 1);
			ArrayList<OrderDetail> orderDetailList = new ArrayList<>();
			orderDetailList.add(orderDetail);
			String strJSON = JsonUtils.objectToJson(orderDetailList);
			Cookie cookie = new Cookie("cartList", strJSON);
			response.addCookie(cookie);				//新增 cartList cookie
			strOut = strJSON;						//将cookie中存储的购物清单json数组记录下来
		}
	}

	//3-将Json字符串写出
	response.getWriter().write(strOut);
%>
