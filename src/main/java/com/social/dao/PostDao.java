package com.social.dao;

import java.lang.System.LoggerFinder;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.social.dto.PostDto;
import com.social.mapper.PostMapper;
import com.social.model.Post;
import com.social.config.DBConnection;

public class PostDao {
	private static final Logger logger=LoggerFactory.getLogger(PostDao.class);
	private static PostDao postDao;
	private static PostMapper postMaper=PostMapper.getInstance();
	private PostDao() {};
	
	public static PostDao getInstance() {
		if(postDao==null) {
			postDao=new PostDao();
		}
		return postDao;		
	}
	
	
	public int saveAndGetID(Post post) throws Exception {
		String sql="Insert into posts (user_id,content,privacy) values (?,?,?)";
		try (Connection connection = DBConnection.getInstance().getConnection();
				PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS))
		{
			ps.setInt(1, post.getPostedBy().getId());
			ps.setString(2,post.getContent());
			ps.setString(3, post.getPrivacy().name());
			int rowsAffected = ps.executeUpdate();
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
		String sql="delete * from posts where id=?";
		try (Connection connection = DBConnection.getInstance().getConnection();
				PreparedStatement ps = connection.prepareStatement(sql))
		{
			ps.setInt(1, id);	
			return ps.executeUpdate()>0;
	
		}
	}
	
	
	
	

}
