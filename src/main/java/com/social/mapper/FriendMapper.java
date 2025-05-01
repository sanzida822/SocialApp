package com.social.mapper;


import java.sql.ResultSet;
import java.sql.SQLException;

import com.social.dto.FriendRequestsDto;
import com.social.enums.FriendRequestStatus;
import com.social.model.FriendRequest;
import com.social.model.User;
import com.social.service.UserService;

public class FriendMapper {
	private static FriendMapper friendRequestMapper;
	private static UserMapper userMapper=UserMapper.getInstance();
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
	
//	public FriendRequest toViewRequestEntity(ResultSet rs) throws SQLException, Exception {
//	int requestId=rs.getInt("fr.id");
//	User senderId=userService.getUserById(rs.getInt("fr.sender_id"));
//	User friendId=userService.getUserById(rs.getInt("fr.friend_id"));
//    FriendRequest friendRequest=new FriendRequest(senderId, friendId,  FriendRequestStatus.valueOf(rs.getString("status")));
//	friendRequest.setId(requestId);	
//    return friendRequest;
//	}
}
