package com.social.service;

import java.sql.Connection;
import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.social.dao.ImageDao;
import com.social.dto.ImageDto;
import com.social.exception.ImageNotFoundException;
import com.social.mapper.ImageMapper;
import com.social.model.Image;
import com.social.util.MessageUtil;

public class ImageService {
	private static final Logger logger = LoggerFactory.getLogger(ImageService.class);
	private static ImageService imageService;
	private static ImageDao imageDao= ImageDao.getInstance();
	private static ImageMapper imageMapper= ImageMapper.getInstance();
	
	public static ImageService getInstance() {
		if(imageService==null) {
			imageService=new ImageService();
		}
		return imageService;
	}
	
	public Image save(ImageDto imageDto, Connection connection) throws SQLException, Exception {
		Image image=imageMapper.toEntity(imageDto);
		logger.debug("image is not save:{}", imageDao.save(image, connection));
		return imageDao.save(image,connection);
	}
	
	public boolean deleteImagebyId(int id) throws SQLException, Exception {
		return imageDao.deleteById(id);
	}
	
	public Image findById(int id) throws SQLException, Exception {
		Image image=imageDao.findById(id);
		if(image==null) {
		    throw new ImageNotFoundException(MessageUtil.getMessage("error.image.found"));
		}
		return image;
	}

}
