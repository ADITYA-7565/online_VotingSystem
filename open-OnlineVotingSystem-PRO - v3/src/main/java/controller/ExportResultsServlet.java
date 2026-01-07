package controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

import com.itextpdf.text.Document;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import dao.CandidateDAO;
import dao.ElectionDAO;
import model.Candidate;
import model.User;

@WebServlet("/export-results")
public class ExportResultsServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    // ================= GET: SHOW FORMAT CHOICE PAGE =================
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("user") == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        User user = (User) session.getAttribute("user");
        if (!"ADMIN".equals(user.getRole())) {
            response.sendError(HttpServletResponse.SC_FORBIDDEN);
            return;
        }

        request.getRequestDispatcher("export-results.jsp").forward(request, response);
    }

    // ================= POST: EXPORT FILE =================
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("user") == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        User user = (User) session.getAttribute("user");
        if (!"ADMIN".equals(user.getRole())) {
            response.sendError(HttpServletResponse.SC_FORBIDDEN);
            return;
        }

        String format = request.getParameter("format");

        CandidateDAO candidateDAO = new CandidateDAO();
        ElectionDAO electionDAO = new ElectionDAO();

        List<Candidate> candidates = candidateDAO.getAllCandidates();
        String electionName = electionDAO.getActiveElectionName();

        if ("csv".equals(format)) {
            exportCSV(response, candidates, electionName);
        } else {
            exportPDF(response, candidates, electionName);
        }
    }

    // ================= CSV EXPORT =================
    private void exportCSV(HttpServletResponse response,
                           List<Candidate> candidates,
                           String electionName) throws IOException {

        String timestamp = getCurrentTimestamp();

        response.setContentType("text/csv");
        response.setHeader(
                "Content-Disposition",
                "attachment; filename=\"results_" +
                        timestamp.replace(":", "-").replace(" ", "_") +
                        ".csv\""
        );

        PrintWriter out = response.getWriter();

        out.println("Election Name," + electionName);
        out.println("Downloaded At," + timestamp);
        out.println();
        out.println("Candidate,Party,Votes");

        for (Candidate c : candidates) {
            out.println(
                    c.getName() + "," +
                    c.getParty() + "," +
                    c.getVotes()
            );
        }

        out.flush();
    }

    // ================= PDF EXPORT =================
    private void exportPDF(HttpServletResponse response,
                           List<Candidate> candidates,
                           String electionName) throws IOException {

        String timestamp = getCurrentTimestamp();

        response.setContentType("application/pdf");
        response.setHeader(
                "Content-Disposition",
                "attachment; filename=results_" +
                        timestamp.replace(":", "-").replace(" ", "_") +
                        ".pdf"
        );

        try {
            Document document = new Document();
            PdfWriter.getInstance(document, response.getOutputStream());
            document.open();

            Font titleFont = new Font(Font.FontFamily.HELVETICA, 16, Font.BOLD);
            Font metaFont = new Font(Font.FontFamily.HELVETICA, 10, Font.ITALIC);

            document.add(new Paragraph("Election Results", titleFont));
            document.add(new Paragraph("Election: " + electionName));
            document.add(new Paragraph("Downloaded At: " + timestamp, metaFont));
            document.add(new Paragraph(" "));

            PdfPTable table = new PdfPTable(3);
            table.setWidthPercentage(100);

            table.addCell("Candidate");
            table.addCell("Party");
            table.addCell("Votes");

            for (Candidate c : candidates) {
                table.addCell(c.getName());
                table.addCell(c.getParty());
                table.addCell(String.valueOf(c.getVotes()));
            }

            document.add(table);
            document.close();

        } catch (Exception e) {
            throw new IOException(e);
        }
    }

    // ================= TIMESTAMP HELPER =================
    private String getCurrentTimestamp() {
        return LocalDateTime.now()
                .format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }
}
