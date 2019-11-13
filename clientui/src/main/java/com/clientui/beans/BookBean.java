package com.clientui.beans;

import java.util.Set;

public class BookBean {

	private Integer id;
	
	private String name;
	
	private String description;
	
	private Boolean image = false;
	
	private BookTypeBean bookTypeBean;
	
	private Set<BookCopyBean> booksCopiesBean;
	
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

	public Boolean getImage() {
		return image;
	}

	public void setImage(Boolean image) {
		this.image = image;
	}

	public BookTypeBean getBookTypeBean() {
		return bookTypeBean;
	}

	public void setBookTypeBean(BookTypeBean bookTypeBean) {
		this.bookTypeBean = bookTypeBean;
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

	@Override
	public String toString() {
		return "BookBean [id=" + id + ", name=" + name + ", description=" + description + ", image=" + image + "]";
	}
}
