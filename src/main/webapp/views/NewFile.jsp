<%@page import="com.social.model.UserModel"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" session="true"%>

<%
Integer id = (Integer) session.getAttribute("id");
String email = (String) session.getAttribute("email");
// String uname=(String)session.getAttribute("uname");
UserModel user = (UserModel) request.getAttribute("user");
out.println(user);
%>

<%@ include file="header.jsp"%>
<%@ include file="navbar.jsp"%>

<div class="container mt-4">

	<h2>Home Page</h2>
	<form>

		<div class="form-group">
			<label for="exampleFormControlSelect1">Privacy</label> <select
				class="form-control" id="exampleFormControlSelect1">
				<option>Public</option>
				<option>Friends</option>
				<option>Only me</option>

			</select>
		</div>
		<div class="form-group">

			<textarea class="form-control" id="exampleFormControlTextarea1"
				Placeholder="Share your thoughts" rows="3"></textarea>
		</div>

		<div class="form-group mt-2">
			<label for="images">Upload Images</label> <input type="file"
				name="images" id="images" multiple class="form-control-file">
		</div>

		<button type="submit" class="btn btn-primary mt-3">Post</button>
	</form>




</div>

<%@ include file="footer.jsp"%>
