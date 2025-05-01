package com.social.service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.social.dao.FriendsDao;
import com.social.dto.FriendRequestViewDto;
import com.social.dto.FriendRequestsDto;
import com.social.dto.UserDto;
import com.social.mapper.FriendMapper;
import com.social.model.FriendRequest;
import com.social.model.User;

public class FriendRequestService {
	private static FriendRequestService friendRequestService;
	private static FriendMapper friendRequestMapper=FriendMapper.getInstance();
	private static FriendsDao friendDao=FriendsDao.getInstance();

	private FriendRequestService() {
	}

	public static FriendRequestService getInstance() {
		if (friendRequestService == null) {
			friendRequestService = new FriendRequestService();

		}
		return friendRequestService;
	}
	
	public boolean addRequest(FriendRequestsDto friendRequestsDto) throws Exception {
		FriendRequest friendRequest=friendRequestMapper.toEntity(friendRequestsDto);
		return friendDao.addFriend(friendRequest);
	}
	public List<FriendRequestViewDto> getFriendRequests(int receiverId) throws SQLException, Exception {
		List<FriendRequest> friendrequests=friendDao.getFriendRequests(receiverId);
	    List<FriendRequestViewDto> friendrequestDtos = new ArrayList<>();
		for(FriendRequest request:friendrequests) {
			FriendRequestViewDto friendRequestViewDto=new FriendRequestViewDto(request.getId(), request.getSenderId().getUsername(), request.getSenderId().getProfileImage(), request.getFriendId().getId(),request.getCreatedAt());
			friendrequestDtos.add(friendRequestViewDto);
		}

		return friendrequestDtos;
	}
	
}
