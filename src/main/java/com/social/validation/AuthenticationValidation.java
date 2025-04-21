package com.social.validation;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.social.dao.UserDao;
import com.social.dto.RegistrationRequestDTO;
import com.social.util.MessageUtil;

public class AuthenticationValidation {

	private static final Logger logger = LoggerFactory.getLogger(AuthenticationValidation.class);
	private static UserDao userdao = UserDao.getInstance();

	public AuthenticationValidation() {
//		this.userdao=UserDao.getInstance();
	}

	public Map<String,String> AuthenticationValidator(RegistrationRequestDTO registrationDto) throws IOException {
		
	    Map<String,String> errorMessages = new HashMap<>();
	    

	    if(registrationDto.getUsername()==null || registrationDto.getUsername().isEmpty()) {
	    	errorMessages.put("username_required", MessageUtil.getErrorMessage("error.username.required"));
	    }
	    
	    if(registrationDto.getEmail()==null || registrationDto.getEmail().isEmpty()) {
	    errorMessages.put("email_required", MessageUtil.getErrorMessage("error.email.required"));    	
	    	
	    }
	    
	    if(registrationDto.getPassword()==null || registrationDto.getPassword().isEmpty()) {
	    errorMessages.put("password_required", MessageUtil.getErrorMessage("error.password.required"));    		    	
	    }
	    
	    if(registrationDto.getConfirm_password()==null || registrationDto.getConfirm_password().isEmpty()) {
	    errorMessages.put("confirm_password_required", MessageUtil.getErrorMessage("error.confirm_password.required"));    		    	
	    }

		if (registrationDto.getPassword().length() < 6) {
			errorMessages.put("password_length",MessageUtil.getErrorMessage("error.password.length")) ;

		}
		if (!registrationDto.getPassword().equals(registrationDto.getConfirm_password())) {
			errorMessages.put("password_mismatch",MessageUtil.getErrorMessage("error.password.mismatch")) ;

		}
		if (registrationDto.getImage() == null || registrationDto.getImage().getSize() == 0) {
		    errorMessages.put("no_image", MessageUtil.getErrorMessage("error.image.no_image"));
		}
		return errorMessages;
	}
}
