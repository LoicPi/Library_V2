package com.clientui.beans;

import java.util.Set;

import com.fasterxml.jackson.annotation.JsonProperty;

public class BookBean {

	private Integer id;
	
	private String name;
	
	private String description;
	
	private String image;
	
	private String bookTypeBean;
	
	private Integer numberOfBooking;
	
	private Integer maxNumberOfBooking;
	
	private String nearestDeadline;
	
	private Boolean avaibleBook;
	
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

	public String getBookTypeBean() {
		return bookTypeBean;
	}

	public void setBookTypeBean(String bookTypeBean) {
		this.bookTypeBean = bookTypeBean;
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
	
	@Override
	public String toString() {
		return "BookBean [id=" + id + ", name=" + name + ", description=" + description + ", image=" + image
				+ ", bookTypeBean=" + bookTypeBean + ", numberOfBooking=" + numberOfBooking + ", maxNumberOfBooking="
				+ maxNumberOfBooking + ", nearestDeadline=" + nearestDeadline + "]";
	}
	
}
