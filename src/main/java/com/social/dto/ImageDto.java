package com.social.dto;

import java.util.Arrays;

public class ImageDto {
	int id;
    private byte[] data;
    private long size;
    private String contentType;
    
    public ImageDto() {}
	public ImageDto(byte[] data, long size, String contentType) {
		super();
		this.data = data;
		this.size = size;
		this.contentType = contentType;
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
	public long getSize() {
		return size;
	}
	public void setSize(long size) {
		this.size = size;
	}
	public String getContentType() {
		return contentType;
	}
	
	public void setContentType(String contentType) {
		this.contentType = contentType;
	}

	@Override
	public String toString() {
		return "ImageDto [id=" + id + ", data=" + Arrays.toString(data) + ", size=" + size + ", contentType="
				+ contentType + "]";
	}
	

    
}
