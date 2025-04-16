package com.social.service;

import com.social.dao.UserDao;
import com.social.model.UserModel;

public class UserService {
private UserDao userDao=null;
	public UserService(){
		userDao=UserDao.getInstance();
		
	}

	public UserModel FindUserById(int id) {
		return userDao.findById(id);

	}
	
	
	
}
