<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"  session="true"%>

<%
    if(session.getAttribute("id") == null){
        response.sendRedirect(request.getContextPath() + "/views/login.jsp");
        return;
    }

    Integer id = (Integer) session.getAttribute("id");
    String email = (String) session.getAttribute("email");
    String uname=(String)session.getAttribute("uname");
%>

<%@ include file="header.jsp" %>
<%@ include file="navbar.jsp" %>

<div class="container mt-4">
    <h2> Home Page</h2>
    <p>Your ID is: <%= id %></p>
    <p>Your email: <%= email %></p>
      <p>Your name: <%= uname %></p>
  <p>Session ID: <%= session.getId() %></p>

    
</div>

<%@ include file="footer.jsp" %>
