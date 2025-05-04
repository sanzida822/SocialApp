package com.social.model;

import com.social.enums.FriendshipStatus;

public class Friendship {
	private int id;
	private int senderId;
	private int receiverId;
	private FriendshipStatus friendShipstatus;
	private int createdAt;

	public Friendship(int id, int senderId, int receiverId, FriendshipStatus friendShipstatus, int createdAt) {
		super();
		this.id = id;
		this.senderId = senderId;
		this.receiverId = receiverId;
		this.friendShipstatus = friendShipstatus;
		this.createdAt = createdAt;
	}

	public Friendship(int senderId, int receiverId, FriendshipStatus friendShipstatus) {
		super();
		this.senderId = senderId;
		this.receiverId = receiverId;
		this.friendShipstatus = friendShipstatus;
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

	public FriendshipStatus getFriendShipstatus() {
		return friendShipstatus;
	}

	public void setFriendShipstatus(FriendshipStatus friendShipstatus) {
		this.friendShipstatus = friendShipstatus;
	}

	public int getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(int createdAt) {
		this.createdAt = createdAt;
	}

	@Override
	public String toString() {
		return "FriendShip [id=" + id + ", senderId=" + senderId + ", receiverId=" + receiverId + ", friendShipstatus="
				+ friendShipstatus + ", createdAt=" + createdAt + "]";
	}

}
