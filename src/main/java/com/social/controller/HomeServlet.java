package com.social.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mysql.cj.Session;
import com.social.dto.UserDto;
import com.social.model.User;
import com.social.service.AuthenticationService;
import com.social.service.UserService;
import com.social.util.MessageUtil;

/**
 * Servlet implementation class HomeServlet
 */
@WebServlet("/user/home")
public class HomeServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final Logger logger = LoggerFactory.getLogger(HomeServlet.class);
	private static UserService userService=UserService.getInstance(); 
	private static final String HOME_PAGE="/user/home";
	
    public HomeServlet() {
        super();
    }
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String servletPath=request.getServletPath();
		HttpSession session=request.getSession(false);
		String email=(String)session.getAttribute("email");
		try {
			switch (servletPath) {
			case HOME_PAGE:
				logger.info("Request comes for home page: for user:{}",email);
				UserDto user=userService.getUserByEmail(email);
				logger.info("user data is:{}", user);
				request.setAttribute("user", user);
				request.getRequestDispatcher("/views/HomePage.jsp").forward(request, response);				
				break;
			default:
				break;
			}

		}catch(Exception e) {
			 e.printStackTrace();
			    request.setAttribute("globalError", MessageUtil.getMessage("error.global.unexpected"));
			    request.getRequestDispatcher("/views/ErrorPage.jsp").forward(request, response);
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
	
	
	public void  getUserById() {}

}
