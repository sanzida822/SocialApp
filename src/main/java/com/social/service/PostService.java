package com.social.service;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.social.dao.PostDao;
import com.social.dao.PostImageDao;
import com.social.dto.PostDto;
import com.social.mapper.PostMapper;
import com.social.model.Post;
import com.social.util.CommonUtil;

public class PostService {
	public static PostService postService;
	private static final Logger logger = LoggerFactory.getLogger(PostService.class);
	private static PostMapper postMapper = PostMapper.getInstance();
	private static PostDao postDao = PostDao.getInstance();
	private static ImageService imageService = ImageService.getInstance();
	private static CommonUtil commonUtil = CommonUtil.getInstance();
	private static PostImageDao postImageDao = PostImageDao.getInstance();

	private PostService() {
	}

	public static PostService getInstance() {
		if (postService == null) {
			return postService = new PostService();
		}
		return postService;
	}

	public boolean save(PostDto postDto) throws Exception {
		Post post = postMapper.toEntity(postDto);
		int postId = postDao.saveAndGetID(post);
		if (postId < 1) {
			logger.warn("Error while saving post in database");
			return false;
		}
		List<Integer> savedImagesId = new ArrayList<Integer>();
		if (!commonUtil.isEmpty(postDto.getImages())) {
			try {
				for (byte[] imageByte : postDto.getImages()) {
					int imageId = imageService.saveAndgetId(imageByte);
					if (imageId < 1) {
						throw new RuntimeException("Image insert failed");
					}
					savedImagesId.add(imageId);

					boolean savedPostAndImage = postImageDao.save(postId, imageId);
					if (!savedPostAndImage) {
						throw new RuntimeException("Failed to save Post-Image");
					}
				}
				return true;
			} catch (Exception e) {
				logger.error("Failure saving images or post with image id, rolling back manually");
				postDao.deleteById(postId);
				for (int imgId : savedImagesId) {
					imageService.deleteImagebyId(imgId);
				}
				return false;

			}
		}
		return true;   
		
	}
	

}
