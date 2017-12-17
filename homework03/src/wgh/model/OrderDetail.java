package wgh.model;

public class OrderDetail {
	Integer detailId;
	Integer orderId;
	Integer bookId;
	Integer orderQuantity;
	public OrderDetail() {
		super();
		// TODO Auto-generated constructor stub
	}
	public OrderDetail(Integer bookId, Integer orderQuatity) {
		super();
		this.bookId = bookId;
		this.orderQuantity = orderQuatity;
	}
	
	public OrderDetail(Integer orderId, Integer bookId, Integer orderQuatity) {
		super();
		this.orderId = orderId;
		this.bookId = bookId;
		this.orderQuantity = orderQuatity;
	}
	public Integer getDetailId() {
		return detailId;
	}
	public void setDetailId(Integer detailId) {
		this.detailId = detailId;
	}
	public Integer getOrderId() {
		return orderId;
	}
	public void setOrderId(Integer orderId) {
		this.orderId = orderId;
	}
	public Integer getBookId() {
		return bookId;
	}
	public void setBookId(Integer bookId) {
		this.bookId = bookId;
	}
	public Integer getOrderQuantity() {
		return orderQuantity;
	}
	public void setOrderQuantity(Integer orderQuantity) {
		this.orderQuantity = orderQuantity;
	}

	
}
