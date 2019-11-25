package com.clientui.beans;

public class BookCopyBean {

	private Integer id;
	
	private String ean;
	
	private BookBean bookbean;
	
	public BookCopyBean() {
		
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getEan() {
		return ean;
	}

	public void setIsbn(String ean) {
		this.ean = ean;
	}

	public BookBean getBookbean() {
		return bookbean;
	}

	public void setBookbean(BookBean bookbean) {
		this.bookbean = bookbean;
	}

	@Override
	public String toString() {
		return "BookCopyBean [id=" + id + ", ean=" + ean + "]";
	}
}
