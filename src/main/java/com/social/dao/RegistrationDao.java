package com.social.dao;

import java.sql.*;

import com.social.model.RegistrationModel;
public class RegistrationDao {

	
	public boolean saveUser(RegistrationModel reg)  {
		boolean status=false;
		try{
			String sql="Insert into users (uname,email,password,image) values(?,?,?,?)";
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/social_media_app","root", "root");
			
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
