package com.social.model;

import java.sql.Timestamp;
import java.util.Arrays;

public class User {
	private int id;
	private String username;
	private String email;
	private String password;
	private String salt;
	private Image profileImage;
	private Timestamp createdAt;
	private Timestamp updatedAt;

	public User(int id, String username, String email, String password, String salt, Image profileImage,
			Timestamp createdAt, Timestamp updatedAt) {
		this.id = id;
		this.username = username;
		this.email = email;
		this.password = password;
		this.salt = salt;
		this.profileImage = profileImage;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
	}

	public User(String username, String email, String password, String salt, Image profileImage) {
		super();
		this.username = username;
		this.email = email;
		this.password = password;
		this.salt = salt;
		this.profileImage = profileImage;
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

	public Timestamp getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Timestamp createdAt) {
		this.createdAt = createdAt;
	}

	public Timestamp getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(Timestamp updatedAt) {
		this.updatedAt = updatedAt;
	}

	public Image getProfileImage() {
		return profileImage;
	}

	public void setProfileImage(Image profileImae) {
		this.profileImage = profileImae;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", username=" + username + ", email=" + email + ", password=" + password + ", salt="
				+ salt + ", profileImage=" + profileImage + ", createdAt=" + createdAt + ", updatedAt=" + updatedAt
				+ "]";
	}

}
