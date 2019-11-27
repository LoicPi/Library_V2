package com.clientui.beans;

import java.util.Date;

public class BorrowingBean {

	private Integer id;
	
	private Date dateBorrowed;
	
	private Date deadline;
	
	private Boolean renewal = false;
	
	private Date dateRenewal;
	
	private Integer idUser;
	
	private String state;
	
	private BookCopyBean bookCopyBean;

	public BorrowingBean() {
		
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Date getDateBorrowed() {
		return dateBorrowed;
	}

	public void setDateBorrowed(Date dateBorrowed) {
		this.dateBorrowed = dateBorrowed;
	}

	public Date getDeadline() {
		return deadline;
	}

	public void setDeadline(Date deadline) {
		this.deadline = deadline;
	}

	public Boolean getRenewal() {
		return renewal;
	}

	public void setRenewal(Boolean renewal) {
		this.renewal = renewal;
	}

	public Date getDateRenewal() {
		return dateRenewal;
	}

	public void setDateRenewal(Date dateRenewal) {
		this.dateRenewal = dateRenewal;
	}

	public Integer getIdUser() {
		return idUser;
	}

	public void setIdUser(Integer idUser) {
		this.idUser = idUser;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public BookCopyBean getBookCopyBean() {
		return bookCopyBean;
	}

	public void setBookCopyBean(BookCopyBean bookCopyBean) {
		this.bookCopyBean = bookCopyBean;
	}
		
}
