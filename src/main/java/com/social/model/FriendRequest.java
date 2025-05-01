package com.social.model;

import java.sql.Timestamp;

import com.social.enums.FriendRequestStatus;

public class FriendRequest {
	private int id;
	private User senderId;
	private User friendId;;
	private FriendRequestStatus friendRequestStatus;
	private Timestamp createdAt;
	
	
	
	public FriendRequest(int id, User senderId, User friendId, FriendRequestStatus friendRequestStatus,
			Timestamp createdAt) {
		super();
		this.id = id;
		this.senderId = senderId;
		this.friendId = friendId;
		this.friendRequestStatus = friendRequestStatus;
		this.createdAt = createdAt;
	}
	public FriendRequest(User senderId, User friendId, FriendRequestStatus friendRequestStatus) {
		super();
		this.senderId = senderId;
		this.friendId=friendId;	
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
	
	public User getFriendId() {
		return friendId;
	}
	public void setFriendId(User friendId) {
		this.friendId = friendId;
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
		return "FriendRequest [id=" + id + ", senderId=" + senderId + ", friendId=" + friendId + ", friendRequestStatus="
				+ friendRequestStatus + ", created_at=" + createdAt + "]";
	}

	
	
	

}
