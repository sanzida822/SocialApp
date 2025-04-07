package com.social.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.social.dao.LoginDao;
import com.social.model.LoginModel;

/**
 * Servlet implementation class LoginServlet
 */
@WebServlet("/login")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * Default constructor. 
     */
    public LoginServlet() {
        // TODO Auto-generated constructor stub
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
	  System.out.println(model);
	 LoginDao dao= new LoginDao();
	 boolean isLogin= dao.validateLogin(model);
	 System.out.println(isLogin);
	 if(isLogin) {
		    System.out.println("login successfully");
			response.sendRedirect(request.getContextPath()+"/views/Home.jsp");
	 }else {
		 request.setAttribute("error", "Invalid User Name or Password");
		
		 request.getRequestDispatcher("/views/login.jsp").forward(request, response);
		 
	 }
	}

}
