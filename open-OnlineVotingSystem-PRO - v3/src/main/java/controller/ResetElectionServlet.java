package controller;

import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;

import util.DBConnection;

@WebServlet("/reset-election")
public class ResetElectionServlet extends HttpServlet {

    /**
	 * 
	 */
	private static final long serialVersionUID = 2083058019455312473L;

	@Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Show confirmation page
        request.getRequestDispatcher("reset-election.jsp")
               .forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String electionName = request.getParameter("electionName");

        try (Connection con = DBConnection.getConnection()) {

            con.setAutoCommit(false);

            // 1️⃣ Deactivate old elections
            con.prepareStatement(
                "UPDATE elections SET is_active = FALSE"
            ).executeUpdate();

            // 2️⃣ Create new election
            PreparedStatement ps = con.prepareStatement(
                "INSERT INTO elections (election_name, is_active) VALUES (?, TRUE)"
            );
            ps.setString(1, electionName);
            ps.executeUpdate();

            // 3️⃣ Delete all votes
            con.prepareStatement("DELETE FROM votes").executeUpdate();

            // 4️⃣ Reset vote counts
            con.prepareStatement(
                "UPDATE candidates SET vote_count = 0"
            ).executeUpdate();

            con.commit();

        } catch (Exception e) {
            e.printStackTrace();
        }

        response.sendRedirect("admin");
    }
}
