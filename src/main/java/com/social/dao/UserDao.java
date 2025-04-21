package com.social.dao;

import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.Properties;

import com.social.controller.AuthenticationServlet;
import com.social.model.LoginModel;
import com.social.model.UserModel;
import com.social.util.DBConnection;
import com.social.util.PasswordUtil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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

	public UserModel save(UserModel userModel) {
		String sql = "Insert into users (user_name,user_email,password,user_image,salt) values(?,?,?,?,?)";
		//boolean status = false;
		try (Connection connection = DBConnection.getInstance().getConnection();
				PreparedStatement ps = connection.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS)

		) {
			String salt = PasswordUtil.generateSalt();
			String hashedPassword = PasswordUtil.hashPassword(userModel.getPassword(), salt);

			ps.setString(1, userModel.getUsername());
			ps.setString(2, userModel.getEmail());
			ps.setString(3, hashedPassword);
			ps.setString(4, userModel.getImage());
			ps.setString(5, salt);
			int rowsAffect = ps.executeUpdate();
			
	        if (rowsAffect > 0) {
	            try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
	                if (generatedKeys.next()) {
	                    userModel.setId(generatedKeys.getInt(1));
	                }
	            }
	            logger.info("Registration completed successfully for user: {}", userModel.getUsername());
	        } else {
	            logger.warn("No rows affected during user registration for: {}", userModel.getUsername());
	            return null;
	        }
			logger.info("Registration completed successfully for user: " + userModel);
			//status = rowsAffect > 0; // return 1 if insert a row to table

		} catch (SQLException e) {
			logger.error("SQL Exception occurred while saving user: {}, error message:", userModel.getUsername(),
					e.getMessage());
			logger.error("Error stack trace: ", e);
		} catch (Exception e) {
			logger.error("General Exception occurred: " + e.getMessage());
			logger.error("Error stack trace: ", e);

		}
		return userModel;

	}

	public LoginModel findByEmail(String email) {
		LoginModel loginModel = null;
		String sql = "Select 1 from users where user_email=?";
		try (Connection connection = DBConnection.getInstance().getConnection();
				PreparedStatement ps = connection.prepareStatement(sql);

		) {

			logger.info("checking duplicate email for email:{} ", email);
			ps.setString(1, email);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				loginModel = new LoginModel();
				loginModel.setId(rs.getInt("id"));
				loginModel.setUsername(rs.getString("uname"));
				loginModel.setEmail(rs.getString("email"));
				logger.info("user is found for email:{} and user object is:{}", email, loginModel);

			}
			logger.info("duplicate email for:{} user object:{} ", email, loginModel);
		} catch (Exception e) {
			logger.error("Error occurred while checking duplicate email for '{}': {}", email, e.getMessage(), e);
		}
		return loginModel;
	}

	public LoginModel getUserByEmailAndPassword(String email, String password) {
		LoginModel loginModel = null;
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
					loginModel = new LoginModel();
					loginModel.setId(rowsAffect.getInt("id"));
					loginModel.setUsername(rowsAffect.getString("user_name"));
					loginModel.setEmail(rowsAffect.getString("user_email"));
					logger.info("Login successful for user: {}", email);

				} else {

					logger.warn("Password mismatch for user: {}", email);
				}

			} else {
				logger.warn("No user found with email: {}", email);

			}
		} catch (Exception e) {
			logger.error("Error checking user for email: {}, message: {}", email, e.getMessage());

		}

		return loginModel;

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
				user.setUsername(rowsAffect.getString("uname"));
				user.setEmail(rowsAffect.getString("email"));
				user.setImage(rowsAffect.getString("image"));
				user.setCreated_at(rowsAffect.getString("created_at"));
				logger.info("User found for user id:{}", id);
			}
		} catch (Exception e) {
			logger.error("User not found with id {}", id, e.getMessage());

		}
		return user;
	}

}
