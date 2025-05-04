package com.social.config;
import java.sql.Connection;
import java.sql.DriverManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.social.util.LoadApplicationProperties;

public class DBConnection {
	private static final Logger logger = LoggerFactory.getLogger(DBConnection.class);
	private static DBConnection instance;
	private Connection connection;
	String url = LoadApplicationProperties.getProperty("mysql.url");
	String username = LoadApplicationProperties.getProperty("mysql.username");
	String password = LoadApplicationProperties.getProperty("mysql.password");
	String dbDriver = LoadApplicationProperties.getProperty("db.driver");

	private DBConnection() throws ClassNotFoundException {
		Class.forName(dbDriver);
		try {
			Class.forName(dbDriver);
			this.connection = DriverManager.getConnection(url, username, password);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Exception occur when creating database connection");
		}
	}

	public static DBConnection getInstance() throws ClassNotFoundException {
		if (instance == null) {
			synchronized (DBConnection.class) {
				if (instance == null) {
					instance = new DBConnection();
				}
			}
		}
		return instance;
	}

	public Connection getConnection() {
        try {
            return DriverManager.getConnection(url, username, password);
        } catch (Exception e) {
            logger.error("Error getting DB connection", e);
            return null;
        }
    
	}

}
