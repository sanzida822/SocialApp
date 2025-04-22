package com.social.dto;

import java.util.Arrays;

import javax.servlet.http.Part;

public class RegistrationRequestDTO {

	private int id;
	private String user_name;
	private String user_email;
	private String password;
	private String confirm_password;
	private byte[] user_image;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUser_name() {
		return user_name;
	}

	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}

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

	public String getConfirm_password() {
		return confirm_password;
	}

	public void setConfirm_password(String confirm_password) {
		this.confirm_password = confirm_password;
	}

	public byte[] getUser_image() {
		return user_image;
	}

	public void setUser_image(byte[] user_image) {
		this.user_image = user_image;
	}

	@Override
	public String toString() {
		return "RegistrationRequestDTO [id=" + id + ", user_name=" + user_name + ", user_email=" + user_email
				+ ", password=" + password + ", confirm_password=" + confirm_password + ", user_image="
				+ Arrays.toString(user_image) + "]";
	}

}
