package com.techpixe.ehr.dto;

public class UserDto {
	private Long user_Id;
	private String fullName;
	private String email;
	private Long mobileNumber;
	private String password;

	public UserDto() {
		super();
	}

	public UserDto(Long id, String fullName, String email, Long mobileNumber, String password) {
		super();
		this.user_Id = id;
		this.fullName = fullName;
		this.email = email;
		this.mobileNumber = mobileNumber;
		this.password = password;
	}

	public Long getId() {
		return user_Id;
	}

	public void setId(Long id) {
		this.user_Id = id;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Long getMobileNumber() {
		return mobileNumber;
	}

	public void setMobileNumber(Long mobileNumber) {
		this.mobileNumber = mobileNumber;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}
