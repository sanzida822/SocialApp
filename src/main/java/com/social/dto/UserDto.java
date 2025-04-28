package com.social.dto;

import java.util.Arrays;

public class UserDto {
	private int id;
	private String username;
	private String email;
	private byte[] profileImage;
	private String userCreated;
	private String userUpdated;

	public UserDto(int id,String username, String email, byte[] profileImage, String userCreated, String userUpdated) {
		super();
		this.id=id;
		this.username = username;
		this.email = email;
		this.profileImage = profileImage;
		this.userCreated = userCreated;
		this.userUpdated = userUpdated;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public UserDto(String username, String email) {
		super();
		this.username = username;
		this.email = email;
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

	public byte[] getProfileImage() {
		return profileImage;
	}

	public void setProfileImage(byte[] profileImage) {
		this.profileImage = profileImage;
	}

	public String getUserCreated() {
		return userCreated;
	}

	public void setUserCreated(String userCreated) {
		this.userCreated = userCreated;
	}

	public String getUserUpdated() {
		return userUpdated;
	}

	public void setUserUpdated(String userUpdated) {
		this.userUpdated = userUpdated;
	}

	@Override
	public String toString() {
		return "UserDto [id=" + id + ", username=" + username + ", email=" + email + ", profileImage="
				+ Arrays.toString(profileImage) + ", userCreated=" + userCreated + ", userUpdated=" + userUpdated + "]";
	}

}
