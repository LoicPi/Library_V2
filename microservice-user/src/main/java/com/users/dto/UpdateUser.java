package com.users.dto;

public class UpdateUser {
	
    private Integer id;

    private String updateLastName;

    private String updateFirstName;

    private String updateEmail;
    
    private String updatePhoneNumber;

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

	public String getUpdatePhoneNumber() {
		return updatePhoneNumber;
	}

	public void setUpdatePhoneNumber(String updatePhoneNumber) {
		this.updatePhoneNumber = updatePhoneNumber;
	}    

}
