package com.social.util;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.social.model.UserModel;

public class DAOUtil {
	
    public static void setUserParams(PreparedStatement ps, UserModel user) throws SQLException {
        ps.setString(1, user.getUsername());
        ps.setString(2, user.getEmail());
        ps.setString(3, user.getPassword());
        ps.setString(4, user.getImage());
        ps.setString(5, user.getSalt());
    }


}
