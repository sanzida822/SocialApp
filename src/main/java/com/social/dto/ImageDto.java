package com.social.dto;

import java.util.Arrays;

public class ImageDto {
    private byte[] data;
    private long size;
    private String contentType;
	public ImageDto(byte[] data, long size, String contentType) {
		super();
		this.data = data;
		this.size = size;
		this.contentType = contentType;
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
		return "ImageDto [data=" + Arrays.toString(data) + ", size=" + size + ", contentType=" + contentType + "]";
	}
    
    
}
