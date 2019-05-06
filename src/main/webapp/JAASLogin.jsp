<%--
  Created by IntelliJ IDEA.
  User: alex
  Date: 6-5-19
  Time: 16:48
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c' %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN">
<html>
<head>
    <title>Login</title>

    <link rel="stylesheet" type="text/css" href="<%=request.getContextPath() %>/css/auth.css" />

</head>

<body>
<center>
    <div class="login">

        <form action="<%=request.getContextPath() %>/jaas" method="POST">
            <fieldset>
                <legend>Login</legend>

                <div>
                    <label for="email">Email</label>
                    <input type="text" id="email" name="email"/>
                </div>
                <div>
                    <label for="password">Password</label>
                    <input type="password" id="password" name="password"/>
                </div>

                <div class="buttonRow">
                    <input type="submit" value="Login" />
                </div>

            </fieldset>
        </form>
    </div>
</center>
</body>
</html>

