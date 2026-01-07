<%@ page import="java.util.List" %>
<%@ page import="model.Candidate" %>

<!DOCTYPE html>
<html>
<head>
    <title>Admin Dashboard</title>
    <link rel="stylesheet" href="css/style.css?v=5">
    <script>
	function confirmDelete() {
    return confirm("Are you sure you want to delete this candidate?");
}


</script>
</head>
<body>
<jsp:include page="header.jsp" />

<div class="container">
    <h1>Admin Dashboard</h1>
    <h3>Manage Candidates</h3>
 

    <!-- ================= ADD CANDIDATE FORM ================= -->
 
<div class="admin-form">
    <form action="addCandidate" method="post" enctype="multipart/form-data">
        <input type="text" name="name" placeholder="Candidate Name" required>
        <input type="text" name="party" placeholder="Party Name" required>
        <input type="file" name="photo" required>
        <button type="submit" class="btn">Add Candidate</button>
    </form>
</div>

<hr class="divider">


    <!--<hr><br> -->

    <!-- ================= CANDIDATE LIST ================= -->
    <%
        List<Candidate> list =
            (List<Candidate>) request.getAttribute("candidates");

        if (list != null && !list.isEmpty()) {
            for (Candidate c : list) {
    %>
                <div class="candidate">
                    <img src="<%= c.getPhoto() %>" alt="Candidate">

                    <span>
                        <b><%= c.getName() %></b><br>
                        Votes: <%= c.getVotes() %>
                    </span>

                    <form action="deleteCandidate" method="post" onsubmit="return confirmDelete();">
   					 <input type="hidden" name="id" value="<%= c.getId() %>">
   						 <button style="background:#dc3545">Delete</button>
				   </form>
                </div>
    <%
            }
        } else {
    %>
            <p style="text-align:center; color:#777;">
                No candidates available.
            </p>
    <%
        }
    %>

</div>

</body>
</html>
