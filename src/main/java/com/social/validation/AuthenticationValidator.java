package com.social.validation;
import java.util.LinkedHashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.social.util.Constants;
import com.social.dto.LoginRequestDto;
import com.social.dto.RegistrationRequestDto;
import com.social.dto.UserDto;
import com.social.model.User;
import com.social.service.AuthenticationService;
import com.social.service.UserService;
import com.social.util.CommonUtil;
import com.social.util.MessageUtil;

public class AuthenticationValidator {

	private static final Logger logger = LoggerFactory.getLogger(AuthenticationValidator.class);
	private static AuthenticationValidator authenticationValidator;
	private static UserService userService = UserService.getInstance();

	private AuthenticationValidator() {
	}

	public static AuthenticationValidator getInstance() {
		if (authenticationValidator == null) {
			authenticationValidator = new AuthenticationValidator();
		}
		return authenticationValidator;
	}

	public Map<String, String> validateRegistration(RegistrationRequestDto registrationDto) throws Exception {
		Map<String, String> errorMessages = new LinkedHashMap<>();
		if (CommonUtil.isNullOrEmpty(registrationDto.getUsername())) {
			errorMessages.put("username", MessageUtil.getMessage("error.username.required"));
		} else if (registrationDto.getUsername().length() < 5 || registrationDto.getUsername().length() > 30) {
			errorMessages.put("username", MessageUtil.getMessage("error.username.length"));
		}
		if (CommonUtil.isNullOrEmpty(registrationDto.getEmail())) {
			errorMessages.put("email", MessageUtil.getMessage("error.email.required"));
		} else if (!CommonUtil.isValidEmail(registrationDto.getEmail())) {
			errorMessages.put("email", MessageUtil.getMessage("error.email.invalid"));
		} else if (userService.getUserByEmail(registrationDto.getEmail()) != null) {
			errorMessages.put("email", MessageUtil.getMessage("error.email.duplicate"));
		}
		if (registrationDto.getImageDto() == null
				|| CommonUtil.isNullorEmpty(registrationDto.getImageDto().getData())) {
			errorMessages.put("image", MessageUtil.getMessage("error.image.required"));
		} else if (!CommonUtil.isValidImageType(registrationDto.getImageDto().getContentType())) {
			errorMessages.put("image", MessageUtil.getMessage("error.image.type"));
		} else if (registrationDto.getImageDto().getSize() > Constants.MAX_IMAGE_SIZE) {
			errorMessages.put("image", MessageUtil.getMessage("error.image.size"));
		}
		if (CommonUtil.isNullOrEmpty(registrationDto.getPassword())) {
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
		if (CommonUtil.isNullOrEmpty(loginDto.getEmail())) {
			errorMessages.put("email", MessageUtil.getMessage("error.email.required"));
		}
		if (CommonUtil.isNullOrEmpty(loginDto.getPassword())) {
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
