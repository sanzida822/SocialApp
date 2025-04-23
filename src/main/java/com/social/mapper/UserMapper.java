package com.social.mapper;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

import com.social.dto.LoginRequestDto;
import com.social.dto.RegistrationRequestDTO;
import com.social.dto.UserDto;
import com.social.model.User;
import com.social.util.PasswordUtil;

public class UserMapper {
	private static UserMapper userMapper;
	private UserMapper() {}
	
	public static UserMapper getInstance() {
		if(userMapper==null) {
			userMapper=new UserMapper();
		}
		return userMapper;
	}
	
	public User toEntity(LoginRequestDto loginDto) {
		if(loginDto==null) {
			return null;
		}		
		User user=new User(loginDto.getEmail(), loginDto.getPassword());	
		return user;
	}
	
	public LoginRequestDto toLoginRequstDTO(User user) {
		if(user==null) {
			return null;
		}
		LoginRequestDto loginDto=new LoginRequestDto(user.getEmail(),user.getPassword());
		return loginDto;
	}
	
	public User toEntity(RegistrationRequestDTO registrationDto) throws NoSuchAlgorithmException, InvalidKeySpecException {
		if(registrationDto==null) {return null;}
		String salt=PasswordUtil.generateSalt();
		String InputHashedPassword=PasswordUtil.hashPassword(registrationDto.getPassword(), salt);	
	    User user=new User(registrationDto.getUsername(),registrationDto.getEmail(),InputHashedPassword, registrationDto.getProfileImage(),salt);
	    return user;
	}
	
	public RegistrationRequestDTO toRegistrationRequestDTO(User user) throws NoSuchAlgorithmException, InvalidKeySpecException {
		if(user==null) {
			return null;
		}
		RegistrationRequestDTO registrationDto=new RegistrationRequestDTO(user.getUsername(),user.getEmail(),user.getPassword(),
			user.getImage());
		return registrationDto;
	}
	

}
