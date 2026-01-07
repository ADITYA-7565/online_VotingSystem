package dao;

import java.sql.*;
import util.DBConnection;

public class ElectionDAO {

    public String getActiveElectionName() {

        String sql = "SELECT election_name FROM elections WHERE is_active = TRUE";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            if (rs.next()) {
                return rs.getString("election_name");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return "Unknown Election";
    }
}
