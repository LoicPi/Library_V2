package com.clientui.beans;

public class UserBean {
	
	private Integer id;
	
	private String lastName;
	
	private String firstName;
	
	private String email;
	
	private String password;
	
	private String passwordControl;
	
	private Integer cardNumber;
	
	private Integer phoneNumber;
	
	private String dateRegistration;
	
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

	public String getPasswordControl() {
		return passwordControl;
	}

	public void setPasswordControl(String passwordControl) {
		this.passwordControl = passwordControl;
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

	public String getDateRegistration() {
		return dateRegistration;
	}

	public void setDateRegistration(String dateRegistration) {
		this.dateRegistration = dateRegistration;
	}

	@Override
	public String toString() {
		return "UserBean [id=" + id + ", lastName=" + lastName + ", firstName=" + firstName + ", email=" + email
				+ ", password=" + password + ", passwordControl=" + passwordControl + ", cardNumber=" + cardNumber
				+ ", phoneNumber=" + phoneNumber + "]";
	}
}
