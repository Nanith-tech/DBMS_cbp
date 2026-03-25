package com.ipms.dao;

import com.ipms.model.Placement;
import com.ipms.util.DBConnection;

import java.sql.*;

public class PlacementDAO {

    public void addPlacement(Placement p) {
        try {
            Connection con = DBConnection.getConnection();
            String sql = "INSERT INTO placements (student_id, company_id) VALUES (?, ?)";
            PreparedStatement ps = con.prepareStatement(sql);

            ps.setInt(1, p.getStudentId());
            ps.setInt(2, p.getCompanyId());

            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void deletePlacement(int id) {
        try {
            Connection con = DBConnection.getConnection();
            String sql = "DELETE FROM placements WHERE id=?";
            PreparedStatement ps = con.prepareStatement(sql);
            System.out.println("Running delete for ID: " + id);
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public ResultSet getAllPlacements() {
        try {
            Connection con = DBConnection.getConnection();
            String sql = """
                SELECT p.id, s.name AS student, c.name AS company, c.package
                FROM placements p
                JOIN students s ON p.student_id = s.id
                JOIN companies c ON p.company_id = c.id
            """;

            PreparedStatement ps = con.prepareStatement(sql);
            return ps.executeQuery();

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    public boolean isAlreadyPlaced(int studentId) {
        try {
            Connection con = DBConnection.getConnection();
            String sql = "SELECT * FROM placements WHERE student_id=?";
            PreparedStatement ps = con.prepareStatement(sql);

            ps.setInt(1, studentId);
            ResultSet rs = ps.executeQuery();

            return rs.next();

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}