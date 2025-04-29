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
	ImageService imageService=ImageService.getInstance();
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
		User user= new User(registrationDto.getUsername(),registrationDto.getEmail(),InputHashedPassword,salt,profileImage);
	logger.info("user object is:{}",user);
	return user;
	}
	
	public User toEntity(ResultSet rs) throws Exception {
		int id = rs.getInt("id");
		String username = rs.getString("user_name");
		String email = rs.getString("user_email");
		String password = rs.getString("password");
		String salt = rs.getString("salt");
		int imageId = rs.getInt("image_id");
		Timestamp createdAt = rs.getTimestamp("created_at");
		Timestamp updatedAt = rs.getTimestamp("updated_at");
		Image profileImage = imageService.getImageById(imageId);
		return new User(id, username, email, password, salt, profileImage, createdAt, updatedAt);
	}
	public UserDto toDTO(User user) {
		return new UserDto(user.getId(), user.getUsername(), user.getEmail(),user.getProfileImage().getId(),
				user.getCreatedAt(), user.getUpdatedAt());
	}

}
