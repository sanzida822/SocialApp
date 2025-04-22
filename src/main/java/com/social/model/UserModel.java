package com.social.model;

import java.util.Arrays;


public class UserModel {

	private int id;
	private String user_name;
	private String user_email;
	private String password;
	private String salt;
	private byte[] user_image;
	private String created_at;
	private String updated_at;
	@Override
	public String toString() {
		return "UserModel [id=" + id + ", user_name=" + user_name + ", user_email=" + user_email + ", password="
				+ password + ", salt=" + salt + ", user_image=" + Arrays.toString(user_image) + ", created_at="
				+ created_at + ", updated_at=" + updated_at + "]";
	}
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
	public String getSalt() {
		return salt;
	}
	public void setSalt(String salt) {
		this.salt = salt;
	}
	public byte[] getUser_image() {
		return user_image;
	}
	public void setUser_image(byte[] user_image) {
		this.user_image = user_image;
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



	
}
