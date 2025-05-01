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
		return new Image(rs.getInt("i.id"),rs.getBytes("i.data"), rs.getLong("i.size_bytes"), rs.getTimestamp("i.created_at"),
		        rs.getTimestamp("i.updated_at"));
	}

}
