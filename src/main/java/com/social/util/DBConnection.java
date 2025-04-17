package com.social.util;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DBConnection {
	private static final Logger logger = LoggerFactory.getLogger(DBConnection.class);
	private static DBConnection instance;
	private static Connection connection;
	private static String dbUrl;
	private static String username;
	private static String password;
	private static String dbDriver;
	Properties applicationProperties;

	private DBConnection() throws Exception {
		try (InputStream applicationPropertiesStream = this.getClass().getClassLoader()
				.getResourceAsStream("application.properties");) {

			if (applicationPropertiesStream == null) {
				logger.error("application.properties file not found.");
				return;
			}

			applicationProperties = new Properties();
			applicationProperties.load(applicationPropertiesStream);
		} catch (Exception e) {
			logger.error("Exception occurred when loading application properties");
		}

		if (applicationProperties != null) {
			dbUrl = applicationProperties.getProperty("mysql.url");
			username = applicationProperties.getProperty("mysql.username");
			password = applicationProperties.getProperty("mysql.password");
			dbDriver = applicationProperties.getProperty("db.driver");
		} else {

			logger.error("Failed to load application properties. Database connection cannot be established.");
		}

		logger.info("Database url:{} DB username: {} DB password:{}", dbUrl, username, password);

		try {
			Class.forName(dbDriver);
			if (connection == null) { // Ensures for the same instance, the same connection is used
				logger.info("Initializing database connection...");
				connection = DriverManager.getConnection(dbUrl, username, password);
				logger.info("Database connection created .");
			} else {
				logger.info("Database Connection failed");
			}

		}
		catch (Exception e) {
			logger.error("Error while initializing DBConnection: ", e);
		}
	}

	public static DBConnection getInstance() throws Exception {
		if (instance == null) {
			synchronized (DBConnection.class) {
				if (instance == null) {
					instance = new DBConnection();
				}
			}
		}
		return instance;
	}

	public static Connection getConnection() {
		try {
			if (connection == null || connection.isClosed()) {
				logger.info("Reinitializing the database connection...");
				// Recreate the connection if it's closed or null
				connection = DriverManager.getConnection(dbUrl, username, password);
			}
		} catch (Exception e) {
			logger.error("Error while getting the connection: ", e);
		}
		return connection;
	}
}
