package com.clientui.beans;

import java.util.Set;

public class BookCopyBean {

	private Integer id;
	
	private String isbn;
	
	private BookBean bookbean;
	
	public BookCopyBean() {
		
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getIsbn() {
		return isbn;
	}

	public void setIsbn(String isbn) {
		this.isbn = isbn;
	}

	public BookBean getBookbean() {
		return bookbean;
	}

	public void setBookbean(BookBean bookbean) {
		this.bookbean = bookbean;
	}

	@Override
	public String toString() {
		return "BookCopyBean [id=" + id + ", isbn=" + isbn + ", bookbean=" + bookbean + "]";
	}
}
