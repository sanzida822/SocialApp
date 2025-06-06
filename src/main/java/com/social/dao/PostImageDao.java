package com.social.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.social.config.DBConnection;

public class PostImageDao {
	private static final Logger logger = LoggerFactory.getLogger(PostImageDao.class);
	private static PostImageDao postImagesDao;

	private PostImageDao() {
	};

	public static PostImageDao getInstance() {
		if (postImagesDao == null) {
			postImagesDao = new PostImageDao();
		}
		return postImagesDao;
	}

	public boolean save(int postId, int imageId, Connection connection) throws Exception {
		String sql = "insert into post_images (post_id, image_id) values (?, ?)";
		try (PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
			ps.setInt(1, postId);
			ps.setInt(2, imageId);
			int rowsAffected = ps.executeUpdate();
			return rowsAffected > 0;
		}
	}
}
