package com.social.validation;

import javax.servlet.http.Part;

public class AuthenticationValidation {

	public static String validateImage(Part imagePart) {
		if (imagePart == null || imagePart.getSize() == 0) {
			return "No image Selected";

		}
		return null;
	}

	public static String ValidateRegistration(String uname, String email, String password, String cpassword) {
		if (uname == null || email == null || password == null || cpassword == null || uname.isEmpty()
				|| email.isEmpty() || cpassword.isEmpty()) {

			return "All fields are required";

		}
		if (password.length() < 6) {

			return "Password length cant be less than 6";

		}
		if (!password.equals(cpassword)) {
			return "Password do not match";

		}
		return null;
	}
}
