<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
</head>
<body>


<%
    Integer id = (Integer) session.getAttribute("id");
    String username = (String) session.getAttribute("username");
    String email = (String) session.getAttribute("email");
%>

<h1>Welcome, <%= username %>!</h1>
<p>Your ID is: <%= id %></p>
<p>Your email: <%= email %></p>
<p>Session ID: <%= session.getId() %></p>
home page
</body>
</html>