package com.social.util;

import java.util.Map;

public class CommonUtil {
	public static boolean isMapEmpty(Map<?, ?> map) {
		return map == null || map.isEmpty();

	}

	public static boolean isStringEmpty(String str) {
		return str == null || str.trim().isEmpty();
	}

}
