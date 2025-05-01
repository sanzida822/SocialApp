package com.social.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

import com.social.dto.UserDto;
import com.social.enums.Privacy;

public class CommonUtil {
	private static CommonUtil instance;

	private CommonUtil() {
	}

	public static CommonUtil getInstance() {
		if (instance == null) {
			instance = new CommonUtil();
		}
		return instance;
	}

	public boolean isEmpty(Map<?, ?> map) {
		return map == null || map.isEmpty();
	}
	public boolean isNullorEmpty(byte[] image) {
		return image == null || image.length==0;
	}
    public boolean isNullOrEmpty(List<?> list) {
        return list == null || list.isEmpty();
    }

	public boolean isNullOrEmpty(String str) {
		return str == null || str.trim().isEmpty();
	}

	public boolean isValidImageType(Part imagePart) {
	    String type = imagePart.getContentType();
	    return "image/jpeg".equalsIgnoreCase(type) || "image/jpg".equalsIgnoreCase(type);
	}
	public byte[] extractImageBytes(Part imagePart) throws IOException {
		try (InputStream imageStream = imagePart.getInputStream()) {
				return imageStream.readAllBytes();
		}
	}
	public Privacy toEnum(String string) {
	    return Privacy.valueOf(string.toUpperCase().replace(" ", "_"));
	}
	
	public UserDto getUserFromSession(HttpServletRequest request) {
		HttpSession session=request.getSession();
		  if (session != null) {
	            return (UserDto) session.getAttribute("user");
	        }
		  return null;
		
	}

	public boolean isValidEmail(String email) {
		String EMAIL_REGEX = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
		Pattern EMAIL_PATTERN = Pattern.compile(EMAIL_REGEX);
		if (email == null) {
			return false;
		}
		return EMAIL_PATTERN.matcher(email).matches();
	}
	
	public boolean isImageSizeValid(byte[] image) {
		return image.length < Constants.MAX_IMAGE_SIZE;
	}

}
