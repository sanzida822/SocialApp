package com.social.mapper;

import com.social.dto.LoginRequestDto;
import com.social.dto.RegistrationRequestDTO;
import com.social.dto.UserDto;
import com.social.model.User;

public class UserMapper {
	public static User toEntity(LoginRequestDto loginDto) {
		if(loginDto==null) {
			return null;
		}		
		User user=new User();
		user.setUser_email(loginDto.getUser_email());
		user.setPassword(loginDto.getPassword());		
		return user;
	}
	
	public static LoginRequestDto toLoginRequstDTO(User userModel) {
		if(userModel==null) {
			return null;
		}
		LoginRequestDto loginDto=new LoginRequestDto();
	//	loginDto.setEmail(userModel.setUser_email(null));
		loginDto.setPassword(userModel.getPassword());
		return loginDto;
	}
	
	
	public static User toEntity(RegistrationRequestDTO registrationDto) {
		if(registrationDto==null) {return null;}
		User user= new User();
		user.setUser_name(registrationDto.getUser_name());
		user.setUser_email(registrationDto.getUser_email());
		user.setPassword(registrationDto.getPassword());
		user.setUser_image(registrationDto.getUser_image());

		//userModel.setConfirmPassword(registrationDto.getConfirm_password());
		return user;
	}
	
	public static RegistrationRequestDTO toRegistrationRequestDTO(User user) {
		if(user==null) {
			return null;
		}
		RegistrationRequestDTO registrationDto=new RegistrationRequestDTO();
		registrationDto.setUser_email(user.getUser_email());
		registrationDto.setPassword(user.getPassword());
	//	registrationDto.setConfirm_password(userModel.getConfirmpassword());
		registrationDto.setUser_image(user.getUser_image());
		return registrationDto;
	}
	
	
//	public static UserModel toEntity(UserDto userDto) {
//		if(userDto==null) {
//			return null;
//		}
//		
//		UserModel user=new UserModel();
//		user.setUser_name(userDto.getUser_name());
//		user.setUser_email(userDto.getUser_email());
//		user.setUser_image(userDto.getUser_image());
//		
//		
//	}
}
