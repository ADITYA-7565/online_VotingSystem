<!DOCTYPE html>
<html>
<head>
    <title>Login</title>
    <link rel="stylesheet" href="css/style.css">
</head>
<body>
<%
    String error = request.getParameter("error");
    if ("true".equals(error)) {
%>
    <div style="
        color:#dc3545;
        background:#ffe6e6;
        padding:10px;
        border-radius:6px;
        margin-bottom:15px;
        text-align:center;">
         Invalid username or password. Please try again.
    </div>
<%
    }
%>


<div class="container">
    <h1>Login</h1>
    <h3>Online Voting System</h3>

    <form action="login" method="post" class="center">
        <input type="text" name="username" placeholder="Username" required><br><br>
        <input type="password" name="password" placeholder="Password" required><br><br>
        <button type="submit">Login</button>
    </form>
</div>

</body>
</html>
