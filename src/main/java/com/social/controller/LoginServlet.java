package com.social.controller;
import com.mysql.cj.log.Slf4JLogger;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.social.dao.LoginDao;
import com.social.model.LoginModel;

/**
 * Servlet implementation class LoginServlet
 */
@WebServlet("/login")
public class LoginServlet extends HttpServlet {
	private static final Logger logger= LoggerFactory.getLogger("LoginServlet.class");
	private static final long serialVersionUID = 1L;

  
    public LoginServlet() {
 
    }


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.getRequestDispatcher("/views/login.jsp").forward(request, response);
		

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	LoginModel model= new LoginModel();	
	  String email=request.getParameter("email");
	  String password=request.getParameter("password");
	  
	  model.setEmail(email);
	  model.setPassword(password);

	  
	 LoginDao dao=  LoginDao.getInstance();
	 boolean isLogin= dao.validateLogin(model);
	 logger.info("user logged in");
	 if(isLogin) {
		    System.out.println("login successfully");
			response.sendRedirect(request.getContextPath()+"/views/Home.jsp");
	 }else {
		 request.setAttribute("error", "Invalid User Name or Password");
		
		 request.getRequestDispatcher("/views/login.jsp").forward(request, response);
		 
	 }
	}

}
