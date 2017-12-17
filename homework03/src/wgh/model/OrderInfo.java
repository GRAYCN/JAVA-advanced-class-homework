package wgh.model;

import java.util.Date;

public class OrderInfo {
	Integer orderId;
	Date orderTime;
	Double orderDiscount;
	public OrderInfo() {
		super();
		// TODO Auto-generated constructor stub
	}
	public OrderInfo(Integer orderId, Date orderTime, Double orderDiscount) {
		super();
		this.orderId = orderId;
		this.orderTime = orderTime;
		this.orderDiscount = orderDiscount;
	}
	
	public OrderInfo(Date orderTime, Double orderDiscount) {
		super();
		this.orderTime = orderTime;
		this.orderDiscount = orderDiscount;
	}
	public Integer getOrderId() {
		return orderId;
	}
	public void setOrderId(Integer orderId) {
		this.orderId = orderId;
	}
	public Date getOrderTime() {
		return orderTime;
	}
	public void setOrderTime(Date orderTime) {
		this.orderTime = orderTime;
	}
	public Double getOrderDiscount() {
		return orderDiscount;
	}
	public void setOrderDiscount(Double orderDiscount) {
		this.orderDiscount = orderDiscount;
	}
	
}
