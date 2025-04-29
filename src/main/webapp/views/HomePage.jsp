<%@page import="com.social.model.User"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" session="true"%>
<%@ include file="Header.jsp"%>
<%@ include file="Navbar.jsp"%>



<div class="container mt-5">
	<!-- Post Creation Form -->
	<div class="row justify-content-center">
		<div class="col-md-6">
			<form action="<%=request.getContextPath()%>/add/post" method="post"
				enctype="multipart/form-data">
				<div class="form-group">
					<label for="exampleFormControlSelect1">Privacy</label> <select
						class="form-control" id="privacySelect" name="privacy">
						<option value="PUBLIC">Public</option>
						<option value="FRIENDS">Friends</option>
						<option value="ONLY_ME">Only me</option>
					</select>
				</div>

				<div class="form-group mt-3">
					<textarea class="form-control" id="exampleFormControlTextarea1"
						placeholder="Share your thoughts" rows="3" name="post_content"></textarea>
				</div>

				<div class="form-group mt-3">
					<label for="images">Upload Images</label> <input type="file"
						name="images" id="images" multiple class="form-control-file">
				</div>

				<button type="submit" class="btn btn-primary mt-4 w-100">Post</button>
			</form>
		</div>
	</div>

	<!-- Posts Section -->
	<div class="row justify-content-center mt-5">
		<div class="col-md-8">
			<!-- Post Card -->
			<div class="card mb-4 shadow-sm">
				<div
					class="card-header d-flex justify-content-between align-items-center">
					<div>
						<h5 class="mb-0">
							Sanzida Sultana <span class="post-privacy">public</span>
						</h5>
						<small class="text-muted">April 24, 2025</small>
					</div>
					<div>
						<button class="btn btn-sm btn-outline-secondary me-2" title="Edit">
							<i class="fa-solid fa-pen-to-square"></i>
						</button>
						<button class="btn btn-sm btn-outline-danger" title="Delete">
							<i class="fa fa-trash" aria-hidden="true"></i>
						</button>
					</div>
				</div>

				<div class="card-body">
					<p>You can write your thoughts here!</p>

					<!-- Image Carousel -->
					<div id="carouselExampleControls" class="carousel slide"
						data-ride="carousel">
						<div class="carousel-inner">
							<div class="carousel-item active">
								<img class="d-block w-100" src="..." alt="First slide">
							</div>
							<div class="carousel-item">
								<img class="d-block w-100" src="..." alt="Second slide">
							</div>
							<div class="carousel-item">
								<img class="d-block w-100" src="..." alt="Third slide">
							</div>
						</div>
						<a class="carousel-control-prev" href="#carouselExampleControls"
							role="button" data-slide="prev"> <span
							class="carousel-control-prev-icon" aria-hidden="true"></span> <span
							class="sr-only">Previous</span>
						</a> <a class="carousel-control-next" href="#carouselExampleControls"
							role="button" data-slide="next"> <span
							class="carousel-control-next-icon" aria-hidden="true"></span> <span
							class="sr-only">Next</span>
						</a>
					</div>
				</div>
			</div>
			<!-- End Post Card -->

			<!-- You can copy and paste the above card to add more posts dynamically -->
		</div>
	</div>
</div>
