package com.social.mapper;

import com.social.model.Image;

public class ImageMapper {
	private static ImageMapper imageMapper;

	private ImageMapper() {
	}

	public static ImageMapper getInstance() {
		if (imageMapper == null) {
			imageMapper = new ImageMapper();
		}
		return imageMapper;
	}
	
	public Image toEntity(byte[] imageBytes) {
		return new Image(imageBytes, (long) imageBytes.length);
	}

}
