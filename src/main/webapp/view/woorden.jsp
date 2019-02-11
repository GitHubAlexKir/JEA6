
<html>
<%@page import="java.util.List"%>
<%@ page import="domain.Person" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri= "http://java.sun.com/jsp/jstl/core" prefix="c"%>

<body>
<%
    List<Person> users = (List<Person>) request.getAttribute("users");
    for (Person s : users) {
        out.print("<br/>" + s.getId() + " " + s.getName() + " " + s.getAddress());
    }
%>
</body>
</html>
