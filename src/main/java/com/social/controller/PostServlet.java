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
import com.social.dto.ImageDto;
import com.social.dto.PostDto;
import com.social.dto.UserDto;
import com.social.enums.Privacy;
import com.social.service.PostService;
import com.social.service.UserService;
import com.social.util.CommonUtil;
import com.social.util.MessageUtil;
import com.social.validation.PostValidator;

@WebServlet(urlPatterns = { "/add/post" })
@MultipartConfig
public class PostServlet extends HttpServlet {
	private static final Logger logger = LoggerFactory.getLogger(PostServlet.class);
	private static final long serialVersionUID = 1L;
	private static UserService userService = UserService.getInstance();
	private static PostValidator postValidator = PostValidator.getInstance();
	private static PostService postService = PostService.getInstance();
	public static final String ADD_POST = "/add/post";

	public PostServlet() {
		super();
		// postservice=new PostService();

	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
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
			logger.error("Exception occurs when adding post:{}, e:", e.getMessage(), e);
	        request.setAttribute("globalError", MessageUtil.getMessage("error.global.unexpected"));
	        request.getRequestDispatcher("/").forward(request, response);
		}
	}

	public void addPost(HttpServletRequest request, HttpServletResponse response) throws Exception {
		UserDto loggedInUser=CommonUtil.getUserFromSession(request);
		List<ImageDto> images = new ArrayList<>();
		Collection<Part> parts = request.getParts();
		for (Part part : parts) {
			if (part.getName().equals("images") && part.getSize() > 0) {
		        byte[] imageBytes = CommonUtil.extractImageBytes(part);
		        String contentType = part.getContentType();
		        long size = part.getSize();
		        ImageDto imageDto=new ImageDto(imageBytes, size, contentType);
		        images.add(imageDto);
			}
		}
		Privacy privacy = CommonUtil.toEnum(request.getParameter("privacy"));
		PostDto postDto = new PostDto(loggedInUser.getId(), request.getParameter("post_content"),privacy, images);
		Map<String, String> errorMessages = postValidator.validate(postDto);
		logger.error("Error messages for creating post:{}", errorMessages);
		if (CommonUtil.isEmpty(errorMessages)) {
			boolean isSaved = postService.save(postDto);
			if (isSaved) {
				logger.info("post is saved successfully:{}", postDto);
				response.sendRedirect(request.getContextPath() + "/");
				return;
			} else {
				logger.error("Failed to save post: {}", postDto);
				request.setAttribute("globalError", MessageUtil.getMessage("error.global.internal"));
				request.getRequestDispatcher("/").forward(request, response);
			}

		}
		request.setAttribute("errorMessages", errorMessages);
		request.getRequestDispatcher("/").forward(request, response);
	}
	

}
