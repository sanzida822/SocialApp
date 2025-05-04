package com.social.dto;

import java.sql.Timestamp;
import java.util.Arrays;

public class SentRequestsViewDto {
	private int requestId;
	private String receiverName;
	private byte[] receiverProfileImage;
	private int receiverId;
	private Timestamp requestDate;
	public SentRequestsViewDto(int requestId, String receiverName, byte[] receiverProfileImage, int receiverId,
			Timestamp requestDate) {
		super();
		this.requestId = requestId;
		this.receiverName = receiverName;
		this.receiverProfileImage = receiverProfileImage;
		this.receiverId = receiverId;
		this.requestDate = requestDate;
	}
	public int getRequestId() {
		return requestId;
	}
	public void setRequestId(int requestId) {
		this.requestId = requestId;
	}
	public String getReceiverName() {
		return receiverName;
	}
	public void setReceiverName(String receiverName) {
		this.receiverName = receiverName;
	}
	public byte[] getReceiverProfileImage() {
		return receiverProfileImage;
	}
	public void setReceiverProfileImage(byte[] receiverProfileImage) {
		this.receiverProfileImage = receiverProfileImage;
	}
	public int getReceiverId() {
		return receiverId;
	}
	public void setReceiverId(int receiverId) {
		this.receiverId = receiverId;
	}
	public Timestamp getRequestDate() {
		return requestDate;
	}
	public void setRequestDate(Timestamp requestDate) {
		this.requestDate = requestDate;
	}
	@Override
	public String toString() {
		return "SentRequestsViewDto [requestId=" + requestId + ", receiverName=" + receiverName
				+ ", receiverProfileImage=" + Arrays.toString(receiverProfileImage) + ", receiverId=" + receiverId
				+ ", requestDate=" + requestDate + "]";
	}
	
	
}
