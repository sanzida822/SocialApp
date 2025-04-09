package com.social.dao;

import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.Properties;

import com.social.controller.RegistrationServlet;
import com.social.model.RegistrationModel;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import util.DBConnection;
public class RegistrationDao {
	 private static final Logger logger = LoggerFactory.getLogger(RegistrationDao.class);
	
	public boolean saveUser(RegistrationModel reg)  {
		  
		boolean status=false;
	
		
		try{
			//String environment = System.getProperty("env", "dev"); //default run dev env. if run in pilot java -Denv=pilot -jar appame.jar


//			InputStream input=this.getClass().getClassLoader().getResourceAsStream("config.local.properties");
//			Properties p=new Properties();
//			p.load(input);
//			//load data from config.propertis file
//			String dbUrl=p.getProperty("url");
//			String username=p.getProperty("username");
//			String password=p.getProperty("password");
//			String dbDriver=p.getProperty("Driver");
			
			
			String sql="Insert into users (uname,email,password,image) values(?,?,?,?)";
//			Class.forName(dbDriver);
//			Connection con=DriverManager.getConnection(dbUrl,username,password);
//			
//			  if (con != null) {
//			        System.out.println("Database connected successfully!");
//			    } else {
//			        System.out.println("Database connection failed.");
//			    }
			
			DBConnection dbconnection=DBConnection.getInstance();
			Connection connection=dbconnection.getConnection();
			PreparedStatement ps=connection.prepareStatement(sql);
			
		    ps.setString(1, reg.getUname());
		    ps.setString(2, reg.getEmail());
		    ps.setString(3, reg.getPassword());
		    ps.setString(4, reg.getImage());
		    
		//status=	ps.executeQuery(sql)>0;  //execute query use for select statement
			int rowsAffect= ps.executeUpdate();
			logger.info("Rows affected: " + rowsAffect);
			status=rowsAffect>0; //return 1 if insert a row to table
			ps.close();
			connection.close();
		
	
		}
		catch(SQLException e) {
			  logger.error("SQL Exception occurred while saving user: " + e.getMessage());
	          logger.error("Error stack trace: ", e);
		    
			
		}catch(Exception e) {
			 logger.error("General Exception occurred: " + e.getMessage());
	         logger.error("Error stack trace: ", e);
			
		}
		
		return status;
		
		
	}
}
