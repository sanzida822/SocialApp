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

/**
 * Servlet Filter implementation class AuthFilter
 */
@WebFilter("/*")
public class AuthFilter extends HttpFilter implements Filter {
       
    /**
     * @see HttpFilter#HttpFilter()
     */
    public AuthFilter() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see Filter#destroy()
	 */
	public void destroy() {
		// TODO Auto-generated method stub
	}

	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
	     HttpServletRequest req=(HttpServletRequest) request;
	     HttpServletResponse res=(HttpServletResponse) response;
	     HttpSession session=req.getSession(false); //Only get the session if it already exists, otherwise give  null
	     
	     String loginUrl=req.getContextPath()+"/login";
	     String regUrl=req.getContextPath()+"/registration";
	     String uri = req.getRequestURI();
	     System.out.println(uri);
	     boolean loggedIn=(session!=null && session.getAttribute("id")!=null);
	     if(loggedIn || req.getRequestURI().equals(loginUrl) || req.getRequestURI().equals(regUrl)) {
	    	 chain.doFilter(request, response); 
	    	 return;
	    	 
	     }
//	     if (uri.endsWith(".jsp") && !uri.contains("login.jsp") && !uri.contains("registration.jsp")) {
//	    	    res.sendRedirect(req.getContextPath() + "/login.jsp");
//	    	    return;
//	    	}
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
