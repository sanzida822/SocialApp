package com.social.dao;

import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.Properties;

import com.social.controller.AuthenticationServlet;
import com.social.model.LoginModel;
import com.social.model.RegistrationModel;
import com.social.util.DBConnection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
public class UserDao {
	 private static final Logger logger = LoggerFactory.getLogger(UserDao.class);
	
	 private static UserDao instance;
		private UserDao() {
			
		}
		
		public static UserDao getInstance() {
			if(instance==null) {
				synchronized (UserDao.class) {
					if(instance==null) {
						instance=new UserDao();
					}
				}
				
			}
			return instance;
			
		}
		
		
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
	
	
	public boolean ExistEmail(String email) {
		boolean status=false;
        
		
		String sql="Select 1 from users where email=?";
		
		try {
			DBConnection dbconnection=DBConnection.getInstance();
			Connection connection=dbconnection.getConnection();
			PreparedStatement ps=connection.prepareStatement(sql);
		
			logger.info("started connectionfor email");
			ps.setString(1, email);
			
			ResultSet rs=ps.executeQuery();
			
			logger.info("{}",rs);
			status=rs.next();
			logger.info("status={}",status);
			
			
		}catch(Exception e) {
			logger.error("{}, {}",e.getMessage(),e);
		}
				
				
				
		return status;
		
	}
	
	public LoginModel getUserByEmailAndPassword(String email, String password) {
		LoginModel loginModel=null;
		
		try {
			String sql = "SELECT id, uname, email, password FROM users WHERE email=? AND password=?";
//			Class.forName("com.mysql.cj.jdbc.Driver");
//			Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/social_media_app","root","root");
			
			
			DBConnection dbconnection=DBConnection.getInstance();
			Connection con=dbconnection.getConnection();
			
			PreparedStatement ps= con.prepareStatement(sql);
			ps.setString(1, email);
			ps.setString(2, password);
			
			ResultSet rowsAffect=ps.executeQuery();
			System.out.println(rowsAffect);
			if(rowsAffect.next()) {
				loginModel=new LoginModel();
				loginModel.setId(rowsAffect.getInt("id"));
				loginModel.setUname(rowsAffect.getString("uname"));
				loginModel.setEmail(rowsAffect.getString("email"));
				
				
				
			}
			
			ps.close();
//			con.close();
		}
		catch(Exception e) {
			System.out.println(e);
		  e.printStackTrace();
		}
		return loginModel;
		
	}
	

}
