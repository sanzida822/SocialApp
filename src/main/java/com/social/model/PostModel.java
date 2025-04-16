package com.social.model;

import java.sql.Timestamp;
import java.util.List;

public class PostModel {
	public enum Privacy {
		ONLY_ME, FRIENDS, PUBLIC
	}
	
	

	int id;
	String content;
	int posted_by;
    List<PostImageModel> post_images;
    Timestamp created_at;
	Timestamp updated_at;
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



	public List<PostImageModel> getPost_images() {
		return post_images;
	}

	public void setPost_images(List<PostImageModel> post_images) {
		this.post_images = post_images;
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

	public Privacy getPrivacy() {
		return privacy;
	}

	public void setPrivacy(Privacy privacy) {
		this.privacy = privacy;
	}

	@Override
	public String toString() {
		return "PostModel [id=" + id + ", content=" + content + ", posted_by=" + posted_by + ", post_images="
				+ post_images + ", created_at=" + created_at + ", updated_at=" + updated_at + ", privacy=" + privacy
				+ "]";
	}

}
