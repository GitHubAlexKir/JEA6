<%@page contentType="text/html" pageEncoding="UTF-8"
%><%@ taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c'
%><c:if test="${pageContext.request.userPrincipal!=null}">
        <c:redirect url="/secure/index.jsp"/>
        <!-- this will redirect if user is already logged in -->
</c:if>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Welcome Page</title>
      </head>
<body>
<div id="app">
    <router-view></router-view>
</div>

<script src="./js/app.js"></script>
</body>
</html>