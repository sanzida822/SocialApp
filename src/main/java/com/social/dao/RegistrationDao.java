package com.social.dao;

import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.Properties;

import com.social.model.RegistrationModel;
public class RegistrationDao {

	
	public boolean saveUser(RegistrationModel reg)  {
		boolean status=false;
	
		
		try{
			//String environment = System.getProperty("env", "dev"); //default run dev env. if run in pilot java -Denv=pilot -jar appame.jar


			InputStream input=this.getClass().getClassLoader().getResourceAsStream("config.local.properties");
			Properties p=new Properties();
			p.load(input);
			//load data from config.propertis file
			String dbUrl=p.getProperty("url");
			String username=p.getProperty("username");
			String password=p.getProperty("password");
			String dbDriver=p.getProperty("Driver");
			
			
			String sql="Insert into users (uname,email,password,image) values(?,?,?,?)";
			Class.forName(dbDriver);
			Connection con=DriverManager.getConnection(dbUrl,username,password);
			
			  if (con != null) {
			        System.out.println("Database connected successfully!");
			    } else {
			        System.out.println("Database connection failed.");
			    }
			PreparedStatement ps=con.prepareStatement(sql);
			
		    ps.setString(1, reg.getUname());
		    ps.setString(2, reg.getEmail());
		    ps.setString(3, reg.getPassword());
		    ps.setString(4, reg.getImage());
		    
		//status=	ps.executeQuery(sql)>0;  //execute query use for select statement
			int rowsAffect= ps.executeUpdate();
			System.out.println(rowsAffect);
			status=rowsAffect>0; //return 1 if insert a row to table
			ps.close();
			con.close();
		
	
		}
		catch(SQLException e) {
         System.out.println(e);
		     e.printStackTrace(); 
			
		}catch(Exception e) {
			e.printStackTrace();
			
		}
		
		return status;
		
		
	}
}
