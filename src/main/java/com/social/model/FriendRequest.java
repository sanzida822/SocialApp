package com.social.model;

import java.sql.Timestamp;

import com.social.enums.FriendRequestStatus;

public class FriendRequest {
	private int id;
	private User senderId;
	private User receiverId;;
	private FriendRequestStatus friendRequestStatus;
	private Timestamp createdAt;
	
	
	
	public FriendRequest(int id, User senderId, User receiverId, FriendRequestStatus friendRequestStatus,
			Timestamp createdAt) {
		super();
		this.id = id;
		this.senderId = senderId;
		this.receiverId = receiverId;
		this.friendRequestStatus = friendRequestStatus;
		this.createdAt = createdAt;
	}
	public FriendRequest(User senderId, User receiverId, FriendRequestStatus friendRequestStatus) {
		super();
		this.senderId = senderId;
		this.receiverId=receiverId;	
		this.friendRequestStatus = friendRequestStatus;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public User getSenderId() {
		return senderId;
	}
	public void setSenderId(User senderId) {
		this.senderId = senderId;
	}
	

	public User getReceiverId() {
		return receiverId;
	}
	public void setReceiverId(User receiverId) {
		this.receiverId = receiverId;
	}
	public FriendRequestStatus getFriendRequestStatus() {
		return friendRequestStatus;
	}
	public void setFriendRequestStatus(FriendRequestStatus friendRequestStatus) {
		this.friendRequestStatus = friendRequestStatus;
	}
	public Timestamp getCreatedAt() {
		return createdAt;
	}
	public void setCreatedAt(Timestamp created_at) {
		this.createdAt = created_at;
	}
	@Override
	public String toString() {
		return "FriendRequest [id=" + id + ", senderId=" + senderId + ", receiverId=" + receiverId
				+ ", friendRequestStatus=" + friendRequestStatus + ", createdAt=" + createdAt + "]";
	}

	
	
	
	

}
