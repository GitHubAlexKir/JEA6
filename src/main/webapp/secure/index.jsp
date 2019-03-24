<%@page import="java.security.Principal"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">

<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Secured JSP Page</title>
</head>
<body>
<h1>You are logged in.</h1>
<a id="logoutLink" href="<%=request.getContextPath() %>/admin" >admin only</a>
<a id="logoutLink" href="<%=request.getContextPath() %>/api/auth/logout" >logout</a>
<br/><br/>

<br/><br/>
<%

    Principal p = request.getUserPrincipal();
    out.write("<br/><br/>");
    if (p == null){
        out.write("<div>Principal = NULL</div>");
    }else{
        out.write("<div>Principal.getName()                 = "+p.getName()+"</div>");
        out.write("<div>request.getRemoteUser()             = "+request.getRemoteUser()+"</div>");
        out.write("<div>request.getAuthType()               = "+request.getAuthType()+"</div>");
        out.write("<div>request.isUserInRole(ADMINISTRATOR) = <h1>"+request.isUserInRole("ADMINISTRATOR") +"</h1></div>");
        out.write("<div>request.isUserInRole(USER)          = "+request.isUserInRole("USER") +"</div>");
        out.write("<div>request.isUserInRole(DEFAULT)       = "+request.isUserInRole("DEFAULT") +"</div>");
        out.write("<div>request.isUserInRole(CUSTOMER)      = "+request.isUserInRole("CUSTOMER") +"</div>");
    }

%>
</body>
</html>