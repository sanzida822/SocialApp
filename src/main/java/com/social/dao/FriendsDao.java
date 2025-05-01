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
import com.social.enums.FriendRequestStatus;
import com.social.mapper.FriendMapper;
import com.social.mapper.UserMapper;
import com.social.model.FriendRequest;
import com.social.model.User;
import com.social.service.UserService;
import com.social.util.DBConnection;

public class FriendsDao {
	private static final Logger logger = LoggerFactory.getLogger(FriendsDao.class);
	private static FriendsDao friendsDao;
	private static UserMapper userMapper = UserMapper.getInstance();
	private static FriendMapper friendMapper=FriendMapper.getInstance();
	private static UserService userService=UserService.getInstance();

	private FriendsDao() {
	};

	public static FriendsDao getInstance() {
		if (friendsDao == null) {
			friendsDao = new FriendsDao();
		}
		return friendsDao;
	}

	public List<User> getNonFriends(int loggedInUserId) throws SQLException, Exception {
		List<User> nonFriends = new ArrayList<>();
		String sql = "SELECT * FROM users u inner join images i  on u.image_id=i.id where u.id!=? "
				+ "AND NOT EXISTS (SELECT 1 FROM friendship f where (f.sender_id=? AND f.receiver_id=u.id) OR "
				+ "(f.receiver_id=? AND f.sender_id=u.id)" + ")";
		try (Connection connection = DBConnection.getInstance().getConnection();
			PreparedStatement ps = connection.prepareStatement(sql);) {
			ps.setInt(1, loggedInUserId);
			ps.setInt(2, loggedInUserId);
			ps.setInt(3, loggedInUserId);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				nonFriends.add(userMapper.toEntity(rs));
			}
			if (nonFriends.isEmpty()) {
				logger.info("No non-friends found for user ID: " + loggedInUserId);
			}
		}
		return nonFriends;
	}

	
	 public List<FriendRequest> getFriendRequests(int receiverId) throws Exception {
	     String sql="SELECT * FROM friend_requests WHERE friend_id = ? AND status = 'PENDING'";
		 List<FriendRequest> requests = new ArrayList<>();
			try (Connection connection = DBConnection.getInstance().getConnection();
					PreparedStatement ps = connection.prepareStatement(sql);) {
	            ps.setInt(1, receiverId);
	            try (ResultSet rs = ps.executeQuery()) {
	                while (rs.next()) {
	                    User sender = userService.getUserById(rs.getInt("sender_id"));
	                    User receiver = UserService.getInstance().getUserById(rs.getInt("friend_id"));

	                    FriendRequest req = new FriendRequest(rs.getInt("id"),sender,receiver,FriendRequestStatus.valueOf(rs.getString("status")),rs.getTimestamp("created_at"));


	                    requests.add(req);
	                }
	            }
	        }
	        return requests;
	    }
	
	public boolean addFriend(FriendRequest friendRequest) throws SQLException, Exception {
		String sql = "Insert into friend_requests (sender_id,friend_id,status) values(?,?,?)";
		try (Connection connection = DBConnection.getInstance().getConnection();
				PreparedStatement ps = connection.prepareStatement(sql);) {
			ps.setInt(1, friendRequest.getSenderId().getId());
			ps.setInt(2, friendRequest.getFriendId().getId());
			ps.setString(3, friendRequest.getFriendRequestStatus().name());
			int rowsAffected = ps.executeUpdate();
			return rowsAffected>0;

		}

	}
}
