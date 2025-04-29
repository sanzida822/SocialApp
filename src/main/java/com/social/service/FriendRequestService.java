package com.social.service;

import com.social.dto.FriendRequestsDto;
import com.social.mapper.FriendMapper;
import com.social.model.FriendRequest;

public class FriendRequestService {
	private static FriendRequestService friendRequestService;
	private static FriendMapper friendRequestMapper=FriendMapper.getInstance();

	private FriendRequestService() {
	}

	public static FriendRequestService getInstance() {
		if (friendRequestService == null) {
			friendRequestService = new FriendRequestService();

		}
		return friendRequestService;
	}
	
	public void addRequest(FriendRequestsDto friendRequestsDto) throws Exception {
		FriendRequest friendRequest=friendRequestMapper.toEntity(friendRequestsDto);
		
		
	}
	
}
