package com.social.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.social.controller.ExplorePeopleServlet;
import com.social.mapper.UserMapper;
import com.social.model.User;
import com.social.util.DBConnection;

public class FriendsDao {
	private static final Logger logger = LoggerFactory.getLogger(FriendsDao.class);
	private static FriendsDao friendsDao;
	private static UserMapper userMapper=UserMapper.getInstance();
	private FriendsDao() {};
	
	public static FriendsDao getInstance() {
		if(friendsDao==null) {
			friendsDao=new FriendsDao();
		}
		return friendsDao;		
	}
	
	public List<User> getNonFriends(int LoggedInUserId) throws SQLException, Exception {
		List<User> nonFriends=new ArrayList<>();
		String sql="SELECT * FROM users u where u.id!=? "+
		"AND NOT EXISTS (SELECT 1 FROM friendship f where (f.sender_id=? AND f.receiver_id=u.id) OR "
		+ "(f.receiver_id=? AND f.sender_id=u.id)"+
		")";
	    try (Connection connection = DBConnection.getInstance().getConnection();
				PreparedStatement ps = connection.prepareStatement(sql);
		){
	    	logger.info("comes in dao");
	    	ps.setInt(1, LoggedInUserId);
	    	ps.setInt(2, LoggedInUserId);
	    	ps.setInt(3, LoggedInUserId);
	    	ResultSet rs = ps.executeQuery();    	
	    	while(rs.next()) {
	    	 	nonFriends.add(userMapper.toEntity(rs));
	    	}
	        if (nonFriends.isEmpty()) {
	            // Log if no non-friends found
	            System.out.println("No non-friends found for user ID: " + LoggedInUserId);
	        }
	    }
	    return nonFriends;
	}
	 
}
