package com.social.controller;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.social.dto.PostDto;
import com.social.dto.UserDto;
import com.social.enums.Privacy;
import com.social.service.PostService;
import com.social.service.UserService;
import com.social.util.CommonUtil;
import com.social.validation.PostValidator;

@WebServlet(urlPatterns = { "/add/post" })
@MultipartConfig
public class PostServlet extends HttpServlet {
	private static final Logger logger = LoggerFactory.getLogger(PostServlet.class);
	private static final long serialVersionUID = 1L;
	private static CommonUtil commonUtil = CommonUtil.getInstance();
	private static UserService userService = UserService.getInstance();
	private static PostValidator postValidator = PostValidator.getInstance();
	private static PostService postService= PostService.getInstance();

	private static final String ADD_POST = "/add/post";
	//private static final String DELETE_POST = "/delete/post";
	private PostDto postDto;
	// PostService postservice=null;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public PostServlet() {
		super();
		// postservice=new PostService();

	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String servletPath = request.getServletPath();
		
	
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String servletPath = request.getServletPath();
		try {
			switch (servletPath) {
			case ADD_POST:
				addPost(request, response);
				break;

			default:
				break;
			}

		} catch (Exception e) {
			logger.error("Exception occure when adding post:{}, e:", e.getMessage(), e);
		}
	}

	public void addPost(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String email = (String) request.getSession(false).getAttribute("email");
		logger.info("Request comes for inserting a post");
		List<byte[]> postImages = new ArrayList<>();
		Collection<Part> parts = request.getParts();
		for (Part part : parts) {
			if (part.getName().equals("images") && part.getSize()>0) {
				byte[] imageBytes = commonUtil.extractImageBytes(part);
				postImages.add(imageBytes);

			}
		}
		Privacy privacy = commonUtil.toEnum(request.getParameter("privacy"));
		UserDto user = userService.getUserByEmail(email);
		postDto = new PostDto(user.getId(), request.getParameter("post_content"), privacy, postImages);
		Map<String, String> errorMessages = postValidator.validate(postDto);
		logger.error("Error messages for creating post:{}",errorMessages);
		if(commonUtil.isEmpty(errorMessages)) {
			boolean isSaved=postService.save(postDto);
			if(isSaved) {
				logger.info("post is saved successfully:{}",postDto);
			}else {
				logger.error("failed to save post:{}",postDto);
			}
	
		}
		logger.info("post dto object:{}", postDto);


	}

}
