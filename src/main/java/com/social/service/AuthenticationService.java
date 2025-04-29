package com.social.service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.social.dao.UserDao;
import com.social.dto.RegistrationRequestDTO;
import com.social.dto.UserDto;
import com.social.mapper.UserMapper;
import com.social.model.Image;
import com.social.model.User;
import com.social.util.PasswordUtil;


public class AuthenticationService {
	private static final Logger logger = LoggerFactory.getLogger(AuthenticationService.class);
	private static AuthenticationService authenticationService;
	private static UserDao userDao=UserDao.getInstance();	
	private static UserMapper userMapper=UserMapper.getInstance();
	private static ImageService imageService=ImageService.getInstance();
	
	private AuthenticationService() {}
	public static AuthenticationService getInstance() {  
		if(authenticationService==null) {
			authenticationService=new AuthenticationService();
		}
		return authenticationService;
	}	
	
	public boolean register(RegistrationRequestDTO registrationDto) throws Exception {
		int imageId=imageService.saveAndgetId(registrationDto.getProfileImage());
		Image image=imageService.getImageById(imageId);
		logger.info("image id for profile image:{}, and the image object:{}",imageId,image);
		User user = userMapper.toEntity(registrationDto,image);
		logger.info("User for registration:{}",user);
		return userDao.save(user);
	}
	
	public UserDto authenticate(String email,String inputPassword) throws Exception {
		User user=userDao.findByEmail(email);
			String storedHashPassword = user.getPassword();
			String storedSalt = user.getSalt();
			String inputHashPassword = PasswordUtil.hashPassword(inputPassword, storedSalt);
			if (storedHashPassword.equals(inputHashPassword)){
				logger.info("Authentication successful for user: {}", user.getEmail());
				return userMapper.toDTO(user);
			}else {
				logger.error("Incorrect Password for user:{}", email);		
				return null;
			}				
	}

}
