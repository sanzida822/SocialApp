package com.social.service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.social.dao.UserDao;
import com.social.dto.RegistrationRequestDTO;
import com.social.dto.UserDto;
import com.social.exception.CustomException;
import com.social.mapper.UserMapper;
import com.social.model.User;
import com.social.util.PasswordUtil;


public class AuthenticationService {
	private static final Logger logger = LoggerFactory.getLogger(AuthenticationService.class);
	private static AuthenticationService authenticationService;
	private UserDao userDao=UserDao.getInstance();	
	
	private AuthenticationService() {}
	public static AuthenticationService getInstance() {
		if(authenticationService==null) {
			authenticationService=new AuthenticationService();
		}
		return authenticationService;
	}	
	public RegistrationRequestDTO getUserByEmail(String email) throws Exception {
		User user = userDao.findByEmail(email);
		if (user != null) {
			logger.warn("Duplicate user found for email: {}", email);
			return UserMapper.toRegistrationRequestDTO(user);
		}
		logger.info("No user found for email: {}", email);
		return null;
	}
	public boolean register(RegistrationRequestDTO registrationDto) throws Exception {
		String salt = PasswordUtil.generateSalt();
		String hashedPassword = PasswordUtil.hashPassword(registrationDto.getPassword(), salt);
		User user = UserMapper.toEntity(registrationDto);
		user.setPassword(hashedPassword);
		user.setSalt(salt);
		return this.userDao.save(user);
	}
	public UserDto authenticate(User user,String inputPassword) throws Exception {
		String storedHashPassword = user.getPassword();
		String storedSalt = user.getSalt();
		String inputHashPassword = PasswordUtil.hashPassword(inputPassword, storedSalt);
		if (storedHashPassword.equals(inputHashPassword)) {
			logger.info("Authentication successful for user: {}", user.getEmail());
			UserDto authenticatedUser = new UserDto();
			authenticatedUser.setEmail(inputHashPassword);
			authenticatedUser.setUsername(inputHashPassword);
			return authenticatedUser;
		} else {
			logger.error("Incorrect password for email: {}", user.getEmail());
			throw new CustomException.AuthenticationPasswordException("Incorrect Password for email:"+user.getEmail());
		}
	}
	public User getUserById(int id) throws Exception {	
		return userDao.findById(id);
	}
}
