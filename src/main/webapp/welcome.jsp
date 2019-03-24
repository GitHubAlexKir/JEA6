<%@page contentType="text/html" pageEncoding="UTF-8"
%><%@ taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c'
%><c:if test="${pageContext.request.userPrincipal!=null}">
        <c:redirect url="/secure/index.jsp"/>
        <!-- this will redirect if user is already logged in -->
</c:if>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
        <link rel="stylesheet" type="text/css" href="./css/auth.css" />
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Welcome Page</title>
      </head>
<body>
<div>
    <h1>secured Web Application</h1>
        <a href="<%=request.getContextPath() %>/secure/index.jsp" >go to secured page</a>
        <br/><br/><br/>
        <div class="login">
         <form action="api/auth/login" method="POST">
            <fieldset>
              <legend>Login</legend>
                  
              <div>
                <label for="email">Email</label>
                <input type="text" id="email" name="email" />
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
</div>
</body>
</html>