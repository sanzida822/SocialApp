package com.social.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.social.model.Image;
import com.social.model.User;

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
		return new Image(imageBytes, (int)imageBytes.length);
	}
	public Image toEntity(ResultSet rs) throws SQLException {
		return new Image(rs.getInt("id"),rs.getBytes("data"), rs.getLong("size_bytes"), rs.getTimestamp("created_at"),
		        rs.getTimestamp("updated_at"));
	}

}
