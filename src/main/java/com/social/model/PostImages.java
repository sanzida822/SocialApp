package com.social.model;

public class PostImages {
	private int postId;
	private int imageId;

	public PostImages(int postId, int imageId) {
		super();
		this.postId = postId;
		this.imageId = imageId;
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

	@Override
	public String toString() {
		return "PostImages [postId=" + postId + ", imageId=" + imageId + "]";
	}

}
