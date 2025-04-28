package com.social.service;

import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.social.dao.ImageDao;
import com.social.mapper.ImageMapper;
import com.social.model.Image;

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
	
	public int saveAndgetId(byte[] imageBytes) throws SQLException, Exception {
		Image image=imageMapper.toEntity(imageBytes);
		return imageDao.saveAndReturnId(image);
	}
	
	public boolean deleteImagebyId(int id) throws SQLException, Exception {
		return imageDao.deleteById(id);
	}

}
