package com.social.mapper;
import org.slf4j.LoggerFactory;

import com.social.dto.PostDto;
import com.social.enums.Privacy;
import com.social.model.Post;
import com.social.model.User;
import com.social.service.AuthenticationService;
import com.social.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PostMapper {
	private static final Logger logger = LoggerFactory.getLogger(PostMapper.class);
	private static PostMapper postMapper;
	private static UserService userService=UserService.getInstance();
	private PostMapper() {}

	public static PostMapper getInstance() {
		if (postMapper == null) {
			postMapper = new PostMapper();
		}
		return postMapper;
	}
	
	public Post toEntity(PostDto postDto) throws Exception {
		User user=userService.getUserById(postDto.getPostedBy());
		Post post= new Post(user,postDto.getContent(), postDto.getPrivacy());
		return post;
	}

}
