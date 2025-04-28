package com.social.model;

import java.sql.Timestamp;
import java.util.Arrays;

public class PostImages {
	private int postId;
	private int imageId;
	private Timestamp updatedAt;

	public PostImages(int postId, int imageId, Timestamp updatedAt) {
		super();
		this.postId = postId;
		this.imageId = imageId;
		this.updatedAt = updatedAt;
	}

	public int getPostId() {
		return postId;
	}

	public void setPostId(int postId) {
		this.postId = postId;
	}

	public int getImageId() {
		return imageId;
	}

	public void setImageId(int imageId) {
		this.imageId = imageId;
	}

	public Timestamp getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(Timestamp updatedAt) {
		this.updatedAt = updatedAt;
	}

	@Override
	public String toString() {
		return "PostImages [postId=" + postId + ", imageId=" + imageId + ", updatedAt=" + updatedAt + "]";
	}

}
