package controller;

import dao.CandidateDAO;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.File;
import java.io.IOException;

@WebServlet("/addCandidate")
@MultipartConfig(
    fileSizeThreshold = 1024 * 1024 * 2,   // 2MB
    maxFileSize = 1024 * 1024 * 10,        // 10MB
    maxRequestSize = 1024 * 1024 * 50      // 50MB
)
public class AddCandidateServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // 1️⃣ Read text fields
        String name = request.getParameter("name");
        String party = request.getParameter("party");

        // 2️⃣ Read uploaded file
        Part filePart = request.getPart("photoFile");
        String fileName = filePart.getSubmittedFileName();

        // 3️⃣ Get images folder path
        String uploadPath = getServletContext().getRealPath("") + "images";
        File uploadDir = new File(uploadPath);
        if (!uploadDir.exists()) {
            uploadDir.mkdir();
        }

        // 4️⃣ Save file to /images folder
        filePart.write(uploadPath + File.separator + fileName);

        // 5️⃣ Store relative path in DB
        String photoPath = "images/" + fileName;

        // 6️⃣ Save candidate to database
        CandidateDAO dao = new CandidateDAO();
        dao.addCandidate(name, party, photoPath);

        // 7️⃣ Redirect back to admin dashboard
        response.sendRedirect("admin");
    }
}
