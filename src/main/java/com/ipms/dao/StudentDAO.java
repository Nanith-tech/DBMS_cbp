package com.ipms.dao;

import com.ipms.model.Student;
import com.ipms.util.DBConnection;
import java.sql.*;

public class StudentDAO {
    public ResultSet getAllStudents() {
        try {
            Connection con = DBConnection.getConnection();
            String sql = "SELECT * FROM students";
            PreparedStatement ps = con.prepareStatement(sql);
            return ps.executeQuery();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public void addStudent(Student s) {
        try {
            Connection con = DBConnection.getConnection();

            String sql = "INSERT INTO students (name,email,branch) VALUES (?,?,?)";
            PreparedStatement ps = con.prepareStatement(sql);

            ps.setString(1, s.getName());
            ps.setString(2, s.getEmail());
            ps.setString(3, s.getBranch());

            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}