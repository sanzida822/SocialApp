package com.social.config;

import java.util.Properties;

import java.io.InputStream;


public class LoadApplicationProperties {
    private static final Properties messageProperties = new Properties();

    static {
        try (InputStream in = LoadApplicationProperties.class.getClassLoader()
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
