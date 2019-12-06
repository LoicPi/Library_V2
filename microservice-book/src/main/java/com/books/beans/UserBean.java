package com.books.beans;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.books.model.BookCopy;
import com.books.model.Borrowing;

public class UserBean {

	private Integer id;
	
	private String lastName;
	
	private String firstName;
	
	private String email;
	
	private String password;
	
	private Integer cardNumber;
	
	private Integer phoneNumber;
	
	private Date dateRegistration;
	
	private List<Borrowing> borrowings = new ArrayList<Borrowing>();
	
	public UserBean() {
		
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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Integer getCardNumber() {
		return cardNumber;
	}

	public void setCardNumber(Integer cardNumber) {
		this.cardNumber = cardNumber;
	}

	public Integer getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(Integer phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public Date getDateRegistration() {
		return dateRegistration;
	}

	public void setDateRegistration(Date dateRegistration) {
		this.dateRegistration = dateRegistration;
	}

	public List<Borrowing> getBorrowings() {
		return borrowings;
	}

	public void setBorrowings(List<Borrowing> borrowings) {
		this.borrowings = borrowings;
	}

	@Override
	public String toString() {
		return "UserBean [id=" + id + ", lastName=" + lastName + ", firstName=" + firstName + ", email=" + email
				+ ", password=" + password + ", cardNumber=" + cardNumber + ", phoneNumber=" + phoneNumber
				+ ", dateRegistration=" + dateRegistration + ", borrowings=" + borrowings + "]";
	}

}
