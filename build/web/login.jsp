<%-- 
    Document   : login
    Created on : Jun 3, 2025, 10:27:40 AM
    Author     : HP
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <div class="login-container">
            <h2>Login</h2>
            <form action="LoginController" method="POST">
                <label for="username">Username</label>
                <input type="text" id="username" name="username" required />
                <br>
                <label for="password">Password</label>
                <input type="password" id="password" name="password" required />
                <br>
                <button type="submit">Login</button>
                <br>
                <p style="color: red">${error}</p>
            </form>
        </div>
    </body>
</html>
