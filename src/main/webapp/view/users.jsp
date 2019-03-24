
<html>
<%@page import="java.util.List"%>
<%@ page import="domain.User" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri= "http://java.sun.com/jsp/jstl/core" prefix="c"%>

<body>
<div>
<%
    List<User> users = (List<User>) request.getAttribute("users");
    for (User u : users) {
        out.print("<br/>" + "<p>" + u + " " + u.getFirstName() + " " + u.getEmail()+ " " + u.getLastName() + "</p>" );
    }
%>
</div>
<a id="logoutLink" href="/1/api/auth/logout" >logout</a>
<br/><br/>
</body>
</html>
