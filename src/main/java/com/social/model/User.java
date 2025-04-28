package com.social.model;

import java.util.Arrays;

public class User {
	private int id;
	private String username;
	private String email;
	private String password;
	private String salt;
	private byte[] ProfileImage;
	private String createdAt;
	private String updatedAt;

    public User() {}
	public User(int id, String username, String email, String password, String salt, byte[] ProfileImage, String createdAt,
			String updatedAt) {   
		this.id=id;
		this.username = username;
		this.email = email;
		this.password = password;
		this.salt = salt;
		this.ProfileImage = ProfileImage;
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


	public byte[] getProfileImage() {
		return ProfileImage;
	}

	public void setProfileImage(byte[] profileImage) {
		ProfileImage = profileImage;
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
				+ salt + ", ProfileImage=" + Arrays.toString(ProfileImage) + ", createdAt=" + createdAt + ", updatedAt="
				+ updatedAt + "]";
	}




}
