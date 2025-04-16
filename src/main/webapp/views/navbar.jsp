<%@page import="com.social.model.UserModel"%>

<%

response.setHeader("Cache-Control", "no-cache, no-store,must-revaldate");
//UserModel user= (UserModel)request.getAttribute("user");
//UserModel user= (UserModel)request.getAttribute("user");
String uname = (String) session.getAttribute("username"); 

%>


<nav class="navbar navbar-expand-lg navbar-dark bg-dark">
    <div class="container-fluid">
        <a class="navbar-brand" href="${pageContext.request.contextPath}/UserProfileServlet">Hello,<%=uname %></a>
        <button class="navbar-toggler" type="button" data-bs-toggle="collapse" 
                data-bs-target="#navbarNav" aria-controls="navbarNav" 
                aria-expanded="false" aria-label="Toggle navigation">
            <span class="navbar-toggler-icon"></span>
        </button>

<%
if (uname == null) {
    out.println("Session uname is null.");
} else {
    out.println("Session uname: " + uname);
}
%>
        <div class="collapse navbar-collapse" id="navbarNav">
            <ul class="navbar-nav ms-auto">
                <li class="nav-item">
                
             <!-- pageContext.request.contextPath return SocialApp   -->   

                    <a class="nav-link" href="${pageContext.request.contextPath}/views/home_page.jsp">Home</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="${pageContext.request.contextPath}/views/friends.jsp">Friends</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="${pageContext.request.contextPath}/views/friend-requests.jsp">Friend Requests</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link text-danger" href="${pageContext.request.contextPath}/auth/logout">Logout</a>
                </li>
            </ul>
        </div>
    </div>
</nav>
