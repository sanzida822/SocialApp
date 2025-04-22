package com.social.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.social.model.LoginModel;
import com.social.model.UserModel;
import com.social.util.DAOUtil;
import com.social.util.DBConnection;
import com.social.util.PasswordUtil;

public class User {
	private static final Logger logger = LoggerFactory.getLogger(User.class);

	private static User instance;

	private User() {

	}

	public static User getInstance() {
		if (instance == null) {
			synchronized (User.class) {
				if (instance == null) {
					instance = new User();
				}
			}

		}
		return instance;

	}

	public boolean save(UserModel userModel) throws Exception {
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

	public UserModel findByEmail(String email) throws SQLException, Exception {
		logger.info("Request comes for checking duplicate email for email:", email);
		UserModel userModel = null;
		String sql = "SELECT * FROM users WHERE user_email = ?";
		try (Connection connection = DBConnection.getInstance().getConnection();
				PreparedStatement ps = connection.prepareStatement(sql);

		) {


			ps.setString(1, email);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				logger.info("user is found for email:{} and user object is:{}", email, userModel);
			    return DAOUtil.mapResultSetToUser(rs);
			
			}else {
				
				logger.info("duplicate email for:{} user object:{} ", email, userModel);
			}

		} 
		return userModel;
	}

	public UserModel findUserByEmailAndPassword(String email, String password) throws Exception {
		UserModel userModel = null;
		String sql = "SELECT id, user_name, user_email, password,salt FROM users WHERE user_email=?";
		try (Connection connection = DBConnection.getInstance().getConnection();
				PreparedStatement ps = connection.prepareStatement(sql);

		) {
			ps.setString(1, email);
			ResultSet rowsAffect = ps.executeQuery();
			boolean hasResult = rowsAffect.next();
			logger.info("Login resultSet has result? {}, for user email:{}", hasResult, email);
			if (hasResult) {
				String Storedpassword = rowsAffect.getString("password");
				String storedsalt = rowsAffect.getString("salt");
				String hashedInputPassword = PasswordUtil.hashPassword(password, storedsalt);
				if (Storedpassword.equals(hashedInputPassword)) {
					 userModel = new UserModel();
                    DAOUtil.setUserParams(ps, userModel);
					logger.info("Login successful for user: {}", email);

				} else {

					logger.warn("Password mismatch for user: {}", email);
				}

			} else {
				logger.warn("No user found with email: {}", email);

			}
		} 

		return userModel;

	}

	public UserModel findById(int id) {
		UserModel user = null;
		String sql = "Select user_name, user_email, user_image,created_at,updated_at from users where id=?";
		try (Connection connection = DBConnection.getInstance().getConnection();
				PreparedStatement ps = connection.prepareStatement(sql);

		) {
			ps.setInt(1, id);
			ResultSet rowsAffect = ps.executeQuery();
			if (rowsAffect.next()) {
				user = new UserModel();
				user.setId(id);
				user.setUser_name(rowsAffect.getString("user_name"));
				user.setUser_email(rowsAffect.getString("user_email"));
				//user.setImage(rowsAffect.getString("image"));
				user.setCreated_at(rowsAffect.getString("created_at"));
				logger.info("User found for user id:{}", id);
			}
		} catch (Exception e) {
			logger.error("User not found with id {}", id, e.getMessage());

		}
		return user;
	}

}
