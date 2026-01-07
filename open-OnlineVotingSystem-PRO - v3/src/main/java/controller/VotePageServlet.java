package controller;

import dao.CandidateDAO;
import model.Candidate;

import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.List;

@WebServlet("/votePage")
public class VotePageServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // 🔐 STEP 7: LOGIN CHECK
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("user") == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        // Load candidates only if user is logged in
        CandidateDAO dao = new CandidateDAO();
        List<Candidate> candidates = dao.getAllCandidates();

        request.setAttribute("candidates", candidates);
        RequestDispatcher rd = request.getRequestDispatcher("vote.jsp");
        rd.forward(request, response);
    }
}
