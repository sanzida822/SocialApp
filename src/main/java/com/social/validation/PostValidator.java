package com.social.validation;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import com.social.dto.ImageDto;
import com.social.dto.PostDto;
import com.social.util.CommonUtil;
import com.social.util.Constants;
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

		if (commonUtil.isNullOrEmpty(postDto.getContent()) && commonUtil.isNullOrEmpty(postDto.getImages())) {
			errorMessages.put("post", MessageUtil.getMessage("error.post.empty"));

		} else {
			if (postDto.getContent().length() > Constants.MAX_CONTENT_LENGTH) {
				errorMessages.put("content", MessageUtil.getMessage("error.content.length"));
			}
			for(ImageDto image:postDto.getImages()) {
				if(!commonUtil.isValidImageType(image.getContentType())) {
					errorMessages.put("imageType", MessageUtil.getMessage("error.image.type"));					
				}
		        if (image.getSize() > Constants.MAX_IMAGE_SIZE) {
		            errorMessages.put("imageSize", MessageUtil.getMessage("error.image.size.exceed"));
		        }
			}


		

		}

		return errorMessages;

	}

}
