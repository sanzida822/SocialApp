package com.social.config;

import java.util.Properties;

import java.io.InputStream;


public class DatabaseConfig {
    private static final Properties messageProperties = new Properties();

    static {
        try (InputStream in = DatabaseConfig.class.getClassLoader()
                .getResourceAsStream("application.properties")) {
            if (in != null) {
                messageProperties.load(in);
            } else {
                System.err.println("messages.properties not found");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String getProperty(String key) {
        return messageProperties.getProperty(key);
    }
}
