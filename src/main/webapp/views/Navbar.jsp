<%@page import="com.social.dto.UserDto"%>
<%@page import="com.social.model.User"%>


<%
response.setHeader("Cache-Control", "no-cache, no-store,must-revaldate");
UserDto user=(UserDto)session.getAttribute("user");
%>


<nav class="navbar navbar-expand-lg navbar-dark bg-dark">
	<div class="container-fluid">
		<a class="navbar-brand"
			href="${pageContext.request.contextPath}/user/profile"><%=user.getUsername()%></a>
		<button class="navbar-toggler" type="button" data-bs-toggle="collapse"
			data-bs-target="#navbarNav" aria-controls="navbarNav"
			aria-expanded="false" aria-label="Toggle navigation">
			<span class="navbar-toggler-icon"></span>
		</button>


		<div class="collapse navbar-collapse" id="navbarNav">
			<ul class="navbar-nav ms-auto">
				<li class="nav-item">
					<!-- pageContext.request.contextPath return SocialApp   --> <a
					class="nav-link"
					href="${pageContext.request.contextPath}/user/home">Home</a>
				</li>
				<li class="nav-item"><a class="nav-link"
					href="${pageContext.request.contextPath}/user/explorePeople">Explore People</a>
				</li>
				<li class="nav-item"><a class="nav-link"
					href="${pageContext.request.contextPath}/views/Friends.jsp">Friends</a>
				</li>
				<li class="nav-item"><a class="nav-link"
					href="${pageContext.request.contextPath}/friend-request/view">Friend
						Requests</a></li>
				<li class="nav-item"><a class="nav-link text-danger"
					href="${pageContext.request.contextPath}/auth/logout">Logout</a></li>
			</ul>
		</div>
	</div>
</nav>
