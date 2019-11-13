package com.clientui.beans;

import java.util.Set;

public class BookTypeBean {

	private Integer id;
	
	private String type;
	
	private Set<BookBean> booksBean;
	
	public BookTypeBean() {
		
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Set<BookBean> getBooksBean() {
		return booksBean;
	}

	public void setBooksBean(Set<BookBean> booksBean) {
		this.booksBean = booksBean;
	}

	@Override
	public String toString() {
		return "BookTypeBean [id=" + id + ", type=" + type + "]";
	}
}
