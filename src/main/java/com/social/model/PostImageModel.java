package com.social.model;

import java.sql.Timestamp;

public class PostImageModel {
	int id;
	int post_id;
	String image_url;
	Timestamp created_at;
	Timestamp updated_at;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getPost_id() {
		return post_id;
	}

	public void setPost_id(int post_id) {
		this.post_id = post_id;
	}

	public String getImage_url() {
		return image_url;
	}

	public void setImage_url(String image_url) {
		this.image_url = image_url;
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
		return "PostImageModel [id=" + id + ", post_id=" + post_id + ", image_url=" + image_url + ", created_at="
				+ created_at + ", updated_at=" + updated_at + "]";
	}

}
