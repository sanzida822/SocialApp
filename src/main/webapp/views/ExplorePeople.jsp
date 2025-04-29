<%@page import="com.social.dto.UserDto"%>
<%@ page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" session="true"%>
<%@ include file="Header.jsp"%>
<%@ include file="Navbar.jsp"%>

<div class="container mt-5">
	<c:if test="${not empty globalWarn}">
		<div class="alert alert-warning" role="alert">${globalWarn}</div>
	</c:if>

	<%
	List<UserDto> userDtos = (List<UserDto>) request.getAttribute("nonFriends");
	%>
	<div class="row justify-content-center">
		<div class="col-md-8">
			<table class="table">
				<thead>
					<tr>
						
						<th scope="col">Name</th>

						<th scope="col">Actions</th>
					</tr>
				</thead>
				<tbody>
					<%
			
					for (UserDto nonFriend : userDtos) {
					%>
					<tr>
					
						<td><%=nonFriend.getUsername()%></td>
						<td>
							<form action="${pageContext.request.contextPath}/friend/request" method="post">
							<input type="hidden" name="receiverId"  value="<%=nonFriend.getId() %>"/>
								<button class="btn btn-sm btn-outline-primary"><i class="fa-solid fa-user-group m-2"></i>Send Request</button>
							</form>
						</td>


					</tr>
					<%
					}
					%>
				</tbody>

			</table>
		</div> 

	</div>


</div>


<%@ include file="Footer.jsp"%>