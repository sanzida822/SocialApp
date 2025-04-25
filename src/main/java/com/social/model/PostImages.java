package com.social.model;

import java.sql.Timestamp;
import java.util.Arrays;

public class PostImages {
	int id;
	int postId;
	private byte[] image;
	Timestamp createdAt;
	Timestamp updatedAt;
	
	
	public PostImages(int id, int postId, byte[] postImages) {
		super();
		this.id = id;
		this.postId = postId;
		this.postImages = postImages;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getPostId() {
		return postId;
	}
	public void setPostId(int postId) {
		this.postId = postId;
	}
	public byte[] getPostImages() {
		return postImages;
	}
	public void setPostImages(byte[] postImages) {
		this.postImages = postImages;
	}
	public Timestamp getCreatedAt() {
		return createdAt;
	}
	public void setCreatedAt(Timestamp createdAt) {
		this.createdAt = createdAt;
	}
	public Timestamp getUpdatedAt() {
		return updatedAt;
	}
	public void setUpdatedAt(Timestamp updatedAt) {
		this.updatedAt = updatedAt;
	}
	@Override
	public String toString() {
		return "PostImages [id=" + id + ", postId=" + postId + ", postImages=" + Arrays.toString(postImages)
				+ ", createdAt=" + createdAt + ", updatedAt=" + updatedAt + "]";
	}



}
