package controller;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.CandidateDAO;
import model.User;
import util.DBConnection;

@WebServlet("/vote")
public class VoteServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // 🔐 Must be logged in
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("user") == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        // Get logged-in user
        User user = (User) session.getAttribute("user");
        int userId = user.getId();

        // Read candidateId
        int candidateId = Integer.parseInt(request.getParameter("candidateId"));

        // Store vote (DB-level enforcement)
        boolean success = storeVote(userId, candidateId);

        if (success) {
            // Fetch candidate name for success page
            CandidateDAO dao = new CandidateDAO();
            String candidateName = dao.getCandidateNameById(candidateId);

            request.setAttribute("candidateName", candidateName);
            request.getRequestDispatcher("vote-success.jsp")
                   .forward(request, response);

        } else {
            // Duplicate vote OR DB error
            response.sendRedirect("already-voted.jsp");
        }
    }

    // ================= DB LOGIC =================
    private boolean storeVote(int userId, int candidateId) {

        String insertVoteSQL =
                "INSERT INTO votes (user_id, candidate_id) VALUES (?, ?)";

        String updateCountSQL =
                "UPDATE candidates SET vote_count = vote_count + 1 WHERE candidate_id = ?";

        try (Connection con = DBConnection.getConnection()) {

            con.setAutoCommit(false); // 🔐 transaction start

            // Insert vote
            try (PreparedStatement ps1 = con.prepareStatement(insertVoteSQL)) {
                ps1.setInt(1, userId);
                ps1.setInt(2, candidateId);
                ps1.executeUpdate();
            }

            // Update candidate vote count
            try (PreparedStatement ps2 = con.prepareStatement(updateCountSQL)) {
                ps2.setInt(1, candidateId);
                ps2.executeUpdate();
            }

            con.commit(); // ✅ both succeed
            return true;

        } catch (Exception e) {
            // UNIQUE user_id violated OR DB error
            return false;
        }
    }
}
