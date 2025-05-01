package com.social.mapper;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.social.dto.LoginRequestDto;
import com.social.dto.RegistrationRequestDTO;
import com.social.dto.UserDto;
import com.social.model.Image;
import com.social.model.User;
import com.social.service.AuthenticationService;
import com.social.service.ImageService;
import com.social.util.PasswordUtil;

public class UserMapper {
	private static UserMapper userMapper;
	private static ImageService imageService = ImageService.getInstance();
	private static ImageMapper imageMapper = ImageMapper.getInstance();
	private static final Logger logger = LoggerFactory.getLogger(UserMapper.class);

	private UserMapper() {
	}

	public static UserMapper getInstance() {
		if (userMapper == null) {
			userMapper = new UserMapper();
		}
		return userMapper;
	}

	public User toEntity(RegistrationRequestDTO registrationDto, Image profileImage)
			throws NoSuchAlgorithmException, InvalidKeySpecException {
		String salt = PasswordUtil.generateSalt();
		String InputHashedPassword = PasswordUtil.hashPassword(registrationDto.getPassword(), salt);
		User user = new User(registrationDto.getUsername(), registrationDto.getEmail(), InputHashedPassword, salt,
				profileImage);
		logger.info("user object is:{}", user);
		return user;
	}

	public User toEntity(ResultSet rs) throws Exception {
    	Image profileImage=imageMapper.toEntity(rs);
		return new User(rs.getInt("u.id"), rs.getString("u.user_name"), rs.getString("u.user_email"),
				rs.getString("u.password"), rs.getString("salt"), profileImage, rs.getTimestamp("u.created_at"),
				rs.getTimestamp("u.updated_at"));
	}

	public UserDto toDTO(User user) {
		return new UserDto(user.getId(), user.getUsername(), user.getEmail(), user.getProfileImage().getId(),
				user.getCreatedAt(), user.getUpdatedAt());
	}

}
