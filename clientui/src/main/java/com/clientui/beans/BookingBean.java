package com.clientui.beans;

public class BookingBean {

	private Integer id;
	
	private Integer idUser;
	
	private Integer position;
	
	private String bookName;
	
	private String deadline;
	
	private String state;

	public BookingBean() {
		
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getIdUser() {
		return idUser;
	}

	public void setIdUser(Integer idUser) {
		this.idUser = idUser;
	}

	public Integer getPosition() {
		return position;
	}

	public void setPosition(Integer position) {
		this.position = position;
	}

	public String getBookName() {
		return bookName;
	}

	public void setBookName(String bookName) {
		this.bookName = bookName;
	}

	public String getDeadline() {
		return deadline;
	}

	public void setDeadline(String deadline) {
		this.deadline = deadline;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	@Override
	public String toString() {
		return "BookingBean [id=" + id + ", idUser=" + idUser + ", position=" + position + ", bookName=" + bookName
				+ ", deadline=" + deadline + ", state=" + state + "]";
	}
	
}
