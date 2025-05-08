package com.social.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.social.mapper.ImageMapper;
import com.social.model.Image;
import com.social.config.DBConnection;

public class ImageDao {
	private static ImageDao imageDao;
	private static ImageMapper imageMapper=ImageMapper.getInstance();
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
	
	public Image getImageById(int id) throws SQLException, Exception {
		String sql="select i.* from images i where id=?";
		try (Connection connection = DBConnection.getInstance().getConnection();
				PreparedStatement ps = connection.prepareStatement(sql))
		{
			ps.setInt(1, id);
			ResultSet rs=ps.executeQuery();
			if (rs.next()) {
				return imageMapper.toEntity(rs);
			}			
		}		
		return null;
	}
	
	List<Image> getImagesByPostId(int postId) throws ClassNotFoundException, SQLException{
		List<Image> imageList = new ArrayList<>();
		String sql="select * from images where id in(select image_id from post_images where post_id=?)";
		try (Connection connection = DBConnection.getInstance().getConnection();
				PreparedStatement ps = connection.prepareStatement(sql))
		{
			ps.setInt(1, postId);
			ResultSet rs=ps.executeQuery();
			  while (rs.next()) {
				  Image image=ImageMapper.getInstance().toEntity(rs);
				  imageList.add(image);				  
			  }			
		}		
		return imageList;		
	}
}
