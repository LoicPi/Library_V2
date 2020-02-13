package com.clientui.beans;

import java.util.Set;

import com.fasterxml.jackson.annotation.JsonProperty;

public class BookBean {

	private Integer id;
	
	private String name;
	
	private String description;
	
	private String image;
	
	private String bookType;
	
	private Integer numberOfBooking;
	
	private Integer maxNumberOfBooking;
	
	private String nearestDeadline;
	
	private Boolean avaibleBook;
	
	private Boolean bookingBook;
	
	@JsonProperty("bookCopies")
	private Set<BookCopyBean> booksCopiesBean;
	
	@JsonProperty("authors")
	private Set<AuthorBean> authorsBean;
	
	public BookBean() {
		
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public String getBookType() {
		return bookType;
	}

	public void setBookType(String bookType) {
		this.bookType = bookType;
	}

	public Integer getNumberOfBooking() {
		return numberOfBooking;
	}

	public void setNumberOfBooking(Integer numberOfBooking) {
		this.numberOfBooking = numberOfBooking;
	}

	public Integer getMaxNumberOfBooking() {
		return maxNumberOfBooking;
	}

	public void setMaxNumberOfBooking(Integer maxNumberOfBooking) {
		this.maxNumberOfBooking = maxNumberOfBooking;
	}

	public Set<BookCopyBean> getBooksCopiesBean() {
		return booksCopiesBean;
	}

	public void setBooksCopiesBean(Set<BookCopyBean> booksCopiesBean) {
		this.booksCopiesBean = booksCopiesBean;
	}

	public Set<AuthorBean> getAuthorsBean() {
		return authorsBean;
	}

	public void setAuthorsBean(Set<AuthorBean> authorsBean) {
		this.authorsBean = authorsBean;
	}

	public String getNearestDeadline() {
		return nearestDeadline;
	}

	public void setNearestDeadline(String nearestDeadline) {
		this.nearestDeadline = nearestDeadline;
	}
	
	public Boolean getAvaibleBook() {
		return avaibleBook;
	}

	public void setAvaibleBook(Boolean avaibleBook) {
		this.avaibleBook = avaibleBook;
	}
	
	public Boolean getBookingBook() {
		return bookingBook;
	}

	public void setBookingBook(Boolean bookingBook) {
		this.bookingBook = bookingBook;
	}

	@Override
	public String toString() {
		return "BookBean [id=" + id + ", name=" + name + ", description=" + description + ", image=" + image
				+ ", bookType=" + bookType + ", numberOfBooking=" + numberOfBooking + ", maxNumberOfBooking="
				+ maxNumberOfBooking + ", nearestDeadline=" + nearestDeadline + "]";
	}
	
}
