
<html>
<%@page import="java.util.List"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri= "http://java.sun.com/jsp/jstl/core" prefix="c"%>

<body>
<%
    List<String> woorden = (List<String>) request.getAttribute("woorden");
    String test = (String) request.getAttribute("dbtest");
    for (String s : woorden) {
        out.print("<br/>" + s);
    }
    out.print("<br/>" + test);
%>
</body>
</html>
