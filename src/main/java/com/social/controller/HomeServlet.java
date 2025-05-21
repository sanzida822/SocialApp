package com.social.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.social.dto.PostDto;
import com.social.dto.UserDto;
import com.social.model.User;
import com.social.service.AuthenticationService;
import com.social.service.PostService;
import com.social.service.UserService;
import com.social.util.CommonUtil;
import com.social.util.MessageUtil;

/**
 * Servlet implementation class HomeServlet
 */
@WebServlet("")
public class HomeServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final Logger logger = LoggerFactory.getLogger(HomeServlet.class);
	private static PostService postservice = PostService.getInstance();

	public HomeServlet() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			getVisiblePosts(request, response);
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("globalError", MessageUtil.getMessage("error.global.internal"));
			request.getRequestDispatcher("/views/ErrorPage.jsp").forward(request, response);
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

	public void getVisiblePosts(HttpServletRequest request, HttpServletResponse response) throws Exception {
		UserDto user = CommonUtil.getUserFromSession(request);
		int loggedInUserId = user.getId();
		List<PostDto> postDtos = postservice.getPostDtosWithImages(loggedInUserId);
		if (CommonUtil.isNullOrEmpty(postDtos)) {
			logger.info("visible posts for user:{}  are {}", loggedInUserId, postDtos);
			request.setAttribute("postList", postDtos);
		} else {
			logger.info("No post available to show for user:{}", loggedInUserId);
			request.setAttribute("message", MessageUtil.getMessage("no.visible.posts"));
		}
		request.getRequestDispatcher("/views/HomePage.jsp").forward(request, response);
	}

}
