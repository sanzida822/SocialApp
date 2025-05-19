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
import com.social.dto.ImageDto;
import com.social.dto.LoginRequestDto;
import com.social.dto.RegistrationRequestDto;
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
	public static final String REGISTRATION_PATH = "/auth/register";
	public static final String LOGIN_PATH = "/auth/login";
	public static final String LOGOUT_PATH = "/auth/logout";

	public AuthenticationServlet() {
		super();
	}

	private static Map<String, String> errorViews = new HashMap<>();

	static {
		errorViews.put(REGISTRATION_PATH, "/views/RegistraionForm.jsp");
		errorViews.put(LOGIN_PATH, "/views/LoginForm.jsp");
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String servletPath = request.getServletPath();
		if (servletPath.equals(REGISTRATION_PATH)) {
			request.getRequestDispatcher("/views/RegistrationForm.jsp").forward(request, response);
		} else if (servletPath.equals(LOGIN_PATH)) {
			request.getRequestDispatcher("/views/LoginForm.jsp").forward(request, response);
		} else if (servletPath.equals(LOGOUT_PATH)) {
			ProcessLogout(request, response);
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String servletPath = request.getServletPath();
		try {
			switch (servletPath) {
			case REGISTRATION_PATH:
				processRegistration(request, response);
				break;
			case LOGIN_PATH:
				processLogin(request, response);
				break;
			default:
				break;
			}
		} catch (Exception e) {
			logger.error("Exception occurred while processing request at {},{}", servletPath,e);
			String Errorview = errorViews.getOrDefault(servletPath, "/views/ErrorPage.jsp");
			request.setAttribute("globalError", MessageUtil.getMessage("error.global.internal"));
			request.getRequestDispatcher(Errorview).forward(request, response);
		}
	}

	public void processRegistration(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Part imagePart = request.getPart("image");
		ImageDto image = null;
		logger.info("Registration request for username:{}, Email:{}, File size: {}", request.getParameter("username"),
				request.getParameter("email"), imagePart.getSize());
		if (imagePart != null && imagePart.getSize() > 0) {
			byte[] imageBytes = commonUtil.extractImageBytes(imagePart);
			image = new ImageDto(imageBytes, imagePart.getSize(), imagePart.getContentType());

		}

		RegistrationRequestDto registrationDto = new RegistrationRequestDto(request.getParameter("username"),
				request.getParameter("email"), request.getParameter("password"),
				request.getParameter("confirm_password"), image);
		Map<String, String> errorMessages = authenticationValidator.validateRegistration(registrationDto);
		logger.info("Error messages for validation of user:{} and error messages is:{}", registrationDto.getEmail(),
				errorMessages);

		if (commonUtil.isEmpty(errorMessages)) {
			boolean isRegistered = authenticationService.register(registrationDto);
			if (isRegistered) {
				logger.info("Registered successfully for user: username:{},Email:{}", registrationDto.getUsername(),
						registrationDto.getEmail());
				response.sendRedirect(request.getContextPath() + LOGIN_PATH);
			} else {
				logger.error("Registration failed for user: username:{}, Email:{}", registrationDto.getUsername(),
						registrationDto.getEmail());
				request.setAttribute("globalError", MessageUtil.getMessage("error.global.internal"));
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
			if (authenticUser != null) {
				logger.info("User is authenticated:{}", authenticUser);
				HttpSession session = request.getSession();
				session.setAttribute("user", authenticUser);
				response.sendRedirect(request.getContextPath() + "/user/home");
			} else {
				logger.warn("Login failed for user: Email:{}", loginDto.getEmail());
				request.setAttribute("globalError", MessageUtil.getMessage("error.global.internal"));
				request.getRequestDispatcher("/views/LoginForm.jsp").forward(request, response);
			}
		} else {
			request.setAttribute("errorMessages", errorMessages);
			logger.warn("Error messages for login input validation:{}", errorMessages);
			request.getRequestDispatcher("/views/LoginForm.jsp").forward(request, response);
		}
	}

	public void ProcessLogout(HttpServletRequest request, HttpServletResponse response) throws IOException {
		HttpSession session = request.getSession(false);
		String email = (String) session.getAttribute("email");
		logger.info("Logout request comes in for user email:{}", email);
		if (session != null) {
			session.invalidate();
			logger.info("User:{} session invalidated", email);
		}
		response.sendRedirect(request.getContextPath() + LOGIN_PATH);
	}
}
