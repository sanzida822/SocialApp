package com.social.model;

import java.sql.Timestamp;

import com.social.enums.Privacy;

public class Post {
	private int id;
	private User postedBy;
	private String content;
	private Privacy privacy;
	private Timestamp createdAt;
	private Timestamp updatedAt;

	public Post(User postedBy, String content, Privacy privacy) {
		super();
		this.postedBy = postedBy;
		this.content = content;
		this.privacy = privacy;
	}
	
	public Post(int id,User postedBy, String content, Privacy privacy, Timestamp createdAt, Timestamp updatedAt) {
		super();
		this.id=id;
		this.postedBy = postedBy;
		this.content = content;
		this.privacy = privacy;
		this.createdAt=createdAt;
		this.updatedAt=updatedAt;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public User getPostedBy() {
		return postedBy;
	}

	public void setPostedBy(User postedBy) {
		this.postedBy = postedBy;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Privacy getPrivacy() {
		return privacy;
	}

	public void setPrivacy(Privacy privacy) {
		this.privacy = privacy;
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
		return "Post [id=" + id + ", postedBy=" + postedBy + ", content=" + content + ", privacy=" + privacy
				+ ", createdAt=" + createdAt + ", updatedAt=" + updatedAt + "]";
	}



}