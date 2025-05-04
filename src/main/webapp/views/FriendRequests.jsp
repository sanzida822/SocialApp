<%@page import="com.social.dto.FriendRequestViewDto"%>
<%@page import="com.social.util.CommonUtil"%>
<%@page import="com.social.dao.FriendRequestDao"%>
<%@ page import="java.util.List"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" session="true"%>
<%@ include file="Header.jsp"%>
<%@ include file="Navbar.jsp"%>

<%
CommonUtil commonUtil = CommonUtil.getInstance();
%>

<div class="container mt-5">
	<c:if test="${not empty globalWarn}">
		<div class="alert alert-warning text-center mx-auto"
			style="max-width: 500px;">${globalWarn}</div>
	</c:if>

	<%
	List<FriendRequestViewDto> friendRequestDtos = (List<FriendRequestViewDto>) request.getAttribute("friendRequests");
	if (!commonUtil.isNullOrEmpty(friendRequestDtos)) {
	%>

	<div class="row justify-content-center">
		<div class="col-md-10">
			<div class="card shadow-sm border-0 rounded-3">
				<div class="card-header bg-info text-white">
					<h5 class="mb-0">Friend Requests</h5>
				</div>
				<div class="card-body p-4">
					<table class="table table-hover align-middle">
						<thead class="table-light">
							<tr>
								<th>Name</th>
								<th style="width: 150px;">Actions</th>
								<th>Time</th>
							</tr>
						</thead>
						<tbody>
							<%
							for (FriendRequestViewDto friendRequest : friendRequestDtos) {
							%>
							<tr>
								<td><%=friendRequest.getSenderUsername()%></td>
								<td>
									<div class="d-flex gap-2">
										<form
											action="${pageContext.request.contextPath}/friend-requests/accept"
											method="post">
											<input type="hidden" name="senderId"
												value="<%=friendRequest.getSenderId()%>" />
											<button type="submit" class="btn btn-sm btn-success">
												<i class="fa-solid fa-check me-1"></i>Accept
											</button>
										</form>
										<form
											action="${pageContext.request.contextPath}/friend-requests/decline"
											method="post">
											<input type="hidden" name="senderId"
												value="<%=friendRequest.getSenderId()%>" />
											<button type="submit" class="btn btn-sm btn-danger">
												<i class="fa-solid fa-times me-1"></i>Decline
											</button>
										</form>
									</div>
								</td>
								<td><%=friendRequest.getRequestDate()%></td>
							</tr>
							<%
							}
							%>
						</tbody>
					</table>
				</div>
			</div>
		</div>
	</div>
	<%
	}
	%>
</div>

<%@ include file="Footer.jsp"%>
