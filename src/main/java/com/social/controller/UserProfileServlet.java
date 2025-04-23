package com.social.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.LoggerFactory;

import com.social.model.User;
import com.social.service.AuthenticationService;
import com.social.service.UserService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Servlet implementation class UserProfileServlet
 */
@WebServlet("/UserProfileServlet")
public class UserProfileServlet extends HttpServlet {

	private static final Logger logger = LoggerFactory.getLogger(AuthenticationServlet.class);
	private static final long serialVersionUID = 1L;
	UserService userservice = null;
	User user = null;

	public UserProfileServlet() {
		super();

	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Integer UserId = (Integer) request.getSession().getAttribute("id");
		logger.info("userprofile servlet called");
		if (UserId != null) {
			logger.info("User id:{} ", UserId);
			userservice = new UserService();
			user = userservice.FindUserById(UserId);
			if (user != null) {
				logger.info("user is:{}", user);
				request.setAttribute("user", user);
				request.getRequestDispatcher("/views/userProfile.jsp").forward(request, response);
			} else {
				logger.error("user not found");
				response.sendRedirect(request.getContextPath() + "/views/error.jsp");
			}

		} else {
			response.sendRedirect(request.getContextPath() + "/views/login.jsp");

		}

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
