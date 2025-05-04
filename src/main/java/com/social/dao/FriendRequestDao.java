package com.social.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.social.config.DBConnection;
import com.social.controller.ExplorePeopleServlet;
import com.social.dto.FriendRequestViewDto;
import com.social.enums.FriendRequestStatus;
import com.social.mapper.FriendMapper;
import com.social.mapper.UserMapper;
import com.social.model.FriendRequest;
import com.social.model.Image;
import com.social.model.User;
import com.social.service.UserService;


public class FriendRequestDao {
	private static final Logger logger = LoggerFactory.getLogger(FriendRequestDao.class);
	private static FriendRequestDao friendRequestDao;
	private static UserMapper userMapper = UserMapper.getInstance();
	private static FriendMapper friendMapper = FriendMapper.getInstance();
	private static UserService userService = UserService.getInstance();

	private FriendRequestDao() {
	}

	public static FriendRequestDao getInstance() {
		if (friendRequestDao == null) {
			friendRequestDao = new FriendRequestDao();
		}
		return friendRequestDao;
	}

	public List<User> getNonFriends(int loggedInUserId) throws SQLException, Exception {
		List<User> nonFriends = new ArrayList<>();
		String sql="SELECT * FROM users u "
				+ "WHERE u.id != ? "
				+ "AND NOT EXISTS( "
				+ "    SELECT 1 "
				+ "    FROM friendship f "
				+ "    WHERE (f.sender_id = ? AND f.receiver_id = u.id)"
				+ "    OR (f.receiver_id = ? AND f.sender_id = u.id) "
				+ ") "
				+ "AND NOT EXISTS ( "
				+ "    SELECT 1 "
				+ "    FROM friend_requests fr "
				+ "    WHERE (fr.sender_id = ? AND fr.receiver_id = u.id) "
				+ "    OR (fr.receiver_id = ? AND fr.sender_id = u.id) "
				+ "    AND fr.status = 'PENDING'"
				  + ");";
				
		try (Connection connection = DBConnection.getInstance().getConnection();
				PreparedStatement ps = connection.prepareStatement(sql);) {
		    ps.setInt(1, loggedInUserId);
	        ps.setInt(2, loggedInUserId);
	        ps.setInt(3, loggedInUserId);
	        ps.setInt(4, loggedInUserId);
	        ps.setInt(5, loggedInUserId);
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
	
	public List<FriendRequest> getSentedRequest(int senderId) throws Exception{ //sender id is logged in user
		String sql="select * from friend_requests fr  where fr.sender_id=? and status ='PENDING'";
		List<FriendRequest> requests = new ArrayList<>();
		try (Connection connection = DBConnection.getInstance().getConnection();
				PreparedStatement ps = connection.prepareStatement(sql);) {
			ps.setInt(1, senderId);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				FriendRequest req = new FriendRequest(rs.getInt("id"),userService.getUserById(rs.getInt("sender_id")),userService.getUserById(rs.getInt("receiver_id")),FriendRequestStatus.valueOf(rs.getString("status")),rs.getTimestamp("created_at"));
				requests.add(req);
			}
		}
		return requests;		
	}



//	public List<FriendRequestViewDto> getFriendRequests(int receiverId) throws Exception {
//		String sql = "select fr.id as requestId, u.user_name as senderUsername, i.data as senderProfileImage, u.id as senderId, fr.created_at as requestDate "
//				+ "from friend_requests fr inner join users u on fr.sender_id=u.id inner join images i on u.image_id=i.id where fr.friend_id=? and fr.status='PENDING' ";
//		List<FriendRequestViewDto> requests = new ArrayList<>();
//		try (Connection connection = DBConnection.getInstance().getConnection();
//				PreparedStatement ps = connection.prepareStatement(sql)) {
//			ps.setInt(1, receiverId);
//			try (ResultSet rs = ps.executeQuery()) {
//				while (rs.next()) {
//					FriendRequestViewDto friendRequestViewDto = new FriendRequestViewDto(rs.getInt("requestId"),
//							rs.getString("senderUsername"), rs.getBytes("senderProfileImage"), rs.getInt("senderId"),
//							rs.getTimestamp("requestDate"));
//					requests.add(friendRequestViewDto);
//				}
//			}
//		}
//
//		return requests;
//	}

	public boolean addRequest(FriendRequest friendRequest) throws SQLException, Exception {
		String sql = "Insert into friend_requests (sender_id,receiver_id,status) values(?,?,?)";
		try (Connection connection = DBConnection.getInstance().getConnection();
				PreparedStatement ps = connection.prepareStatement(sql);) {
			ps.setInt(1, friendRequest.getSenderId().getId());
			ps.setInt(2, friendRequest.getReceiverId().getId());
			ps.setString(3, friendRequest.getFriendRequestStatus().name());
			int rowsAffected = ps.executeUpdate();
			return rowsAffected > 0;
		}
	}
	
//	public boolean cancelRequest(FriendRequest friendRequest) throws SQLException, Exception {
//		String sql = "Insert into friend_requests (sender_id,receiver_id,status) values(?,?,?)";
//		try (Connection connection = DBConnection.getInstance().getConnection();
//				PreparedStatement ps = connection.prepareStatement(sql);) {
//			ps.setInt(1, friendRequest.getSenderId().getId());
//			ps.setInt(2, friendRequest.getReceiverId().getId());
//			ps.setString(3, friendRequest.getFriendRequestStatus().name());
//			int rowsAffected = ps.executeUpdate();
//			return rowsAffected > 0;
//		}
//	}
	
	
	public boolean cancelRequest(int senderId,int receiverId) throws ClassNotFoundException, SQLException {
		String sql="delete from friend_requests fr where sender_id=? and receiver_id=?";
		try (Connection connection = DBConnection.getInstance().getConnection();
				PreparedStatement ps = connection.prepareStatement(sql);) {
			ps.setInt(1, senderId);
			ps.setInt(2, receiverId);
			int rowsAffect=ps.executeUpdate();
			return rowsAffect>0;					
		}
	}
}
