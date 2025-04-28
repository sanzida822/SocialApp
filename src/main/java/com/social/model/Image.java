package com.social.model;

import java.sql.Timestamp;
import java.util.Arrays;

public class Image {
	private int id;
	private byte[] data;
	private Long sizeBytes;
	private Timestamp createdAt;

	public Image(byte[] data, Long sizeBytes) {
		super();
		this.data = data;
		this.sizeBytes = sizeBytes;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public byte[] getData() {
		return data;
	}

	public void setData(byte[] data) {
		this.data = data;
	}


	public Long getSizeBytes() {
		return sizeBytes;
	}

	public void setSizeBytes(Long sizeBytes) {
		this.sizeBytes = sizeBytes;
	}

	public Timestamp getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Timestamp createdAt) {
		this.createdAt = createdAt;
	}

	@Override
	public String toString() {
		return "Image [id=" + id + ", data=" + Arrays.toString(data) + ", sizeBytes=" + sizeBytes + ", createdAt="
				+ createdAt + "]";
	}



}
