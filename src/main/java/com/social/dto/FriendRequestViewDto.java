package com.social.dto;

import java.sql.Timestamp;
import java.util.Arrays;

import com.social.model.Image;

public class FriendRequestViewDto {
	private int requestId;
	private String senderUsername;
	private Image SenderProfileImage;
	private int senderId;
	private Timestamp requestDate;
	public FriendRequestViewDto(int requestId, String senderUsername, Image SenderProfileImage, int senderId,
			Timestamp timestamp) {
		super();
		this.requestId = requestId;
		this.senderUsername = senderUsername;
		this.SenderProfileImage = SenderProfileImage;
		this.senderId = senderId;
		this.requestDate = timestamp;
	}
	public int getRequestId() {
		return requestId;
	}
	public void setRequestId(int requestId) {
		this.requestId = requestId;
	}
	public String getSenderUsername() {
		return senderUsername;
	}
	public Image getSenderProfileImage() {
		return SenderProfileImage;
	}
	public void setSenderProfileImage(Image profileImage) {
		this.SenderProfileImage = profileImage;
	}
	public void setSenderUsername(String senderUsername) {
		this.senderUsername = senderUsername;
	}

	public int getSenderId() {
		return senderId;
	}
	public void setSenderId(int senderId) {
		this.senderId = senderId;
	}
	public Timestamp getRequestDate() {
		return requestDate;
	}
	public void setRequestDate(Timestamp requestDate) {
		this.requestDate = requestDate;
	}
	@Override
	public String toString() {
		return "FriendRequestViewDto [requestId=" + requestId + ", senderUsername=" + senderUsername + ", profileImage="
				+ SenderProfileImage + ", senderId=" + senderId + ", requestDate=" + requestDate + "]";
	}
	
	
}
