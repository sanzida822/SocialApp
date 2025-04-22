package com.social.service;

import com.social.dao.User;
import com.social.model.UserModel;

public class UserService {
private User userDao=null;
	public UserService(){
		userDao=User.getInstance();
		
	}

	public UserModel FindUserById(int id) {
		return userDao.findById(id);

	}
	
	
	
}
