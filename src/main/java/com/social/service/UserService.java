package com.social.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.social.dao.UserDao;
import com.social.dto.UserDto;
import com.social.mapper.UserMapper;
import com.social.model.User;

public class UserService {
	private static final Logger logger = LoggerFactory.getLogger(UserService.class);
	private static UserDao userDao = UserDao.getInstance();
	private static UserService userService;
	private UserMapper userMapper=UserMapper.getInstance();
	

	private UserService() {
	}

	public static UserService getInstance() {
		if (userService == null) {
			userService = new UserService();
		}
		return userService;
	}
	
	public UserDto getUserByEmail(String email) throws Exception {
		User user = userDao.findByEmail(email);
		logger.info("logged in user details:{}",user);
		if (user != null) {
			logger.warn("User found for email: {}", email);
			return userMapper.toDto(user);
		}
		logger.info("No user found for email: {}", email);
		return null;
	}
	
	public User getUserById(int id) throws Exception {
		User user=userDao.findById(id);
		if (user != null) {
			logger.warn("User found for id: {}", id);
			return user;
		}
		logger.info("No user found for email: {}", id);
		return null;
	}

}
