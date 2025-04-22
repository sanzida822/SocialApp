package com.social.service;



import javax.servlet.http.Part;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.social.dao.User;
import com.social.dto.LoginRequestDto;
import com.social.dto.RegistrationRequestDTO;
import com.social.mapper.UserMapper;
import com.social.model.LoginModel;
import com.social.model.UserModel;
import com.social.util.PasswordUtil;
import com.social.validation.AuthenticationValidation;

import ch.qos.logback.classic.spi.LoggerRemoteView;

public class AuthenticationService {
	private static final Logger logger = LoggerFactory.getLogger(AuthenticationService.class);
	private User user;
	private UserModel userModel;

	public AuthenticationService() {
		this.user = User.getInstance();
		// this.login = new LoginModel();
		this.userModel = new UserModel();
	}

	public RegistrationRequestDTO getUserByEmail(String email) throws Exception {
		UserModel userModel = user.findByEmail(email);

		if (userModel != null) {
            logger.warn("Duplicate user found for email:{}",email);
			return UserMapper.toRegistrationRequestDTO(userModel);
		}
	    logger.info("No user found for email: {}", email);
		return null;
	}

	public boolean registerUser(RegistrationRequestDTO registrationDto) throws Exception {
		String salt = PasswordUtil.generateSalt();
		String hashedPassword = PasswordUtil.hashPassword(registrationDto.getPassword(), salt);
		userModel = UserMapper.toEntity(registrationDto);
		userModel.setPassword(hashedPassword);
		userModel.setSalt(salt);
		return this.user.save(userModel);

	}

	public  AuthenticUser(LoginRequestDto loginDto) throws Exception {
        UserModel userModel= user.findUserByEmailAndPassword(loginDto.getUser_email(), loginDto.getPassword());
		return user.findUserByEmailAndPassword(loginDto.getUser_email(),loginDto.getPassword());

	}

}
