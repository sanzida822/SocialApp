package com.social.model;

public class PostModel {
	public enum Privacy {
		ONLY_ME, FRIENDS, PUBLIC
	}

	int id;
	String content;
	int posted_by;
	String post_image;
	String created_at;
	String updated_at;
	Privacy privacy;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public int getPosted_by() {
		return posted_by;
	}

	public void setPosted_by(int posted_by) {
		this.posted_by = posted_by;
	}

	public String getPost_image() {
		return post_image;
	}

	public void setPost_image(String post_image) {
		this.post_image = post_image;
	}

	public String getCreated_at() {
		return created_at;
	}

	public void setCreated_at(String created_at) {
		this.created_at = created_at;
	}

	public String getUpdated_at() {
		return updated_at;
	}

	public void setUpdated_at(String updated_at) {
		this.updated_at = updated_at;
	}

	public Privacy getPrivacy() {
		return privacy;
	}

	public void setPrivacy(Privacy privacy) {
		this.privacy = privacy;
	}

	@Override
	public String toString() {
		return "PostModel [id=" + id + ", content=" + content + ", posted_by=" + posted_by + ", post_image="
				+ post_image + ", created_at=" + created_at + ", updated_at=" + updated_at + ", privacy=" + privacy
				+ "]";
	}

}
