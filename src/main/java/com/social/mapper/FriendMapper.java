package com.social.mapper;

import com.social.dto.FriendRequestsDto;
import com.social.model.FriendRequest;
import com.social.model.User;
import com.social.service.UserService;

public class FriendMapper {
	private static FriendMapper friendRequestMapper;
	private static UserService userService=UserService.getInstance();
	private FriendMapper() {}

	public static FriendMapper getInstance() {
		if (friendRequestMapper == null) {
			friendRequestMapper = new FriendMapper();
		}
		return friendRequestMapper;
	}
	
	public FriendRequest toEntity(FriendRequestsDto friendRequestDto) throws Exception {
		User senderId=userService.getUserById(friendRequestDto.getSenderId());
		User receiverId=userService.getUserById(friendRequestDto.getFriendId());
		return new FriendRequest(senderId,receiverId,friendRequestDto.getFriendRequestStatus());
	}
}
