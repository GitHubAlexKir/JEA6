<%@ page import="java.security.Principal" %><%--
  Created by IntelliJ IDEA.
  User: alex
  Date: 6-5-19
  Time: 17:37
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
 WORKED
 <a id="logoutLink" href="<%=request.getContextPath() %>/api/jaas/owner" >Owner only</a>
 <a id="logoutLink" href="<%=request.getContextPath() %>/api/jaas/logout" >logout</a>
 <%
     Principal p = request.getUserPrincipal();
     out.write("<br/><br/>");
     if (p == null){
         out.write("<div>Principal = NULL</div>");
     }else{
         out.write("<div>Principal.getName()                 = "+p.getName()+"</div>");
         out.write("<div>request.getAuthType()               = "+request.getAuthType()+"</div>");
         out.write("<div>request.isUserInRole(Worker)          = "+request.isUserInRole("Worker") +"</div>");
         out.write("<div>request.isUserInRole(Owner)       = "+request.isUserInRole("Owner") +"</div>");
     }
 %>
</body>
</html>
