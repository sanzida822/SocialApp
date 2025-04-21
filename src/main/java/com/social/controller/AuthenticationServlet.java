package com.social.controller;

import java.io.File;
import java.io.IOException;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

import com.social.dto.LoginRequestDto;
import com.social.dto.RegistrationRequestDTO;
import com.social.model.LoginModel;
import com.social.service.AuthenticationService;
import com.social.util.CommonUtil;
import com.social.validation.AuthenticationValidation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@WebServlet(urlPatterns = { "/auth/register", "/auth/login", "/auth/logout" })
@MultipartConfig
public class AuthenticationServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final Logger logger = LoggerFactory.getLogger(AuthenticationServlet.class);
	public AuthenticationService authService;
	public RegistrationRequestDTO registrationDto;

	public AuthenticationServlet() {
		super();
		authService = new AuthenticationService();
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
			processRegistration(request, response);
			break;

		case "/auth/login":
			processLogin(request, response);
			break;

		default:
			break;
		}
	}

	public void processRegistration(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		try {
			String username = request.getParameter("username");
			String email = request.getParameter("email");
			String password = request.getParameter("password");
			String confirm_password = request.getParameter("confirm_password");
			Part imagePart = request.getPart("image"); 
			//String imagePath=AuthenticationService.saveImageToDisk(imagePart);
			logger.info("Registration request for username:{},Email:{}, File name: {}, Content type: {}:, \"File size: {}",username,email,  imagePart.getSubmittedFileName(),
					imagePart.getContentType(), imagePart.getSize());
			
			registrationDto=new RegistrationRequestDTO();
			registrationDto.setUsername(username);
			registrationDto.setEmail(email);
			registrationDto.setPassword(password);
			registrationDto.setConfirm_password(confirm_password);
			registrationDto.setImage(imagePart);
			//registrationDto.setImage(imagePath);
			
			AuthenticationValidation authValidator=new AuthenticationValidation();
			
		    Map<String, String> errorMessages= authValidator.AuthenticationValidator(registrationDto);
			if(CommonUtil.isMapEmpty(errorMessages)) {
				boolean isRegister=authService.registerUser(registrationDto);
				if(isRegister) {
					logger.info("Registered successfully for user: username:{},Email:{}",username,email);
					response.sendRedirect(request.getContextPath() + "/views/login_form.jsp");	
					
				}

				
			}
			else {
				request.setAttribute("errorMessages", errorMessages);
				request.getRequestDispatcher("/views/registration_form.jsp").forward(request, response);
			}	
		}
		catch(Exception e) {
			logger.error("An unexpected error occurred during registration for user:{}, error message:{}, error{} ", registrationDto, e.getMessage(),e);
		}


	}

	public void processLogin(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		String email = request.getParameter("email");
		String password = request.getParameter("password");
		logger.info("Received login request for email:{}", email);

		LoginRequestDto loginDto = new LoginRequestDto();
		loginDto.setEmail(email);
		loginDto.setPassword(password);
		LoginModel loginUser = authService.AuthenticUser(loginDto);
		logger.info("login user data:{}", loginUser);

		if (loginUser != null) {
			HttpSession session = request.getSession();
			session.setAttribute("id", loginUser.getId());
			session.setAttribute("username", loginUser.getUsername());
			session.setAttribute("email", loginUser.getEmail());
			logger.info("User logged in: username={}, email={}", loginUser.getUsername(), loginUser.getEmail());
			response.sendRedirect(request.getContextPath() + "/user/home");

		} else {
			request.setAttribute("error", "Login Attempt failed");
			logger.error("Login attemp failed for user:{}", email);
			request.getRequestDispatcher("/auth/login_form.jsp").forward(request, response);
		}
	}

	public void ProcessLogout(HttpServletRequest request, HttpServletResponse response) throws IOException {
		HttpSession session = request.getSession(false);
		String username = (String) session.getAttribute("username");
		String email = (String) session.getAttribute("email");
		logger.info("logout request comes in for user:{}, email:{}", username, email);
		if (session != null) {
			session.invalidate();
			logger.info("user:{} session invalidated", username);
		}
		response.sendRedirect(request.getContextPath() + "/auth/login");
	}

}
