package com.social.validation;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.social.dto.LoginRequestDto;
import com.social.dto.RegistrationRequestDTO;
import com.social.service.AuthenticationService;
import com.social.util.CommonUtil;
import com.social.util.MessageUtil;

public class AuthenticationValidator {

	private static final Logger logger = LoggerFactory.getLogger(AuthenticationValidator.class);
	private static AuthenticationValidator authenticationValidator;
	CommonUtil commonUtil = CommonUtil.getInstance();
	AuthenticationService authenticationService = AuthenticationService.getInstance();

	private AuthenticationValidator() {
	}

	public static AuthenticationValidator getInstance() {
		if (authenticationValidator == null) {
			authenticationValidator = new AuthenticationValidator();
		}
		return authenticationValidator;
	}
//	public AuthenticationValidator() {
//		this.userdao = UserDao.getInstance();
//	}

	public Map<String, String> validateRegistration(RegistrationRequestDTO registrationDto) throws Exception {
		Map<String, String> errorMessages = new HashMap<>();
		if (commonUtil.isNullOrEmpty(registrationDto.getUsername())) {
			errorMessages.put("username", MessageUtil.getMessage("error.username.required"));
		}
		if (commonUtil.isNullOrEmpty(registrationDto.getEmail())) {
			errorMessages.put("email", MessageUtil.getMessage("error.email.required"));
		} else if (!commonUtil.isValidEmail(registrationDto.getEmail())) {
			errorMessages.put("email", MessageUtil.getMessage("error.email.invalid"));
		} else if (authenticationService.getUserByEmail(registrationDto.getEmail()) != null) {
			errorMessages.put("email", MessageUtil.getMessage("error.email.duplicate"));
		}
		if (commonUtil.isNullOrEmpty(registrationDto.getPassword())) {
			errorMessages.put("password", MessageUtil.getMessage("error.password.required"));
		} else {
			if (registrationDto.getPassword().length() < 6 || registrationDto.getPassword().length() > 12) {
				errorMessages.put("password", MessageUtil.getMessage("error.password.length"));
			}
			if (!registrationDto.getPassword().equals(registrationDto.getConfirmPassword())) {
				errorMessages.put("password", MessageUtil.getMessage("error.password.mismatch"));
			}
		}
		return errorMessages;
	}

	public Map<String, String> validateLogin(LoginRequestDto loginDto) {
		Map<String, String> errorMessages = new HashMap<>();
		if (commonUtil.isNullOrEmpty(loginDto.getEmail())) {
			errorMessages.put("email", MessageUtil.getMessage("error.email.required"));
		}
		if (commonUtil.isNullOrEmpty(loginDto.getPassword())) {
			errorMessages.put("password", MessageUtil.getMessage("error.password.required"));
		}
		return errorMessages;
	}

}
