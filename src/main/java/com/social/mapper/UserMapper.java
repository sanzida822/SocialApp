package com.social.mapper;

import com.social.dto.LoginRequestDto;
import com.social.dto.RegistrationRequestDTO;
import com.social.model.LoginModel;
import com.social.model.UserModel;

public class UserMapper {
	public static UserModel toEntity(LoginRequestDto loginDto) {
		if(loginDto==null) {
			return null;
		}		
		UserModel userModel=new UserModel();
		userModel.setUser_email(loginDto.getEmail());
		userModel.setPassword(loginDto.getPassword());		
		return userModel;
	}
	
	public static LoginRequestDto toLoginRequstDTO(UserModel userModel) {
		if(userModel==null) {
			return null;
		}
		LoginRequestDto loginDto=new LoginRequestDto();
	//	loginDto.setEmail(userModel.setUser_email(null));
		loginDto.setPassword(userModel.getPassword());
		return loginDto;
	}
	
	
	public static UserModel toEntity(RegistrationRequestDTO registrationDto) {
		if(registrationDto==null) {return null;}
		UserModel userModel= new UserModel();
		userModel.setUser_name(registrationDto.getUser_name());
		userModel.setUser_email(registrationDto.getUser_email());
		userModel.setPassword(registrationDto.getPassword());
		userModel.setUser_image(registrationDto.getUser_image());

		//userModel.setConfirmPassword(registrationDto.getConfirm_password());
		return userModel;
	}
	
	public static RegistrationRequestDTO toRegistrationRequestDTO(UserModel userModel) {
		if(userModel==null) {
			return null;
		}
		RegistrationRequestDTO registrationDto=new RegistrationRequestDTO();
		registrationDto.setUser_email(userModel.getUser_email());
		registrationDto.setPassword(userModel.getPassword());
	//	registrationDto.setConfirm_password(userModel.getConfirmpassword());
		registrationDto.setUser_image(userModel.getUser_image());
		return registrationDto;
	}
}
