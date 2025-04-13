package com.social.validation;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import javax.servlet.http.Part;

import org.apache.tomcat.util.descriptor.InputSourceUtil;
import org.slf4j.LoggerFactory;

import com.social.dao.UserDao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
public class AuthenticationValidation {
	
	private static final Logger logger=LoggerFactory.getLogger(AuthenticationValidation.class);
	private static UserDao userdao = UserDao.getInstance();

	public AuthenticationValidation() {
//		this.userdao=UserDao.getInstance();
	}
	  
//	public static String validateImage(Part imagePart) {
//	
//		
//		if (imagePart == null || imagePart.getSize() == 0) {
//			return "No image Selected";
//
//		}
//		return null;
//	}

	public static String ValidateRegistration(Part imagePart,String uname, String email, String password, String cpassword) throws IOException {
		InputStream is=AuthenticationValidation.class.getClassLoader().getResourceAsStream("messages.properties");
		if(is==null) {
			
			logger.error("messages.properties file not found");
		}
		
		 Properties p = new Properties();
         p.load(is);
         
     	if (uname == null || email == null || password == null || cpassword == null || uname.isEmpty()
				|| email.isEmpty() || cpassword.isEmpty()) {

			return p.getProperty("error.validation.all_fields_required");

		}
         
         
 		if (imagePart == null || imagePart.getSize() == 0) {
			return p.getProperty("error.image.no_image");

		}
 		if(userdao.ExistEmail(email)) {
 			return p.getProperty("error.email.exists");
 		}

	
		if (password.length() < 6) {

			return p.getProperty("error.password.length");

		}
		if (!password.equals(cpassword)) {
			return p.getProperty("error.password.mismatch");

		}
		return null;
	}
}
