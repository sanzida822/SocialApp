<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.5/dist/css/bootstrap.min.css"
	rel="stylesheet"
	integrity="sha384-SgOJa3DmI69IUzQ2PVdRZhwQ+dy64/BUtbMJw1MZ8t5HZApcHrRKUc4W0kG879m7"
	crossorigin="anonymous">

<link rel="stylesheet" href="../css/style.css">

<title>Registration Form</title>
</head>
<body>
	<section class="vh-100 registration" style="background-color: #eee;">
		<div class="container h-100">
			<div
				class="row d-flex justify-content-center align-items-center h-100">
				<div class="col-lg-12 col-xl-11">
					<div class="card text-black" style="border-radius: 25px;">
						<div class="card-body p-md-3">
							<div class="row justify-content-center">
								<div class="col-md-10 col-lg-6 col-xl-5 order-2 order-lg-1">

									<p class="text-center h1 fw-bold mb-5 mx-1 mx-md-4 mt-4">Sign
										up</p>
									<c:if test="${not empty errorMessages}">
										<div class="alert alert-danger">
											<c:forEach var="error" items="${errorMessages}">
												<p>${error.value}</p>
											</c:forEach>
										</div>
									</c:if>
									<%
									String globalError = (String) request.getAttribute("globalError");

									if (globalError != null) {
									%>
									<div class="alert alert-danger"><%=globalError%></div>
									<%
									}
									%>

									<form class="mx-1 mx-md-4"
										action="<%=request.getContextPath()%>/auth/register"
										method="post" enctype="multipart/form-data">


										<div class="d-flex flex-row align-items-center mb-2">
											<i class="fas fa-user fa-lg me-3 fa-fw"></i>
											<div data-mdb-input-init class="form-outline flex-fill mb-0">
												<input type="text" id="form3Example1c" class="form-control"
													name="username" /> <label class="form-label"
													for="form3Example1c">Your Name</label>
												<c:if test="${not empty errorMessages.username_required}">
													<div class="error">${errorMessages.username_required}</div>
												</c:if>
											</div>
										</div>

										<div class="d-flex flex-row align-items-center mb-2">
											<i class="fas fa-envelope fa-lg me-3 fa-fw"></i>
											<div data-mdb-input-init class="form-outline flex-fill mb-0">
												<input type="email" id="form3Example3c" name="email"
													class="form-control" /> <label class="form-label"
													for="form3Example3c">Your Email</label>
											</div>
										</div>
										<div class="d-flex flex-row align-items-center mb-4">
											<i class="fas fa-image fa-lg me-3 fa-fw"></i>
											<div data-mdb-input-init class="form-outline flex-fill mb-0">
												<input type="file" id="form3Example5c" class="form-control"
													name="image" accept="image/*" /> <label class="form-label"
													for="form3Example5c">Upload Image</label>
											</div>
										</div>

										<div class="d-flex flex-row align-items-center mb-4">
											<i class="fas fa-lock fa-lg me-3 fa-fw"></i>
											<div data-mdb-input-init class="form-outline flex-fill mb-0">
												<input type="password" name="password" id="form3Example4c"
													class="form-control" /> <label class="form-label"
													for="form3Example4c">Password</label>
											</div>
										</div>

										<div class="d-flex flex-row align-items-center mb-2">
											<i class="fas fa-key fa-lg me-3 fa-fw"></i>
											<div data-mdb-input-init class="form-outline flex-fill mb-0">
												<input type="password" name="confirm_password"
													id="form3Example4cd" class="form-control" /> <label
													class="form-label" for="form3Example4cd">Repeat
													your password</label>
											</div>
										</div>



										<div class="d-flex justify-content-center mx-4 mb-3 mb-lg-2">
											<button type="submit" data-mdb-button-init
												data-mdb-ripple-init class="btn btn-primary btn-lg">Register</button>
										</div>

									</form>
									<div class="form-check d-flex justify-content-center mb-5">
										<input class="form-check-input me-2" type="checkbox" value=""
											id="form2Example3c" /> <label class="form-check-label"
											for="form2Example3"> Already have an account? <a
											href="<%=request.getContextPath()%>/auth/login">Log in</a>
											here
										</label>
									</div>
								</div>
								<div
									class="col-md-10 col-lg-6 col-xl-7 d-flex align-items-center order-1 order-lg-2">

									<img
										src="https://mdbcdn.b-cdn.net/img/Photos/new-templates/bootstrap-registration/draw1.webp"
										class="img-fluid" alt="Sample image">

								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</section>



	<script
		src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.5/dist/js/bootstrap.bundle.min.js"
		integrity="sha384-k6d4wzSIapyDyv1kpU366/PK5hCdSbCRGRCMv+eplOQJWyd1fbcAu9OCUj5zNLiq"
		crossorigin="anonymous"></script>
</body>
</html>

