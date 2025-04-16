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

    private DBConnection() {
        try {
 
            InputStream is = this.getClass().getClassLoader().getResourceAsStream("application.properties");
            if (is == null) {
                logger.error("application.properties file not found.");
                return; 
            }

            Properties p = new Properties();
            p.load(is);

            String dbUrl = p.getProperty("mysql.url");
            String username = p.getProperty("mysql.username");
            String password = p.getProperty("mysql.password");
            String dbDriver = p.getProperty("db.driver");

            logger.info("Database url:{} DB username: {} DB password:{}",dbUrl, username,password);

            
            try {
                Class.forName(dbDriver);
                if (connection == null) {  // Ensures for the same instance, the same connection is used
                    logger.info("Initializing database connection...");
                    connection = DriverManager.getConnection(dbUrl, username, password);
                   
                    logger.info("Database connection created .");
                } else {
                    logger.info("not.");
                }

            } catch (ClassNotFoundException e) {
                logger.error("JDBC Driver not found: " + dbDriver, e);
                throw new Exception("Database driver not found.");
            }

       
        } catch (Exception e) {
            logger.error("Error while initializing DBConnection: ", e);
        }
    }

    public static DBConnection getInstance() {
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
        return connection;
    }
}
