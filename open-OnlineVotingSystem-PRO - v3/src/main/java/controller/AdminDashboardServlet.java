package controller;

import dao.CandidateDAO;
import model.Candidate;

import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.List;

@WebServlet("/admin")
public class AdminDashboardServlet extends HttpServlet {

    /**
	 * 
	 */
	private static final long serialVersionUID = -8764542328979073928L;

	@Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // 🔐 Role-based access control
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("user") == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        // Check ADMIN role
        model.User user = (model.User) session.getAttribute("user");
        if (!"ADMIN".equals(user.getRole())) {
            response.sendError(HttpServletResponse.SC_FORBIDDEN, "Access Denied");
            return;
        }

        // Load candidates
        CandidateDAO dao = new CandidateDAO();
        List<Candidate> candidates = dao.getAllCandidates();

        request.setAttribute("candidates", candidates);
        request.setAttribute("isAdminPage", true);
        request.getRequestDispatcher("admin-dashboard.jsp")
               .forward(request, response);
    }
}
