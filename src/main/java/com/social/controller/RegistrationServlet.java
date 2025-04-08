package com.social.controller;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import com.social.dao.RegistrationDao;
import com.social.model.RegistrationModel;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@WebServlet("/registration")
@MultipartConfig
public class RegistrationServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
	
	   private static final Logger logger = LoggerFactory.getLogger(RegistrationServlet.class);

    public RegistrationServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		request.getRequestDispatcher("/views/registration.jsp").forward(request, response);

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		RegistrationModel reg= new RegistrationModel();
		
		String uname = request.getParameter("uname");
		String email = request.getParameter("email");
		String password = request.getParameter("password");
		String cpassword = request.getParameter("cpassword");
		Part imagePart = request.getPart("image");

		if (imagePart == null) {
			System.out.println("no image");
		    request.setAttribute("error", "No image selected.");
		    request.getRequestDispatcher("/views/registration.jsp").forward(request, response);
		    return;
		}
	
		
		System.out.println("File name: " + imagePart.getSubmittedFileName());
		System.out.println("Content type: " + imagePart.getContentType());
		System.out.println("File size: " + imagePart.getSize());

        logger.info("File name: {}", imagePart.getSubmittedFileName());
        logger.info("Content type: {}", imagePart.getContentType());
        logger.error("File size: {}", imagePart.getSize());
		String imagePath = saveImageToDisk(imagePart);
		
		reg.setUname(uname);
		reg.setEmail(email);
		reg.setPassword(password);
		reg.setCpassword(cpassword);
		reg.setImage(imagePath);
		
//		
//		System.out.println(uname);
//		System.out.println(email);
//		System.out.println(password);
//		System.out.println(cpassword);
//		System.out.println(imagePath);
		if(uname==null|| email==null|| password==null || cpassword==null|| imagePath==null|| uname.isEmpty() || email.isEmpty() || cpassword.isEmpty() ||imagePath.isEmpty()) {
		
			
			request.setAttribute("error", "All fields are required");
			request.getRequestDispatcher("/views/registration.jsp").forward(request, response);
			return;
			
		}
		if(password.length()<6) {
			
			request.setAttribute("error", "Password length cant be less than 6");
			request.getRequestDispatcher("/views/registration.jsp").forward(request, response);
			return;
		}
		if(!password.equals(cpassword)) {
			request.setAttribute("error", "Password do not match");
			request.getRequestDispatcher("/views/registration.jsp").forward(request, response);
			return;
			
		}
		RegistrationDao Dao= new RegistrationDao();
		boolean saveUser=Dao.saveUser(reg);
		System.out.println(saveUser);
		if(saveUser) {		
			response.sendRedirect(request.getContextPath()+"/views/login.jsp");
			
		}else {
			request.setAttribute("error", "Registration failed");
			request.getRequestDispatcher("views/registration.jsp").forward(request, response);
			
		}

		//doGet(request, response);
	
		
	}
	
	
	private String saveImageToDisk(Part imagePart) throws IOException {
		String imageName = UUID.randomUUID().toString() + ".jpg";
		String imageDirectory = "D:/java/SocialApp/images/";
		File imageFile = new File(imageDirectory + imageName);

		imagePart.write(imageFile.getAbsolutePath());
		System.out.println(imageDirectory + imageName);
		return imageDirectory + imageName;
	}

}
