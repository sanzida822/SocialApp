<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"  session="true"%>

<%
    if(session.getAttribute("id") == null){
        response.sendRedirect(request.getContextPath() + "/views/login.jsp");
        return;
    }

    Integer id = (Integer) session.getAttribute("id");
    String email = (String) session.getAttribute("email");
   // String uname=(String)session.getAttribute("uname");
%>

<%@ include file="header.jsp" %>
<%@ include file="navbar.jsp" %>

<div class="container mt-4">
    <h2> Friend Page</h2>
   
    
</div>

<%@ include file="footer.jsp" %>
