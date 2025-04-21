package com.social.service;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.UUID;

import javax.servlet.http.Part;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.social.dao.UserDao;
import com.social.dto.LoginRequestDto;
import com.social.dto.RegistrationRequestDTO;
import com.social.mapper.UserMapper;
import com.social.model.LoginModel;
import com.social.model.UserModel;
import com.social.validation.AuthenticationValidation;

public class AuthenticationService {
	private static final Logger logger = LoggerFactory.getLogger(AuthenticationService.class);
	private UserDao userDao;
	private UserModel userModel;
	private LoginModel login;

	public AuthenticationService() {
		this.userDao = UserDao.getInstance();
		this.login = new LoginModel();
	}
	public String validateAndSaveUser(RegistrationRequestDTO registrationDto)
			throws IOException {
		String validationError=AuthenticationValidation.ValidateRegistration(registrationDto);
		Properties messageProperties  = new Properties();
		if (validationError != null) {
			logger.error("Validaion error:{} occured for user:{}, email:{}", validationError,registrationDto.getUsername(),registrationDto.getEmail());
			return validationError;

		}
		try(InputStream messagePropertiesStream  = AuthenticationService.class.getClassLoader().getResourceAsStream("messages.properties"))
		{
			messageProperties .load(messagePropertiesStream);			
		}
		catch(Exception e) {
			logger.error("messages.properties file not found");
			
		}
		

		//String imagePath = saveImageToDisk(imagePart);
//		userModel = new UserModel();
//		userModel.setUsername(registrationDto.getUsername());
//		userModel.setEmail(registrationDto.getEmail());
//		userModel.setPassword(registrationDto.getPassword());
//		//userModel.setConfirmPassword(registrationDto.getConfirm_password());
//		userModel.setImage(registrationDto.getImage());
//		UserModel saveUser = userDao.save(userModel);
		
		   UserModel userModel = UserMapper.toEntity(registrationDto);
		   UserModel savedUser = userDao.save(userModel);
		if (savedUser!=null) {
			return null; // Registration successful
		} else {
			logger.error("Registration failed");
			return messageProperties.getProperty("error.registration_failed"); // Return error if registration failed
		}

	}

	public static String saveImageToDisk(Part imagePart) throws IOException {
	    String imageName = UUID.randomUUID().toString() + ".jpg";
	    File uploadDir = new File("images");
	    
	    if (!uploadDir.exists()) {
	        uploadDir.mkdirs();
	    }

	    File imageFile = new File(uploadDir, imageName);
	    imagePart.write(imageFile.getAbsolutePath());
	    return "images/" + imageName;
	}

	public LoginModel AuthenticUser(LoginRequestDto loginDto) {
		UserModel userModel=UserMapper.toEntity(loginDto);
		
		return userDao.getUserByEmailAndPassword(userModel.getEmail(),userModel.getPassword());

	}

	
	
	
	
}
