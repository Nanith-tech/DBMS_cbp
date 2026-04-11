package com.ipms.dao;

import com.ipms.model.Placement;
import com.ipms.util.DBConnection;

import java.sql.*;

public class PlacementDAO {

    public void addPlacement(Placement p) {
        Connection con = null;
        try {
            con = DBConnection.getConnection();
            con.setAutoCommit(false);

            String duplicateSql = "SELECT id FROM placed_students WHERE original_student_id=?";
            PreparedStatement duplicatePs = con.prepareStatement(duplicateSql);
            duplicatePs.setInt(1, p.getStudentId());
            ResultSet duplicateRs = duplicatePs.executeQuery();
            if (duplicateRs.next()) {
                con.rollback();
                return;
            }

            String sql = """
                INSERT INTO placed_students (original_student_id, name, email, branch, company_id, company_name, role, package, placed_on)
                SELECT s.id, s.name, s.email, s.branch, c.id, c.name, c.role, c.package, CURRENT_DATE()
                FROM students s
                JOIN companies c ON c.id=?
                WHERE s.id=?
            """;
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, p.getCompanyId());
            ps.setInt(2, p.getStudentId());
            ps.executeUpdate();

            String deleteSql = "DELETE FROM students WHERE id=?";
            PreparedStatement deletePs = con.prepareStatement(deleteSql);
            deletePs.setInt(1, p.getStudentId());
            deletePs.executeUpdate();

            con.commit();

        } catch (Exception e) {
            try {
                if (con != null) {
                    con.rollback();
                }
            } catch (SQLException rollbackError) {
                rollbackError.printStackTrace();
            }
            e.printStackTrace();
        } finally {
            try {
                if (con != null) {
                    con.setAutoCommit(true);
                }
            } catch (SQLException autoCommitError) {
                autoCommitError.printStackTrace();
            }
        }
    }
    public void deletePlacement(int id) {
        try {
            Connection con = DBConnection.getConnection();
            String sql = "DELETE FROM placed_students WHERE id=?";
            PreparedStatement ps = con.prepareStatement(sql);
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
                SELECT id, name AS student, company_name AS company, package, placed_on
                FROM placed_students
                ORDER BY placed_on DESC, id DESC
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
            String sql = "SELECT id FROM placed_students WHERE original_student_id=?";
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
