package com.social.util;

import java.io.InputStream;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.social.validation.AuthenticationValidator;

public class ConfigLoader {
	private static final Logger logger = LoggerFactory.getLogger(ConfigLoader.class);
	private static final Properties ApplicationProperties = new Properties();
	static {
		try (InputStream ApplicationPropertiesStream = AuthenticationValidator.class.getClassLoader()
				.getResourceAsStream("application.properties");) {

			if (ApplicationPropertiesStream != null) {
				ApplicationProperties.load(ApplicationPropertiesStream);
			}
			else {
				logger.error("applicaton.properties file not found");

			}
		}

		catch (Exception e) {
			logger.error("Error occurred while loading messages.properties: {}", e.getMessage());
		}
	}

	private ConfigLoader() {
	}

	public static String getMessage(String key) {
		return ApplicationProperties.getProperty(key);
	}

}
