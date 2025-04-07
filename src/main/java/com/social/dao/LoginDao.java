package com.social.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.social.model.LoginModel;

public class LoginDao {

	
	public boolean validateLogin(LoginModel model) {
		boolean status=false;
		try {
			String sql="Select email, password from users where email=? and password=?";
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/social_media_app","root","root");
			
			
			PreparedStatement ps= con.prepareStatement(sql);
			ps.setString(1, model.getEmail());
			ps.setString(2, model.getPassword());
			
			ResultSet rowsAffect=ps.executeQuery();
			System.out.println(rowsAffect);
			if(rowsAffect.next()) {
				System.out.println(rowsAffect.getString("email"));
				System.out.println(rowsAffect.getString("password"));
				status =true;
			}
			
			ps.close();
			con.close();
		}
		catch(Exception e) {
			System.out.println(e);
		  e.printStackTrace();
		}
		
		
		return status;
		
		
		
	}
}
