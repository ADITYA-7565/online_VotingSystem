<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Login Error</title>
    <link rel="stylesheet" href="css/style.css">
</head>
<body>

<div class="container center">
    <h2 style="color:#dc3545;">❌ Login Failed</h2>

    <p style="color:#555;">
        Invalid username or password.<br>
        Please try again.
    </p>

    <form action="login" method="post">
        <input type="text" name="username" placeholder="Username" required>
        <br><br>
        <input type="password" name="password" placeholder="Password" required>
        <br><br>
        <button type="submit">Login</button>
    </form>

    <br>
    <a href="index.html">← Back to Home</a>
</div>

</body>
</html>
