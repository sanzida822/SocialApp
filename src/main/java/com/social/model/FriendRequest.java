package com.social.model;

import java.sql.Timestamp;

import com.social.enums.FriendRequestStatus;

public class FriendRequest {
	private int id;
	private User senderId;
	private User friendId;;
	private FriendRequestStatus friendRequestStatus;
	private Timestamp created_at;
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
	public Timestamp getCreated_at() {
		return created_at;
	}
	public void setCreated_at(Timestamp created_at) {
		this.created_at = created_at;
	}
	@Override
	public String toString() {
		return "FriendRequest [id=" + id + ", senderId=" + senderId + ", friendId=" + friendId + ", friendRequestStatus="
				+ friendRequestStatus + ", created_at=" + created_at + "]";
	}

	
	
	

}
