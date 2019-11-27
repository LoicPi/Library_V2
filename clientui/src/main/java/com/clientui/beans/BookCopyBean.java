package com.clientui.beans;

import java.util.Set;

public class BookCopyBean {

	private Integer id;
	
	private String ean;
	
	private BookBean bookbean;
	
	private Set<BorrowingBean> borrowingsbean;
	
	private Boolean borrowed;
	
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

	public void setEan(String ean) {
		this.ean = ean;
	}

	public BookBean getBookbean() {
		return bookbean;
	}

	public void setBookbean(BookBean bookbean) {
		this.bookbean = bookbean;
	}

	public Set<BorrowingBean> getBorrowingsbean() {
		return borrowingsbean;
	}

	public void setBorrowingsbean(Set<BorrowingBean> borrowingsbean) {
		this.borrowingsbean = borrowingsbean;
	}

	public Boolean getBorrowed() {
		return borrowed;
	}

	public void setBorrowed(Boolean borrowed) {
		this.borrowed = borrowed;
	}

	@Override
	public String toString() {
		return "BookCopyBean [id=" + id + ", ean=" + ean + ", borrowed=" + borrowed + "]";
	}

}
