package com.ipms.dao;

import com.ipms.util.DBConnection;

import java.sql.*;

public class PlacedStudentDAO {
    public ResultSet getAllPlacedStudents() {
        try {
            Connection con = DBConnection.getConnection();
            String sql = "SELECT * FROM placed_students ORDER BY placed_on DESC, id DESC";
            PreparedStatement ps = con.prepareStatement(sql);
            return ps.executeQuery();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public ResultSet getPlacedStudentByOriginalStudentId(int studentId) {
        try {
            Connection con = DBConnection.getConnection();
            String sql = "SELECT * FROM placed_students WHERE original_student_id=?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, studentId);
            return ps.executeQuery();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public ResultSet getPlacedStudentByEmail(String email) {
        try {
            Connection con = DBConnection.getConnection();
            String sql = "SELECT * FROM placed_students WHERE email=?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, email);
            return ps.executeQuery();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
