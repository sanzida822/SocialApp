package com.social.dto;

import java.util.Arrays;

import javax.servlet.http.Part;

public class RegistrationRequestDTO {
	private String username;
	private String email;
	private String password;
	private String confirmPassword;
	private Part profileImagePart;
	private byte[] profileImage;

	public RegistrationRequestDTO() {}
	public RegistrationRequestDTO(String username, String email, String password, String confirmPassword,
			byte[] profileImage,Part profileImagePart) {
		super();
		this.username = username;
		this.email = email;
		this.password = password;
		this.confirmPassword = confirmPassword;
		this.profileImage = profileImage;
		this.profileImagePart=profileImagePart;
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

	public String getConfirmPassword() {
		return confirmPassword;
	}

	public void setConfirmPassword(String confirmPassword) {
		this.confirmPassword = confirmPassword;
	}

	public byte[] getProfileImage() {
		return profileImage;
	}

	public Part getProfileImagePart() {
		return profileImagePart;
	}
	public void setProfileImagePart(Part profileImagePart) {
		this.profileImagePart = profileImagePart;
	}
	public void setProfileImage(byte[] profileImage) {
		this.profileImage = profileImage;
	}
	@Override
	public String toString() {
		return "RegistrationRequestDTO [username=" + username + ", email=" + email + ", password=" + password
				+ ", confirmPassword=" + confirmPassword + ", profileImagePart=" + profileImagePart + ", profileImage="
				+ Arrays.toString(profileImage) + "]";
	}



}
