package com.social.service;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import com.social.dao.FriendRequestDao;
import com.social.dao.FriendsDao;
import com.social.dto.FriendRequestViewDto;
import com.social.dto.FriendRequestsDto;
import com.social.dto.SentRequestsViewDto;
import com.social.dto.UserDto;
import com.social.mapper.FriendMapper;
import com.social.model.FriendRequest;
import com.social.model.User;

public class FriendRequestService {
	private static FriendRequestService friendRequestService;
	private static FriendMapper friendRequestMapper = FriendMapper.getInstance();
	private static FriendRequestDao friendRequestDao = FriendRequestDao.getInstance();
	private static FriendsDao friendsDao=FriendsDao.getInstance();

	private FriendRequestService() {
	}

	public static FriendRequestService getInstance() {
		if (friendRequestService == null) {
			friendRequestService = new FriendRequestService();
		}
		return friendRequestService;
	}

	public boolean addRequest(FriendRequestsDto friendRequestsDto) throws Exception {
		FriendRequest friendRequest = friendRequestMapper.toEntity(friendRequestsDto);
		return friendRequestDao.addRequest(friendRequest);
	}
	


	public List<FriendRequestViewDto> getFriendRequests(int receiverId) throws SQLException, Exception {
		List<FriendRequest> friendRequests= friendsDao.getFriendRequests(receiverId);
		List<FriendRequestViewDto> friendRequestViewDtos=new ArrayList<>();
		for(FriendRequest friendRequest:friendRequests) {
			FriendRequestViewDto friendRequestViewDto=new FriendRequestViewDto(
					friendRequest.getId(), 
					friendRequest.getSenderId().getUsername(), 
					friendRequest.getSenderId().getProfileImage().getData(), 
					friendRequest.getSenderId().getId(), 
					friendRequest.getCreatedAt());
			friendRequestViewDtos.add(friendRequestViewDto);
		}
		return friendRequestViewDtos;	
	}
	public List<SentRequestsViewDto> getSentedRequest(int senderId) throws SQLException, Exception {
		List<FriendRequest> friendRequests= friendRequestDao.getSentedRequest(senderId);
		List<SentRequestsViewDto> sentRequestViewDtos=new ArrayList<>();
		for(FriendRequest sentedRequest:friendRequests) {
			SentRequestsViewDto friendRequestViewDto=new SentRequestsViewDto(
					sentedRequest.getId(), 
					sentedRequest.getReceiverId().getUsername(), 
					sentedRequest.getReceiverId().getProfileImage().getData(), 
					sentedRequest.getReceiverId().getId(), 
					sentedRequest.getCreatedAt());
			sentRequestViewDtos.add(friendRequestViewDto);
		}
		return sentRequestViewDtos;	
	}
	
	public boolean cancelRequest(int senderId,int receiverId) throws ClassNotFoundException, SQLException {
		return friendRequestDao.cancelRequest(senderId, receiverId);
	}
}
