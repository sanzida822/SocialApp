package com.social.dao;

import java.lang.System.LoggerFinder;
import java.sql.Connection;
import java.sql.PreparedStatement;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.social.model.PostModel;
import com.social.util.DBConnection;

public class PostDao {
	private static final Logger logger=LoggerFactory.getLogger(PostDao.class);
	private PostModel postModel;
	public PostDao(PostModel postModel) {
		
		this.postModel=new PostModel();
		
	}
	
	public void savePost(PostModel post) {
		
		String sql= "insert into posts (posted_by, post_content, privacy) values(?,?,?)";
		
		
		
		try {
			
			DBConnection DbConnection= DBConnection.getInstance();
			Connection connection=DbConnection.getConnection();
			PreparedStatement ps= connection.prepareStatement(null);
			ps.setInt(1, post.getPosted_by());
			ps.setString(2, post.getContent());
			ps.setString(3, post.getPrivacy().name());
			ps.executeUpdate();
			
			
			
		}catch(Exception e) {
			logger.error("error while saving posts");
			
			
		}
		
		
		
	}
	
	

}
