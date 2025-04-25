package com.social.dao;

import java.lang.System.LoggerFinder;
import java.sql.Connection;
import java.sql.PreparedStatement;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.social.model.Post;
import com.social.util.DBConnection;

public class PostDao {
	private static final Logger logger=LoggerFactory.getLogger(PostDao.class);
	private PostDao postDao;
	private PostDao() {};
	
	public PostDao getInstance() {
		if(postDao==null) {
			postDao=new PostDao();
		}
		return postDao;		
	}
	
	
	public void savePost(Post post) {
		
	
		
		
		
	}
	
	

}
