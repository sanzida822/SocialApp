package com.social.model;

import java.io.Serializable;

public class UserModel {

	int id;
	String username;
	String email;
	String password;
	String confirm_password;
	String image;
	String created_at;
	String updated_at;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}



	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setConfirmPassword(String confirm_password) {
		this.confirm_password = confirm_password;
	}

	public String getConfirmpassword() {
		return confirm_password;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public String getCreated_at() {
		return created_at;
	}

	public void setCreated_at(String created_at) {
		this.created_at = created_at;
	}

	public String getUpdated_at() {
		return updated_at;
	}

	public void setUpdated_at(String updated_at) {
		this.updated_at = updated_at;
	}

	@Override
	public String toString() {
		return "Registration [id=" + id + ", uname=" + username + ", email=" + email + ", password=" + password
				+ ", image=" + image + ", created_at=" + created_at + ", updated_at=" + updated_at + "]";
	}

}
