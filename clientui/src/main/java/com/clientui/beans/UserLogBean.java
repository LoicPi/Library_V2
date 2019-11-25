package com.clientui.beans;

public class UserLogBean {

	private String email;
	
	private String password;
	
	public UserLogBean() {
		
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

	@Override
	public String toString() {
		return "UserLogBean [email=" + email + ", password=" + password + "]";
	}
}
