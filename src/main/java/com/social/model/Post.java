package com.social.model;

import java.sql.Timestamp;

import com.social.enums.Privacy;

public class Post {
	private int id;
	private User postedBy;
	private String content;
	private Privacy privacy;
	private Timestamp created_at;
	private Timestamp updated_at;

	public Post(User postedBy, String content, Privacy privacy) {
		super();
		this.postedBy = postedBy;
		this.content = content;
		this.privacy = privacy;
	}
	
	public Post(int id,User postedBy, String content, Privacy privacy, Timestamp createdAt, Timestamp updated_at) {
		super();
		this.id=id;
		this.postedBy = postedBy;
		this.content = content;
		this.privacy = privacy;
		this.created_at=createdAt;
		this.updated_at=updated_at;
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

	public Timestamp getCreated_at() {
		return created_at;
	}

	public void setCreated_at(Timestamp created_at) {
		this.created_at = created_at;
	}

	public Timestamp getUpdated_at() {
		return updated_at;
	}

	public void setUpdated_at(Timestamp updated_at) {
		this.updated_at = updated_at;
	}

	@Override
	public String toString() {
		return "Post [id=" + id + ", postedBy=" + postedBy + ", content=" + content + ", privacy=" + privacy
				+ ", created_at=" + created_at + ", updated_at=" + updated_at + "]";
	}

}