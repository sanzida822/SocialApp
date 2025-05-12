package com.social.service;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.social.dao.ImageDao;
import com.social.dao.PostDao;
import com.social.dao.PostImageDao;
import com.social.dto.ImageDto;
import com.social.dto.PostDto;
import com.social.exception.CustomException.ImageInsertionFailedException;
import com.social.exception.CustomException.PostImageInsertionFailedException;
import com.social.mapper.ImageMapper;
import com.social.mapper.PostMapper;
import com.social.model.Image;
import com.social.model.Post;
import com.social.util.CommonUtil;
import com.social.util.MessageUtil;

public class PostService {
	public static PostService postService;
	private static final Logger logger = LoggerFactory.getLogger(PostService.class);
	private static PostMapper postMapper = PostMapper.getInstance();
	private static PostDao postDao = PostDao.getInstance();
	private static ImageService imageService = ImageService.getInstance();
	private static CommonUtil commonUtil = CommonUtil.getInstance();
	private static PostImageDao postImageDao = PostImageDao.getInstance();
	private static ImageDao imageDao = ImageDao.getInstance();
	private static ImageMapper imageMapper = ImageMapper.getInstance();

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
		if (!commonUtil.isNullOrEmpty(postDto.getImages())) {
			try {
				for (ImageDto imageDto : postDto.getImages()) {
					int imageId = imageService.saveAndgetId(imageDto);
					if (imageId < 1) {
						throw new ImageInsertionFailedException(MessageUtil.getMessage("error.image.insert"));
					}
					savedImagesId.add(imageId);

					boolean savedPostAndImage = postImageDao.save(postId, imageId);
					if (!savedPostAndImage) {
						throw new PostImageInsertionFailedException(MessageUtil.getMessage("error.save.postImage"));
					}
				}
				return true;
			} catch (Exception e) {
				logger.error("Failure saving images or post with image id, rolling back manually");
				postDao.deleteById(postId);
				for (int imageId : savedImagesId) {
					imageService.deleteImagebyId(imageId);
				}
				return false;
			}
		}
		return true;
	}

	public List<PostDto> getPostDtosWithImages(int loggedInUserId) throws Exception {
		List<Post> visiblePosts = postDao.getVisiblePostForUser(loggedInUserId);
		List<PostDto> postDtos = new ArrayList<>();
		for (Post post : visiblePosts) {
			List<Image> images = imageDao.getImagesByPostId(post.getId());
			List<ImageDto> imageDtos = new ArrayList<>();
			for (Image image : images) {
				ImageDto imageDto = imageMapper.toDto(image);
				imageDtos.add(imageDto);

			}
			PostDto postDto = postMapper.toDto(post, imageDtos);
			postDtos.add(postDto);
			logger.info("postDtos are :{}", postDto);
		}
		return postDtos;

	}

}
