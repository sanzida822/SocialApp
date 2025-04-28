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

import com.social.dto.UserDto;
import com.social.service.ExplorePeopleService;
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
	private static CommonUtil commonUtil=CommonUtil.getInstance();
	private static final String EXPLORE_PEOPLE="/user/explorePeople";
    public ExplorePeopleServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        UserDto loggedInUser = (UserDto) session.getAttribute("user");
        try {
			List<UserDto> nonFriends=explorePeopleService.getUsersNotInFriends(loggedInUser.getId());
			logger.info("non friends:{}",nonFriends);
			if(!commonUtil.isEmpty(nonFriends)) {
				logger.info("User:{} has friends to explore:{}",loggedInUser.getId(),nonFriends);
				request.setAttribute("nonFriends", nonFriends);
				//request.getRequestDispatcher("/views/ExplorePeople.jsp").forward(request, response);
			}else {
				logger.info("No new friends to explore");
				request.setAttribute("globalWarn", MessageUtil.getMessage("explore.no.people"));
				request.getRequestDispatcher("/views/ExplorePeople.jsp").forward(request, response);
				
			}
		} catch (Exception e) {
		    logger.error("Error occurred while exploring people", e);
			e.printStackTrace();
		}
        
        
        
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
