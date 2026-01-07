<%@ page import="java.util.List" %>
<%@ page import="model.Candidate" %>

<!DOCTYPE html>
<html>
<head>
    <title>Online Voting System</title>
    <link rel="stylesheet" href="css/style.css">
</head>
<body>
<jsp:include page="header.jsp" />
<div class="container">
    <h1>Cast Your Vote</h1>
	<h3>Select one candidate</h3>

    <form action="vote" method="post">

        <%
            List<Candidate> list = (List<Candidate>) request.getAttribute("candidates");
            for (Candidate c : list) {
        %>

        <div class="candidate">
            <img src="<%= c.getPhoto() %>" />
            <span><%= c.getName() %></span>
            <button name="candidateId" value="<%= c.getId() %>">Vote</button>
        </div>

        <% } %>

    </form>
</div>

</body>
</html>
