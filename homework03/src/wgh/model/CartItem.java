package wgh.model;

import java.util.Date;

//存储前端网页中购物车每一行的信息
// 包装了bookInfo,而且添加了数量信息
public class CartItem {
//	String bookName;
//	Double bookPrice;
//	Integer quantity;
//	public CartItem() {

	
	BookInfo bookInfo;
	Integer quality;
	public CartItem() {
		super();
		// TODO Auto-generated constructor stub
	}
	public CartItem(BookInfo bookInfo, Integer quality) {
		super();
		this.bookInfo = bookInfo;
		this.quality = quality;
	}
	public BookInfo getBookInfo() {
		return bookInfo;
	}
	public void setBookInfo(BookInfo bookInfo) {
		this.bookInfo = bookInfo;
	}
	public Integer getQuality() {
		return quality;
	}
	public void setQuality(Integer quality) {
		this.quality = quality;
	}
	
	
}
