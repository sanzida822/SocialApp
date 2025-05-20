package com.social.mapper;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.social.dto.LoginRequestDto;
import com.social.dto.RegistrationRequestDto;
import com.social.dto.UserDto;
import com.social.model.Image;
import com.social.model.User;
import com.social.service.AuthenticationService;
import com.social.service.ImageService;
import com.social.util.PasswordUtil;

public class UserMapper {
	private static UserMapper userMapper;
	private static ImageService imageService = ImageService.getInstance();
	private static ImageMapper imageMapper = ImageMapper.getInstance();
	private static final Logger logger = LoggerFactory.getLogger(UserMapper.class);

	private UserMapper() {
	}

	public static UserMapper getInstance() {
		if (userMapper == null) {
			userMapper = new UserMapper();
		}
		return userMapper;
	}

	public User toEntity(RegistrationRequestDto registrationDto, Image profileImage)
			throws NoSuchAlgorithmException, InvalidKeySpecException {
		String salt = PasswordUtil.generateSalt();
		String InputHashedPassword = PasswordUtil.hashPassword(registrationDto.getPassword(), PasswordUtil.generateSalt());
		User user = new User(registrationDto.getUsername(), registrationDto.getEmail(), InputHashedPassword, salt,
				profileImage);
		return user;
	}


//    public User toEntity(ResultSet rs) throws SQLException {
//        return new User(
//            rs.getInt("user_id"),                     
//            rs.getString("user_name"),                 
//            rs.getString("user_email"),                
//            rs.getString("password"),                
//            rs.getString("salt"),                       
//            new Image(
//                rs.getInt("image_id"),                 
//                rs.getBytes("image_data"),              
//                rs.getLong("image_size"),               
//                rs.getTimestamp("image_created_at"),   
//                rs.getTimestamp("image_updated_at")    
//            ),
//            rs.getTimestamp("user_created_at"),          
//            rs.getTimestamp("user_updated_at")          
//        );
//    }
    
    public User toEntity(ResultSet rs) throws SQLException, Exception {
    	Image profileImage=imageService.findById(rs.getInt("image_id"));
    	return new User(rs.getInt("id"),
    		rs.getString("user_name"),
    		rs.getString("user_email"),
    		rs.getString("password"),
    		rs.getString("salt"),
    		profileImage,
    		rs.getTimestamp("created_at"),
    		rs.getTimestamp("updated_at")
    		);
    }

	public UserDto toDto(User user) {
		return new UserDto(user.getId(), user.getUsername(), user.getEmail(), user.getProfileImage().getId(),
				user.getCreatedAt(), user.getUpdatedAt());
	}

}
