package com.social.controller;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

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
import com.social.model.PostImages;
import com.social.service.AuthenticationService;


@WebServlet(urlPatterns= {"/add/post"})
@MultipartConfig
public class PostServlet extends HttpServlet {
	private static final Logger logger = LoggerFactory.getLogger(PostServlet.class);
	private static final long serialVersionUID = 1L;
	AuthenticationService authenticationService= AuthenticationService.getInstance();
	private static final String ADD_POST="/add/post";
	private PostDto postDto;
	//PostService postservice=null;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public PostServlet() {
    	super();
    //	postservice=new PostService();

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
       String servletPath=request.getServletPath();
       try {
           switch (servletPath) {
       	case ADD_POST:
       		addPost(request, response);		
       		break;

       	default:
       		break;
       	}

 
       }catch(Exception e) {
    	   logger.error("Exception occure when adding post:{}, e:",e.getMessage(),e);
       }

	}	
	
	public void addPost(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String email = (String)request.getSession(false).getAttribute("email");
		logger.info("Request comes for insert a post");
		List<byte[]> postImages = new ArrayList<>();
		Collection<Part> parts=request.getParts();
	    for (Part part : parts) {
	        if (part.getName().equals("images") && part.getSize() > 0) {
	            try (InputStream imageStream = part.getInputStream()) {
	                byte[] imageBytes = imageStream.readAllBytes();
	                postImages.add(imageBytes);
	            }
	        }
	    }
        String privacy=request.getParameter("privacy");
        Privacy privacyEnum=Privacy.valueOf(privacy);
		UserDto postedBy=authenticationService.getUserByEmail(email);
	    postDto= new PostDto(postedBy.getId(), request.getParameter("post_content"), postImages,privacyEnum);
	    logger.info("post dto object:{}", postDto);


	}

}
