package com.social.controller;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.social.dto.LoginRequestDto;
import com.social.dto.RegistrationRequestDTO;
import com.social.dto.UserDto;
import com.social.service.AuthenticationService;
import com.social.util.CommonUtil;
import com.social.util.MessageUtil;
import com.social.validation.AuthenticationValidator;

@WebServlet(urlPatterns = { "/auth/register", "/auth/login", "/auth/logout" })
@MultipartConfig
public class AuthenticationServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final Logger logger = LoggerFactory.getLogger(AuthenticationServlet.class);
	private static AuthenticationValidator authenticationValidator = AuthenticationValidator.getInstance();
	private static AuthenticationService authenticationService = AuthenticationService.getInstance();
	private static CommonUtil commonUtil = CommonUtil.getInstance();
	public RegistrationRequestDTO registrationDto;
	private static final String REGISTRATION="/auth/register";
	private static final String LOGIN="/auth/login";
	private static final String LOGOUT="/auth/logout";

	public AuthenticationServlet() {
		super();
	}
	
	private static  Map<String, String> error_views = new HashMap<>();

	static {
		error_views.put(REGISTRATION, "/views/RegistraionForm.jsp");
		error_views.put(LOGIN, "/views/LoginForm.jsp");
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String servletPath = request.getServletPath();
		if (servletPath.equals(REGISTRATION)) {
			request.getRequestDispatcher("/views/RegistrationForm.jsp").forward(request, response);
		} else if (servletPath.equals(LOGIN)) {
			request.getRequestDispatcher("/views/LoginForm.jsp").forward(request, response);
		} else if (servletPath.equals(LOGOUT)) {
			ProcessLogout(request, response);
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String servletPath = request.getServletPath();
		try {
			switch (servletPath) {
			case REGISTRATION:
				processRegistration(request, response);
				break;
			case LOGIN:
				processLogin(request, response);
				break;				
			default:
				break;
			}
		}
		catch(Exception e) {
			logger.error("Exception occurred while processing request at {}, e:{}", servletPath, e);
			String view=error_views.getOrDefault(servletPath,"/views/ErrorPage.jsp");
	        request.setAttribute("globalError", MessageUtil.getMessage("error.global.unexpected"));
	        request.getRequestDispatcher(view).forward(request, response);
		}
	}

	public void processRegistration(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Part imagePart = request.getPart("image");	
		logger.info("Registration request for username:{}, Email:{}, File size: {}", request.getParameter("username"),
				request.getParameter("email"), imagePart.getSize());
		byte[] imageBytes = commonUtil.extractImageBytes(imagePart);
		registrationDto = new RegistrationRequestDTO(request.getParameter("username"), request.getParameter("email"),
				request.getParameter("password"), request.getParameter("confirm_password"), imageBytes,imagePart);
		Map<String, String> errorMessages = authenticationValidator.validateRegistration(registrationDto);
		logger.info("error messages for validation of user:{} and error messages is:{}", registrationDto.getEmail(),
				errorMessages);

		if (commonUtil.isEmpty(errorMessages)) {
			boolean isRegistered = authenticationService.register(registrationDto);
			if (isRegistered) {
				logger.info("Registered successfully for user: username:{},Email:{}", registrationDto.getUsername(),
						registrationDto.getEmail());
				response.sendRedirect(request.getContextPath() + LOGIN);
			}else {
			    logger.warn("Registration failed for user: username:{}, Email:{}", registrationDto.getUsername(), registrationDto.getEmail());
			    request.setAttribute("globalError", MessageUtil.getMessage("error.registration.fail"));
			    request.getRequestDispatcher("/views/RegistrationForm.jsp").forward(request, response);

			}				
		} else {
			request.setAttribute("errorMessages", errorMessages);
			request.getRequestDispatcher("/views/RegistrationForm.jsp").forward(request, response);

		}
	}

	public void processLogin(HttpServletRequest request, HttpServletResponse response) throws Exception {
		logger.info("Received login request for email:{}", request.getParameter("email"));
		LoginRequestDto loginDto = new LoginRequestDto(request.getParameter("email"), request.getParameter("password"));
		Map<String, String> errorMessages = authenticationValidator.validateLogin(loginDto);
		if (commonUtil.isEmpty(errorMessages)) {
				logger.info("User:{} is registered in this system", loginDto.getEmail());
				UserDto authenticUser = authenticationService.authenticate(loginDto.getEmail(), loginDto.getPassword());				
				if(authenticUser!=null) {
					logger.info("user is authenticated:{}",authenticUser);
					HttpSession session = request.getSession();	
					session.setAttribute("user", authenticUser);
					response.sendRedirect(request.getContextPath() + "/user/home");
				}else {
				    logger.warn("Login failed for user: Email:{}",loginDto.getEmail());
				    request.setAttribute("globalError", MessageUtil.getMessage("error.login.fail"));
				    request.getRequestDispatcher("/views/LoginForm.jsp").forward(request, response);
				}
		} else {
			request.setAttribute("errorMessages", errorMessages);
			logger.warn("error messages for login input validation:{}", errorMessages);
			request.getRequestDispatcher(LOGIN).forward(request, response);
		}
	}

	public void ProcessLogout(HttpServletRequest request, HttpServletResponse response) throws IOException {
		HttpSession session = request.getSession(false);
		String email = (String) session.getAttribute("email");
		logger.info("logout request comes in for user email:{}", email);
		if (session != null) {
			session.invalidate();
			logger.info("user:{} session invalidated", email);
		}
		response.sendRedirect(request.getContextPath() + LOGIN);
	}

}
