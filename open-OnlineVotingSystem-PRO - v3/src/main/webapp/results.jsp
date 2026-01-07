<%@ page import="java.util.List" %>
<%@ page import="model.Candidate" %>

<!DOCTYPE html>
<html>
<head>
    <title>Voting Results</title>
    <link rel="stylesheet" href="css/style.css">
    <style>
        .winner {
            border: 2px solid green;
            background: #eaffea;
        }
    </style>
</head>
<body>
<jsp:include page="header.jsp" />
<div class="container">
    <h1>Election Results</h1>

    <%
        List<Candidate> list = (List<Candidate>) request.getAttribute("candidates");
        Candidate winner = (Candidate) request.getAttribute("winner");
    %>

    <% for (Candidate c : list) { %>
        <div class="candidate <%= (winner != null && c.getId() == winner.getId()) ? "winner" : "" %>">
            <img src="<%= c.getPhoto() %>" alt="Candidate">
            <span>
                <b><%= c.getName() %></b><br>
                Votes: <%= c.getVotes() %>
            </span>
        </div>
    <% } %>

    <% if (winner != null) { %>
        <h2> Winner: <%= winner.getName() %></h2>
    <% } %>

    <a href="votePage">Back to Voting</a>
</div>

</body>
</html>
