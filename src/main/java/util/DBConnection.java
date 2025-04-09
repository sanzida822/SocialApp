package util;

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
            // Load the properties file
            InputStream is = this.getClass().getClassLoader().getResourceAsStream("config.local.properties");
            if (is == null) {
                logger.error("config.local.properties file not found.");
                return; 
            }

            Properties p = new Properties();
            p.load(is);

            String dbUrl = p.getProperty("url");
            String username = p.getProperty("username");
            String password = p.getProperty("password");
            String dbDriver = p.getProperty("driver");

            // Log the database properties for debugging
            logger.info("DB URL: " + dbUrl);
            logger.info("DB Username: " + username);
            logger.info("DB Driver: " + dbDriver);

            // Load the database driver
            try {
                Class.forName(dbDriver);
            } catch (ClassNotFoundException e) {
                logger.error("JDBC Driver not found: " + dbDriver, e);
                throw new Exception("Database driver not found.");
            }

            // Initialize the database connection
            if (connection == null) {  // Ensures for the same instance, the same connection is used
                logger.info("Initializing database connection...");
                connection = DriverManager.getConnection(dbUrl, username, password);
               
                logger.info("Database connection created .");
            } else {
                logger.info("not.");
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
