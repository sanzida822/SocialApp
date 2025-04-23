package com.social.controller;

import java.io.IOException;
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

import com.social.dao.UserDao;
import com.social.dto.LoginRequestDto;
import com.social.dto.RegistrationRequestDTO;
import com.social.dto.UserDto;
import com.social.exception.CustomException.AuthenticationPasswordException;
import com.social.model.User;
import com.social.service.AuthenticationService;
import com.social.util.CommonUtil;
import com.social.validation.AuthenticationValidator;

@WebServlet(urlPatterns = { "/auth/register", "/auth/login", "/auth/logout" })
@MultipartConfig
public class AuthenticationServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final Logger logger = LoggerFactory.getLogger(AuthenticationServlet.class);
	private AuthenticationValidator authenticationValidator = AuthenticationValidator.getInstance();
	private  AuthenticationService authenticationService = AuthenticationService.getInstance();
	private CommonUtil commonUtil = CommonUtil.getInstance();
	private UserDao userDao = UserDao.getInstance();
	public RegistrationRequestDTO registrationDto;

	public AuthenticationServlet() {
		super();

	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String servletPath = request.getServletPath();
		if (servletPath.equals("/auth/register")) {
			request.getRequestDispatcher("/views/registration_form.jsp").forward(request, response);
		} else if (servletPath.equals("/auth/login")) {
			request.getRequestDispatcher("/views/login_form.jsp").forward(request, response);
		} else if (servletPath.equals("/auth/logout")) {
			ProcessLogout(request, response);
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String servletPath = request.getServletPath();
		switch (servletPath) {
		case "/auth/register":
			try {
				processRegistration(request, response);
			} catch (Exception e) {
				logger.error("Exception occurred while processing user registration at /auth/register", e);
				request.setAttribute("globalError", "Something went wrong. Please try again.");
				logger.error("An unexpected error occurred during registration for user:{}, error message:{}, error{} ",
						registrationDto, e.getMessage(), e);
				request.getRequestDispatcher("/views/registration_form.jsp").forward(request, response);
			}
			break;
		case "/auth/login":
			try {
				processLogin(request, response);
			} catch (Exception e) {
				request.setAttribute("globalError", "Something went wrong. Please try again.");
				request.getRequestDispatcher("/views/login_form.jsp").forward(request, response);
			}
			break;
		default:
			break;
		}
	}

	public void processRegistration(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Part imagePart = request.getPart("image");
		logger.info("Registration request for username:{}, Email:{}, File size: {}", request.getParameter("username"), request.getParameter("email"),
				imagePart.getSize());
		byte[] imageBytes = commonUtil.extractImageBytes(imagePart);
		registrationDto = new RegistrationRequestDTO(request.getParameter("username"), request.getParameter("email"), request.getParameter("password"), request.getParameter("confirm_password"), imageBytes);
		Map<String, String> errorMessages = authenticationValidator.validateRegistration(registrationDto);
		logger.info("error messages for validation of user:{} and error messages is:{}", registrationDto.getEmail(),
				errorMessages);

		if (commonUtil.isEmpty(errorMessages)) {
			boolean isRegistered = authenticationService.register(registrationDto);
			if (isRegistered) {
				logger.info("Registered successfully for user: username:{},Email:{}", registrationDto.getUsername(), registrationDto.getEmail());
				response.sendRedirect(request.getContextPath() + "/views/login_form.jsp");
			}
		} else {
			request.setAttribute("errorMessages", errorMessages);
			request.getRequestDispatcher("/views/registration_form.jsp").forward(request, response);
		}
	}

	public void processLogin(HttpServletRequest request, HttpServletResponse response) throws Exception {
		logger.info("Received login request for email:{}", request.getParameter("email"));
		LoginRequestDto loginDto = new LoginRequestDto(request.getParameter("email"), request.getParameter("password"));
		Map<String, String> errorMessages = authenticationValidator.validateLogin(loginDto);

		if (commonUtil.isEmpty(errorMessages)) {
			User user = userDao.findByEmail(loginDto.getEmail());
			if (user != null) {
				try {
					logger.info("User:{} is registered in this system", user.getPassword());
					UserDto authenticUser = authenticationService.authenticate(user, loginDto.getPassword());
					logger.info("login user data:{}", authenticUser);
					HttpSession session = request.getSession();
					//session.setAttribute("email", email);
					response.sendRedirect(request.getContextPath() + "/user/home");
				} catch (AuthenticationPasswordException e) {
					request.setAttribute("globalError", e.getMessage());
					request.getRequestDispatcher("/views/login_form.jsp").forward(request, response);
				}

			} else {
				request.setAttribute("globalError", "User is not registered.");
				request.getRequestDispatcher("/views/login_form.jsp").forward(request, response);

			}
		} else {
			request.setAttribute("errorMessages", errorMessages);
			logger.warn("error messages for login input validation:{}", errorMessages);
			request.getRequestDispatcher("/views/login_form.jsp").forward(request, response);
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
		response.sendRedirect(request.getContextPath() + "/auth/login");
	}

}
