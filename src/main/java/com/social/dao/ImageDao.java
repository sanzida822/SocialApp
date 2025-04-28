package com.social.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.social.model.Image;
import com.social.util.DBConnection;

public class ImageDao {
	private static ImageDao imageDao;
	private ImageDao() {};
	
	public static ImageDao getInstance() {
		if(imageDao==null) {
			imageDao=new ImageDao();
		}
		return imageDao;		
	}
	
	public int saveAndReturnId(Image image) throws SQLException, Exception {
		String sql="Insert into images (data,size_bytes) values (?,?)";
		try (Connection connection = DBConnection.getInstance().getConnection();
				PreparedStatement ps = connection.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS)
		){
			ps.setBytes(1, image.getData() );
			ps.setLong(2, image.getSizeBytes());
			int rowsAffected=ps.executeUpdate();
		    if (rowsAffected > 0) {
	            try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
	                if (generatedKeys.next()) {
	                    return generatedKeys.getInt(1); 
	                }
	            }
	        }
			
			
			
		}
		return 0;
	}
	
	
	public boolean deleteById(int id) throws SQLException, Exception {
		String sql="delete * from images where id=?";
		try (Connection connection = DBConnection.getInstance().getConnection();
				PreparedStatement ps = connection.prepareStatement(sql))
		{
			ps.setInt(1, id);	
			return ps.executeUpdate()>0;
	
		}
	}
	
}
