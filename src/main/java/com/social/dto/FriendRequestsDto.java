package com.social.dto;

import com.social.enums.FriendRequestStatus;

public class FriendRequestsDto {
	int id;
	private int senderId;
	private int receiverId;
	private FriendRequestStatus friendRequestStatus;
	private String friendRequestSent;

	public FriendRequestsDto(int senderId, int receiverId, FriendRequestStatus friendRequestStatus) {
		super();
		this.senderId = senderId;
		this.receiverId = receiverId;
		this.friendRequestStatus = friendRequestStatus;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
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

	public FriendRequestStatus getFriendRequestStatus() {
		return friendRequestStatus;
	}

	public void setFriendRequestStatus(FriendRequestStatus friendRequestStatus) {
		this.friendRequestStatus = friendRequestStatus;
	}

	public String getFriendRequestSent() {
		return friendRequestSent;
	}

	public void setFriendRequestSent(String friendRequestSent) {
		this.friendRequestSent = friendRequestSent;
	}

	@Override
	public String toString() {
		return "FriendRequestsDto [id=" + id + ", senderId=" + senderId + ", receiverId=" + receiverId
				+ ", friendRequestStatus=" + friendRequestStatus + ", friendRequestSent=" + friendRequestSent + "]";
	}

	

}