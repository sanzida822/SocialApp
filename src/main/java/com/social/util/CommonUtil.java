package com.social.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.regex.Pattern;

import javax.servlet.http.Part;

public class CommonUtil {
	private static CommonUtil instance;

	private CommonUtil() {
	}
	public static CommonUtil getInstance() {
		if(instance==null) {
			instance= new CommonUtil();
		}
		return instance;
	}
	public boolean isEmpty(Map<?, ?> map) {
		return map == null || map.isEmpty();
	}
	public boolean isNullOrEmpty(String str) {
		return str == null || str.trim().isEmpty();
	}
	public  byte[] extractImageBytes(Part imagePart) throws IOException {
		try (InputStream imageStream = imagePart.getInputStream()) {
			if (imagePart != null && imagePart.getSize() > 0) {
				return imageStream.readAllBytes();
			}
		}
		return null;
	}
	public  boolean isValidEmail(String email) {
		String EMAIL_REGEX = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
		Pattern EMAIL_PATTERN = Pattern.compile(EMAIL_REGEX);
		if (email == null) {
			return false;
		}
		return EMAIL_PATTERN.matcher(email).matches();
	}

}
