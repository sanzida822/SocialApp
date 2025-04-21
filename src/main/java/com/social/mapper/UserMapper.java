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
		userModel.setEmail(loginDto.getEmail());
		userModel.setPassword(loginDto.getPassword());		
		return userModel;
	}
	
	public static LoginRequestDto toLoginRequstDTO(UserModel userModel) {
		if(userModel==null) {
			return null;
		}
		LoginRequestDto loginDto=new LoginRequestDto();
		loginDto.setEmail(userModel.getEmail());
		loginDto.setPassword(userModel.getPassword());
		return loginDto;
	}
	
	
	public static UserModel toEntity(RegistrationRequestDTO registrationDto) {
		if(registrationDto==null) {return null;}
		UserModel userModel= new UserModel();
		userModel.setUsername(registrationDto.getUsername());
		userModel.setEmail(registrationDto.getEmail());
		userModel.setImage(registrationDto.getImage());
		userModel.setPassword(registrationDto.getPassword());
		//userModel.setConfirmPassword(registrationDto.getConfirm_password());
		return userModel;
		
		
	}
	public static RegistrationRequestDTO toRegistrationRequestDTO(UserModel userModel) {
		if(userModel==null) {
			return null;
		}
		RegistrationRequestDTO registrationDto=new RegistrationRequestDTO();
		registrationDto.setEmail(userModel.getEmail());
		registrationDto.setPassword(userModel.getPassword());
	//	registrationDto.setConfirm_password(userModel.getConfirmpassword());
		registrationDto.setImage(userModel.getImage());
		return registrationDto;
	}
}
