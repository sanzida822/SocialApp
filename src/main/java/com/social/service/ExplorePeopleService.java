package com.social.service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.social.controller.AuthenticationServlet;
import com.social.dao.FriendsDao;
import com.social.dto.UserDto;
import com.social.mapper.UserMapper;
import com.social.model.User;

public class ExplorePeopleService {
	private static ExplorePeopleService explorePeopleService;
	private static final Logger logger = LoggerFactory.getLogger(ExplorePeopleService.class);
	private static FriendsDao friendsDao;
	private static UserMapper userMapper=UserMapper.getInstance();
	
	private ExplorePeopleService() {}
	
	public static ExplorePeopleService getInstance() {  
		if(explorePeopleService==null) {
			explorePeopleService=new ExplorePeopleService();
		}
		return explorePeopleService;
	}	
	
	
	public List<UserDto> getUsersNotInFriends(int loggedInUserId) throws SQLException, Exception{
	       logger.info("non friend user:{}");
	    List<User> users = friendsDao.getNonFriends(loggedInUserId);
        logger.info("non friend user:{}",users);
	    List<UserDto> userDtos = new ArrayList<>();
	    for (User user : users) {
	        userDtos.add(userMapper.toDTO(user));
	    }
	    return userDtos;
	
	}

}
