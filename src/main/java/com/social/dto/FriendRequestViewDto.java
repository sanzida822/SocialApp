package com.social.dto;

import java.sql.Timestamp;
import java.util.Arrays;

import com.social.model.Image;

public class FriendRequestViewDto {
	private int requestId;
	private String senderUsername;
	private byte[] SenderProfileImage;
	private int senderId;
	private Timestamp requestDate;
	public FriendRequestViewDto(int requestId, String senderUsername, byte[] SenderProfileImage, int senderId,
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

	public byte[] getSenderProfileImage() {
		return SenderProfileImage;
	}
	public void setSenderProfileImage(byte[] senderProfileImage) {
		SenderProfileImage = senderProfileImage;
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
		return "FriendRequestViewDto [requestId=" + requestId + ", senderUsername=" + senderUsername
				+ ", SenderProfileImage=" + Arrays.toString(SenderProfileImage) + ", senderId=" + senderId
				+ ", requestDate=" + requestDate + "]";
	}

	
}
