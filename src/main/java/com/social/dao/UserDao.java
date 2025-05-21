package com.social.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.social.config.DBConnection;
import com.social.dto.ImageDto;
import com.social.exception.ImageInsertionFailedException;
import com.social.exception.UserSaveFailedException;
import com.social.mapper.UserMapper;
import com.social.model.User;
import com.social.model.Image;
import com.social.service.ImageService;

import com.social.util.MessageUtil;

public class UserDao {
	private static final Logger logger = LoggerFactory.getLogger(UserDao.class);
	private UserMapper userMapper = UserMapper.getInstance();
	private static ImageDao imageDao=ImageDao.getInstance();
	private static UserDao userDao;

	private UserDao() {}

	public static UserDao getInstance() {
		if (userDao == null) {
			userDao = new UserDao();
		}
		return userDao;
	}


	public boolean save(User user, Connection connection) throws Exception {
		String sql = "insert into users (user_name,user_email,password,salt,image_id) values(?,?,?,?,?)";
		boolean status = false;
		try (PreparedStatement ps = connection.prepareStatement(sql)) {
			ps.setString(1, user.getUsername());
			ps.setString(2, user.getEmail());
			ps.setString(3, user.getPassword());
			ps.setString(4, user.getSalt());
			ps.setInt(5, user.getProfileImage().getId());
			int rowsAffect = ps.executeUpdate();
			if (rowsAffect > 0) {
				status = true;
				logger.info("Registration completed successfully for user: " + user);
			} else {
				logger.error("Registration failed for user: " + user);
			}
			return status;
		}
	}

	public boolean register(User user, Image image) throws SQLException {
		Connection connection = null;
		 try {
			 connection = DBConnection.getInstance().getConnection();
			 connection.setAutoCommit(false);
			 Image profileImage=imageDao.save(image, connection);
			 if(profileImage==null) {
				 throw new ImageInsertionFailedException(MessageUtil.getMessage("error.image.insert"));
			 }
			 user.setProfileImage(image);
			// user.setEmail("sanzidasultana824@gmail.com"); 
			 logger.debug("saved image id is:{}", profileImage.getId());
			 boolean isSaved=save(user, connection);
			 if(!isSaved) {
				 throw new UserSaveFailedException(MessageUtil.getMessage("error.user.save"));
			 }
			 connection.commit();
			 return true;
		     }catch(Exception e) {
		    	 connection.rollback();
		    	 logger.error("User registration failed: {}", e.getMessage(), e); 
		    	 return false;
		     }finally {
		    	 connection.setAutoCommit(true);
		    	 connection.close();
		     }	
	}

	public User findByEmail(String email) throws SQLException, Exception {
		String sql = "select * from users where user_email=?";

		try (Connection connection = DBConnection.getInstance().getConnection();
				PreparedStatement ps = connection.prepareStatement(sql);) {
			ps.setString(1, email);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				logger.info("user is found for email:{}", email);
				return userMapper.toEntity(rs);
			} else {
				logger.info("duplicate email for:{}", email);
			}
		}
		return null;
	}

	public User findById(int id) throws Exception {
		String sql = "select * from users where id=?";

		try (Connection connection = DBConnection.getInstance().getConnection();
				PreparedStatement ps = connection.prepareStatement(sql);

		) {
			logger.info("connection is:{}", connection);
			ps.setInt(1, id);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				logger.info("user is found for id:{}", id);
				return userMapper.toEntity(rs);
			} else {
				logger.info("User not found for id:{}", id);
			}
		}
		return null;
	}

}
