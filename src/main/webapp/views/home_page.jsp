<%@page import="com.social.model.User"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" session="true"%>
<%
String email = (String) session.getAttribute("email");
%>
<%@ include file="header.jsp"%>
<%@ include file="navbar.jsp"%>

<div class="container mt-5">
    <!-- Post Creation Form -->
    <div class="row justify-content-center">
        <div class="col-md-6">
            <form action="<%=request.getContextPath()%>/PostServlet" method="post" enctype="multipart/form-data">
                <div class="form-group">
                    <label for="exampleFormControlSelect1">Privacy</label>
                    <select class="form-control" id="exampleFormControlSelect1" name="privacy">
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
                    <label for="images">Upload Images</label>
                    <input type="file" name="images" id="images" multiple class="form-control-file">
                </div>

                <button type="submit" class="btn btn-primary mt-4 w-100">Post</button>
            </form>
        </div>
    </div>

    <!-- Posts Section -->
    <div class="row justify-content-center mt-5">
        <div class="col-md-6">
            <!-- Post Card -->
            <div class="card mb-4 shadow-sm">
                <div class="card-header d-flex justify-content-between align-items-center">
                    <div>
                        <h5 class="mb-0">Sanzida Sultana</h5>
                        <small class="text-muted">April 24, 2025</small>
                    </div>
                    <div>
                        <button class="btn btn-sm btn-outline-secondary me-2" title="Edit">
                            <i class="bi bi-pencil-square"></i>
                        </button>
                        <button class="btn btn-sm btn-outline-danger" title="Delete">
                            <i class="bi bi-trash"></i>
                        </button>
                    </div>
                </div>

                <div class="card-body">
                    <p>This is an example of a post content. You can write your thoughts here!</p>

                    <!-- Image Carousel -->
                    <div id="postImagesCarousel" class="carousel slide" data-bs-ride="carousel">
                        <div class="carousel-inner rounded">
                            <div class="carousel-item active">
                                <img src="images/post1.jpg" class="d-block w-100" alt="Image 1">
                            </div>
                            <div class="carousel-item">
                                <img src="images/post2.jpg" class="d-block w-100" alt="Image 2">
                            </div>
                            <div class="carousel-item">
                                <img src="images/post3.jpg" class="d-block w-100" alt="Image 3">
                            </div>
                        </div>
                        <button class="carousel-control-prev" type="button" data-bs-target="#postImagesCarousel" data-bs-slide="prev">
                            <span class="carousel-control-prev-icon"></span>
                        </button>
                        <button class="carousel-control-next" type="button" data-bs-target="#postImagesCarousel" data-bs-slide="next">
                            <span class="carousel-control-next-icon"></span>
                        </button>
                    </div>
                </div>
            </div>
            <!-- End Post Card -->

            <!-- You can copy and paste the above card to add more posts dynamically -->
        </div>
    </div>
</div>

<%@ include file="footer.jsp"%>
