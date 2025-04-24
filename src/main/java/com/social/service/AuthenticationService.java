package com.social.service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.social.dao.UserDao;
import com.social.dto.RegistrationRequestDTO;
import com.social.dto.UserDto;
import com.social.mapper.UserMapper;
import com.social.model.User;
import com.social.util.PasswordUtil;


public class AuthenticationService {
	private static final Logger logger = LoggerFactory.getLogger(AuthenticationService.class);
	private static AuthenticationService authenticationService;
	private static UserDao userDao=UserDao.getInstance();	
	private static UserMapper userMapper=UserMapper.getInstance();
	
	private AuthenticationService() {}
	public static AuthenticationService getInstance() {  
		if(authenticationService==null) {
			authenticationService=new AuthenticationService();
		}
		return authenticationService;
	}	
	
	public UserDto getUserByEmail(String email) throws Exception {
		User user = userDao.findByEmail(email);
		if (user != null) {
			logger.warn("User found for email: {}", email);
			return userMapper.toDTO(user);
		}
		logger.info("No user found for email: {}", email);
		return null;
	}
	
	public boolean register(RegistrationRequestDTO registrationDto) throws Exception {
		User user = userMapper.toEntity(registrationDto);
		return userDao.save(user);
	}
	
	public boolean authenticate(String email,String inputPassword) throws Exception {
		User user=userDao.findByEmail(email);
			String storedHashPassword = user.getPassword();
			String storedSalt = user.getSalt();
			String inputHashPassword = PasswordUtil.hashPassword(inputPassword, storedSalt);
			if (storedHashPassword.equals(inputHashPassword)) {
				logger.info("Authentication successful for user: {}", user.getEmail());
				return true;
			}else {
				logger.error("Incorrect Password for user:{}", email);
				return false;			
			} 		
	}
	
	public User getUserById(int id) throws Exception {	
		return userDao.findById(id);
	}
}
