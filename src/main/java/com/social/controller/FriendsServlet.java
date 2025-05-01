package com.social.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.social.dto.FriendRequestsDto;
import com.social.dto.UserDto;
import com.social.enums.FriendRequestStatus;
import com.social.service.FriendRequestService;
import com.social.util.CommonUtil;
import com.social.util.MessageUtil;

/**
 * Servlet implementation class FriendsServlet
 */
@WebServlet(urlPatterns = { "/friend-requests/send","/friend-request","/friend-requests/respond","/friend-requests/cancel" })
public class FriendsServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final Logger logger = LoggerFactory.getLogger(FriendsServlet.class);
	private static FriendRequestService friendRequestService = FriendRequestService.getInstance();
	private static CommonUtil commonUtil = CommonUtil.getInstance();
	public static final String SEND_REQUEST = "/friend-requests/send";
	public static final String VIEW_REQUEST="/friend-request";

	public FriendsServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String servletPath = request.getServletPath();
		try {
			switch (servletPath) {
			case  SEND_REQUEST:

				break;
			case VIEW_REQUEST:
				request.getRequestDispatcher("/views/FriendRequests.jsp").forward(request, response);
				break;

			default:
				break;
			}
		} catch (Exception e) {
			logger.error("Exception occurred while processing request at {}, e:{},{}", servletPath, e.getMessage(),e);
		}


	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String servletPath = request.getServletPath();

		try {
			switch (servletPath) {
			case  SEND_REQUEST:
				sendRequest(request, response);
				break;
			case VIEW_REQUEST:
				viewRequest(request,response);
				break;

			default:
				break;
			}
		} catch (Exception e) {
			logger.error("Exception occurred while processing request at {}, e:{},{}", servletPath, e.getMessage(),e);
		}

		doGet(request, response);
	}

	public void sendRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
		UserDto user = commonUtil.getUserFromSession(request);
		int senderId = user.getId();
		int receiverId = Integer.parseInt(request.getParameter("receiverId"));
		FriendRequestsDto friendRequestDto = new FriendRequestsDto(senderId, receiverId, FriendRequestStatus.PENDING);
		boolean isSent = friendRequestService.addRequest(friendRequestDto);
		if (isSent) {
			logger.info("User1:{} sends request to user2:{}", senderId, receiverId);

		} else {
			logger.info("failed to send friend request:{}",isSent);
		}
		response.sendRedirect(request.getContextPath() + "/user/explorePeople");
	}
	public void viewRequest(HttpServletRequest request, HttpServletResponse response) {	
		friendRequestService.
		
	}
}
