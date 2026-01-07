package controller;

import dao.CandidateDAO;

import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

@WebServlet("/deleteCandidate")
public class DeleteCandidateServlet extends HttpServlet {

    /**
	 * 
	 */
	private static final long serialVersionUID = 4974574239932876140L;

	@Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        int id = Integer.parseInt(request.getParameter("id"));

        CandidateDAO dao = new CandidateDAO();
        dao.deleteCandidate(id);

        response.sendRedirect("admin");
    }
}
