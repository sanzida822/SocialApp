package com.social.controller;

import java.io.IOException;
import java.util.Collection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.social.model.PostModel;
import com.social.service.PostService;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;



/**
 * Servlet implementation class PostServlet
 */
@WebServlet("/PostServlet")
@MultipartConfig
public class PostServlet extends HttpServlet {
	private static final Logger logger = LoggerFactory.getLogger(AuthenticationServlet.class);
	private static final long serialVersionUID = 1L;
	PostService postservice=null;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public PostServlet() {
    	super();
    	postservice=new PostService();

    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		logger.info("enter post servlet post request..");
		
		Collection<Part> parts=request.getParts();
		for(Part part: parts) {
			if(part.getName().equals("images") && part.getSize()>0) {
				String imageName=part.getSubmittedFileName();
				logger.info(imageName);
				String uploadPath="images/"+imageName;
				logger.info(uploadPath);
				
				
				
			}
		}
		HttpSession session = request.getSession(false);
		int posted_by = (Integer) session.getAttribute("id"); 
		String privacy=request.getParameter("privacy");
		String post_content= request.getParameter("post_content");
		logger.info("privacy is:{},post content:{}, posted by id:{}" ,privacy, post_content, posted_by);
		
		postservice.savePost(privacy, post_content, posted_by);
		
		
		
		doGet(request, response);
	}

}
