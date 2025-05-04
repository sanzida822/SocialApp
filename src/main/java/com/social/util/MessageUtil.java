package com.social.util;
import java.io.InputStream;
import java.util.Properties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.social.validation.AuthenticationValidator;

public class MessageUtil {
	private static final Logger logger = LoggerFactory.getLogger(MessageUtil.class);
	private static final Properties messageProperties = new Properties();
	static {
		try (InputStream messagePropertiesStream = MessageUtil.class.getClassLoader()
				.getResourceAsStream("messages.properties")) {
			if (messagePropertiesStream != null) {
				messageProperties.load(messagePropertiesStream);
			}
			else {
				logger.error("messages.properties file not found");
			}
		}

		catch (Exception e) {
			logger.error("Error occurred while loading messages.properties: {},e:{}", e.getMessage(),e);
		}
	}

	private MessageUtil() {
	}

	public static String getMessage(String key) {
		return messageProperties.getProperty(key);
	}

}

