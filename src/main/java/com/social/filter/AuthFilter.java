package com.social.filter;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebFilter("/*")
public class AuthFilter extends HttpFilter implements Filter {

    public AuthFilter() {
        super();
        // TODO Auto-generated constructor stub
    }

	public void destroy() {
		// TODO Auto-generated method stub
	}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
	     HttpServletRequest req=(HttpServletRequest) request;
	     HttpServletResponse res=(HttpServletResponse) response;
	     HttpSession session=req.getSession(false); //Only get the session if it already exists, otherwise give  null	     
	     String loginUrl=req.getContextPath()+"/auth/login";
	     String regUrl=req.getContextPath()+"/auth/register";
	     String uri = req.getRequestURI();
	     System.out.println(uri);
	     boolean loggedIn=(session!=null && session.getAttribute("email")!=null);
	     if(loggedIn || req.getRequestURI().equals(loginUrl) || req.getRequestURI().equals(regUrl)) {
	    	 chain.doFilter(request, response); 
	    	 return;	    	 
	     }
	     else {
	    	 res.sendRedirect(loginUrl);
	    	 
	     }
	     
	
		
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
		// TODO Auto-generated method stub
	}

}
