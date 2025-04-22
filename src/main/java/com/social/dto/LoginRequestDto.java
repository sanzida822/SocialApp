package com.social.dto;

public class LoginRequestDto {
	private String user_email;
	private String password;
	public String getUser_email() {
		return user_email;
	}
	public void setUser_email(String user_email) {
		this.user_email = user_email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	@Override
	public String toString() {
		return "LoginRequestDto [user_email=" + user_email + ", password=" + password + "]";
	}
	
	

}
