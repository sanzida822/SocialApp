package com.social.dao;

import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.Properties;

import com.social.controller.AuthenticationServlet;
import com.social.model.LoginModel;
import com.social.model.UserModel;
import com.social.util.DBConnection;

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

	public boolean save(UserModel userModel) {
		String sql = "Insert into users (uname,email,password,image) values(?,?,?,?)";
		boolean status = false;
		try (Connection connection = DBConnection.getInstance().getConnection();
				PreparedStatement ps = connection.prepareStatement(sql)

		) {

			ps.setString(1, userModel.getUsername());
			ps.setString(2, userModel.getEmail());
			ps.setString(3, userModel.getPassword());
			ps.setString(4, userModel.getImage());
			int rowsAffect = ps.executeUpdate();
			logger.info("User saved: " + rowsAffect);
			status = rowsAffect > 0; // return 1 if insert a row to table
		} catch (SQLException e) {
			logger.error("SQL Exception occurred while saving user: {}, error message:", userModel.getUsername(),
					e.getMessage());
			logger.error("Error stack trace: ", e);
		} catch (Exception e) {
			logger.error("General Exception occurred: " + e.getMessage());
			logger.error("Error stack trace: ", e);

		}
		return status;

	}

	public boolean findByEmail(String email) {
		boolean status = false;
		String sql = "Select 1 from users where email=?";
		try (Connection connection = DBConnection.getInstance().getConnection();
				PreparedStatement ps = connection.prepareStatement(sql);
				
		) {

			logger.info("checking duplicate email for email:{} ", email);
			ps.setString(1, email);
			ResultSet rs = ps.executeQuery();
			status = rs.next();
			logger.info("duplicate email for:{} status:{} ", email, status);
		} catch (Exception e) {
			logger.error("Error occurred while checking duplicate email for '{}': {}", email, e.getMessage(), e);
		}
		return status;
	}

	public LoginModel getUserByEmailAndPassword(String email, String password) {
		LoginModel loginModel = null;
		String sql = "SELECT id, uname, email, password FROM users WHERE email=? AND password=?";
		try (Connection connection = DBConnection.getInstance().getConnection();
				PreparedStatement ps = connection.prepareStatement(sql);
			
			) {
			ps.setString(1, email);
			ps.setString(2, password);
			ResultSet rowsAffect = ps.executeQuery();
			System.out.println(rowsAffect);
			if (rowsAffect.next()) {
				loginModel = new LoginModel();
				logger.info("User found for email:{}",email);
			
				loginModel.setId(rowsAffect.getInt("id"));
				loginModel.setUsername(rowsAffect.getString("uname"));
				loginModel.setEmail(rowsAffect.getString("email"));
				logger.info("Get User for email:{}", loginModel.getEmail());
			}
		} catch (Exception e) {
		    logger.error("Error checking user for email: {}, message: {}", email, e.getMessage());

		}
		return loginModel;

	}

	public UserModel findById(int id) {
		UserModel user = null;
		String sql = "Select uname, email, image,created_at,updated_at from users where id=?";
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
