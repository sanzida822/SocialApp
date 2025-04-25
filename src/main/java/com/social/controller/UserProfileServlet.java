package com.social.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.LoggerFactory;

import com.social.dto.UserDto;
import com.social.model.User;
import com.social.service.AuthenticationService;
import com.social.service.UserService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Servlet implementation class UserProfileServlet
 */
@WebServlet("/user/profile")
public class UserProfileServlet extends HttpServlet {

	private static final Logger logger = LoggerFactory.getLogger(AuthenticationServlet.class);
	private static final long serialVersionUID = 1L;
	private static AuthenticationService authenticationService=AuthenticationService.getInstance();
	User user = null;

	public UserProfileServlet() {
		super();

	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		String email = (String) request.getSession().getAttribute("email");

		try {
		UserDto	user = authenticationService.getUserByEmail(email);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

			logger.info("User email:{} ", email);

				request.setAttribute("user", user);
				request.getRequestDispatcher("/views/UserProfile.jsp").forward(request, response);


	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

	public void addPost(HttpServletRequest request, HttpServletResponse response) {
		
		
	}
}
