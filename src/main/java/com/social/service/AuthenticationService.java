package com.social.service;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

import javax.servlet.http.Part;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.social.dao.UserDao;
import com.social.model.LoginModel;
import com.social.model.RegistrationModel;
import com.social.validation.AuthenticationValidation;

public class AuthenticationService {
	private static final Logger logger = LoggerFactory.getLogger(AuthenticationService.class);
	private UserDao userDao;
	private RegistrationModel reg;
	private LoginModel login;

	public AuthenticationService() {
		this.userDao = UserDao.getInstance();
		this.login = new LoginModel();
	}
//	public AuthenticationService() {
//		regDao = new UserDao();
//
//	}

	public String validateAndSaveUser(String uname, String email, String password, String cpassword, Part imagePart)
			throws IOException {
		boolean isexist = userDao.ExistEmail(email);

		if (isexist) {
			return "Email already exists";

		}

		String imageError = AuthenticationValidation.validateImage(imagePart);
		if (imageError != null) {
			return imageError;
		}
		String formError = AuthenticationValidation.ValidateRegistration(uname, email, password, cpassword);
		if (formError != null) {
			return formError;

		}

		String imagePath = saveImageToDisk(imagePart);
		reg = new RegistrationModel();
		reg.setUname(uname);
		reg.setEmail(email);
		reg.setPassword(password);
		reg.setCpassword(cpassword);
		reg.setImage(imagePath);
		boolean saveUser = userDao.saveUser(reg);

		if (saveUser) {
			return null; // Registration successful
		} else {
			return "Registration failed"; // Return error if registration failed
		}

	}

	private String saveImageToDisk(Part imagePart) throws IOException {
		String imageName = UUID.randomUUID().toString() + ".jpg";
		String imageDirectory = "D:/java/SocialApp/images/";
		File imageFile = new File(imageDirectory + imageName);
		imagePart.write(imageFile.getAbsolutePath());
		System.out.println(imageDirectory + imageName);
		return imageDirectory + imageName;
	}

	public LoginModel AuthenticUser(String email, String password) {

		return userDao.getUserByEmailAndPassword(email, password);

	}

	
}
