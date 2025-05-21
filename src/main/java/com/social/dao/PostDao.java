package com.social.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.social.mapper.PostMapper;
import com.social.mapper.UserMapper;
import com.social.model.Image;
import com.social.model.Post;
import com.social.model.User;
import com.social.util.CommonUtil;
import com.social.util.MessageUtil;
import com.social.config.DBConnection;
import com.social.exception.ImageInsertionFailedException;
import com.social.exception.PostSaveFailedException;

public class PostDao {
	private static final Logger logger = LoggerFactory.getLogger(PostDao.class);
	private static PostDao postDao;
	private static PostMapper postMaper = PostMapper.getInstance();
	private static ImageDao imageDao = ImageDao.getInstance();
	private static PostImageDao postImageDao = PostImageDao.getInstance();

	private PostDao() {
	};

	public static PostDao getInstance() {
		if (postDao == null) {
			postDao = new PostDao();
		}
		return postDao;
	}

	public int save(Post post, Connection connection) throws Exception {
		String sql = "insert into posts (posted_by,content,privacy) values (?,?,?)";
		try (PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
			ps.setInt(1, post.getPostedBy().getId());
			ps.setString(2, post.getContent());
			ps.setString(3, post.getPrivacy().name());
			int rowsAffected = ps.executeUpdate();
			if (rowsAffected > 0) {
				try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
					if (generatedKeys.next()) {
						return generatedKeys.getInt(1);
					}
				}
			}
		}
		return 0;
	}

	public boolean saveWithImage(Post post, List<Image> images) throws SQLException {
		Connection connection = null;
		try {
			connection = DBConnection.getInstance().getConnection();
			connection.setAutoCommit(false);
			int postId = save(post, connection);
			if (postId < 1) {
				logger.error("Error while saving post in database");
				throw new PostSaveFailedException(MessageUtil.getMessage("post.save.fail"));
			}

			if (!CommonUtil.isNullOrEmpty(images)) {
				for (Image image : images) {
					Image savedImage = imageDao.save(image, connection);
					if (savedImage == null) {
						logger.error("Error while saving post's image in database");
						throw new ImageInsertionFailedException(MessageUtil.getMessage("error.image.insert"));
					}

					boolean savedPostAndImage = postImageDao.save(postId, savedImage.getId(), connection);
					if (!savedPostAndImage) {
						throw new ImageInsertionFailedException(MessageUtil.getMessage("error.image.insert"));
					}
				}
			}
			connection.commit();
			return true;
		} catch (Exception e) {
			connection.rollback();
			logger.error("Error occured while saving post with image:{}", e.getMessage(), e);
			return false;
		} finally {
			connection.setAutoCommit(true);
			connection.close();
		}

	}

	public boolean deleteById(int id) throws SQLException, Exception {
		String sql = "delete * from posts where id=?";
		try (Connection connection = DBConnection.getInstance().getConnection();
				PreparedStatement ps = connection.prepareStatement(sql)) {
			ps.setInt(1, id);
			return ps.executeUpdate() > 0;

		}
	}

	public List<Post> getVisiblePostForUser(int loggedInUserId) throws Exception {
		List<Post> postList = new ArrayList<>();
		String sql = "select p.id as post_id, p.content,p.privacy,p.created_at,p.updated_at,u.id as user_id, u.user_name as user_name, "
				+ "u.user_email as user_email from posts p join users u on "
				+ "p.posted_by=u.id where p.posted_by=? or p.privacy='PUBLIC' "
				+ "or (p.privacy='FRIENDS' AND p.posted_by in ("
				+ "select sender_id from friendship where receiver_id=? "
				+ "union select receiver_id from friendship where sender_id=?" + " )" + ")"
				+ " order by p.created_at desc";
		try (Connection connection = DBConnection.getInstance().getConnection();
				PreparedStatement ps = connection.prepareStatement(sql)) {
			ps.setInt(1, loggedInUserId);
			ps.setInt(2, loggedInUserId);
			ps.setInt(3, loggedInUserId);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				User postedBy = new User(rs.getInt("user_id"), rs.getString("user_name"), rs.getString("user_email"));
				Post post = postMaper.toEntity(rs, postedBy);
				postList.add(post);
			}
		}
		return postList;
	}

}
