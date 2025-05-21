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

import com.social.dto.FriendRequestViewDto;
import com.social.dto.SentRequestsViewDto;
import com.social.dto.UserDto;
import com.social.service.ExplorePeopleService;
import com.social.service.FriendRequestService;
import com.social.util.CommonUtil;
import com.social.util.MessageUtil;

/**
 * Servlet implementation class ExplorePeopleServlet
 */
@WebServlet("/user/explorePeople")
public class ExplorePeopleServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final Logger logger = LoggerFactory.getLogger(ExplorePeopleServlet.class);
	private static ExplorePeopleService explorePeopleService=ExplorePeopleService.getInstance();
	public static FriendRequestService friendRequestService=FriendRequestService.getInstance();

    public ExplorePeopleServlet() {
        super();
        // TODO Auto-generated constructor stub
    }
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		UserDto loggedInUser=CommonUtil.getUserFromSession(request);
        try {
			List<UserDto> nonFriends=explorePeopleService.getUsersNotInFriends(loggedInUser.getId());
			List<SentRequestsViewDto> sentedRequest=friendRequestService.getSentedRequest(loggedInUser.getId());
			logger.info("Friend request list you have sent:{}",sentedRequest);
			if(CommonUtil.isNullOrEmpty(nonFriends) || CommonUtil.isNullOrEmpty(sentedRequest)) {
			    logger.info("User:{} has friends to explore:{}",loggedInUser.getId(),nonFriends);
			    request.setAttribute("nonFriends", nonFriends);
			    request.setAttribute("sentedRequests", sentedRequest); 
			    request.getRequestDispatcher("/views/ExplorePeople.jsp").forward(request, response); 
			}else {
				logger.info("No new friends to explore");
				request.setAttribute("globalWarn", MessageUtil.getMessage("explore.no.people"));
				request.getRequestDispatcher("/views/ExplorePeople.jsp").forward(request, response);			
			}
		} catch (Exception e) {
		    logger.error("Error occurred while exploring people:{}, e:{}",e.getMessage(),e);
			e.printStackTrace();
		}
        
        
        
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
