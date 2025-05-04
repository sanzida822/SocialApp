package com.social.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.social.config.DBConnection;
import com.social.enums.FriendRequestStatus;
import com.social.model.FriendRequest;
import com.social.model.Friendship;
import com.social.service.UserService;

public class FriendsDao {
	private static final Logger logger = LoggerFactory.getLogger(FriendRequestDao.class);
	private static FriendsDao friendsDao;
	private static UserService userService = UserService.getInstance();

	private FriendsDao() {
	}

	public static FriendsDao getInstance() {
		if (friendsDao == null) {
			friendsDao = new FriendsDao();
		}
		return friendsDao;
	}

	public List<FriendRequest> getFriendRequests(int receiverId) throws Exception { // to show logged in users available
																					// friend request
		String sql = "SELECT * FROM friend_requests WHERE receiver_id = ? AND status = 'PENDING'";
		List<FriendRequest> requests = new ArrayList<>();
		try (Connection connection = DBConnection.getInstance().getConnection();
				PreparedStatement ps = connection.prepareStatement(sql);) {
			ps.setInt(1, receiverId);
			ResultSet rs = ps.executeQuery();
			logger.info("rs is:{}", rs);
			while (rs.next()) {
				FriendRequest req = new FriendRequest(rs.getInt("id"), userService.getUserById(rs.getInt("sender_id")),
						userService.getUserById(rs.getInt("receiver_id")),
						FriendRequestStatus.valueOf(rs.getString("status")), rs.getTimestamp("created_at"));
				requests.add(req);
			}
		}
		return requests;
	}

	public boolean declineRequest(int receiverId, int senderId) throws ClassNotFoundException, SQLException {
		String sql = "delete from friend_requests fr where receiver_id=? and sender_id=?";
		try (Connection connection = DBConnection.getInstance().getConnection();
				PreparedStatement ps = connection.prepareStatement(sql);) {
			ps.setInt(1, receiverId);
			ps.setInt(2, senderId);
			int rowsAffect = ps.executeUpdate();
			return rowsAffect > 0;
		}
	}

	public boolean acceptRequest(Friendship frienship) throws ClassNotFoundException, SQLException {
		String sql = "Insert into friendship (sender_id,receiver_id,status) values(?,?,?)";
		try (Connection connection = DBConnection.getInstance().getConnection();
				PreparedStatement ps = connection.prepareStatement(sql);) {
			ps.setInt(1, frienship.getSenderId());
			ps.setInt(2, frienship.getReceiverId());
			ps.setString(3, frienship.getFriendShipstatus().name());
			int rowsAffected = ps.executeUpdate();
			return rowsAffected > 0;
		}
	}

	public boolean deleteFriendRequest(Friendship friendship) throws ClassNotFoundException, SQLException {
		String sql = "delete from friend_requests where sender_id=? and receiver_id=?";
		try (Connection connection = DBConnection.getInstance().getConnection();
				PreparedStatement ps = connection.prepareStatement(sql);) {
			ps.setInt(1, friendship.getSenderId());
			ps.setInt(2, friendship.getReceiverId());
			int rowsAffeced = ps.executeUpdate();
			return rowsAffeced > 0;
		}
	}

	

}
