package com.social.validation;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import javax.servlet.http.Part;

import org.slf4j.LoggerFactory;

import com.social.dao.UserDao;
import com.social.dto.RegistrationRequestDTO;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AuthenticationValidation {

	private static final Logger logger = LoggerFactory.getLogger(AuthenticationValidation.class);
	private static UserDao userdao = UserDao.getInstance();

	public AuthenticationValidation() {
//		this.userdao=UserDao.getInstance();
	}

	public static String ValidateRegistration(RegistrationRequestDTO registrationDTO) throws IOException {

		Properties messageProperties  = new Properties();

		try (InputStream messagePropertiesStream = AuthenticationValidation.class.getClassLoader()
				.getResourceAsStream("messages.properties");) {
			messageProperties .load(messagePropertiesStream);
			if (messagePropertiesStream == null) {
				logger.error("messages.properties file not found");

			}
		}

		catch (Exception e) {
			logger.error("Error occurred while loading messages.properties: {}", e.getMessage());
		}

		if (registrationDTO.getUsername() == null || registrationDTO.getEmail() == null || registrationDTO.getPassword() == null || registrationDTO.getConfirm_password() == null || registrationDTO.getUsername().isEmpty()
				|| registrationDTO.getEmail().isEmpty() || registrationDTO.getPassword().isEmpty()||registrationDTO.getConfirm_password().isEmpty()) {

			return messageProperties.getProperty("error.validation.all_fields_required");

		}

//		if (imagePart == null || imagePart.getSize() == 0) {
//			return messageProperties.getProperty("error.image.no_image");
//
//		}
		if (userdao.findByEmail(registrationDTO.getEmail())!=null) {		
			return messageProperties.getProperty("error.email.exists");
		}

		if (registrationDTO.getPassword().length() < 6) {

			return messageProperties.getProperty("error.password.length");

		}
		if (!registrationDTO.getPassword().equals(registrationDTO.getConfirm_password())) {
			return messageProperties.getProperty("error.password.mismatch");

		}
		return null;
	}
}
