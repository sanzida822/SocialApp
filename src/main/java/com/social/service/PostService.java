package com.social.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.social.dao.PostDao;
import com.social.model.PostModel;

public class PostService {
	private static final Logger logger = LoggerFactory.getLogger(AuthenticationService.class);
	PostDao postdao=null;
	PostModel postmodel=null;
	
	public PostService() {
		postmodel=new PostModel();
		
	}
	
	
	public PostModel savePost(String privacy, String post_content,int posted_by) {
		
		return postmodel;
		
	}
	

}
