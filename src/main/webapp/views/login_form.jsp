<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.5/dist/css/bootstrap.min.css"
	rel="stylesheet"
	integrity="sha384-SgOJa3DmI69IUzQ2PVdRZhwQ+dy64/BUtbMJw1MZ8t5HZApcHrRKUc4W0kG879m7"
	crossorigin="anonymous">
<script
	src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.5/dist/js/bootstrap.bundle.min.js"
	integrity="sha384-k6d4wzSIapyDyv1kpU366/PK5hCdSbCRGRCMv+eplOQJWyd1fbcAu9OCUj5zNLiq"
	crossorigin="anonymous"></script>
<meta charset="ISO-8859-1">
<title>Login Form</title>
</head>
<body>
	<section class="vh-100" style="background-color: #508bfc;">
		<div class="container py-5 h-100">
			<div
				class="row d-flex justify-content-center align-items-center h-100">
				<div class="col-12 col-md-8 col-lg-6 col-xl-5">
					<div class="card shadow-2-strong" style="border-radius: 1rem;">
						<div class="card-body p-5 text-center">
							<c:if test="${not empty errorMessages}">
								<div class="alert alert-danger">
									<c:forEach var="error" items="${errorMessages}">
										<p>${error.value}</p>
									</c:forEach>
								</div>
							</c:if>
							<%
							String error = (String) request.getAttribute("globalError");

							if (error != null) {
							%>
							<div class="alert alert-danger"><%=error%></div>

							<%
							}
							%>
							<h3 class="mb-5">Sign in</h3>
							<form action="<%=request.getContextPath()%>/auth/login"
								method="post">
								<div data-mdb-input-init class="form-outline mb-4">
									<input type="email" name="email" id="typeEmailX-2"
										class="form-control form-control-lg" /> <label
										class="form-label" for="typeEmailX-2">Email</label>
								</div>

								<div data-mdb-input-init class="form-outline mb-4">
									<input type="password" name="password" id="typePasswordX-2"
										class="form-control form-control-lg" /> <label
										class="form-label" for="typePasswordX-2">Password</label>
								</div>


								<button data-mdb-button-init data-mdb-ripple-init
									class="btn btn-primary btn-lg btn-block" type="submit">Login</button>
							</form>
						</div>
					</div>
				</div>
			</div>
		</div>
	</section>
</body>
</html>