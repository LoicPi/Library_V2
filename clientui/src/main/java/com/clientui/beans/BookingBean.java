package com.clientui.beans;

public class BookingBean {

	private Integer id;
	
	private Integer idUser;
	
	private Integer position;
	
	private String bookName;
	
	private String nearestDeadline;

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

	public String getNearestDeadline() {
		return nearestDeadline;
	}

	public void setNearestDeadline(String nearestDeadline) {
		this.nearestDeadline = nearestDeadline;
	}

	@Override
	public String toString() {
		return "BookingBean [id=" + id + ", idUser=" + idUser + ", position=" + position + ", bookName=" + bookName
				+ ", nearestDeadline=" + nearestDeadline + "]";
	}
	
}
