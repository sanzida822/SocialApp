package com.social.util;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.social.model.User;

import ch.qos.logback.classic.Logger;

public class DAOUtil {


	public static User mapResultSetToUser(ResultSet rs) throws SQLException {
		User user = new User();
		user.setId(rs.getInt("id"));
		user.setUser_name(rs.getString("user_name"));
		user.setUser_email(rs.getString("user_email"));
		user.setUser_image(rs.getBytes("user_image"));
		user.setSalt(rs.getString("salt"));
		user.setPassword(rs.getString("password"));
		user.setCreated_at(rs.getString("created_at"));
		user.setUpdated_at(rs.getString("updated_at"));
		System.out.println("user is:"+user);
		return user;
	}

}
