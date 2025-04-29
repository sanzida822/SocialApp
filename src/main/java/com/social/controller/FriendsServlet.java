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

import com.social.dto.FriendRequestsDto;
import com.social.dto.UserDto;
import com.social.enums.FriendRequestStatus;
import com.social.service.FriendRequestService;
import com.social.util.CommonUtil;

/**
 * Servlet implementation class FriendsServlet
 */
@WebServlet(urlPatterns = {"/friend/request"})
public class FriendsServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final Logger logger = LoggerFactory.getLogger(FriendsServlet.class);
	private static FriendRequestService friendRequestService=FriendRequestService.getInstance();
	private static CommonUtil commonUtil=CommonUtil.getInstance();
	public static final String FRIEND_REQUEST= "/friend/request";
       

    public FriendsServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String servletPath=request.getServletPath();
		
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String servletPath=request.getServletPath();

		try {
			switch (servletPath) {
			case FRIEND_REQUEST:
				sendRequest(request,response);
				break;

			default:
				break;
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		doGet(request, response);
	}
	public void sendRequest(HttpServletRequest request, HttpServletResponse response) {
		UserDto user=commonUtil.getUserFromSession(request);
		int senderId=user.getId();
		int receiverId=Integer.parseInt(request.getParameter("receiverId"));
		logger.info("User1:{} sends request to user2:{}", senderId,receiverId);
		FriendRequestsDto friendRequestDto= new FriendRequestsDto(senderId, receiverId, FriendRequestStatus.PENDING);
		friendRequestService.
		
	}
	

}
