<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="com.etfbl.ip.rentalcompany.service.UserService" %>
<%@ page import="com.etfbl.ip.rentalcompany.beans.UserBean" %>
<%
    String loginFail = null;
    UserBean userBean = (UserBean)session.getAttribute("userBean");

    String actionParam = request.getParameter("action");
    if("logout".equals(actionParam)) {
        session.invalidate();
        response.sendRedirect(request.getContextPath() +"/pages/login.jsp");
        return;
    }

    if("POST".equalsIgnoreCase(request.getMethod()) && request.getParameter("loginSubmit")!= null) {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        if(username == null || username.trim().isEmpty() || password == null || password.isEmpty()) {
            loginFail = "Username and password are required!";
        } else {
            UserService us = new UserService();
            UserBean loggedInUser = us.login(username, password);
            if(loggedInUser != null && loggedInUser.isLoggedIn()) {
                if("MANAGER".equalsIgnoreCase(loggedInUser.getRole())) {
                    session.setAttribute("userBean", loggedInUser);
                    response.sendRedirect(request.getContextPath() + "/pages/main.jsp");
                    return;
                } else {
                    loginFail = "Access denied, please check your role in the system!";
                }
            } else {
                loginFail="Invalid username or password";
            }
        }
        request.setAttribute("loginFail", loginFail);
    }
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Rent a vehicle</title>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
<style>
header {
    background-color: #000000;
    color: #ffffff;
    padding: 16px;
    box-shadow: 0 4px 10px rgba(0,0,0,0.3);
}

.header-content {
    display: flex;
    align-items: center;
    justify-content: center;
}

.app-title {
    font-size: 20px;
    font-weight: bold;
}

.login-container {
    display: flex;
    justify-content: center;
    align-items: center;
    min-height: 100vh;
    background: white;
}

.login-card {
    width: 400px;
    border-radius: 6px;
    overflow: hidden;
    box-shadow: 0 8px 20px rgba(0, 0, 0, 0.3);
}

.login-header {
    background: linear-gradient(135deg, #1d3557, #457b9d);
    color: #fff;
    padding: 12px 16px;
    font-weight: bold;
}

.login-body {
    background: linear-gradient(135deg, #1d3557, #457b9d);
    padding: 20px;
}

.login-body label {
    color: #fff;
    font-weight: 500;
}

.login-body input {
    width: 100%;
    padding: 10px;
    border: 1px solid #ccc;
    border-radius: 10px !important;
    margin-top: 6px;
    margin-bottom: 16px;
    font-size: 14px;
}


.login-footer {
	align: center;
}
.btn-login {
    background-color: #007bff;
    color: #fff;
    border: none;
    padding: 8px 16px;
    border-radius: 4px;
    cursor: pointer;
    font-weight: 500;
}

.btn-login:hover {
    background-color: #0056b3;
}


</style>
</head>

<body>
<nav class="navbar navbar-expand-lg navbar-dark bg-dark mb-4">
        <div class="container">
            <a class="navbar-brand" href="#">Rent an Electrical Vehicle - Manager Main Page</a>
        </div>
</nav>
<main>
    <div class="login-container">
        <div class="login-card">
            <div class="login-header">
                <h5>Login</h5>
            </div>
            <div class="login-body">
                <form action="<%= request.getContextPath() %>/pages/login.jsp" method="post">
                    <% if (loginFail != null && !loginFail.isEmpty()) { %>
                        <div class="error-message">
                            <%= loginFail %>
                        </div>
                    <% } %>

                    <div class="input-group">
                        <label for="username">Username</label>
                        <input type="text" id="username" name="username" placeholder="Enter your username" required>
                    </div>

                    <div class="input-group">
                        <label for="password">Password</label>
                        <input type="password" id="password" name="password" placeholder="Enter your password" required>
                    </div>

                    <div class="login-footer">
                        <button type="submit" name="loginSubmit" value="login" class="btn-login">Login</button>
                    </div>
                </form>
            </div>
        </div>
    </div>
</main>
</body>
</html>
