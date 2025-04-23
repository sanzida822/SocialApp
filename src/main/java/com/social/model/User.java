package com.social.model;

import java.util.Arrays;

public class User {
	private int id;
	private String username;
	private String email;
	private String password;
	private String salt;
	private byte[] image;
	private String createdAt;
	private String updatedAt;

	public User(String email, String password) {  //for login dto conversion
		this.email = email;
		this.password = password;
	}

	public User(String username, String email, String password, byte[] image,String salt) {   //for registration dto conversion
		super();
		this.username = username;
		this.email = email;
		this.password = password;
		this.image = image;
		this.salt=salt;
	}

	public User(int id, String username, String email, String password, String salt, byte[] image, String createdAt,
			String updatedAt) {    //for resultset
		this.id = id;
		this.username = username;
		this.email = email;
		this.password = password;
		this.salt = salt;
		this.image = image;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
	}

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

	public byte[] getImage() {
		return image;
	}

	public void setImage(byte[] image) {
		this.image = image;
	}

	public String getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(String createdAt) {
		this.createdAt = createdAt;
	}

	public String getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(String updatedAt) {
		this.updatedAt = updatedAt;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", username=" + username + ", email=" + email + ", password=" + password + ", salt="
				+ salt + ", image=" + Arrays.toString(image) + ", createdAt=" + createdAt + ", updatedAt=" + updatedAt
				+ "]";
	}

}
