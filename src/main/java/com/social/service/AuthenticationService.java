package com.social.service;

import java.sql.Connection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.social.config.DBConnection;
import com.social.dao.UserDao;
import com.social.dto.RegistrationRequestDto;
import com.social.dto.UserDto;
import com.social.mapper.UserMapper;
import com.social.model.Image;
import com.social.model.User;
import com.social.util.PasswordUtil;

public class AuthenticationService {
	private static final Logger logger = LoggerFactory.getLogger(AuthenticationService.class);
	private static AuthenticationService authenticationService;
	private static UserDao userDao = UserDao.getInstance();
	private static UserMapper userMapper = UserMapper.getInstance();
	private static ImageService imageService = ImageService.getInstance();

	private AuthenticationService() {
	}

	public static AuthenticationService getInstance() {
		if (authenticationService == null) {
			authenticationService = new AuthenticationService();
		}
		return authenticationService;
	}

	public boolean register(RegistrationRequestDto registrationDto) throws Exception {
		Connection connection = null;

		try {
			connection = DBConnection.getInstance().getConnection();
			connection.setAutoCommit(false);
			Image image = imageService.save(registrationDto.getImageDto(), connection);
			logger.info("Image id for profile image:{}, and the image object:{}", image.getId(), image);
			User user = userMapper.toEntity(registrationDto, image);
			logger.info("User for registration:{}", user);
			boolean isSaved = userDao.save(user, connection);
			if (!isSaved) {
				throw new Exception("Failed to save user");
			}
			connection.commit();
			return true;
		} catch (Exception e) {
			if (connection != null) {
				connection.rollback();

			}
			return false;

		} finally {
			if (connection != null) {
				connection.setAutoCommit(true);
				connection.close();
			}
		}

	}

	public UserDto authenticate(String email, String inputPassword) throws Exception {
		User user = userDao.findByEmail(email);
		String storedHashPassword = user.getPassword();
		String storedSalt = user.getSalt();
		String inputHashPassword = PasswordUtil.hashPassword(inputPassword, storedSalt);
		if (storedHashPassword.equals(inputHashPassword)) {
			logger.info("Authentication successful for user: {}", user.getEmail());
			return userMapper.toDto(user);
		} else {
			logger.error("Incorrect Password for user:{}", email);
			return null;
		}
	}

}
