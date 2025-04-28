package com.social.validation;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.social.dto.LoginRequestDto;
import com.social.dto.RegistrationRequestDTO;
import com.social.dto.UserDto;
import com.social.model.User;
import com.social.service.AuthenticationService;
import com.social.service.UserService;
import com.social.util.CommonUtil;
import com.social.util.MessageUtil;

public class AuthenticationValidator {

	private static final Logger logger = LoggerFactory.getLogger(AuthenticationValidator.class);
	private static AuthenticationValidator authenticationValidator;
	private static CommonUtil commonUtil = CommonUtil.getInstance();
	private static AuthenticationService authenticationService = AuthenticationService.getInstance();
	private static UserService userService = UserService.getInstance();

	private AuthenticationValidator() {
	}

	public static AuthenticationValidator getInstance() {
		if (authenticationValidator == null) {
			authenticationValidator = new AuthenticationValidator();
		}
		return authenticationValidator;
	}

	public Map<String, String> validateRegistration(RegistrationRequestDTO registrationDto) throws Exception {
		Map<String, String> errorMessages = new LinkedHashMap<>();	
		if (commonUtil.isNullOrEmpty(registrationDto.getUsername())) {
			errorMessages.put("username", MessageUtil.getMessage("error.username.required"));
		} else if (registrationDto.getUsername().length() < 5 || registrationDto.getUsername().length() > 30) {
			errorMessages.put("username", MessageUtil.getMessage("error.username.length"));
		}
		if (commonUtil.isNullOrEmpty(registrationDto.getEmail())) {
			errorMessages.put("email", MessageUtil.getMessage("error.email.required"));
		} else if (!commonUtil.isValidEmail(registrationDto.getEmail())) {
			errorMessages.put("email", MessageUtil.getMessage("error.email.invalid"));
		} else if (userService.getUserByEmail(registrationDto.getEmail()) != null) {
			errorMessages.put("email", MessageUtil.getMessage("error.email.duplicate"));
		}
		if(commonUtil.isNullorEmpty(registrationDto.getProfileImage())) {
			errorMessages.put("image", MessageUtil.getMessage("error.image.required"));
		}else if(!commonUtil.isImageSizeValid(registrationDto.getProfileImage())) {
			errorMessages.put("image", MessageUtil.getMessage("error.image.size"));
		}	
		if (commonUtil.isNullOrEmpty(registrationDto.getPassword())) {
			errorMessages.put("password", MessageUtil.getMessage("error.password.required"));
		} else if (registrationDto.getPassword().length() < 6 || registrationDto.getPassword().length() > 12) {
			errorMessages.put("password", MessageUtil.getMessage("error.password.length"));
		} else if (!registrationDto.getPassword().equals(registrationDto.getConfirmPassword())) {
			errorMessages.put("password", MessageUtil.getMessage("error.password.mismatch"));
		}

		return errorMessages;
	}

	public Map<String, String> validateLogin(LoginRequestDto loginDto) throws Exception {
		Map<String, String> errorMessages = new LinkedHashMap<>();
		if (commonUtil.isNullOrEmpty(loginDto.getEmail())) {
			errorMessages.put("email", MessageUtil.getMessage("error.email.required"));
		}
		if (commonUtil.isNullOrEmpty(loginDto.getPassword())) {
			errorMessages.put("password", MessageUtil.getMessage("error.password.required"));
		}
		if (errorMessages.isEmpty()) {
			UserDto user = userService.getUserByEmail(loginDto.getEmail());
			if (user == null) {
				errorMessages.put("email", MessageUtil.getMessage("error.email.notfound"));
			}
		}
		return errorMessages;
	}

}
