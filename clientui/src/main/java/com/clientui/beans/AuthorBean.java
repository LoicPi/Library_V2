package com.clientui.beans;

import java.util.Set;

public class AuthorBean {

	private Integer id;
	
	private String lastName;
	
	private String firstName;
	
	private String description;
	
	private Boolean image = false;
	
	private Set<BookBean> books;

	public AuthorBean() {
		
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
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

	public Set<BookBean> getBooks() {
		return books;
	}

	public void setBooks(Set<BookBean> books) {
		this.books = books;
	}

	@Override
	public String toString() {
		return "AuthorBean [id=" + id + ", lastName=" + lastName + ", firstName=" + firstName + ", description="
				+ description + ", image=" + image + "]";
	}
}
