package com.social.validation;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.social.dto.RegistrationRequestDTO;
import com.social.service.AuthenticationService;
import com.social.util.CommonUtil;
import com.social.util.MessageUtil;

public class AuthenticationValidation {

	private static final Logger logger = LoggerFactory.getLogger(AuthenticationValidation.class);


	public AuthenticationValidation() {
//		this.userdao=UserDao.getInstance();
	}

	public Map<String, String> AuthenticationValidator(RegistrationRequestDTO registrationDto) throws Exception {

		Map<String, String> errorMessages = new HashMap<>();

		if (CommonUtil.isNullOrEmpty(registrationDto.getUsername())) {
			errorMessages.put("username_required", MessageUtil.getErrorMessage("error.username.required"));
		}

		if (CommonUtil.isNullOrEmpty(registrationDto.getEmail())) {
			errorMessages.put("email_required", MessageUtil.getErrorMessage("error.email.required"));

		}else {
			   AuthenticationService authService = new AuthenticationService();
               if (authService.getUserByEmail(registrationDto.getEmail()) != null) {
                   errorMessages.put("email_duplicate", MessageUtil.getErrorMessage("error.email.duplicate"));
               }
			
		}

		if (CommonUtil.isNullOrEmpty(registrationDto.getPassword())) {
			errorMessages.put("password_required", MessageUtil.getErrorMessage("error.password.required"));
		}

		if (CommonUtil.isNullOrEmpty(registrationDto.getConfirm_password())) {
			errorMessages.put("confirm_password_required",
					MessageUtil.getErrorMessage("error.confirm_password.required"));
		}

		if (registrationDto.getPassword().length() < 6) {
			errorMessages.put("password_length", MessageUtil.getErrorMessage("error.password.length"));

		}
		if (!registrationDto.getPassword().equals(registrationDto.getConfirm_password())) {
			errorMessages.put("password_mismatch", MessageUtil.getErrorMessage("error.password.mismatch"));

		}

		return errorMessages;
	}


}
