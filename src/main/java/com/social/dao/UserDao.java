package com.social.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import com.social.model.User;
import com.social.util.DAOUtil;
import com.social.util.DBConnection;
import com.social.util.PasswordUtil;

public class UserDao {
	private static final Logger logger = LoggerFactory.getLogger(UserDao.class);

	private static UserDao instance;

	private UserDao() {

	}

	public static UserDao getInstance() {
		if (instance == null) {
			synchronized (UserDao.class) {
				if (instance == null) {
					instance = new UserDao();
				}
			}

		}
		return instance;

	}

	public boolean save(User userModel) throws Exception {
		String sql = "Insert into users (user_name,user_email,password,user_image,salt) values(?,?,?,?,?)";
		boolean status = false;
		try (Connection connection = DBConnection.getInstance().getConnection();
				PreparedStatement ps = connection.prepareStatement(sql)

		) {
			DAOUtil.setUserParams(ps, userModel);
			int rowsAffect = ps.executeUpdate();
            if(rowsAffect>0) {
            	status=true;
            	logger.info("Registration completed successfully for user: " + userModel);
            	
            }else {
                logger.error("Registration failed for user: " + userModel);
            }
			return status;
		}
	}

	public User findByEmail(String email) throws SQLException, Exception {
		User userModel = null;
		String sql = "SELECT * FROM users WHERE user_email = ?";
		try (Connection connection = DBConnection.getInstance().getConnection();
				PreparedStatement ps = connection.prepareStatement(sql);

		) {
			ps.setString(1, email);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				logger.info("user is found for email:{}", email);
			    return DAOUtil.mapResultSetToUser(rs);
			
			}else {
				
				logger.info("duplicate email for:{} user object:{} ", email, userModel);
			}
		} 
		return null;
	}

	public User findById(int id) throws Exception {
		logger.info("Request comes for checking users existence by its id:{}",id);
		User user = null;
		String sql = "Select * from users where id=?";
		try (Connection connection = DBConnection.getInstance().getConnection();
				PreparedStatement ps = connection.prepareStatement(sql);

		) {
			ps.setInt(1, id);
			ResultSet rowsAffect = ps.executeQuery();
			if (rowsAffect.next()) {
				DAOUtil.mapResultSetToUser(rowsAffect);

				logger.info("User found for user id:{}", id);
			}
		}
		return user;
	}

}
