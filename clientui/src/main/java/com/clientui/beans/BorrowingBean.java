package com.clientui.beans;

public class BorrowingBean {

	private Integer id;
	
	private String dateBorrowed;
	
	private String deadline;
	
	private Boolean renewal;
	
	private String dateRenewal;
	
	private String dateReturn;
	
	private Integer idUser;
	
	private String state;
	
	private String bookName;

	public BorrowingBean() {
		
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getDateBorrowed() {
		return dateBorrowed;
	}

	public void setDateBorrowed(String dateBorrowed) {
		this.dateBorrowed = dateBorrowed;
	}

	public String getDeadline() {
		return deadline;
	}

	public void setDeadline(String deadline) {
		this.deadline = deadline;
	}

	public Boolean getRenewal() {
		return renewal;
	}

	public void setRenewal(Boolean renewal) {
		this.renewal = renewal;
	}

	public String getDateRenewal() {
		return dateRenewal;
	}

	public void setDateRenewal(String dateRenewal) {
		this.dateRenewal = dateRenewal;
	}

	public String getDateReturn() {
		return dateReturn;
	}

	public void setDateReturn(String dateReturn) {
		this.dateReturn = dateReturn;
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

	public String getBookName() {
		return bookName;
	}

	public void setBookName(String bookName) {
		this.bookName = bookName;
	}

	@Override
	public String toString() {
		return "BorrowingBean [id=" + id + ", dateBorrowed=" + dateBorrowed + ", deadline=" + deadline + ", renewal="
				+ renewal + ", dateRenewal=" + dateRenewal + ", dateReturn=" + dateReturn + ", idUser=" + idUser
				+ ", state=" + state + ", bookName=" + bookName + "]";
	}
}
