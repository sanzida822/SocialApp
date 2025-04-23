package com.social.service;

import com.social.dao.UserDao;
import com.social.model.User;

public class UserService {
private UserDao userDao=null;
	public UserService(){
		userDao=UserDao.getInstance();
		
	}


	
}
