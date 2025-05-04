package com.social.service;

import java.sql.SQLException;
import com.social.dao.FriendsDao;
import com.social.dto.FriendshipDto;
import com.social.mapper.FriendMapper;
import com.social.model.Friendship;

public class FriendsService {
	private static FriendsService friendsService;
	private static FriendMapper friendMapper = FriendMapper.getInstance();
	private static FriendsDao friendsDao = FriendsDao.getInstance();

	private FriendsService() {
	}

	public static FriendsService getInstance() {
		if (friendsService == null) {
			friendsService = new FriendsService();
		}
		return friendsService;
	}

	public boolean declineReceivedRequest(int receiverId, int senderId) throws ClassNotFoundException, SQLException {
		return friendsDao.declineRequest(receiverId, senderId);
	}

	public boolean acceptFriendRequest(FriendshipDto friendshipDto) throws ClassNotFoundException, SQLException {
		Friendship friendship = friendMapper.toEntity(friendshipDto);
		boolean isAccepted = friendsDao.acceptRequest(friendship);
		if (isAccepted) {
			boolean deleted = friendsDao.deleteFriendRequest(friendship);
			return deleted;
		}
		return false;
	}
}
