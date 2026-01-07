package controller;

import dao.CandidateDAO;
import model.Candidate;

import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.List;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
@WebServlet("/results")
public class ResultsServlet extends HttpServlet {

    /**
	 * 
	 */
	private static final long serialVersionUID = 3208484495453505902L;

	@Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        CandidateDAO dao = new CandidateDAO();
        List<Candidate> candidates = dao.getAllCandidates();

        // Find winner
        Candidate winner = null;
        for (Candidate c : candidates) {
            if (winner == null || c.getVotes() > winner.getVotes()) {
                winner = c;
            }
        }

        request.setAttribute("candidates", candidates);
        request.setAttribute("winner", winner);

        RequestDispatcher rd = request.getRequestDispatcher("results.jsp");
        rd.forward(request, response);
    }
}
