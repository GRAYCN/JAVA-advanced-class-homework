package wgh.model;

import java.util.Date;

public class BookInfo {
	Integer bookId;
	String bookName;
	Double bookPrice;
	Date publishDate;
	
	public BookInfo() {
		super();
		// TODO Auto-generated constructor stub
	}
	public BookInfo(Integer bookId, String bookName, Double bookPrice, Date publishDate) {
		super();
		this.bookId = bookId;
		this.bookName = bookName;
		this.bookPrice = bookPrice;
		this.publishDate = publishDate;
	}
	public Integer getBookId() {
		return bookId;
	}
	public void setBookId(Integer bookId) {
		this.bookId = bookId;
	}
	public String getBookName() {
		return bookName;
	}
	public void setBookName(String bookName) {
		this.bookName = bookName;
	}
	public double getBookPrice() {
		return bookPrice;
	}
	public void setBookPrice(Double bookPrice) {
		this.bookPrice = bookPrice;
	}
	public Date getPublishDate() {
		return publishDate;
	}
	public void setPublishDate(Date publishDate) {
		this.publishDate = publishDate;
	}
	@Override
	public String toString() {
		return "BookInfo [bookId=" + bookId + ", bookName=" + bookName + ", bookPrice=" + bookPrice + ", publishDate="
				+ publishDate + "]";
	}
	
	
}
