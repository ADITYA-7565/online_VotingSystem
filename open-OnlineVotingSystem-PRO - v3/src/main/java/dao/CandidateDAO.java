package dao;

import model.Candidate;
import util.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class CandidateDAO {

    // ✅ METHOD 1: Fetch all candidates
    public List<Candidate> getAllCandidates() {

        List<Candidate> list = new ArrayList<>();
        String sql = "SELECT * FROM candidates";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Candidate c = new Candidate();
                c.setId(rs.getInt("candidate_id"));
                c.setName(rs.getString("candidate_name"));
                c.setParty(rs.getString("party_name"));
                c.setPhoto(rs.getString("photo"));
                c.setVotes(rs.getInt("vote_count"));
                list.add(c);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    // ✅ METHOD 2: Fetch candidate name by ID
    public String getCandidateNameById(int id) {

        String name = null;
        String sql = "SELECT candidate_name FROM candidates WHERE candidate_id = ?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                name = rs.getString("candidate_name");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return name;
    }
    
    public void addCandidate(String name, String party, String photo) {

        String sql = "INSERT INTO candidates (candidate_name, party_name, photo) VALUES (?, ?, ?)";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, name);
            ps.setString(2, party);
            ps.setString(3, photo);
            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void deleteCandidate(int id) {

        String sql = "DELETE FROM candidates WHERE candidate_id = ?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);
            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
