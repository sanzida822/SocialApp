package com.social.service;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Properties;
import java.util.UUID;

import javax.servlet.http.Part;

import org.apache.catalina.startup.PasswdUserDatabase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.social.dao.UserDao;
import com.social.dto.LoginRequestDto;
import com.social.dto.RegistrationRequestDTO;
import com.social.mapper.UserMapper;
import com.social.model.LoginModel;
import com.social.model.UserModel;
import com.social.util.PasswordUtil;
import com.social.validation.AuthenticationValidation;

public class AuthenticationService {
	private static final Logger logger = LoggerFactory.getLogger(AuthenticationService.class);
	private UserDao userDao;
	private UserModel userModel;
	private LoginModel login;

	public AuthenticationService() {
		this.userDao = UserDao.getInstance();
		this.login = new LoginModel();
		this.userModel=new UserModel();
	}

	public boolean registerUser(RegistrationRequestDTO registrationDto) throws Exception {
		String salt=PasswordUtil.generateSalt();
		String hashedPassword=PasswordUtil.hashPassword(registrationDto.getPassword(), salt);
		userModel = UserMapper.toEntity(registrationDto);
		userModel.setPassword(hashedPassword);
		userModel.setSalt(salt);
		return this.userDao.save(userModel);

	
	}



	public LoginModel AuthenticUser(LoginRequestDto loginDto) {
		UserModel userModel = UserMapper.toEntity(loginDto);

		return userDao.getUserByEmailAndPassword(userModel.getEmail(), userModel.getPassword());

	}

}
