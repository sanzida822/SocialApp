package com.social.validation;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.social.constants.Constants;
import com.social.dto.PostDto;
import com.social.util.CommonUtil;

import com.social.util.MessageUtil;

public class PostValidator {
	private static PostValidator postValidator;
	private static CommonUtil commonUtil = CommonUtil.getInstance();

	private PostValidator() {
	}

	public static PostValidator getInstance() {
		if (postValidator == null) {
			postValidator = new PostValidator();
		}
		return postValidator;
	}

	public Map<String, String> validate(PostDto postDto) {
		LinkedHashMap<String, String> errorMessages = new LinkedHashMap<String, String>();
		List<byte[]> images = postDto.getImages();

		if (commonUtil.isNullOrEmpty(postDto.getContent()) && commonUtil.isNullOrEmpty(images)) {
			errorMessages.put("post", MessageUtil.getMessage("error.post.empty"));

		} else {
			if (postDto.getContent().length() > Constants.MAX_CONTENT_LENGTH) {
				errorMessages.put("content", MessageUtil.getMessage("error.content.length"));
			}

			if (!images.isEmpty()) {
				for (byte[] image : images) {
					if (!commonUtil.isImageSizeValid(image)){
						errorMessages.put("image", MessageUtil.getMessage("error.image.size"));
						break;
					}

				}
			}

		}

		return errorMessages;

	}

}
