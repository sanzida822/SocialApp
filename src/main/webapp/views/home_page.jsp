<%@page import="com.social.model.User"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" session="true"%>
<%
String email = (String) session.getAttribute("email");
%>
<%@ include file="header.jsp"%>
<%@ include file="navbar.jsp"%>

<div class="container mt-5">


	<div class="row justify-content-center">
		<div class="col-md-6">
			<form action="<%=request.getContextPath()%>/PostServlet"
				method="post" enctype="multipart/form-data">

				<div class="form-group">
					<label for="exampleFormControlSelect1">Privacy</label> <select
						class="form-control" id="exampleFormControlSelect1" name="privacy">
						<option>Public</option>
						<option>Friends</option>
						<option>Only me</option>
					</select>
				</div>

				<div class="form-group mt-3">
					<textarea class="form-control" id="exampleFormControlTextarea1"
						placeholder="Share your thoughts" rows="3" name="post_content"></textarea>
				</div>

				<div class="form-group mt-3">
					<label for="images">Upload Images</label> <input type="file"
						name="images" id="images" multiple class="form-control-file"
						name="images">
				</div>

				<button type="submit" class="btn btn-primary mt-4 w-100">Post</button>
			</form>
		</div>
	</div>
</div>

<%@ include file="footer.jsp"%>
