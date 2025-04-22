package com.social.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

import javax.servlet.http.Part;

public class CommonUtil {
	public static boolean isMapEmpty(Map<?, ?> map) {
		return map == null || map.isEmpty();

	}

	public static boolean isNullOrEmpty(String str) {
		return str == null || str.trim().isEmpty();
	}
	
	
	public static byte[] extractImageBytes(Part imagePart) throws IOException {
		try (InputStream imageStream = imagePart.getInputStream())

		{
			if (imagePart != null && imagePart.getSize() > 0) {
				return imageStream.readAllBytes();
			}
		}
		return null;
	}


}
