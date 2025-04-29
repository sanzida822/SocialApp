package com.social.dto;

import java.sql.Timestamp;
import java.util.Arrays;

public class UserDto {
	private int id;
	private String username;
	private String email;
	int profileImageId;
	private Timestamp userCreated;
	private Timestamp userUpdated;

	public UserDto(int id, String username, String email, int profileImageId, Timestamp userCreated,
			Timestamp userUpdated) {
		super();
		this.id = id;
		this.username = username;
		this.email = email;
		this.profileImageId = profileImageId;
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

	
	public int getProfileImageId() {
		return profileImageId;
	}

	public void setProfileImageId(int profileImageId) {
		this.profileImageId = profileImageId;
	}

	public Timestamp getUserCreated() {
		return userCreated;
	}

	public void setUserCreated(Timestamp userCreated) {
		this.userCreated = userCreated;
	}

	public Timestamp getUserUpdated() {
		return userUpdated;
	}

	public void setUserUpdated(Timestamp userUpdated) {
		this.userUpdated = userUpdated;
	}

	@Override
	public String toString() {
		return "UserDto [id=" + id + ", username=" + username + ", email=" + email + ", profileImageId="
				+ profileImageId + ", userCreated=" + userCreated + ", userUpdated=" + userUpdated + "]";
	}


}
