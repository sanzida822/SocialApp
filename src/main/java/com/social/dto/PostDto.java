package com.social.dto;
import java.sql.Timestamp;
import java.util.List;
import com.social.enums.Privacy;

public class PostDto {
	private int postedBy;
	private String content;
	private Privacy privacy;
	private List<byte[]> images;
	private Timestamp createdAt;
	private Timestamp updatedAt;

	public PostDto(int postedBy, String content, Privacy privacy, List<byte[]> images) {
		super();
		this.postedBy = postedBy;
		this.content = content;
		this.privacy = privacy;
		this.images = images;
	}

	public int getPostedBy() {
		return postedBy;
	}

	public void setPostedBy(int postedBy) {
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

	public List<byte[]> getImages() {
		return images;
	}

	public void setImages(List<byte[]> images) {
		this.images = images;
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
		return "PostDto [postedBy=" + postedBy + ", content=" + content + ", privacy=" + privacy + ", images=" + images
				+ ", createdAt=" + createdAt + ", updatedAt=" + updatedAt + "]";
	}




}
