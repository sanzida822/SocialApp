package com.social.mapper;
import org.slf4j.LoggerFactory;

import com.social.dto.ImageDto;
import com.social.dto.PostDto;
import com.social.enums.Privacy;
import com.social.model.Post;
import com.social.model.User;
import com.social.service.AuthenticationService;
import com.social.service.UserService;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

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
	public Post toEntity(ResultSet rs, User postedBy) throws SQLException {
		return new Post(rs.getInt("post_id"),postedBy, rs.getString("content"),Privacy.valueOf(rs.getString("privacy")), rs.getTimestamp("created_at"),rs.getTimestamp("updated_at"));
	}

	public PostDto toDto(Post post,List<ImageDto> imageDtos) {
		return new PostDto(post.getPostedBy().getId(),post.getPostedBy().getUsername(), post.getContent(),post.getPrivacy(),imageDtos,post.getCreatedAt(),post.getUpdatedAt());
		
	}
}
