package com.social.service;

import java.io.File;
import java.io.IOException;
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
	//	boolean existEmail = userDao.findByEmail(email);
		String validationError=AuthenticationValidation.ValidateRegistration(imagePart, username, email, password, confirm_password);
		if (validationError != null) {
			logger.error("Validaion error:{} occured for user:{}, email:{}", validationError,username,email);
			return validationError;

		}

		String imagePath = saveImageToDisk(imagePart);
		userModel = new UserModel();
		userModel.setUsername(username);
		userModel.setEmail(email);
		userModel.setPassword(password);
		userModel.setConfirmPassword(confirm_password);
		userModel.setImage(imagePath);
		boolean saveUser = userDao.save(userModel);

		if (saveUser) {
			return null; // Registration successful
		} else {
			logger.error("Registration failed");
			return "Registration failed"; // Return error if registration failed
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
