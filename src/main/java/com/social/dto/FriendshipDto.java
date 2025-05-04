package com.social.dto;

import com.social.enums.FriendshipStatus;

public class FriendshipDto {
	private int senderId;
	private int receiverId;
	private FriendshipStatus status;

	public FriendshipDto(int senderId, int receiverId, FriendshipStatus status) {
		super();
		this.senderId = senderId;
		this.receiverId = receiverId;
		this.status = status;
	}

	public int getSenderId() {
		return senderId;
	}

	public void setSenderId(int senderId) {
		this.senderId = senderId;
	}

	public int getReceiverId() {
		return receiverId;
	}

	public void setReceiverId(int receiverId) {
		this.receiverId = receiverId;
	}

	public FriendshipStatus getStatus() {
		return status;
	}

	public void setStatus(FriendshipStatus status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "FriendshipDto [senderId=" + senderId + ", receiverId=" + receiverId + ", status=" + status + "]";
	}

}
