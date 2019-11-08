package com.clientui.beans;

public class UserUpdateBean {

    private Integer id;

    private String updateLastName;

    private String updateFirstName;

    private String updateEmail;
    
    private Integer updatePhoneNumber;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getUpdateLastName() {
		return updateLastName;
	}

	public void setUpdateLastName(String updateLastName) {
		this.updateLastName = updateLastName;
	}

	public String getUpdateFirstName() {
		return updateFirstName;
	}

	public void setUpdateFirstName(String updateFirstName) {
		this.updateFirstName = updateFirstName;
	}

	public String getUpdateEmail() {
		return updateEmail;
	}

	public void setUpdateEmail(String updateEmail) {
		this.updateEmail = updateEmail;
	}

	public Integer getUpdatePhoneNumber() {
		return updatePhoneNumber;
	}

	public void setUpdatePhoneNumber(Integer updatePhoneNumber) {
		this.updatePhoneNumber = updatePhoneNumber;
	}
    
    
}
