package com.social.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.social.mapper.UserMapper;
import com.social.model.User;
import com.social.util.DAOUtil;
import com.social.util.DBConnection;
import com.social.util.PasswordUtil;

public class UserDao {
	private static final Logger logger = LoggerFactory.getLogger(UserDao.class);
	private UserMapper userMapper=UserMapper.getInstance();

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

	public boolean save(User user) throws Exception {
		String sql = "Insert into users (user_name,user_email,password,user_image,salt) values(?,?,?,?,?)";
		boolean status = false;
		try (Connection connection = DBConnection.getInstance().getConnection();
				PreparedStatement ps = connection.prepareStatement(sql)
		) {
			ps.setString(1, user.getUsername());
			ps.setString(2, user.getEmail());
			ps.setString(3, user.getPassword());
			ps.setBytes(4, user.getImage());	
			ps.setString(5, user.getSalt());
			int rowsAffect = ps.executeUpdate();
            if(rowsAffect>0) {
            	status=true;
            	logger.info("Registration completed successfully for user: " + user);          	
            }else {
                logger.error("Registration failed for user: " + user);
            }
			return status;
		}
	}

	public User findByEmail(String email) throws SQLException, Exception {
		String sql = "SELECT * FROM users WHERE user_email = ?";
		try (Connection connection = DBConnection.getInstance().getConnection();
				PreparedStatement ps = connection.prepareStatement(sql);
		) {
			ps.setString(1, email);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				User user;
				logger.info("user is found for email:{}", email);
			    return new User(rs.getInt("id"),rs.getString("user_name"),rs.getString("user_email"),rs.getString("password"),
			    		rs.getString("salt"),rs.getBytes("user_image"),rs.getString("created_at"),rs.getString("updated_at"));	
			}else {				
				logger.info("duplicate email for:{} user object:{} ", email);
			}
		} 
		return null;
	}

	public User findById(int id) throws Exception {
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
