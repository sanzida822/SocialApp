package com.social.util;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.social.model.UserModel;

public class DAOUtil {

	public static void setUserParams(PreparedStatement ps, UserModel user) throws SQLException {
		ps.setString(1, user.getUsername());
		ps.setString(2, user.getEmail());
		ps.setString(3, user.getPassword());
		ps.setBytes(4, user.getImage());
		ps.setString(5, user.getSalt());
	}

	public static UserModel mapResultSetToUser(ResultSet rs) throws SQLException {
		UserModel user = new UserModel();
		user.setId(rs.getInt("id"));
		user.setUsername(rs.getString("user_name"));
		user.setEmail(rs.getString("user_email"));
		user.setImage(rs.getBytes("user_image"));
		user.setSalt(rs.getString("salt"));
		user.setPassword(rs.getString("password"));
		user.setCreated_at(rs.getString("created_at"));
		user.setUpdated_at(rs.getString("updated_at"));
		return user;
	}

}
