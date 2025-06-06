package com.social.controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.social.dto.FriendRequestViewDto;
import com.social.dto.FriendRequestsDto;
import com.social.dto.FriendshipDto;
import com.social.dto.UserDto;
import com.social.enums.FriendRequestStatus;
import com.social.enums.FriendshipStatus;
import com.social.model.FriendRequest;
import com.social.service.FriendRequestService;
import com.social.service.FriendsService;
import com.social.util.CommonUtil;
import com.social.util.MessageUtil;

/**
 * Servlet implementation class FriendsServlet
 */
@WebServlet(urlPatterns = { "/friend-request/send", "/friend-request/view", "/friend-request/cancel",
		"/friend-request/decline", "/friend-request/accept" })
public class FriendsServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final Logger logger = LoggerFactory.getLogger(FriendsServlet.class);
	private static FriendRequestService friendRequestService = FriendRequestService.getInstance();
	private static FriendsService friendService = FriendsService.getInstance();
	public static final String EXPLORE_PEOPLE="/user/explorePeople";
	public static final String SEND_REQUEST = "/friend-request/send";
	public static final String VIEW_REQUEST = "/friend-request/view";
	public static final String CANCEL_REQUEST = "/friend-request/cancel";
	public static final String DECLINE_REQUEST="/friend-request/decline";
	public static final String ACCEPT_REQUEST="/friend-request/accept";

	public FriendsServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String servletPath = request.getServletPath();
		try {
			switch (servletPath) {
			case VIEW_REQUEST:
				viewRequest(request, response);
				break;

			default:
				break;
			}
		} catch (Exception e) {
			logger.error("Exception occurred while processing request at {}, e:{},{}", servletPath, e.getMessage(), e);
		}

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String servletPath = request.getServletPath();
		try {
			switch (servletPath) {
			case SEND_REQUEST:
				sendRequest(request, response);
				break;
			case CANCEL_REQUEST:
				cancelRequest(request, response);
				break;
			case DECLINE_REQUEST:
				declineReceivedRequest(request, response);
				break;
			case ACCEPT_REQUEST:
				acceptFriendRequest(request, response);
				break;
			default:
				break;
			}
		} catch (Exception e) {
			logger.error("Exception occurred while processing request at {}, e:{},{}", servletPath, e.getMessage(), e);
			response.sendRedirect("/views/ErrorPage.jsp");
		}
	}

	public void sendRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
		UserDto user = CommonUtil.getUserFromSession(request);
		int senderId = user.getId();
		int receiverId = Integer.parseInt(request.getParameter("receiverId"));
		FriendRequestsDto friendRequestDto = new FriendRequestsDto(senderId, receiverId, FriendRequestStatus.PENDING);
		boolean isSent = friendRequestService.addRequest(friendRequestDto);
		if (isSent) {
			logger.info("User1:{} sends request to user2:{}", senderId, receiverId);
		} else {
			logger.info("failed to send friend request:{}", isSent);
		}
		response.sendRedirect(request.getContextPath() + "/user/explorePeople");
	}

	public void viewRequest(HttpServletRequest request, HttpServletResponse response) throws SQLException, Exception {
		UserDto loggedInUser = CommonUtil.getUserFromSession(request);
		List<FriendRequestViewDto> friendRequests = friendRequestService.getFriendRequests(loggedInUser.getId());
		logger.info("friend requests for user:{} are", loggedInUser.getUsername(), friendRequests);
		if (CommonUtil.isNullOrEmpty(friendRequests)) {
			logger.info("user:{} has friend request list:{}", loggedInUser.getUsername(), friendRequests);
			request.setAttribute("friendRequests", friendRequests);
			request.getRequestDispatcher("/views/FriendRequests.jsp").forward(request, response);
		} else {
			logger.info("No new friends request");
			request.setAttribute("globalWarn", MessageUtil.getMessage("no.friend.request"));
			request.getRequestDispatcher("/views/FriendRequests.jsp").forward(request, response);
		}
	}

	public void cancelRequest(HttpServletRequest request, HttpServletResponse response)
			throws ClassNotFoundException, SQLException, IOException {
		UserDto loggedInUser = CommonUtil.getUserFromSession(request);
		int receiverId = Integer.parseInt(request.getParameter("receiverId"));
		boolean isCancelled = friendRequestService.cancelRequest(loggedInUser.getId(), receiverId);
		if (isCancelled) {
			logger.info("sender:{} cancelled friend request to receiver:{}", loggedInUser.getUsername(), receiverId);
			response.sendRedirect(request.getContextPath() + EXPLORE_PEOPLE);
		} else {
			logger.error("Error occured while processing cancel friend request");
		}
	}

	public void declineReceivedRequest(HttpServletRequest request, HttpServletResponse response)
			throws ClassNotFoundException, SQLException, IOException {
		UserDto loggedInUser = CommonUtil.getUserFromSession(request);
		int senderId = Integer.parseInt(request.getParameter("senderId"));
		boolean isCancelled = friendService.declineReceivedRequest(loggedInUser.getId(), senderId);
		if (isCancelled) {
			logger.info("user:{} decline friend request of user:{}", loggedInUser.getUsername(), senderId);
			response.sendRedirect(request.getContextPath() + VIEW_REQUEST);
		} else {
			logger.error("Error occured while processing cancel friend request");
		}
	}

	public void acceptFriendRequest(HttpServletRequest request, HttpServletResponse response)
			throws ClassNotFoundException, SQLException, IOException {
		logger.info("accepting request come ins");
		UserDto loggedInUser = CommonUtil.getUserFromSession(request);
		int loggedInUserId = loggedInUser.getId();
		int senderId = Integer.parseInt(request.getParameter("senderId"));
		FriendshipDto friendshipDto = new FriendshipDto(senderId, loggedInUserId, FriendshipStatus.ACTIVE);
		boolean isAccepted = friendService.acceptFriendRequest(friendshipDto);
		logger.info("accept request:{}", isAccepted);
		if (isAccepted) {
			logger.info("accepted friend request");
			response.sendRedirect(request.getContextPath() + VIEW_REQUEST);
		} else {
			logger.error("Error occured while processing accept friend request");
		}

	}
}
