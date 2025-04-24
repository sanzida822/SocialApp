package com.social.mapper;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.social.dto.LoginRequestDto;
import com.social.dto.RegistrationRequestDTO;
import com.social.dto.UserDto;
import com.social.model.User;
import com.social.util.PasswordUtil;

public class UserMapper {
	private static UserMapper userMapper;

	private UserMapper() {
	}

	public static UserMapper getInstance() {
		if (userMapper == null) {
			userMapper = new UserMapper();
		}
		return userMapper;
	}

	public LoginRequestDto toLoginRequstDTO(User user) {
		return new LoginRequestDto(user.getEmail(), user.getPassword());
	}

	public User toEntity(RegistrationRequestDTO registrationDto)
			throws NoSuchAlgorithmException, InvalidKeySpecException {
		String salt = PasswordUtil.generateSalt();
		String InputHashedPassword = PasswordUtil.hashPassword(registrationDto.getPassword(), salt);
		return new User(registrationDto.getUsername(), registrationDto.getEmail(), InputHashedPassword, salt,
				registrationDto.getProfileImage(), null, null);

	}

	public User toEntity(ResultSet rs) throws SQLException {
		return new User(rs.getString("user_name"), rs.getString("user_email"), rs.getString("password"),
				rs.getString("salt"), rs.getBytes("user_image"), rs.getString("created_at"),
				rs.getString("updated_at"));
	}
	
	public UserDto toDTO(User user) {
		return new UserDto(user.getUsername(),user.getEmail(),user.getProfileImage(),user.getCreatedAt(),user.getUpdatedAt());
	}
	
}
