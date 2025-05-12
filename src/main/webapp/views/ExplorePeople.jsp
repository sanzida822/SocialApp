<%@page import="com.social.dto.SentRequestsViewDto"%>
<%@page import="com.social.dto.FriendRequestViewDto"%>
<%@page import="com.social.util.CommonUtil"%>
<%@page import="com.social.dto.UserDto"%>
<%@ page import="java.util.List"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" session="true"%>
<%@ include file="Header.jsp"%>
<%@ include file="Navbar.jsp"%>

<%
    CommonUtil commonUtil = CommonUtil.getInstance();
%>

<div class="container mt-5">
    <c:if test="${not empty globalWarn}">
        <div class="alert alert-warning text-center mx-auto" style="max-width: 500px;">
            ${globalWarn}
        </div>
    </c:if>


    <%
        List<UserDto> userDtos = (List<UserDto>) request.getAttribute("nonFriends");
        if (!commonUtil.isNullOrEmpty(userDtos)) {
    %>

    <div class="row justify-content-center">
        <div class="col-md-10">
            <div class="card shadow-sm border-0 rounded-3">
                <div class="card-header bg-info text-white">
                    <h5 class="mb-0">People You May Know</h5>
                </div>
                <div class="card-body p-4">
                    <table class="table table-hover align-middle">
                        <thead class="table-light">
                            <tr>
                                <th>Name</th>
                                <th style="width: 150px;">Actions</th>
                            </tr>
                        </thead>
                        <tbody>
                            <%
                                for (UserDto nonFriend : userDtos) {
                            %>
                            <tr>
                                <td><%= nonFriend.getUsername() %></td>
                                <td>
                                    <form action="${pageContext.request.contextPath}/friend-request/send" method="post" class="d-inline">
                                        <input type="hidden" name="receiverId" value="<%= nonFriend.getId() %>" />
                                        <button class="btn btn-sm btn-success">
                                            <i class="fa-solid fa-user-plus me-2"></i>Send Request
                                        </button>
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
        
        
         <%
        }
    %>
</div>
    <%
        List<SentRequestsViewDto> sentedRequest = (List<SentRequestsViewDto>) request.getAttribute("sentedRequests");
        if (!commonUtil.isNullOrEmpty(sentedRequest)) {
    %>

    <div class="row justify-content-center mt-3">
        <div class="col-md-10">
            <div class="card shadow-sm border-0 rounded-3">
                <div class="card-header bg-info text-white">
                    <h5 class="mb-0">Request You have already sent</h5>
                </div>
                <div class="card-body p-4">
                    <table class="table table-hover align-middle">
                        <thead class="table-light">
                            <tr>
                                <th>Name</th>
                                <th style="width: 150px;">Actions</th>
                            </tr>
                        </thead>
                        <tbody>
                            <%
                                for (SentRequestsViewDto requests : sentedRequest) {
                            %>
                            <tr>
                                <td><%= requests.getReceiverName()%></td>
                                <td>
                                    <form action="${pageContext.request.contextPath}/friend-request/cancel" method="post" class="d-inline">
                                        <input type="hidden" name="receiverId" value="<%= requests.getReceiverId() %>" />
                                        <button class="btn btn-sm btn-danger">
                                            <i class="fa-solid fa-times me-1"></i>Cancel Request
                                        </button>
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
    </div>
    <%
        }
    %>
</div>
    </div>
   
<%@ include file="Footer.jsp"%>
