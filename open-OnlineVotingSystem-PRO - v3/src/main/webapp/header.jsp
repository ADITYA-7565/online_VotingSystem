<%@ page import="model.User" %>
<%@ page import="dao.ElectionDAO" %>

<%
    // Logged-in user
    User user = (User) session.getAttribute("user");

    // Page context flag (set ONLY in AdminDashboardServlet)
    Boolean isAdminPage = (Boolean) request.getAttribute("isAdminPage");

    // Fetch active election name
    ElectionDAO electionDAO = new ElectionDAO();
    String electionName = electionDAO.getActiveElectionName();
%>

<div style="
    display:flex;
    justify-content:space-between;
    align-items:center;
    padding:14px 24px;
    background:#f8f9fa;
    border-bottom:1px solid #ddd;
">

    <!-- LEFT SIDE : Election + System Name -->
    <div>
        <div style="font-size:20px; font-weight:600;">
             <%= electionName %>
        </div>
        <div style="font-size:13px; color:#555;">
            Online Voting System
        </div>
    </div>

    <!-- RIGHT SIDE : User + Actions -->
    <div style="display:flex; align-items:center; gap:12px;">

        <%-- Logged-in user info --%>
        <% if (user != null) { %>
            <span style="font-size:14px;">
                <b><%= user.getUsername() %></b>
                (<%= user.getRole() %>)
            </span>
        <% } %>

        <%--  Export Results : ADMIN + Admin Page ONLY --%>
        <% if (user != null
               && "ADMIN".equals(user.getRole())
               && Boolean.TRUE.equals(isAdminPage)) { %>

            <form action="export-results" method="get" style="margin:0;">
                <button class="btn" style="font-size:14px;">
                    Export Results
                </button>
            </form>

        <% } %>

        <%--  Reset Election : ADMIN + Admin Page ONLY --%>
        <% if (user != null
               && "ADMIN".equals(user.getRole())
               && Boolean.TRUE.equals(isAdminPage)) { %>

            <form action="reset-election" method="get" style="margin:0;">
                <button style="
                    background:#dc3545;
                    color:white;
                    padding:6px 14px;
                    border:none;
                    border-radius:4px;
                    cursor:pointer;
                    font-size:14px;
                ">
                    Reset Election
                </button>
            </form>

        <% } %>

        <%--  Logout : ALL authenticated users --%>
        <% if (user != null) { %>
            <a href="logout" class="btn">Logout</a>
        <% } %>

    </div>
</div>
