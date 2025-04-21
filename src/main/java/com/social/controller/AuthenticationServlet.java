package com.social.controller;

import java.io.File;
import java.io.IOException;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

import com.social.model.LoginModel;
import com.social.service.AuthenticationService;
import com.social.validation.AuthenticationValidation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@WebServlet(urlPatterns = { "/auth/register", "/auth/login", "/auth/logout" })
@MultipartConfig
public class AuthenticationServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final Logger logger = LoggerFactory.getLogger(AuthenticationServlet.class);
	public AuthenticationService authService;

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
			throws IOException, ServletException {
		String username = request.getParameter("username");
		String email = request.getParameter("email");
		String password = request.getParameter("password");
		String confirm_password = request.getParameter("confirm_password");
		Part imagePart = request.getPart("image"); 
		logger.info("Registration request for username:{},Email:{}, File name: {}, Content type: {}:, \"File size: {}",username,email,  imagePart.getSubmittedFileName(),
				imagePart.getContentType(), imagePart.getSize());
		String result = authService.validateAndSaveUser(username, email, password, confirm_password, imagePart);
		if (result != null) {
			logger.info("registration failed for user: username:{},Email:{}",username,email);
			request.setAttribute("error", result);
			request.getRequestDispatcher("/views/registration_form.jsp").forward(request, response);
			return;

		} else {
			response.sendRedirect(request.getContextPath() + "/views/login_form.jsp");

		}

	}


	public void processLogin(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		String email = request.getParameter("email");
		String password = request.getParameter("password");
		logger.info("Received login request for email:{}",email);
		LoginModel loginUser = authService.AuthenticUser(email, password);
		logger.info("login user:{}", loginUser);
        
		if (loginUser != null) {
			HttpSession session = request.getSession();
			session.setAttribute("id", loginUser.getId());
			session.setAttribute("username", loginUser.getUsername());
			session.setAttribute("email", loginUser.getEmail());
			logger.info("User logged in: username={}, email={}", loginUser.getUsername(), loginUser.getEmail());
			response.sendRedirect(request.getContextPath() + "/user/home");

		} else {
			request.setAttribute("error", "Login Attempt failed");
			logger.error("login attemp failed for user:{}",email);
			request.getRequestDispatcher("/auth/login_form.jsp").forward(request, response);
		}
	}

	public void ProcessLogout(HttpServletRequest request, HttpServletResponse response) throws IOException {
		HttpSession session = request.getSession(false);
		String username=(String)session.getAttribute("username");
		String email=(String)session.getAttribute("email");
		logger.info("logout request comes in for user:{}, email:{}",username,email);		
		if (session != null) {
			session.invalidate();
			logger.info("user:{} session invalidated", username);
		}
		response.sendRedirect(request.getContextPath() + "/auth/login");
	}


}
