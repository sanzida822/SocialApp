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

@WebServlet(urlPatterns = { "/registration", "/login", "/logout" })
@MultipartConfig
public class AuthenticationServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private static final Logger logger = LoggerFactory.getLogger(AuthenticationServlet.class);
	public AuthenticationService authService;

	public AuthenticationServlet() {
		super();
		authService = new AuthenticationService();
		// TODO Auto-generated constructor stub
	}

	// request.getRequestDistpatcher -->to SocialApp
	// requrst.getContextPath--> returns SocialApp(not full url)

//Example output: http://localhost:8080/SocialApp

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

//		String fullUrl = request.getScheme() + "://" +
//	            request.getServerName() + ":" +
//	            request.getServerPort() +
//	            request.getContextPath();
//
//	System.out.println(fullUrl);
		String servletPath = request.getServletPath();
		if (servletPath.equals("/registration")) {
			request.getRequestDispatcher("/views/registration.jsp").forward(request, response);
		} else if (servletPath.equals("/login")) {
			request.getRequestDispatcher("/views/login.jsp").forward(request, response);
		} else if (servletPath.equals("/logout")) {
			ProcessLogout(request, response);
		//	response.sendRedirect(request.getContextPath() + "/views/login.jsp");
		}

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String servletPath = request.getServletPath();

		switch (servletPath) {
		case "/registration":
			processRegistration(request, response);

			break;
		case "/login":
			processLogin(request, response);
			break;
		case "/logout":
			ProcessLogout(request, response);
			break;

		default:
			break;
		}

//		String imagePath = saveImageToDisk(imagePart);

//		reg.setUname(uname);
//		reg.setEmail(email);
//		reg.setPassword(password);
//		reg.setCpassword(cpassword);
//		reg.setImage(imagePath);

//		
//		System.out.println(uname);
//		System.out.println(email);
//		System.out.println(password);
//		System.out.println(cpassword);
//		System.out.println(imagePath);

//		RegistrationDao Dao = new RegistrationDao();
//	
//
//
//		if (saveUser) {
//			response.sendRedirect(request.getContextPath() + "/views/login.jsp");
//
//		} else {
//			request.setAttribute("error", "Registration failed");
//			request.getRequestDispatcher("views/registration.jsp").forward(request, response);
//
//		}

		// doGet(request, response);

	}

	public void processRegistration(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		// RegistrationModel reg = new RegistrationModel();

		String uname = request.getParameter("uname");
		String email = request.getParameter("email");
		String password = request.getParameter("password");
		String cpassword = request.getParameter("cpassword");
		Part imagePart = request.getPart("image");

		// String imageError = AuthenticationValidation.validateImage(imagePart);
		logger.info("File name: {}, Content type: {}:, \"File size: {}", imagePart.getSubmittedFileName(),
				imagePart.getContentType(), imagePart.getSize());

		String result = authService.validateAndSaveUser(uname, email, password, cpassword, imagePart);
		if (result != null) {
			logger.info("failed");
			request.setAttribute("error", result);
			request.getRequestDispatcher("/views/registration.jsp").forward(request, response);
			return;

		} else {
			response.sendRedirect(request.getContextPath() + "/views/login.jsp");

		}

	}

	public void processLogin(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {

		String email = request.getParameter("email");
		String password = request.getParameter("password");
		System.out.println("email is" + email);

		logger.info("received login request");
		String path = getServletContext().getRealPath("/");
		logger.info(path);
		logger.info("email:{}, password:{} from servlet", email, password);
		LoginModel loginUser = authService.AuthenticUser(email, password);

		if (loginUser != null) {
			logger.info("user logged in");
			HttpSession session = request.getSession();
			session.setAttribute("id", loginUser.getId());
			session.setAttribute("uname", loginUser.getUname());
			System.out.println(loginUser);
			session.setAttribute("email", loginUser.getEmail());

			logger.info("User logged in: username={}, email={}", loginUser.getUname(), loginUser.getEmail());
			response.sendRedirect(request.getContextPath() + "/UserProfileServlet");
		//	response.sendRedirect(request.getContextPath() + "/views/NewFile.jsp");
		} else {
			request.setAttribute("error", "Login Attempt failed");
			request.getRequestDispatcher("/views/login.jsp").forward(request, response);

		}
	}

	public void ProcessLogout(HttpServletRequest request, HttpServletResponse response) throws IOException {

		logger.info("logout request comes in...");
		HttpSession session = request.getSession(false);
		if (session != null) {
			session.invalidate();
			logger.info("session invalidate");
		}
		response.sendRedirect(request.getContextPath() + "/views/login.jsp");
	}


}
