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
	public String validateAndSaveUser(String username, String email, String password, String confirm_password, Part imagePart)
			throws IOException {
		String validationError=AuthenticationValidation.ValidateRegistration(imagePart, username, email, password, confirm_password);
		Properties messageProperties  = new Properties();
		if (validationError != null) {
			logger.error("Validaion error:{} occured for user:{}, email:{}", validationError,username,email);
			return validationError;

		}
		try(InputStream messagePropertiesStream  = AuthenticationService.class.getClassLoader().getResourceAsStream("messages.properties"))
		{
			messageProperties .load(messagePropertiesStream);			
		}
		catch(Exception e) {
			logger.error("messages.properties file not found");
			
		}
		

		String imagePath = saveImageToDisk(imagePart);
		userModel = new UserModel();
		userModel.setUsername(username);
		userModel.setEmail(email);
		userModel.setPassword(password);
		userModel.setConfirmPassword(confirm_password);
		userModel.setImage(imagePath);
		UserModel saveUser = userDao.save(userModel);
		if (saveUser!=null) {
			return null; // Registration successful
		} else {
			logger.error("Registration failed");
			return messageProperties.getProperty("error.registration_failed"); // Return error if registration failed
		}

	}

	private String saveImageToDisk(Part imagePart) throws IOException {
	    String imageName = UUID.randomUUID().toString() + ".jpg";
	    File uploadDir = new File("images");
	    
	    if (!uploadDir.exists()) {
	        uploadDir.mkdirs();
	    }

	    File imageFile = new File(uploadDir, imageName);
	    imagePart.write(imageFile.getAbsolutePath());
	    return "images/" + imageName;
	}

	public LoginModel AuthenticUser(String email, String password) {
		return userDao.getUserByEmailAndPassword(email, password);

	}

	
	
	
	
}
