package com.social.dto;

import java.sql.Timestamp;
import java.util.List;

import com.social.enums.Privacy;
import com.social.model.PostImages;


public class PostDto {
	private int postedBy;
	private String postContent;
	private List<byte[]> postImages;
	private Timestamp created_at;
	private Timestamp updated_at;
	private Privacy privacy;

	public PostDto(int postedBy, String postContent, List<byte[]> postImages2, Privacy privacy) {
		super();
		this.postedBy = postedBy;
		this.postContent = postContent;
		this.postImages =postImages2;
		this.privacy = privacy;
	}

	public int getPostedBy() {
		return postedBy;
	}

	public void setPostedBy(int postedBy) {
		this.postedBy = postedBy;
	}

	public String getPostContent() {
		return postContent;
	}

	public void setPostContent(String postContent) {
		this.postContent = postContent;
	}



	public List<byte[]> getPostImages() {
		return postImages;
	}

	public void setPostImages(List<byte[]> postImages) {
		this.postImages = postImages;
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
		return "PostDto [postedBy=" + postedBy + ", postContent=" + postContent + ", postImages=" + postImages
				+ ", created_at=" + created_at + ", updated_at=" + updated_at + ", privacy=" + privacy + "]";
	}


}
